package cn.com.lz.generator.backstage.common.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DateUtilTest {

    @Test
    void nowYMDHMSM() {
        String now = DateUtil.nowYMDHMSM();
        System.out.println(now);
    }
}
