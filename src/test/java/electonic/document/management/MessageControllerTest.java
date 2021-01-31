package electonic.document.management;

import org.junit.Test;
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

import static electonic.document.management.LoginTest.login;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/delete-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private void getAllMessagesInTaskThreeAndExpectString(Cookie authorization, String expectedString) throws Exception {
        this.mockMvc.perform(get("/messages/in_task/3").cookie(authorization))
                .andDo(print())
                .andExpect(content().string(containsString(expectedString)));
    }

    @Test
    public void testGetAllMessagesInTask() throws Exception {
        Cookie authorization = login(this.mockMvc, "1", "1");
        getAllMessagesInTaskThreeAndExpectString(authorization, "hello message hi privet");
    }

    @Test
    public void testMessageCreation() throws Exception {
        Cookie authorization = login(this.mockMvc, "1", "1");
        this.mockMvc.perform(post("/messages")
                .param("text", "test message")
                .param("task", "3")
                .cookie(authorization))
                .andDo(print())
                .andExpect(content().string(containsString("Message was successfully created")));
        getAllMessagesInTaskThreeAndExpectString(authorization, "test message");
    }

    @Test
    public void testEditMessage() throws Exception {
        Cookie authorization = login(this.mockMvc, "1", "1");
        this.mockMvc.perform(patch("/messages/8")
                .param("text", "new text in message")
                .cookie(authorization))
                .andDo(print())
                .andExpect(content().string(containsString("Message was successfully edited")));
        getAllMessagesInTaskThreeAndExpectString(authorization, "new text in message");
    }

    @Test
    public void testGetMessage() throws Exception {
        Cookie authorization = login(this.mockMvc, "1", "1");
        this.mockMvc.perform(get("/messages/8")
                .cookie(authorization))
                .andDo(print())
                .andExpect(content().string(containsString("\"id\":8,\"text\":\"hello message hi privet\"," +
                        "\"creationDate\":\"2020-12-18 19:26:57\",\"author\":{\"id\":1,\"username\":\"1\"}")));
    }

    @Test
    public void deleteTask() throws Exception {
        Cookie authorization = login(this.mockMvc, "1", "1");
        this.mockMvc.perform(delete("/messages/8")
                .cookie(authorization))
                .andDo(print())
                .andExpect(content().string(containsString("Message was successfully deleted")));
        this.mockMvc.perform(get("/messages/in_task/3").cookie(authorization))
                .andDo(print())
                .andExpect(content().string(not(containsString("hello message hi privet"))));
    }

    @Test
    public void testDateInMessage() throws Exception {
        Cookie authorization = login(this.mockMvc, "1", "1");
        this.mockMvc.perform(post("/messages")
                .param("text", "test message")
                .param("task", "3")
                .cookie(authorization))
                .andDo(print())
                .andExpect(content().string(containsString("Message was successfully created")));
        getAllMessagesInTaskThreeAndExpectString(authorization, "test message");
    }
}
