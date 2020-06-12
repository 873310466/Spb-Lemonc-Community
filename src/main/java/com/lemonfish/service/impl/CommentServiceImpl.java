package com.lemonfish.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lemonfish.dto.CommentDTO;
import com.lemonfish.entity.*;
import com.lemonfish.enumcode.CodeEnum;
import com.lemonfish.enumcode.CommentTypeEnum;
import com.lemonfish.enumcode.NotificationActionEnum;
import com.lemonfish.enumcode.NotificationTypeEnum;
import com.lemonfish.exception.BaseException;
import com.lemonfish.mapper.ArticleMapper;
import com.lemonfish.mapper.CommentMapper;
import com.lemonfish.mapper.NotificationMapper;
import com.lemonfish.mapper.UserMapper;
import com.lemonfish.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemonfish.util.TreeUtils;
import com.lemonfish.util.ValidateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *  * <p>
 *  * 评论表 服务实现类
 *  * </p>
 *  *
 *  * @author LemonFish
 *  * @since 2020-05-08
 *  
 */
@Service
@Transactional
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {


    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NotificationMapper notificationMapper;



    /**
     * 保存评论
     * @param comment
     * @return
     */
    @Override
    public boolean saveComment(Comment comment) {
        if (comment.getParentId() != null || comment.getArticleId() != null) {
            // 添加
            articleMapper.incrViewComment(comment.getArticleId(), 1);
            // 回复文章
            if (comment.getType().equals(CommentTypeEnum.REPLY_ARTICLE.getType())) {
                Article article = articleMapper.selectById(comment.getArticleId());
                if (article == null) {
                    throw new BaseException(CodeEnum.FAIL_NOT_FOUND);
                }
                // 添加评论
                this.save(comment);
                // 发布通知
                if (!comment.getCommentatorId().equals(article.getAuthorId())) {
                    postNotificationToArticle(comment, article);
                }
                return true;
            }

            // 回复评论
            if (comment.getType().equals(CommentTypeEnum.REPLY_COMMENT.getType())) {
                // 判断目标评论是否为空
                Comment targetComment = commentMapper.selectById(comment.getTargetId());
                if (targetComment == null) {
                    throw new BaseException(CodeEnum.FAIL_NOT_FOUND);
                }
                // 添加评论
                this.save(comment);
                // 发布通知
                if (!comment.getCommentatorId().equals(targetComment.getCommentatorId())) {
                    postNotificationToComment(comment, targetComment);
                }
                return true;
            }
        }
        return false;

    }

    private void postNotificationToComment(Comment comment, Comment targetComment) {
        Notification notification = new Notification();
        notification
                .setNotifierId(comment.getCommentatorId())
                .setNotifyAction(NotificationActionEnum.REPLY_COMMENT.getAction())
                .setNotifyContent(comment.getContent())
                .setReceiverId(targetComment.getCommentatorId())
                .setTargetTitle(targetComment.getContent())
                .setTargetId(comment.getArticleId())
                .setTargetType(NotificationTypeEnum.NOTIFY_COMMENT.getType());
        notificationMapper.insert(notification);
    }

    private void postNotificationToArticle(Comment comment, Article article) {
        Notification notification = new Notification();
        notification
                .setNotifierId(comment.getCommentatorId())
                .setNotifyAction(NotificationActionEnum.REPLY_ARTICLE.getAction())
                .setNotifyContent(comment.getContent())
                .setReceiverId(article.getAuthorId())
                .setTargetTitle(article.getTitle())
                .setTargetId(comment.getArticleId())
                .setTargetType(NotificationTypeEnum.NOTIFY_ARTICLE.getType());
        notificationMapper.insert(notification);
    }


    /**
     * 通过文章ID 获取 相应的评论
     *
     * @param id
     * @return
     */
    @Override
    public List<Comment> getComments(Long id) {
        // 校验id
        ValidateUtils.validateId(id);
        // 设置条件，按时间倒序·按文章ID
        LambdaQueryWrapper<Comment> lqw = new LambdaQueryWrapper<>();
        lqw.orderByDesc(BaseEntity::getCreatedTime)
                .eq(Comment::getArticleId, id);
        List<Comment> commentList;

        // 1. 找到符合要求的评论，即一级评论（第一次数据库访问）
        commentList = this.list(lqw);

        // 2. 对评论进行封装，返回给前端需要的数据
        return commentList.size() > 0 ? getCommentsDTO(commentList) : commentList;

    }

    /**
     * 封装一个，根据Comment封装成CommentDTO的方法
     *
     * @param commentList
     * @return List<Comment>
     */

    private List<Comment> getCommentsDTO(List<Comment> commentList) {
        // 1.获取去重的用户id，为了后面进行 评论|用户 的匹配
        Set<Long> commentators = commentList.stream().map(Comment::getCommentatorId).collect(Collectors.toSet());

        // 2. 根据用户id一次性搜索出来（以放多次重复访问数据库）
        List<User> users = userMapper.selectBatchIds(commentators);

        // 3. 把查出来的用户根据ID 存储到Map中，方便评论进行一一匹配,同时把评论存储到Map中，为了二级评论匹配目标使用

        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(User::getId, user -> user));
        Map<Long, Comment> commentMap = commentList.stream().collect(Collectors.toMap(Comment::getId, comment -> comment));
        // 4. 评论和用户进行匹配
        // 通过comment组装commentDTO
        List<Comment> result = commentList.stream().map(comment -> {

            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            if (commentDTO.getType().equals(CommentTypeEnum.REPLY_COMMENT.getType())) {
                // 找到回复的评论
                Comment commentTarget = commentMap.get(commentDTO.getTargetId());
                // 设置评论的目标用户
                commentDTO.setTargetUser(userMap.get(commentTarget.getCommentatorId()));
            }
            //设置发出评论的人
            commentDTO.setUser(userMap.get(commentDTO.getCommentatorId()));
            return commentDTO;
        }).collect(Collectors.toList());

        // 建立树形结构
        return TreeUtils.buildTreePlus(result);
    }


}


