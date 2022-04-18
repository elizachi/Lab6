package ru.itmo.client.to_server;

import ru.itmo.common.collection.model.HumanBeing;
import ru.itmo.common.responses.Response;

public interface ServerAPI {
    Response executeCommand(String command, HumanBeing human);
}
