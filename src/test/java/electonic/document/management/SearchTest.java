package electonic.document.management;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;

import static electonic.document.management.LoginTest.login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-before.sql", "/create-before-search.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/delete-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class SearchTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testMessageSearch() throws Exception {
        Cookie authorization = login(this.mockMvc, "1", "1");
        MockMultipartFile jsonFile = new MockMultipartFile("test.json", "",
                "application/json", "{\"key1\": \"value1\"}".getBytes());
        this.mockMvc.perform(get("/messages/with_params")
                .param("text_contains", "hello")
                .param("author_id", "2")
                .cookie(authorization))
                .andDo(print());
    }

}
