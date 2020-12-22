package com.semestrovaya.demo;

import com.semestrovaya.demo.controllers.CountController;
import com.semestrovaya.demo.models.Count;
import com.semestrovaya.demo.models.Transaction;
import com.semestrovaya.demo.models.User;
import com.semestrovaya.demo.repo.CountRepository;
import com.semestrovaya.demo.repo.TransactionRepository;
import com.semestrovaya.demo.repo.UserRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("us")
@TestPropertySource("/application-test.properties")
public class ControllersTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CountController countController;

    @Autowired
    private CountRepository countRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepo userRepo;

    @Test
    public void DatabaseTransTest() throws Exception{

        Transaction tr = new Transaction(1000,11l);
        transactionRepository.save(tr);
        assert(transactionRepository.findById(tr.getId()).orElseThrow().getSum() == 1000);
        transactionRepository.delete(tr);
    }

    @Test
    public void DatabaseCountTest() throws Exception{

        Count count = new Count("test","some comment");
        countRepository.save(count);
        assert(countRepository.findById(count.getId()).orElseThrow().getName().equals("test"));
        countRepository.delete(count);
    }

    @Test
    public void DatabaseUserTest() throws Exception{

        User usr = new User();
        usr.setActive(true);
        usr.setUsername("test");
        usr.setPassword("test");
        userRepo.save(usr);
        assert(userRepo.findById(usr.getId()).orElseThrow().getUsername().equals("test"));
        userRepo.delete(usr);
    }

    @Test
    public void mainPageTest() throws Exception{

        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(SecurityMockMvcResultMatchers.authenticated());
    }

    @Test
    public void GetCountTest() throws Exception{

        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(xpath("//div[@id='count-container']/div").nodeCount(3));
    }

    @Test
    public void GetAddTest() throws Exception{

        this.mockMvc.perform(get("/count/add"))
                .andDo(print())
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(xpath("//*[@id=\"String-ForTest\"]").string("Добавление счёта"));
    }

    @Test
    public void GetCountAddTransTest() throws Exception{

        this.mockMvc.perform(get("/count/11/addtrans"))
                .andDo(print())
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(xpath("//*[@id=\"String-ForTest\"]").string("Добавление транзакции"));
    }

    @Test
    public void GetCountDetailsTest() throws Exception{

        this.mockMvc.perform(get("/count/11"))
                .andDo(print())
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(xpath("//*[@id=\"trans-container\"]").nodeCount(4));
    }

    @Test
    public void GetCountEditTest() throws Exception{

        this.mockMvc.perform(get("/count/11/editcomment"))
                .andDo(print())
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(xpath("//*[@id=\"String-ForTest\"]").string("Изменение комментария к счёту"));
    }


}
