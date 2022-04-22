package ru.itmo.client.service;

import ru.itmo.client.handlers.InputHandler;
import ru.itmo.common.model.HumanBeing;

public class FormedManager {
    private final AskInput request;

    public FormedManager(AskInput request) {
        this.request = request;
    }

    public HumanBeing formed(InputHandler reader, HumanBeing human) {
        return null;
    }
}
