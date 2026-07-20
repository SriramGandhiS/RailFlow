package com.railflow;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class NotificationE2ETest {

    @Test
    void contextLoads() {
        assertThat(true).isTrue();
    }
}
