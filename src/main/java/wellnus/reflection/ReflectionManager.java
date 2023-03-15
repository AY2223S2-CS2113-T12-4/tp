package wellnus.reflection;

import wellnus.command.Command;
import wellnus.exception.BadCommandException;
import wellnus.exception.WellNusException;
import wellnus.manager.Manager;

import java.util.HashMap;
import java.util.NoSuchElementException;

public class ReflectionManager extends Manager {
    private static final String LOGO =
            "  _____ ______ _      ______   _____  ______ ______ _      ______ _____ _______ _____ ____  _   _ \n"
                    + " / ____|  ____| |    |  ____| |  __ \\|  ____|  ____| "
                    + "|    |  ____/ ____|__   __|_   _/ __ \\| \\ | |\n"
                    + "| (___ | |__  | |    | |__    | |__) | |__  | |__  | "
                    + "|    | |__ | |       | |    | || |  | |  \\| |\n"
                    + " \\___ \\|  __| | |    |  __|   |  _  /|  __| |  __| "
                    + "| |    |  __|| |       | |    | || |  | | . ` |\n"
                    + " ____) | |____| |____| |      | | \\ \\| |____| |    "
                    + "| |____| |___| |____   | |   _| || |__| | |\\  |\n"
                    + "|_____/|______|______|_|      |_|  \\_\\______|_|    "
                    + "|______|______\\_____|  |_|  "
                    + "|_____\\____/|_| \\_|\n";
    private static final String GREETING_MESSAGE = "Welcome to WellNUS++ Self Reflection section :D"
            + System.lineSeparator() + "Feel very occupied and cannot find time to self reflect?"
            + System.lineSeparator() + "No worries, this section will give you the opportunity to reflect "
            + "and improve on yourself!!";
    private static final String GOODBYE_MESSAGE = "Sending you back to the main session...";
    private static final String FEATURE_NAME = "reflect";
    private static final String BRIEF_DESCRIPTION = "Users can get a random set of questions to reflect on.";
    private static final String FULL_DESCRIPTION = "";
    private static final String GET_COMMAND = "get";
    private static final String GET_PAYLOAD = "";
    private static final String RETURN_MAIN = "return";
    private static final String RETURN_PAYLOAD = "";
    private static final String EXIT_COMMAND = "exit";
    private static final String EXIT_PAYLOAD = "";
    private static final String NO_ELEMENT_MESSAGE = "There is no new line of input, please key in inputs.";
    private static final String INVALID_COMMAND_MESSAGE = "Please check the available commands "
            + "and enter a valid command.";
    private static final String IS_EXIT_ASSERTION = "isExit should be true after exiting while loop";
    private static final int EMPTY_COMMAND_TYPE = 0;
    private static final String COMMAND_TYPE_ASSERTION = "Command type should have length greater than 0";

    // TODO: Update with more commands being added
    private static final int NUM_SUPPORTED_COMMANDS = 3;
    private static final String SUPPORTED_COMMANDS_ASSERTION = "The number of supported commands should be 3";
    private static final String ARGUMENT_PAYLOAD_ASSERTION = "Argument-payload pairs cannot be empty";
    private static final boolean INITIAL_EXIT_STATUS = false;
    private final ReflectUi UI = new ReflectUi();
    private String commandType;
    private HashMap<String, String> argumentPayload;

    public ReflectionManager() {
        setSupportedCommands();
    }

    private ReflectUi getReflectUi() {
        return this.UI;
    }

    /**
     * Print greeting logo and message.
     */
    private void greet() {
        this.getReflectUi().printLogoWithSeparator(LOGO);
        this.getReflectUi().printOutputMessage(GREETING_MESSAGE);
    }

    private void goodbye() {
        this.getReflectUi().printOutputMessage(ReflectionManager.GOODBYE_MESSAGE);
    }

