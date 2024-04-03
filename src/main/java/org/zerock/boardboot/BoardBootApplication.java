package org.zerock.boardboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // AuditionEntityListener 활성화용(감시)
public class BoardBootApplication {

    public static void main(String[] args) {

        SpringApplication.run(BoardBootApplication.class, args);
    }

}
