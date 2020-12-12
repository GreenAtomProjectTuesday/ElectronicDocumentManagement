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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/delete-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AttributeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    //TODO remove this same code in all tests
    public Cookie login(String username, String password) throws Exception {
        MvcResult resultActions = this.mockMvc.perform(post("/login").param("username", username)
                .param("password", password))
                .andDo(print())
                .andExpect(cookie().exists("Authorization")).andReturn();
        return resultActions.getResponse().getCookie("Authorization");
    }

    @Test
    public void testCreateAttribute() throws Exception {
        Cookie authorizationWithAuthorities = login("1", "1");
        this.mockMvc.perform(post("/attributes")
                .param("name", "test attribute number 2")
                .param("document_id", "5")
                .param("value", "test attribute number 2 value")
                .cookie(authorizationWithAuthorities))
                .andDo(print())
                .andExpect(content().string(containsString("Attribute was successfully registered")));
    }

    @Test
    public void testEditAttribute() throws Exception {
        Cookie authorizationWithAuthorities = login("1", "1");
        this.mockMvc.perform(patch("/attributes/6")
                .param("new_value", "test attribute value changed")
                .cookie(authorizationWithAuthorities))
                .andDo(print())
                .andExpect(content().string(containsString("Attribute was successfully edited")));
    }

    @Test
    public void testDeleteAttribute() throws Exception {
        Cookie authorizationWithAuthorities = login("1", "1");
        this.mockMvc.perform(delete("/attributes/7")
                .cookie(authorizationWithAuthorities))
                .andDo(print())
                .andExpect(content().string(containsString("Attribute was successfully deleted")));
    }
}