    /**
     * Get Self Reflection feature name.
     *
     * @return Self Reflection feature name
     */
    @Override
    public String getFeatureName() {
        return FEATURE_NAME;
    }

    /**
     * Get a summary of description of self reflection feature.
     *
     * @return Brief description of self reflection
     */
    @Override
    public String getBriefDescription() {
        return BRIEF_DESCRIPTION;
    }

    /**
     * Get a full description of self reflection feature.<br/>
     * TODO: FULL_DESCRIPTION is not completed yet!
     *
     * @return Full description of self reflection
     */
    @Override
    public String getFullDescription() {
        return FULL_DESCRIPTION;
    }

    /**
     * Set up the set of command-payload pairs supported by self reflection.<br/>
     * <li>Command: get, Payload: ""
     * <li>Command: exit, Payload: ""
     * <li>Command: return, Payload: ""
     */
    @Override
    protected void setSupportedCommands() {
        try {
            HashMap<String, String> getCmdArgumentPayload = new HashMap<>();
            getCmdArgumentPayload.put(GET_COMMAND, GET_PAYLOAD);
            GetCommand getCmd = new GetCommand(getCmdArgumentPayload);
            HashMap<String, String> returnCmdArgumentPayload = new HashMap<>();
            returnCmdArgumentPayload.put(RETURN_MAIN, RETURN_PAYLOAD);
            ReturnCommand returnCmd = new ReturnCommand(returnCmdArgumentPayload);
            HashMap<String, String> exitCmdArgumentPayload = new HashMap<>();
            exitCmdArgumentPayload.put(EXIT_COMMAND, EXIT_PAYLOAD);
            ExitCommand exitCmd = new ExitCommand(exitCmdArgumentPayload);
            supportedCommands.add(getCmd);
            supportedCommands.add(returnCmd);
            supportedCommands.add(exitCmd);
        } catch (BadCommandException badCommandException) {
            UI.printErrorFor(badCommandException, INVALID_COMMAND_MESSAGE);
        }
        assert supportedCommands.size() == NUM_SUPPORTED_COMMANDS : SUPPORTED_COMMANDS_ASSERTION;
    }

    /**
     * Main entry point of self reflection section.<br/>
     * <br/>
     * It prints out greeting messages, listen to and execute user commands.
     */
    @Override
    public void runEventDriver() {
        this.greet();
        boolean isExit = INITIAL_EXIT_STATUS;
        while (!isExit) {
            try {
                String inputCommand = UI.getCommand();
                Command command = getCommandFor(inputCommand);
                command.execute();
                isExit = ReturnCommand.isReturn(command);
            } catch (NoSuchElementException noSuchElement) {
                UI.printErrorFor(noSuchElement, NO_ELEMENT_MESSAGE);
            } catch (WellNusException exception) {
                UI.printErrorFor(exception, INVALID_COMMAND_MESSAGE);
            }
        }
        this.goodbye();
    }

    /**
     * Decide which command to create based on command type.<br/>
     * <br/>
     * Commands available at this moment are:
     * <li>Get a random set of reflection questions<br/>
     * <li>Return back main interface<br/>
     * <li>Exit program<br/>
     * @param userCommand Full command issued by the user
     * @return Command that can handle the user's command
     * @throws BadCommandException If an unrecognised command was given
     */
    public Command getCommandFor(String userCommand) throws BadCommandException {
        String commandType = commandParser.getMainArgument(userCommand);
        HashMap<String, String> arguments = commandParser.parseUserInput(userCommand);
        assert commandType.length() > EMPTY_COMMAND_TYPE : COMMAND_TYPE_ASSERTION;
        switch (commandType) {
        case GET_COMMAND:
            return new GetCommand(arguments);
        case RETURN_MAIN:
            return new ReturnCommand(arguments);
        case EXIT_COMMAND:
            return new ExitCommand(arguments);
        default:
            throw new BadCommandException(INVALID_COMMAND_MESSAGE);
        }
    }
}

