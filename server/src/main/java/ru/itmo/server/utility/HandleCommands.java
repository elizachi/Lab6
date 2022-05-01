package ru.itmo.server.utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.itmo.common.exceptions.TypeOfError;
import ru.itmo.common.exceptions.WrongArgumentException;
import ru.itmo.common.messages.MessageManager;
import ru.itmo.common.model.HumanBeing;
import ru.itmo.common.requests.Request;
import ru.itmo.common.responses.Response;
import ru.itmo.server.collection.commands.*;
import ru.itmo.server.collection.dao.ArrayDequeDAO;
import ru.itmo.server.collection.dao.DAO;

import java.io.IOException;

public class HandleCommands {
    private static final Logger log = LogManager.getLogger(ru.itmo.server.ServerLauncher.class.getName());
    private final MessageManager msg = new MessageManager();
    private final String commandName;
    private HumanBeing humanBeing;
    private static DAO database;
    private static final FileManager fileManager = new FileManager("DAO_COLLECTION_FILEPATH");

    //загрузка коллекции из файла для дальнейшей работы
    static {
        database = new ArrayDequeDAO(fileManager.readCollection());
    }

    public HandleCommands(String commandName, HumanBeing humanBeing){
        this.commandName = commandName;
        this.humanBeing = humanBeing;
    }

    public void exit() {
        try {
            database.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Response handle(Request request) {
        return executeCommand(request.getCommand(), request.getArgumentAs(request.argument.getClass()));
    }

    private Response executeCommand(String commandName, Object commandArgument){
        try {
            CommandType cmd = isCorrectCommand(commandName);
            //todo execute commands somehow...
            return new Response(Response.Status.OK, commandArgument);
        } catch (WrongArgumentException e) {
            msg.printErrorMessage(e);
            return new Response(Response.Status.ERROR, commandArgument);
        }
    }

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

    /**
     * enum of commands
     */
    enum CommandType {
        ADD(new String[]{"askName", "askSoundtrackName", "askMinutesOfWaiting",
                "askImpactSpeed", "askRealHero", "askHasToothpick", "askCoordinates", "askMood", "askCar"}),
        CLEAR(new String[]{}),
        EXECUTE_SCRIPT(new String[]{"askFileName"}),
        EXIT(new String[]{}),
        FILTER_BY_MINUTES_OF_WAITING(new String[]{"askMinutesOfWaiting"}),
        FILTER_GREATER_THAN_IMPACT_SPEED(new String[]{"askImpactSpeed"}),
        HEAD(new String[]{}),
        HELP(new String[]{}),
        INFO(new String[]{}),
        PRINT_UNIQUE_IMPACT_SPEED(new String[]{}),
        REMOVE_BY_ID(new String[]{"askId"}),
        REMOVE_GREATER(new String[]{"askName", "askSoundtrackName", "askMinutesOfWaiting",
                "askImpactSpeed", "askRealHero", "askHasToothpick", "askCoordinates", "askMood", "askCar"}),
        REMOVE_HEAD(new String[]{}),
        SHOW(new String[]{}),
        UPDATE(new String[]{"askId", "askName", "askSoundtrackName", "askMinutesOfWaiting",
                "askImpactSpeed", "askRealHero", "askHasToothpick", "askCoordinates", "askMood", "askCar"});

        private final String[] commandFields;

        CommandType(String[] fields) {
            this.commandFields = fields;
        }

        public String[] getCommandFields() {
            return this.commandFields;
        }
    };
}
