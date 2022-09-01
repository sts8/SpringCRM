package com.sts8.springcrm;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {"spring.sql.init.mode=never"})
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class SpringCrmApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testMainGet() throws Exception {
        mockMvc.perform(get("/"))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Welcome to the Spring CRM!")));
    }

    @Test
    @DirtiesContext
    public void testArticleAddAndListing() throws Exception {

        mockMvc.perform(post("/articles/add").param("name", "some new Article"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("successMsg", "Article added successfully!"));

        mockMvc.perform(get("/articles"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<span>1</span>")))
                .andExpect(content().string(containsString("<span>some new Article</span>")));
    }

}
