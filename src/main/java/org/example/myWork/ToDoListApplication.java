package org.example.myWork;

import lombok.RequiredArgsConstructor;
import org.example.myWork.parser.CommandParser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class ToDoListApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ToDoListApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
