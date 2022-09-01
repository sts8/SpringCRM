package com.sts8.springcrm.service;

import com.sts8.springcrm.repository.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ArticleServiceTest {

    private ArticleRepository articleRepository;
    private ArticleService articleService;

    @BeforeEach
    void setUp() {
        articleRepository = mock(ArticleRepository.class);
        articleService = new ArticleService(articleRepository, null);
    }

    @Test
    void getArticleListingPage() {

        when(articleRepository.count()).thenReturn(234L);

        int expectedLastPageNumber = 23;
        int lastPageNumber = articleService.getLastArticlePageNumber();

        assertEquals(expectedLastPageNumber, lastPageNumber);
    }

}
