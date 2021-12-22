package org.example.myWork;

import lombok.RequiredArgsConstructor;
import org.example.myWork.parser.CommandParser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@SpringBootApplication
@RequiredArgsConstructor
public class ToDoListApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ToDoListApplication.class, args);
    }

    private final CommandParser commandParser;

    @Override
    public void run(String... args) throws Exception {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        reader.lines()
                .map(commandParser);
    }
}


