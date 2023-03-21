package wellnus.focus;

import wellnus.command.Command;
import wellnus.exception.BadCommandException;
import wellnus.ui.TextUi;

import java.util.HashMap;

/**
 * Represents a command to check the time left in the current session.
 */
public class CheckCommand extends Command {

    private static final String COMMAND_KEYWORD = "check";
    private static final int COMMAND_NUM_OF_ARGUMENTS = 1;
    private static final String COMMAND_INVALID_ARGUMENTS_MESSAGE = "Invalid command, expected 'check'";
    private static final String NO_ADDITIONAL_MESSAGE = "";
    private static final String COMMAND_KEYWORD_ASSERTION = "The key should be check.";
    private final String CHECK_OUTPUT = "Time left: ";
    private final Session session;
    private final TextUi textUi;

    /**
     * Constructs a CheckCommand object.
     * Session in FocusManager is passed into this class.
     *
     * @param arguments Argument-Payload Hashmap generated by CommandParser
     * @param session   Session object which is an arraylist of Countdowns
     */
    public CheckCommand(HashMap<String, String> arguments, Session session) {
        super(arguments);
        this.session = session;
        this.textUi = new TextUi();
    }

    /**
     * Identifies this Command's keyword. Override this in subclasses so
     * toString() returns the correct String representation.
     *
     * @return String Keyword of this Command
     */
    @Override
    protected String getCommandKeyword() {
        return COMMAND_KEYWORD;
    }


    /**
     * Identifies the feature that this Command is associated with. Override
     * this in subclasses so toString() returns the correct String representation.
     *
     * @return String Keyword for the feature associated with this Command
     */
    @Override
    protected String getFeatureKeyword() {
        return FocusManager.FEATURE_NAME;
    }

    /**
     * Checks the current time left in the current countdown.
     * Prints the time left in the current countdown.
     */
    @Override
    public void execute() {
        try {
            validateCommand(super.getArguments());
        } catch (BadCommandException badCommandException) {
            textUi.printErrorFor(badCommandException, NO_ADDITIONAL_MESSAGE);
            return;
        }
        int minutes = session.getSession().get(session.getCurrentCountdownIndex()).getMinutes();
        int seconds = session.getSession().get(session.getCurrentCountdownIndex()).getSeconds();
        textUi.printOutputMessage(CHECK_OUTPUT + String.format("%d:%d", minutes, seconds));
    }

    /**
     * Validate the arguments and payloads from a commandMap generated by CommandParser.
     * User input is valid if no exceptions are thrown.
     *
     * @param arguments Argument-Payload map generated by CommandParser
     * @throws BadCommandException If the arguments have any issues
     */
    @Override
    public void validateCommand(HashMap<String, String> arguments) throws BadCommandException {
        if (arguments.size() != COMMAND_NUM_OF_ARGUMENTS) {
            throw new BadCommandException(COMMAND_INVALID_ARGUMENTS_MESSAGE);
        }
        if (!arguments.containsKey(COMMAND_KEYWORD)) {
            throw new BadCommandException(COMMAND_INVALID_ARGUMENTS_MESSAGE);
        }
        if (arguments.get(COMMAND_KEYWORD) != "") {
            throw new BadCommandException(COMMAND_INVALID_ARGUMENTS_MESSAGE);
        }

    }
}
