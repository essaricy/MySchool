package com.myschool.infra.application.constants;

@Deprecated
public enum CommandType {

    ADD, UPDATE, DELETE;

    public static CommandType get(String name) {
        if (name != null) {
            for (CommandType command : values()) {
                if (name.equalsIgnoreCase(command.toString())) {
                    return command;
                }
            }
        }
        return null;
    }
}
