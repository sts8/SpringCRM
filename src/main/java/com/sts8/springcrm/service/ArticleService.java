package com.sts8.springcrm.service;

import com.sts8.springcrm.dto.ArticleAddDto;
import com.sts8.springcrm.dto.ArticleListingDto;
import com.sts8.springcrm.model.Article;
import com.sts8.springcrm.repository.ArticleRepository;
import com.sts8.springcrm.repository.OrderContainsArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {

    private static final int PAGINATION_ELEMENTS_PER_PAGE = 10;

    private final ArticleRepository articleRepository;
    private final OrderContainsArticleRepository containsRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository, OrderContainsArticleRepository containsRepository) {
        this.articleRepository = articleRepository;
        this.containsRepository = containsRepository;
    }

    public Page<ArticleListingDto> getArticleListingPage(String page) throws IllegalArgumentException {

        int pageNo;

        try {
            pageNo = Integer.parseInt(page);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Page number invalid!");
        }

        if (pageNo < 0) {
            throw new IllegalArgumentException("Page number < 0!");
        }

        Pageable pageable = PageRequest.of(pageNo, PAGINATION_ELEMENTS_PER_PAGE);
        Page<Article> articlePage = articleRepository.findAll(pageable);

        List<ArticleListingDto> articles = new ArrayList<>();

        for (Article article : articlePage) {
            articles.add(
                    new ArticleListingDto(
                            article.getId(),
                            article.getName(),
                            containsRepository.existsByArticleId(article.getId())));
        }

        return new PageImpl<>(articles, pageable, articlePage.getTotalElements());
    }

    public int getLastArticlePageNumber() {
        long articleCount = articleRepository.count();
        return (int) Math.ceil(articleCount / (double) PAGINATION_ELEMENTS_PER_PAGE) - 1;
    }

    public void addArticle(ArticleAddDto articleAddDto) {
        Article newArticle = new Article();
        newArticle.setName(articleAddDto.getName());

        articleRepository.save(newArticle);
    }

    public void deleteArticle(int id) throws IllegalArgumentException, IllegalStateException {

        if (!articleRepository.existsById(id)) {
            throw new IllegalArgumentException("Specified article does not exist!");
        }

        articleRepository.deleteById(id);

        if (articleRepository.existsById(id)) {
            throw new IllegalStateException("Article could not be deleted!");
        }
    }

    public List<ArticleListingDto> getAllArticles() {

        List<ArticleListingDto> allArticles = new ArrayList<>();
        Iterable<Article> articles = articleRepository.findAll();

        for (Article article : articles) {
            allArticles.add(
                    new ArticleListingDto(
                            article.getId(),
                            article.getName(),
                            false));
        }

        return allArticles;
    }

}
