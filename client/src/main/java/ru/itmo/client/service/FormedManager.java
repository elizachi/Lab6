package ru.itmo.client.service;

import ru.itmo.client.handlers.InputHandler;
import ru.itmo.common.collection.model.HumanBeing;

public class FormedManager {
    private final AskInput request;

    public FormedManager(AskInput request) {
        this.request = request;
    }

    public HumanBeing formed(InputHandler reader) {
        return new HumanBeing(
//                request.askName(reader),
//                request.askSoundtrackName(reader),
//                request.askMinutesOfWaiting(reader),
//                request.askImpactSpeed(reader),
//                request.askRealHero(reader),
//                request.askHasToothpick(reader),
//                request.askCoordinates(reader),
//                request.askMood(reader),
//                request.askCar(reader)
        );
    }
}
