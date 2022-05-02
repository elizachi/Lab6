package ru.itmo.server.utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.itmo.common.commands.CommandType;
import ru.itmo.common.exceptions.TypeOfError;
import ru.itmo.common.exceptions.WrongArgumentException;
import ru.itmo.common.messages.MessageManager;
import ru.itmo.common.requests.Request;
import ru.itmo.common.responses.Response;
import ru.itmo.server.collection.commands.*;
import ru.itmo.server.collection.dao.ArrayDequeDAO;
import ru.itmo.server.collection.dao.DAO;

import java.io.IOException;

public class HandleCommands {
    private static final Logger log = LogManager.getLogger(ru.itmo.server.ServerLauncher.class.getName());
    private final MessageManager msg = new MessageManager();
//    private final String commandName;
//    private HumanBeing human;
    private static DAO database;
    private static final FileManager fileManager = new FileManager("DAO_COLLECTION_FILEPATH");

    //загрузка коллекции из файла для дальнейшей работы
    static {
        database = new ArrayDequeDAO(fileManager.readCollection());
        database.setAvailableId();
    }

//    public HandleCommands(String commandName, HumanBeing humanBeing){
//        this.commandName = commandName;
//        this.humanBeing = humanBeing;
//    }

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
        try {
            int commandIndex = command.ordinal();
            commands[commandIndex].execute(commandArgument);
            return new Response(Response.Status.OK, commandArgument);
        } catch (WrongArgumentException e) {
            return new Response(Response.Status.ERROR, commandArgument);
        }
    }


 // todo убрать, тк скорее всего не понадобится, но пока оставлю, чтобы не утерять
    private CommandType isCorrectCommand(String input) throws WrongArgumentException {
        try {
            return CommandType.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            if(input.isEmpty()) throw new WrongArgumentException(TypeOfError.EMPTY);
            throw new WrongArgumentException(TypeOfError.UNKNOWN);
        }
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
            new ScriptCommand(),
            new ShowCommand(database),
            new UpdateCommand(database),
            new ExitCommand()
    };
}
