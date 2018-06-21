package com.staxter.demo;

import com.staxter.demo.user.User;
import com.staxter.demo.user.UserAlreadyExistsException;
import com.staxter.demo.user.UserRepository;
import com.staxter.demo.util.TestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class IntegrationTest {

    @Autowired
    protected WebApplicationContext context;
    @Autowired
    private UserRepository userRepository;

    protected MockMvc mockMvc;
    private User user = new User("id", "Igor", "Kravchenko", "igorkrav", "plainPwd", "hashedPwd");


    @Before
    public void init() throws UserAlreadyExistsException {
        userRepository.saveUser(user);
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @After
    public void clear() {
        userRepository.deleteAll();
    }


    @Test
    public void shouldSaveUser() throws Exception {
        String createDto = TestUtils.loadResourceAsString("api/user/request/create-dto.json");
        String expectedPayload = TestUtils.loadResourceAsString("api/user/response/successful-created-user.json");

        this.mockMvc.perform(post("/userservice/register")
                .content(createDto)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(expectedPayload, false));
    }

    @Test
    public void shouldNotSaveUserWithSameUsername() throws Exception {
        String createDto = TestUtils.loadResourceAsString("api/user/request/create-dto-user-already-exists.json");
        String expectedPayload = TestUtils.loadResourceAsString("api/user/response/user-already-exist.json");

        this.mockMvc.perform(post("/userservice/register")
                .content(createDto)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(expectedPayload));
    }

    @Test
    public void shouldReturnErrorMessageWithValidationErrors() throws Exception {
        String createDto = TestUtils.loadResourceAsString("api/user/request/create-dto-with-validation-errors.json");
        String expectedPayload = TestUtils.loadResourceAsString("api/user/response/validation-errors.json");

        this.mockMvc.perform(post("/userservice/register")
                .content(createDto)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(expectedPayload, false));
    }

    @Test
    public void shouldReturnJsonErrorForBadJson() throws Exception {
        String createDto = TestUtils.loadResourceAsString("api/user/request/create-dto-with-bad-json.json");
        String expectedPayload = TestUtils.loadResourceAsString("api/user/response/bad-json.json");

        this.mockMvc.perform(post("/userservice/register")
                .content(createDto))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(expectedPayload));
    }
}
