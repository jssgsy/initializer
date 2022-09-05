package com.univ.initializer;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = InitializerApplication.class)
public class InitializerApplicationTests {

    @Test
    public void contextLoads() {
        System.out.println("---");
    }

}
