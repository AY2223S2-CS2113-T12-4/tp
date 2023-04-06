package wellnus.reflection.command;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import wellnus.command.Command;
import wellnus.common.WellNusLogger;
import wellnus.exception.BadCommandException;
import wellnus.reflection.feature.QuestionList;
import wellnus.reflection.feature.ReflectUi;
import wellnus.reflection.feature.ReflectionManager;

//@@author wenxin-c
/**
 * Home command to return back to WellNUS++ main interface.
 */
public class HomeCommand extends Command {
    public static final String COMMAND_DESCRIPTION = "home - Return back to the main menu of WellNUS++.";
    public static final String COMMAND_USAGE = "usage: home";
    public static final String COMMAND_KEYWORD = "home";
    private static final Logger LOGGER = WellNusLogger.getLogger("ReflectHomeCommandLogger");
    private static final String FEATURE_NAME = "reflect";
    private static final String PAYLOAD = "";
    private static final String INVALID_COMMAND_MSG = "Invalid command issued, expected 'home'!";
    private static final String INVALID_ARGUMENT_MSG = "Invalid arguments given to 'home'!";
    private static final String INVALID_PAYLOAD = "Invalid payload given to 'home'!";
    private static final String INVALID_COMMAND_NOTES = "home command " + COMMAND_USAGE;
    private static final String COMMAND_PAYLOAD_ASSERTION = "The payload should be empty.";
    private static final String HOME_MESSAGE = "How do you feel after reflecting on yourself?"
            + System.lineSeparator() + "Hope you have gotten some takeaways from self reflection, see you again!!";
    private static final int ARGUMENT_PAYLOAD_SIZE = 1;
    private static final ReflectUi UI = new ReflectUi();
    private QuestionList questionList;

    /**
     * Set up the argument-payload pairs for this command.<br/>
     * Pass in a questionList object from ReflectionManager to manipulate history data.
     *
     * @param arguments Argument-payload pairs from users
     * @param questionList Object that contains the data about questions
     */
    public HomeCommand(HashMap<String, String> arguments, QuestionList questionList) {
        super(arguments);
        this.questionList = questionList;
    }

    /**
     * Get the command itself.
     *
     * @return Command: home
     */
    @Override
    protected String getCommandKeyword() {
        return COMMAND_KEYWORD;
    }

    /**
     * Get the name of the feature in which this home command is generated.
     *
     * @return Feature name: reflect
     */
    @Override
    protected String getFeatureKeyword() {
        return FEATURE_NAME;
    }

    /**
     * Method to ensure that developers add in a command usage.
     * <p>
     * For example, for the 'add' command in AtomicHabit package: <br>
     * "usage: add --name (name of habit)"
     *
     * @return String of the proper usage of the habit
     */
    @Override
    public String getCommandUsage() {
        return COMMAND_USAGE;
    }

    /**
     * Method to ensure that developers add in a description for the command.
     * <p>
     * For example, for the 'add' command in AtomicHabit package: <br>
     * "add - add a habit to your list"
     *
     * @return String of the description of what the command does
     */
    @Override
    public String getCommandDescription() {
        return COMMAND_DESCRIPTION;
    }

    /**
     * Main entry point of this command.<br/>
     * Return back to WellNUS++ main interface and clear the questionList history data.
     */
    @Override
    public void execute() {
        try {
            validateCommand(getArguments());
        } catch (BadCommandException invalidCommand) {
            LOGGER.log(Level.INFO, INVALID_COMMAND_MSG);
            UI.printErrorFor(invalidCommand, INVALID_COMMAND_NOTES);
            return;
        }
        UI.printOutputMessage(HOME_MESSAGE);
        if (!questionList.getRandomQuestionIndexes().isEmpty()) {
            questionList.clearRandomQuestionIndexes();
        }
        ReflectionManager.setIsExit(true);
    }

    /**
     * Validate the command.<br/>
     * <br/>
     * Conditions for command to be valid:<br/>
     * <li>Only one argument-payload pair
     * <li>The pair contains key: home
     * <li>Payload is empty
     * Whichever mismatch will cause the command to be invalid.
     *
     * @param commandMap Argument-Payload map generated by CommandParser
     * @throws BadCommandException If an invalid command is given
     */
    @Override
    public void validateCommand(HashMap<String, String> commandMap) throws BadCommandException {
        if (commandMap.size() != ARGUMENT_PAYLOAD_SIZE) {
            throw new BadCommandException(INVALID_ARGUMENT_MSG);
        } else if (!commandMap.containsKey(COMMAND_KEYWORD)) {
            throw new BadCommandException(INVALID_COMMAND_MSG);
        }
        String payload = commandMap.get(getCommandKeyword());
        if (!payload.isBlank()) {
            throw new BadCommandException(INVALID_PAYLOAD);
        }
        assert getArguments().get(COMMAND_KEYWORD).equals(PAYLOAD) : COMMAND_PAYLOAD_ASSERTION;
    }
}

