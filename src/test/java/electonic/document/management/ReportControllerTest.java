package electonic.document.management;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.servlet.http.Cookie;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-before-report.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/delete-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class ReportControllerTest {
    @Autowired
    private MockMvc mockMvc;

    //TODO remove this same code in all tests
    public Cookie login() throws Exception {
        MvcResult resultActions = this.mockMvc.perform(post("/login").param("username", "1")
                .param("password", "1"))
                .andDo(print())
                .andExpect(cookie().exists("Authorization")).andReturn();
        return resultActions.getResponse().getCookie("Authorization");
    }

    @Test
    void printReport() throws Exception {
        Cookie authorization = login();
        this.mockMvc.perform(get("/reports/eee")
                .cookie(authorization))
                .andDo(print())
                .andExpect(content().string(containsString("edm task test")))
                .andExpect(content().string(containsString("FEBRUARY")))
                .andExpect(content().string(containsString("Aidar")));
    }
}