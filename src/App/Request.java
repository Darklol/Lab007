package App;

import Commands.Command;
import Data.DragonValidator;
import java.io.Serializable;

public class Request implements Serializable {
    private String[] arguments;
    private Command command;
    private DragonValidator validator;

    public Request(Command command, String[] arguments){
        this.command = command;
        this.arguments = arguments;
    }

    public Request(Command command, String[] arguments, DragonValidator validator){
        this.command = command;
        this.arguments = arguments;
        this.validator = validator;
    }

    public String[] getArguments() {
        return arguments;
    }

    public DragonValidator getValidator() {
        return validator;
    }

    public Command getCommand() {
        return command;
    }

    public void setValidator(DragonValidator validator) {
        this.validator = validator;
    }

}
