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

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/delete-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class DepartmentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    //TODO this same code in all tests
    public Cookie login(String username, String password) throws Exception {
        MvcResult resultActions = this.mockMvc.perform(post("/login").param("username", username)
                .param("password", password))
                .andDo(print())
                .andExpect(cookie().exists("Authorization")).andReturn();
        return resultActions.getResponse().getCookie("Authorization");
    }

//    @Test
//    public void testCreateDepartment() throws Exception {
//        Cookie authorizationWithAuthorities = login("1", "1");
//        Cookie authorization = login("2", "2");
//        this.mockMvc.perform(post("/departments")
//                .param("name", "test department")
//                .cookie(authorization))
//                .andDo(print())
//                .andExpect(status().is4xxClientError());
//        this.mockMvc.perform(post("/departments")
//                .param("name", "test department")
//                .cookie(authorizationWithAuthorities))
//                .andDo(print())
//                .andExpect(content().string(containsString("Department already exists!")));
//        this.mockMvc.perform(post("/departments")
//                .param("name", "test department number 2")
//                .cookie(authorizationWithAuthorities))
//                .andDo(print())
//                .andExpect(content().string(containsString("Department successfully created!")));
//    }
//
//    @Test
//    public void testGetAllDepartments() throws Exception {
//        Cookie authorization = login("1", "1");
//        this.mockMvc.perform(get("/departments")
//                .cookie(authorization))
//                .andDo(print())
//                .andExpect(content().string(containsString("\"id\":4,\"name\":\"test department\"")));
//    }
//
//    @Test
//    public void testDeleteDepartment() throws Exception {
//        Cookie authorization = login("1", "1");
//        this.mockMvc.perform(delete("/departments/4")
//                .cookie(authorization))
//                .andDo(print())
//                .andExpect(content().string(containsString("department was successfully deleted")));
//    }
}
