package ru.itmo.client;

import ru.itmo.client.service.AskInput;
import ru.itmo.client.service.ReaderManager;
import ru.itmo.common.exceptions.WrongArgumentException;
import ru.itmo.common.messages.MessageManager;
import ru.itmo.common.model.HumanBeing;

public class Client {
    private final MessageManager msg = new MessageManager();
    private final AskInput ask = new AskInput();

    // todo получать порт и хост здесь
    public void start() {
//        String serverHost = "localhost";
//        int serverPort = 65100;
//        ServerAPI serverAPI = new ServerAPIImpl(serverHost, serverPort);

        while(true) {
            try {
                String commandName = ask.askCommand(ReaderManager.getHandler());
                HumanBeing human = ask.askInputManager(commandName, ReaderManager.getHandler());
//            Response response = serverAPI.executeCommand(data);
            } catch (NullPointerException e) {
                ReaderManager.returnOnPreviousReader();
                ask.removeLastElement();
            } catch (RuntimeException e) {
                e.printStackTrace();
                // если команда введена неверно
            } catch (WrongArgumentException e) {
                msg.printErrorMessage(e);
            }
//        if(response.status == Response.Status.OK) {
//            System.out.println("Ура ура! Получилось!");
//        }
        }

    }
}
