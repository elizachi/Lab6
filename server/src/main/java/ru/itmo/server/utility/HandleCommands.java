package ru.itmo.server.utility;

import ru.itmo.common.commands.CommandType;
import ru.itmo.common.requests.Request;
import ru.itmo.common.responses.Response;
import ru.itmo.server.ServerLauncher;
import ru.itmo.server.collection.commands.*;
import ru.itmo.server.collection.dao.ArrayDequeDAO;
import ru.itmo.server.collection.dao.DAO;

import java.io.IOException;

public class HandleCommands {
    private static DAO database;
    private static final FileManager fileManager = new FileManager("DAO_COLLECTION_FILEPATH");

    //загрузка коллекции из файла для дальнейшей работы
    static {
        database = new ArrayDequeDAO(fileManager.readCollection());
        database.setAvailableId();
    }

    public void exit() {
        try {
            database.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Response handleRequest(Request request) {
        return executeCommand(request.getCommand(), request.getArgumentAs(request.argument.getClass()));
    }

    private Response executeCommand(CommandType command, Object commandArgument){
        int commandIndex = command.ordinal();
        commands[commandIndex].execute(commandArgument);
        ServerLauncher.log.info("Запрос успешно обработан");
        return new Response(Response.Status.OK, commandArgument);
    }

    /**
     * existed commands
     */
    private static final Command[] commands = {
            new AddCommand(database),
            new ClearCommand(database),
            new FilterByMinutesCommand(database),
            new FilterGreaterThanSpeedCommand(database),
            new HeadCommand(database),
            new HelpCommand(),
            new InfoCommand(database),
            new PrintUniqueSpeedCommand(database),
            new RemoveByIdCommand(database),
            new RemoveGreaterCommand(database),
            new RemoveHeadCommand(database),
            new ShowCommand(database),
            new UpdateCommand(database),
            new ExitCommand()
    };
}
