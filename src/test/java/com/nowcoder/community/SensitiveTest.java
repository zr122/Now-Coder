package com.nowcoder.community;

import com.nowcoder.community.util.SensitiveWordsFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SensitiveTest {

    @Autowired
    SensitiveWordsFilter sensitiveWordsFilter;

    @Test
    public void testSensitiveFilter(){
        String text = "这里可以赌博，可以嫖娼，可以吸毒，可以开票，啊啊啊啊啊";
        text = sensitiveWordsFilter.filter(text);
        System.out.println(text);
    }
}
