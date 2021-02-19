package com.starter.use;


import com.xianyanyang.starter.School;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import({MyApplication.class})
public class SchoolTest {

    @Autowired
    private School school;

    @Test
    public void name() {
        school.ding();
    }
}
