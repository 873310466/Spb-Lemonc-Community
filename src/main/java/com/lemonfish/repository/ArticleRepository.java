package com.lemonfish.repository;

import com.lemonfish.entity.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Chao Zhang
 * @version V1.0
 * @Package com.lemonfish.repository
 * @date 2020/5/31 11:38
 */
@Repository
public interface ArticleRepository extends ElasticsearchRepository<Article,Long> {

}
