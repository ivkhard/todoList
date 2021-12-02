package org.example.myWork.parser;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandParser implements Function<String, CommandDescription> {
    private static final Pattern COMMAND_PATTERN = Pattern.compile("\\s*(?<cmd>\\w+)(?:\\s+(?<args>(?:(?<id>\\d+)\\b)?(?<text>.*)))?");

    public CommandDescription apply(String commandLine) {
        Matcher matcher = COMMAND_PATTERN.matcher(commandLine);
        if (matcher.find()) {
            CommandDescription.CommandDescriptionBuilder builder = CommandDescription.builder()
                    .name(matcher.group("cmd"))
                    .args(matcher.group("args"))
                    .text(matcher.group("text"));
            String taskId = matcher.group("id");
            if (taskId != null) {
                builder.taskId(Long.parseLong(taskId));
            }
            return builder.build();
        }
        return null;
    }
}
