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
public class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testTaskCreation() throws Exception {
        Cookie authorization = login(this.mockMvc, "1", "1");
        this.mockMvc.perform(post("/tasks")
                .param("name", "1")
                .param("taskDescription", "1")
                .cookie(authorization))
                .andDo(print())
                .andExpect(content().string(containsString("Task was successfully created")));

    }

    @Test
    public void testEditTask() throws Exception {
        Cookie authorization = login(this.mockMvc, "1", "1");
        this.mockMvc.perform(patch("/tasks/3")
                .param("task_name", "ett")
                .cookie(authorization))
                .andDo(print())
                .andExpect(content().string(containsString("Task was successfully edited")));
    }

    @Test
    public void testGetTask() throws Exception {
        Cookie authorization = login(this.mockMvc, "1", "1");
        this.mockMvc.perform(get("/tasks/3")
                .cookie(authorization))
                .andDo(print())
                .andExpect(content().string(containsString("eee")));
    }

    @Test
    public void testReviewTask() throws Exception {
        Cookie authorization = login(this.mockMvc, "1", "1");
        this.mockMvc.perform(patch("/tasks/3/ready_to_review")
                .cookie(authorization))
                .andDo(print())
                .andExpect(content().string(containsString("Task was marked as ready to review")));
    }

    @Test
    public void deleteTask() throws Exception {
        Cookie authorization = login(this.mockMvc, "1", "1");
        this.mockMvc.perform(delete("/tasks/3")
                .cookie(authorization))
                .andDo(print())
                .andExpect(content().string(containsString("Task was successfully deleted")));
    }

    @Test
    public void testGetAllTasks() throws Exception {
        Cookie authorization = login(this.mockMvc, "1", "1");
        this.mockMvc.perform(get("/tasks").cookie(authorization))
                .andDo(print())
                .andExpect(content().string(containsString("eee")));
    }


}
