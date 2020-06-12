package com.lemonfish.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lemonfish.entity.Article;
import com.lemonfish.entity.Collection;
import com.lemonfish.enumcode.CodeEnum;
import com.lemonfish.service.CollectionService;
import com.lemonfish.util.MyJsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  *
 *  * @author LemonFish
 *  * @since 2020-05-13
 *  
 */
@RestController
@RequestMapping("/collection")
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    // 写到这里突然意识到用Collection起名不太好- - ，算了就这样吧，意为收藏夹

    /**
     * 创建收藏夹
     */
    @PostMapping("/")
    public MyJsonResult createCollection(@RequestBody Collection collection) {
        boolean result = collectionService.save(collection);
        return result ? MyJsonResult.success(collection) : MyJsonResult.fail(CodeEnum.FAIL_OPERATION);
    }

    /**
     * 更新收藏夹
     */
    @PutMapping("/")
    public MyJsonResult updateCollection(@RequestBody Collection collection) {

        boolean res = collectionService.updateOne(collection);
        return res ? MyJsonResult.success() : MyJsonResult.fail(CodeEnum.FAIL_OPERATION);
    }


    /**
     * 删除整个收藏夹
     */
    @DeleteMapping("/{id}")
    public MyJsonResult deleteCollection(@PathVariable("id")Long cid){
        boolean res = collectionService.deleteCollection(cid);
        return res ? MyJsonResult.success(CodeEnum.DELETE_OK) : MyJsonResult.fail(CodeEnum.FAIL_OPERATION);
    }


    /**
     * 根据用户ID获取收藏夹
     * @param id
     * @return
     */
    @GetMapping("/{uid}")
    public MyJsonResult getUserCollection(@PathVariable("uid") Long id) {
        List<Collection> userCollection = collectionService.getUserCollection(id);
        return MyJsonResult.success(userCollection);
    }

    /**
     * 判断改用户是收藏了该文章
     * @param articleId
     * @param userId
     * @return
     */
    @GetMapping("/has")
    public MyJsonResult getUserHasCollection(@RequestParam("aid") Long articleId,
                                             @RequestParam("uid") Long userId) {
        List<Collection> userHasCollection = collectionService.getUserHasCollection(articleId, userId);
        return MyJsonResult.success(userHasCollection);
    }

    /**
     * 获取ID为xx的收藏夹信息
     * @param cid
     * @return
     */
    @GetMapping("/c/{cid}")
    public MyJsonResult getCollectionById(@PathVariable("cid") Long cid) {
        Collection c = collectionService.getCollectionById(cid);
        return c != null ? MyJsonResult.success(c) : MyJsonResult.fail(CodeEnum.FAIL_NOT_FOUND);
    }

}
