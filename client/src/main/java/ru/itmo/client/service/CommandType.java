package ru.itmo.client.service;

public enum CommandType {
    ADD(new String[]{"askName", "askSoundtrackName", "askMinutesOfWaiting",
            "askImpactSpeed", "askRealHero"}),
    CLEAR(new String[]{}),
    FILTER_BY_MINUTES_OF_WAITING(new String[]{"askMinutesOfWaiting"}),
    FILTER_GREATER_THAN_IMPACT_SPEED(new String[]{"askImpactSpeed"}),
    HEAD(new String[]{}),
    HELP(new String[]{}),
    INFO(new String[]{}),
    PRINT_UNIQUE_IMPACT_SPEED(new String[]{}),
    REMOVE_BY_ID(new String[]{"askId"}),
    REMOVE_GREATER(new String[]{}),
    REMOVE_HEAD(new String[]{}),
    EXECUTE_SCRIPT(new String[]{}),
    SHOW(new String[]{}),
    UPDATE(new String[]{"askId", "askName", "askSoundtrackName",
            "askMinutesOfWaiting", "askImpactSpeed", "askRealHero"}),
    EXIT(new String[]{});

    private final String[] commandFields;

    CommandType(String[] fields) {
        this.commandFields = fields;
    }

    public String[] getCommandFields() {
        return this.commandFields;
    }

    /**
     * Возвращает элемент enum'а по заданному индексу
     * @param index
     * @return
     */
    public static CommandType getEnum(int index) {
        CommandType[] commands = CommandType.values();
        for(CommandType command: commands) {
            if(command.ordinal() == index) {
                return command;
            }
        }
        return null;
    }
};