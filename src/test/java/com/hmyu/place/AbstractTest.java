package com.hmyu.place;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hmyu.place.exception.ExceptionControllerAdvice;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("local")
@WebAppConfiguration
public class AbstractTest {
    protected MockMvc mockMvc;
    protected ObjectMapper om;

    protected <T> void setup(T controller) {
        System.setProperty("Globals.Secret", "2d7ece7153934e3f91d930cbe6a01661");

        this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(ExceptionControllerAdvice.class)
                .addFilter(new CharacterEncodingFilter(StandardCharsets.UTF_8.name(), true))
                .alwaysDo(print())
                .build();
        this.om = new ObjectMapper();
    }
}
