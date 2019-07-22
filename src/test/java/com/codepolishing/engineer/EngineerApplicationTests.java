package com.codepolishing.engineer;

import com.codepolishing.engineer.controller.UserController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EngineerApplicationTests {

    @Autowired
    private UserController userController;

    @Test
    public void contextLoads() {
       assertNotNull(userController);
    }


}
