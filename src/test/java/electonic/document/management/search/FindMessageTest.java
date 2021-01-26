package electonic.document.management.search;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;

import static electonic.document.management.LoginTest.login;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-before.sql", "/create-before-search.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/delete-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class FindMessageTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testFindMessageByAuthor() throws Exception {
        Cookie authorization = login(this.mockMvc, "1", "1");
        this.mockMvc.perform(get("/messages/with_params")
                .param("author_id", "2")
                .cookie(authorization))
                .andDo(print())
                .andExpect(content().string(not(containsString("\"author\":{\"id\":1"))));
    }

    @Test
    public void testFindMessageByText() throws Exception {
        Cookie authorization = login(this.mockMvc, "1", "1");
        this.mockMvc.perform(get("/messages/with_params")
                .param("text_contains", "hello message hi privet")
                .cookie(authorization))
                .andDo(print())
                .andExpect(jsonPath("$.*", hasSize(3)));
    }

    @Test
    public void testFindMessageByTextAndAuthor() throws Exception {
        Cookie authorization = login(this.mockMvc, "1", "1");
        this.mockMvc.perform(get("/messages/with_params")
                .param("text_contains", "hello message hi privet")
                .param("author_id", "2")
                .cookie(authorization))
                .andDo(print())
                .andExpect(jsonPath("$.*", hasSize(1)));
    }

    @Test
    public void testFindMessageByTask() throws Exception {
        Cookie authorization = login(this.mockMvc, "1", "1");
        this.mockMvc.perform(get("/messages/with_params")
                .param("task_id", "20")
                .cookie(authorization))
                .andDo(print())
                .andExpect(jsonPath("$.*", hasSize(2)));
    }
}
