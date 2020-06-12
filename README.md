# 柠檬C社区

柠檬C社区是仿照**掘金社区**进行设计和开发的交流社区，目前功能主要为第三方登录、发表文章、文章搜索、点赞、评论、收藏、消息通知、排行榜······

### 目前功能：
1、使用Oauth完成第三方登录，同时完成第三方用户--本地用户的映射关系，解决用户唯一的难点。

2、完成社区的发文章功能，包括展示Markdown格式和使用OSS上传图片

3、使用Redis的zset完成排行榜的设计

4、使用Redis的HyperLogLog完成浏览量的并发处理

5、使用Redis完成数据的缓存

**`6、使用ElasticSearch完成文章高亮搜索功能（Review √）`**

博客：[掘金社区](https://juejin.im/post/5edf13d16fb9a04797068bc7)

![](https://typora-imgzc.oss-cn-beijing.aliyuncs.com/typora_img/20200610212733.gif)


7、使用Logstash完成ElasticSearch和Mysql数据的同步

8、使用拦截器校验token完成权限控制

9、完成点赞、评论、消息通知功能

10、利用文章标签完成相关问题的展示

11、······
