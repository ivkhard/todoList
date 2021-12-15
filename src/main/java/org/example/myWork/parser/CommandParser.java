package org.example.myWork.parser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class CommandParser implements Function<String, CommandDescription> {
    private static final Pattern COMMAND_PATTERN = Pattern.compile("\\s*(?<cmd>\\w+)(?:\\s+(?<args>(?:(?<id>\\d+)\\b)?(?<text>.*)))?");

    public CommandDescription apply(String commandLine) {
        log.debug("User input: {}", commandLine);
        Matcher matcher = COMMAND_PATTERN.matcher(commandLine);
        if (matcher.find()) {
            CommandDescription.CommandDescriptionBuilder builder = CommandDescription.builder()
                    .name(matcher.group("cmd"))
                    .args(matcher.group("args"))
                    .text(matcher.group("text"));
            String taskId = matcher.group("id");
            if (taskId != null) {
                try {
                    builder.taskId(Long.parseLong(taskId));
                } catch (NumberFormatException e) {
                    log.error("Cannot parse: {}", taskId, e);
                }
            }
            return builder.build();
        }
        return null;
    }
}
