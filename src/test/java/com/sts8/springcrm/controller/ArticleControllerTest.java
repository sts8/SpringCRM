package com.sts8.springcrm.controller;

import com.sts8.springcrm.dto.ArticleListingDto;
import com.sts8.springcrm.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(ArticleController.class)
class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleService articleService;

    @Test
    public void testGetArticleListing() throws Exception {

        ArticleListingDto dummyDto = new ArticleListingDto(999, "some article", true);
        Page<ArticleListingDto> dummyPage = new PageImpl<>(List.of(dummyDto));

        when(articleService.getArticleListingPage(anyString()))
                .thenReturn(dummyPage);

        mockMvc.perform(get("/articles"))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("list-articles"))
                .andExpect(content().string(containsString("999")))
                .andExpect(content().string(containsString("some article")))
                .andExpect(content().string(containsString("Article is part of an order!")));
    }

}
