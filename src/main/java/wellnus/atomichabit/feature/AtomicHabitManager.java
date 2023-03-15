package wellnus.atomichabit.feature;

import java.util.HashMap;

import wellnus.atomichabit.command.AddCommand;
import wellnus.atomichabit.command.ExitCommand;
import wellnus.atomichabit.command.ListCommand;
import wellnus.atomichabit.command.UpdateCommand;
import wellnus.command.Command;
import wellnus.exception.AtomicHabitException;
import wellnus.exception.BadCommandException;
import wellnus.exception.WellNusException;
import wellnus.manager.Manager;
import wellnus.ui.TextUi;

public class AtomicHabitManager extends Manager {
    public static final String FEATURE_NAME = "hb";
    private static final String ADD_COMMAND_KEYWORD = "add";
    private static final String ATOMIC_HABIT_GREET = "Welcome to the atomic habits feature!";
    private static final String EXIT_COMMAND_KEYWORD = "exit";
    /*
     * FEATURE_* variables: Information about this feature to assist the 'help' command
     */
    private static final String FEATURE_BRIEF_DESCRIPTION = "";
    private static final String FEATURE_FULL_DESCRIPTION = "";
    private static final String LIST_COMMAND_KEYWORD = "list";
    private static final String UNKNOWN_COMMAND_MESSAGE = "No such command in atomic habits!";
    private static final String UPDATE_COMMAND_KEYWORD = "update";
    private final TextUi textUi;
    private final AtomicHabitList habitList;

    public AtomicHabitManager() {
        this.habitList = new AtomicHabitList();
        this.textUi = new TextUi();
    }

    /**
     * Parses the given command from the user and determines the correct Command
     *     subclass that can handle its execution.
     *
     * @param commandString Full command issued by the user
     * @return Command object that can execute the user's command
     * @throws BadCommandException If an unknown command was issued by the user
     */
    private Command getCommandFor(String commandString) throws BadCommandException {
        HashMap<String, String> arguments = getCommandParser().parseUserInput(commandString);
        if (!arguments.containsKey(AtomicHabitManager.FEATURE_NAME)) {
            throw new BadCommandException(UNKNOWN_COMMAND_MESSAGE);
        }
        String commandKeyword = arguments.get(AtomicHabitManager.FEATURE_NAME);
        switch (commandKeyword) {
        case ADD_COMMAND_KEYWORD:
            return new AddCommand(arguments, getHabitList());
        case EXIT_COMMAND_KEYWORD:
            return new ExitCommand(arguments);
        case LIST_COMMAND_KEYWORD:
            return new ListCommand(arguments, getHabitList());
        case UPDATE_COMMAND_KEYWORD:
            return new UpdateCommand(arguments, getHabitList());
        default:
            throw new BadCommandException(UNKNOWN_COMMAND_MESSAGE);
        }
    }

    private AtomicHabitList getHabitList() {
        return this.habitList;
    }

    private TextUi getTextUi() {
        return this.textUi;
    }

    private void greet() {
        getTextUi().printOutputMessage(ATOMIC_HABIT_GREET);
    }

    /**
     * Reads user commands continuously and execute those that are supported
     *     until the exit command is given.
     */
    private void runCommands() {
        boolean isExit = false;
        while (!isExit) {
            try {
                String commandString = getTextUi().getCommand();
                Command command = getCommandFor(commandString);
                command.execute();
                isExit = ExitCommand.isExit(command);
            } catch (BadCommandException badCommandException) {
                String NO_ADDITIONAL_MESSAGE = "";
                getTextUi().printErrorFor(badCommandException, NO_ADDITIONAL_MESSAGE);
            } catch (WellNusException exception) {
                getTextUi().printErrorFor(exception, "Check user guide for valid commands!");
            }
        }
    }

    /**
     * Returns the commandline name of the atomic habits feature
     *
     * @return Commandline name of this feature
     */
    @Override
    public String getFeatureName() {
        return FEATURE_NAME;
    }

    /**
     * Returns a summary description of the atomic habits feature
     *
     * @return Summary description of this feature
     */
    @Override
    public String getBriefDescription() {
        return FEATURE_BRIEF_DESCRIPTION;
    }

    /**
     * Returns the full description of the atomic habits feature
     *
     * @return Full description of this feature
     */
    @Override
    public String getFullDescription() {
        return FEATURE_FULL_DESCRIPTION;
    }

    /**
     * Returns a list of main commands the atomic habit feature supports <br>
     * <br>
     * Suggested implementation: <br>
     * <code> this.supportedCommands.add([cmd1, cmd2, ...]); </code>
     */
    @Override
    protected void setSupportedCommands() {
    }

    /**
     * First welcomes user with our unique greeting.<br>
     * <br>
     * Then continuously read commands from the user and execute those that are supported.
     */
    @Override
    public void runEventDriver() {
        greet();
        runCommands();
    }

    /**
     * Method to test for exception handling of invalid command using JUnit
     *
     * @param userCommand Command identified after parsing through userInput
     * @return Command according to userInput
     * @throws AtomicHabitException For every invalid command being tested below
     */
    public Command testInvalidCommand(String userCommand) throws AtomicHabitException {
        String DESCRIPTION_TEST = "testing";
        String EXIT_COMMAND = "hb exit";
        String LIST_COMMAND = "hb list";
        String indexTest = "1";
        String invalidCommandErrorMessage = "Invalid command! Please enter a valid command";
        HashMap<String, String> arguments;
        try {
            switch (userCommand) {
            case ADD_COMMAND_KEYWORD:
                arguments = getCommandParser().parseUserInput(DESCRIPTION_TEST);
                return new AddCommand(arguments, new AtomicHabitList());
            case LIST_COMMAND_KEYWORD:
                arguments = getCommandParser().parseUserInput(LIST_COMMAND);
                return new ListCommand(arguments, new AtomicHabitList());
            case EXIT_COMMAND_KEYWORD:
                arguments = getCommandParser().parseUserInput(EXIT_COMMAND);
                return new ExitCommand(arguments);
            case UPDATE_COMMAND_KEYWORD:
                arguments = getCommandParser().parseUserInput(indexTest);
                return new UpdateCommand(arguments, new AtomicHabitList());
            default:
                throw new AtomicHabitException(invalidCommandErrorMessage);
            }
        } catch (BadCommandException badCommandException) {
            String NO_ADDITIONAL_MESSAGE = "";
            getTextUi().printErrorFor(badCommandException, NO_ADDITIONAL_MESSAGE);
            return null;
        }
    }
}



