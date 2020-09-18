package App;

import Commands.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * класс, содержащий в себе все доступные команды
 * хранит соответствия команда-имя
 */
public class RegisteredCommands {

    private static HashMap<String, Command> commandsName = new HashMap<String,Command>();
    private static Set<String> commandsWithDragons = new HashSet<>();
    private static Set<String> authorizationCommands = new HashSet<>();
    private Receiver receiver;

    static {
        commandsName.put(new HelpCommand().commandName(), new HelpCommand());
        commandsName.put(new InfoCommand().commandName(),new InfoCommand());
        commandsName.put(new ShowCommand().commandName(),new ShowCommand());
        commandsName.put(new InsertCommand().commandName(),new InsertCommand());
        commandsName.put(new UpdateCommand().commandName(), new UpdateCommand());
        commandsName.put(new RemoveKeyCommand().commandName(), new RemoveKeyCommand());
        commandsName.put(new ClearCommand().commandName(),new ClearCommand());
        commandsName.put(new ExecuteScriptCommand().commandName(), new ExecuteScriptCommand());
        commandsName.put(new RemoveGreaterKeyCommand().commandName(),new RemoveGreaterKeyCommand());
        commandsName.put(new ReplaceIfGreaterCommand().commandName(),new ReplaceIfGreaterCommand());
        commandsName.put(new RemoveGreaterCommand().commandName(),new RemoveGreaterCommand());
        commandsName.put(new MinByNameCommand().commandName(),new MinByNameCommand());
        commandsName.put(new PrintUniqueKillerCommand().commandName(),new PrintUniqueKillerCommand());
        commandsName.put(new PrintAscendingDescCommand().commandName(),new PrintAscendingDescCommand());
        commandsName.put(new RegisterCommand().commandName(), new RegisterCommand());
        commandsName.put(new LoginCommand().commandName(), new LoginCommand());

        commandsWithDragons.add("replace_if_greater");
        commandsWithDragons.add("update");
        commandsWithDragons.add("insert");

        authorizationCommands.add("register");
        authorizationCommands.add("login");
    }

    public static HashMap<String, Command> getCommandsName() {
        return commandsName;
    }

    public static Set<String> getCommandsWithDragons() {
        return commandsWithDragons;
    }

    public static Set<String> getAuthorizationCommands() {
        return authorizationCommands;
    }
}
