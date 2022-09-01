package com.sts8.springcrm.repository;

import com.sts8.springcrm.model.Article;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ArticleRepository extends PagingAndSortingRepository<Article, Integer> {

}
