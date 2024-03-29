package ru.itmo.client;

import ru.itmo.client.service.AskInput;
import ru.itmo.client.service.ReaderManager;
import ru.itmo.client.to_server.ServerAPI;
import ru.itmo.client.to_server.ServerAPIImpl;
import ru.itmo.common.commands.CommandType;
import ru.itmo.common.exceptions.TypeOfError;
import ru.itmo.common.exceptions.WrongArgumentException;
import ru.itmo.common.messages.MessageManager;
import ru.itmo.common.model.HumanBeing;
import ru.itmo.common.responses.Response;

/**
 * Класс, содержащий основную логику работы клиента
 */
public class Client {
    private final MessageManager msg = new MessageManager();
    private final AskInput ask = new AskInput();
    private final String serverHost;
    private final int serverPort;
    private final int connectionAttempts = 20;
    private final int connectionTimeout = 2000;

    public Client(String serverHost, int serverPort){
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    public void start() {
        ServerAPI serverAPI = new ServerAPIImpl(serverHost, serverPort);
        boolean run = true;

        while (!serverAPI.connectToServer()) {
            if(serverAPI.getAttempts() > connectionAttempts){
                ClientLauncher.log.error("Превышено количество попыток подключиться");
                return;
            }
            try {
                Thread.sleep(connectionTimeout);
            } catch (InterruptedException e) {
                ClientLauncher.log.error("Произошла ошибка при попытке ожидания подключения!");
                ClientLauncher.log.info("Повторное подключение будет произведено немедленно.");
            }
        }

        while(run) {
            try {
                // получаю проверенный тип команды, которую ввел пользователь
                CommandType commandType = ask.askCommand(ReaderManager.getHandler());
                // получаю сформированный объект, содержащий аргументы, необходимые для команды
                HumanBeing human = ask.askInputManager(commandType, ReaderManager.getHandler());
                // делаю запрос на сервер, передаю CommandType типа команды и
                // объект класса HumanBeing, получаю ответ
                Response response = serverAPI.executeCommand(commandType, human);
                // если статус ответа от сервера - ОК, соообщаю об этом на консоль
                if(response.status == Response.Status.OK) {
                    ClientLauncher.log.info("Ура ура! Получилось! Команда успешно выполнена.");
                    if(response.getArgumentAs(String.class) != null) {
                        System.out.println(response.getArgumentAs(String.class));
                    }
                } else if(response.status == Response.Status.SERVER_EXIT) {
                    ClientLauncher.log.info("Клиент завершает свою работу.");
                    serverAPI.closeConnection();
                    run = false;
                } else if(response.status == Response.Status.ERROR) {
                    ClientLauncher.log.error("В процессе выполнения данной команды произошла ошибка.");
                }
            } catch (NullPointerException e) {
                ReaderManager.returnOnPreviousReader();
                ask.removeLastElement();
            } catch (RuntimeException e) {
                e.printStackTrace();
                ClientLauncher.log.error("Непредвиденная ошибка.");
            } catch (WrongArgumentException e) {
                msg.printErrorMessage(e);
                if (e.getType() == TypeOfError.CONNECTION_BROKEN) {

                    System.out.println("Попытка переподключиться..");
                    while (!serverAPI.connectToServer()){
                        if(serverAPI.getAttempts() > connectionAttempts){
                            ClientLauncher.log.error("Превышено количество попыток подключиться.");
                            return;
                        }
                        try {
                            Thread.sleep(connectionTimeout);
                        } catch (InterruptedException exception) {
                            ClientLauncher.log.error("Произошла ошибка при попытке ожидания подключения.");
                        }
                    }

                }
                if (e.getType() == TypeOfError.CONNECTED_REFUSE) {
                    run = false;
                }
                if(e.getType() == TypeOfError.NOT_STARTED) {
                    run = false;
                }
            }

        }
    }
}
