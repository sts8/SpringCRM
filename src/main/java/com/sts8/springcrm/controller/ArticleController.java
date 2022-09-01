package com.sts8.springcrm.controller;

import com.sts8.springcrm.dto.ArticleAddDto;
import com.sts8.springcrm.dto.ArticleListingDto;
import com.sts8.springcrm.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/articles")
    public String articleListing(Model model, @RequestParam(defaultValue = "0") String page) {

        Page<ArticleListingDto> articlePage;

        try {
            articlePage = articleService.getArticleListingPage(page);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMsg", e.getMessage());
            articlePage = articleService.getArticleListingPage("0");
        }

        if (articlePage.isEmpty()) {
            model.addAttribute("generalMsg", "No articles!");
        }

        model.addAttribute("page", articlePage);
        return "list-articles";
    }

    @GetMapping("/articles/add")
    public String addArticle(Model model) {
        model.addAttribute("articleAddDto", new ArticleAddDto());
        return "add-article";
    }

    @PostMapping("/articles/add")
    public String addArticleSubmit(RedirectAttributes redirectAttributes, @ModelAttribute ArticleAddDto articleAddDto) {

        articleService.addArticle(articleAddDto);
        redirectAttributes.addFlashAttribute("successMsg", "Article added successfully!");
        redirectAttributes.addAttribute("page", articleService.getLastArticlePageNumber());

        return "redirect:/articles";
    }

    @GetMapping("/articles/{id}/delete")
    public String deleteArticle(RedirectAttributes redirectAttributes, @PathVariable int id) {

        try {
            articleService.deleteArticle(id);
            redirectAttributes.addFlashAttribute("successMsg", "Article deleted successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMsg", e.getMessage());
        }

        return "redirect:/articles";
    }

}
