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
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/delete-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class DocumentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testUploadFile() throws Exception {
        Cookie authorization = login(this.mockMvc, "1", "1");
        MockMultipartFile jsonFile = new MockMultipartFile("test.json", "",
                "application/json", "{\"key1\": \"value1\"}".getBytes());
        this.mockMvc.perform(multipart("/documents")
                .file("file", jsonFile.getBytes())
                .param("task_id", "3")
                .cookie(authorization))
                .andDo(print())
                .andExpect(content().string(containsString("Document was successfully uploaded!")));
    }

    @Test
    public void testGetAllDocumentNames() throws Exception {
        Cookie authorization = login(this.mockMvc, "1", "1");
        this.mockMvc.perform(get("/documents")
                .cookie(authorization))
                .andDo(print())
                .andExpect(content().string(containsString("test document")));
    }

    @Test
    public void testEditDocument() throws Exception {
        MockMultipartFile jsonFile = new MockMultipartFile("test.json", "",
                "application/json", "{\"key2\": \"value2\"}".getBytes());
        Cookie authorization = login(this.mockMvc, "1", "1");
        this.mockMvc.perform(multipart("/documents/5")
                .file("new_file", jsonFile.getBytes())
                .param("commitMessage", "test commit message")
                .cookie(authorization))
                .andDo(print())
                .andExpect(content().string(containsString("test commit message")));
    }

    @Test
    public void testDeleteDocument() throws Exception {
        Cookie authorization = login(this.mockMvc, "1", "1");
        this.mockMvc.perform(delete("/documents/5")
                .cookie(authorization))
                .andDo(print())
                .andExpect(content().string(containsString("Document was successfully deleted")));
    }
}
