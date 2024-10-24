package com.cotech.helpdesk;

import com.cotech.helpdesk.util.PdfUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootTest
class HelpdeskBackendApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void demo() {
        PdfUtil.readFile();
        List<Integer> list = new ArrayList<>();
    }

}
