package electonic.document.management;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import electonic.document.management.model.Department;
import electonic.document.management.model.Views;
import electonic.document.management.utils.DepartmentUtils;
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
import java.util.List;

import static electonic.document.management.LoginTest.login;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-before.sql", "/create-before-department.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/delete-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class DepartmentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateDepartment() throws Exception {
        Cookie authorizationWithAuthorities = login(this.mockMvc, "1", "1");
        Cookie authorization = login(this.mockMvc, "2", "2");
        this.mockMvc.perform(post("/departments")
                .param("name", "17")
                .cookie(authorization))
                .andDo(print())
                .andExpect(status().is4xxClientError());
        this.mockMvc.perform(post("/departments")
                .param("name", "17")
                .param("parentId", "13")
                .cookie(authorizationWithAuthorities))
                .andDo(print());
        this.mockMvc.perform(get("/departments")
                .cookie(authorization))
                .andDo(print())
                .andExpect(content().string(containsString("\"name\":\"17\"," +
                        "\"departmentStaff\":[],\"level\":5,\"parentId\":13,\"leftKey\":17,\"rightKey\":18")))
                .andExpect(content().string(containsString("\"name\":\"4\"," +
                        "\"departmentStaff\":[],\"level\":2,\"parentId\":1,\"leftKey\":26,\"rightKey\":33")));
    }

    @Test
    public void testGetAllDepartments() throws Exception {
        Cookie authorization = login(this.mockMvc, "1", "1");
        this.mockMvc.perform(get("/departments")
                .cookie(authorization))
                .andDo(print())
                .andExpect(content().string(containsString("\"name\":\"5\"," +
                        "\"departmentStaff\":[],\"level\":3,\"parentId\":2,\"leftKey\":3,\"rightKey\":8")));
    }

    @Test
    public void testDeleteDepartment() throws Exception {
        Cookie authorization = login(this.mockMvc, "1", "1");
        this.mockMvc.perform(delete("/departments/7")
                .cookie(authorization))
                .andDo(print())
                .andExpect(content().string(containsString("department was successfully deleted")));
        this.mockMvc.perform(get("/departments")
                .cookie(authorization))
                .andDo(print())
                .andExpect(content().string(containsString("\"name\":\"1\"," +
                        "\"departmentStaff\":[],\"level\":1,\"parentId\":1,\"leftKey\":1,\"rightKey\":24")))
                .andExpect(content().string(containsString("\"name\":\"10\"," +
                        "\"departmentStaff\":[],\"level\":4,\"parentId\":5,\"leftKey\":4,\"rightKey\":5")));
    }

    @Test
    public void testMoveDepartmentUp() throws Exception {
        Cookie authorization = login(this.mockMvc, "1", "1");
        this.mockMvc.perform(patch("/departments/9/11")
                .cookie(authorization))
                .andDo(print());
        MvcResult mvcResult = this.mockMvc.perform(get("/departments")
                .cookie(authorization))
                .andDo(print())
                .andExpect(content().string(containsString("\"name\":\"9\"," +
                        "\"departmentStaff\":[],\"level\":3,\"parentId\":11,\"leftKey\":7,\"rightKey\":12")))
                .andExpect(content().string(containsString("\"name\":\"3\"," +
                        "\"departmentStaff\":[],\"level\":2,\"parentId\":1,\"leftKey\":16,\"rightKey\":29")))
                .andReturn();
        printTreeOutputToConsole(mvcResult.getResponse().getContentAsString());

    }

    @Test
    public void testMoveDepartmentDown() throws Exception {
        Cookie authorization = login(this.mockMvc, "1", "1");
        this.mockMvc.perform(patch("/departments/7/16")
                .cookie(authorization))
                .andDo(print());
        MvcResult mvcResult = this.mockMvc.perform(get("/departments")
                .cookie(authorization))
                .andDo(print())
                .andExpect(content().string(containsString("\"name\":\"7\"," +
                        "\"departmentStaff\":[],\"level\":3,\"parentId\":16,\"leftKey\":20,\"rightKey\":27")))
                .andExpect(content().string(containsString("\"name\":\"3\"," +
                        "\"departmentStaff\":[],\"level\":2,\"parentId\":1,\"leftKey\":10,\"rightKey\":15")))
                .andReturn();
        printTreeOutputToConsole(mvcResult.getResponse().getContentAsString());

    }

    private void printTreeOutputToConsole(String tree) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

        try {
            List<Department> departments = objectMapper
                    .readerWithView(Views.FullClass.class)
                    .forType(new TypeReference<List<Department>>() {
                    })
                    .readValue(tree);
            DepartmentUtils.printTreeToSystemOut(departments);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
