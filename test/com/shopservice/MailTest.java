package com.shopservice;

import org.junit.Test;

import java.util.Arrays;

public class MailTest {
    @Test
    public void testReport() throws Exception {
        MailService mailService = MailService.getInstance();
        mailService.report("test", "test body", Arrays.asList( "gmen7070@gmail.com", "oleg@themidnightcoders.com" ));

    }
}
