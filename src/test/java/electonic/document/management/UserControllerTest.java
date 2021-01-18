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
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAddUser() throws Exception {
        this.mockMvc.perform(post("/users")
                .param("username", "3")
                .param("password", "3")
                .param("telephoneNumber", "3"))
                .andDo(print())
                .andExpect(content().string(containsString("user was registered")));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        Cookie authorization = login(this.mockMvc, "1", "1");
        this.mockMvc.perform(get("/users")
                .cookie(authorization))
                .andDo(print())
                .andExpect(content().string(containsString("{\"id\":1,\"username\":\"1\"}")));
    }

    @Test
    public void testSetRole() throws Exception {
        Cookie authorization = login(this.mockMvc, "1", "1");
        this.mockMvc.perform(post("/users/2/roles")
                .param("role", "ADMIN")
                .cookie(authorization))
                .andDo(print())
                .andExpect(content().string(containsString("User role was successfully added!")));
    }


    @Test
    public void testDeleteUser() throws Exception {
        Cookie authorization = login(this.mockMvc, "1", "1");
        this.mockMvc.perform(delete("/users/2")
                .cookie(authorization))
                .andDo(print())
                .andExpect(content().string(containsString("U can't delete another user")));
        this.mockMvc.perform(delete("/users/1")
                .cookie(authorization))
                .andDo(print())
                .andExpect(content().string(containsString("User was successfully deleted")));
    }

}
