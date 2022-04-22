package ru.itmo.client.to_server;

import ru.itmo.common.model.HumanBeing;
import ru.itmo.common.responses.Response;

public interface ServerAPI {
    Response executeCommand(int index, HumanBeing human);
}
