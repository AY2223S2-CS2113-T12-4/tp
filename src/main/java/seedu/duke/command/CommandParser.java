package seedu.duke.command;

import java.util.HashMap;

/**
 * A CommandParser processes user input from a defined format <p>
 * <p><br>
 * Each user input via console consists of: <br>
 * <ol>
 * <li>COMMANDS - A Argument and Payload pairs </li><br>
 * <li>ARGUMENTS - String representing the action/parameters of the command </li><br>
 * <li>PAYLOADS - value of the action/parameters </li><br>
 * </ol>
 * <p>
 * In short, user input is a list of commands, each command containing arguments and payloads. <br><br>
 * Further, we define the FIRST command to be the MAIN command of any given user input. <br>
 * So, <code>"deadline work on CS2113 --by Sunday"</code> has <code>"deadline work on CS2113"</code>
 * as the main command <br><br>
 * Each command (argument-payload pair) except for the main command MUST
 * be delimited by <code>" --"</code> (whitespace intentional)
 * <br><br>
 * For example, a given user input: <code>"deadline work on CS2113 --by Sunday"</code>
 * <li>Has commands ["deadline work on CS2113", "by Sunday"]</li>
 * <li>Has arguments ["deadline", "by"]</li>
 * <li>Has payloads ["work on CS2113", ["Sunday"]</li>
 * <br>
 */
public class CommandParser {

    private static final String ARGUMENT_DELIMITER = " --";
    private static final String UNPADDED_DELIMITER = "--";
    private static final String PAYLOAD_DELIMITER = " ";

    // Message string constants for errors and ui
    private static final String ERROR_EMPTY_COMMAND = "Command is empty!";
    private static final String ERROR_EMPTY_ARGUMENT = "Command is missing an argument!";

    /**
     * Constructs an instance of CommandParser. <br>
     * <p>
     * CommandParser should be used to break down raw user input into
     * logical <code>[Argument,Payload]</code> pairs
     */
    public CommandParser() {

    }

    private String[] splitIntoCommands(String fullCommandString) throws BadCommandException {
        // Perform a string length sanity check
        fullCommandString = fullCommandString.strip();
        if (fullCommandString.length() == 0) {
            throw new BadCommandException(ERROR_EMPTY_COMMAND);
        }

        String[] rawCommands = fullCommandString.split(ARGUMENT_DELIMITER, -1);
        // Adversarial user input check
        // There are 2 possible adversarial inputs that should be checked for
        // 1. Whitespace/Empty Arguments: `cmd payload -- payload1 -- `
        //    Split renders it as ["cmd payload", " payload1", ""]
        //    " payload1" will cause issues with rendering
        //    So, check for empty commands and whitespace prefix
        // 2. Missing main argument: `--argument payload`
        //    Split renders this as ["--argument payload"]
        //    So, check for "--" prefix

        String[] cleanCommands = new String[rawCommands.length];
        for (int i = 0; i < rawCommands.length; ++i) {
            String currentCommand = rawCommands[i];
            // Case 1 check
            if (currentCommand.startsWith(" ") || currentCommand.length() == 0) {
                throw new BadCommandException(ERROR_EMPTY_ARGUMENT);
            }
            // Strip command of whitespace to clean input
            currentCommand = currentCommand.strip();
            // Case 2 check
            if (currentCommand.startsWith(UNPADDED_DELIMITER)) {
                throw new BadCommandException(ERROR_EMPTY_COMMAND);
            }
            cleanCommands[i] = currentCommand;
        }
        return cleanCommands;
    }

    private String getArgumentFromCommand(String commandString) throws BadCommandException {
        String[] words = commandString.split(PAYLOAD_DELIMITER);
        // Bad input checks
        if (words.length == 0) {
            throw new BadCommandException(ERROR_EMPTY_ARGUMENT);
        }
        return words[0];
    }

    private String getPayloadFromCommand(String commandString) {
        String[] words = commandString.split(PAYLOAD_DELIMITER);
        String payload = "";
        for (int i = 0; i < words.length; ++i) {
            payload = payload.concat(words[i]);
            if (i != words.length - 1) {
                payload = payload.concat(PAYLOAD_DELIMITER);
            }
        }
        // No checks for payload length is done as payload CAN be empty
        return payload;
    }

    /**
     * Takes in raw user input and splits it into Argument-Payload pairs
     *
     * @param userInput Raw user input from stdin in string form
     * @return HashMap mapping a Argument (key) to a Payload (value)
     * @throws BadCommandException when command is empty or is problematic
     */
    public HashMap<String, String> parseUserInput(String userInput) throws BadCommandException {
        if (userInput.length() == 0) {
            throw new BadCommandException(ERROR_EMPTY_COMMAND);
        }
        HashMap<String, String> argumentPayload = new HashMap<>();
        String[] commands = splitIntoCommands(userInput);
        for (String command : commands) {
            String argument = getArgumentFromCommand(command);
            String payload = getPayloadFromCommand(command);
            argumentPayload.put(argument, payload);
        }
        return argumentPayload;
    }

    /**
     * Takes in a string and returns the inferred "Main Argument"
     * <br>
     * Practically, this is the <b>First</b> argument of any command string.
     * For example, <code>"hb add --name foobar"</code> <br>
     * Has main argument "hb"
     *
     * @param userInput Any string input representing a command
     * @return the inferred Main Argument
     * @throws BadCommandException when String is empty
     */
    public String getMainArgument(String userInput) throws BadCommandException {
        userInput = userInput.strip();
        String[] parameters = userInput.split(" ");
        if (parameters.length == 0) {
            throw new BadCommandException(ERROR_EMPTY_COMMAND);
        }
        return parameters[0];
    }

}
