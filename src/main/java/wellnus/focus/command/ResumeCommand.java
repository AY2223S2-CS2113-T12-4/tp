package wellnus.focus.command;

import java.util.HashMap;

import wellnus.command.Command;
import wellnus.exception.BadCommandException;
import wellnus.focus.feature.FocusManager;
import wellnus.focus.feature.FocusUi;
import wellnus.focus.feature.Session;

/**
 * Represents a command to resume the countdown timer in the current session.
 */
public class ResumeCommand extends Command {

    public static final String COMMAND_DESCRIPTION = "resume - Continue the countdown. "
            + "Can only be used when a countdown is paused.";
    public static final String COMMAND_USAGE = "usage: home";
    public static final String COMMAND_KEYWORD = "resume";
    private static final int COMMAND_NUM_OF_ARGUMENTS = 1;
    private static final String COMMAND_INVALID_COMMAND_MESSAGE = "Invalid command issued, expected 'resume'!";
    private static final String COMMAND_INVALID_ARGUMENTS_MESSAGE = "Invalid arguments given to 'resume'!";
    private static final String COMMAND_INVALID_PAYLOAD = "Invalid payload given to 'resume'!";
    private static final String COMMAND_KEYWORD_ASSERTION = "The key should be resume.";
    private static final String RESUME_OUTPUT = "Timer resumed at: ";
    private static final String ERROR_NOT_PAUSED = "You don't seem to be paused. Ignoring the command!";
    private static final String COMMAND_INVALID_COMMAND_NOTE = "resume command " + COMMAND_USAGE;
    private final Session session;
    private final FocusUi focusUi;

    /**
     * Constructs a ResumeCommand object.
     * Session in FocusManager is passed into this class.
     *
     * @param arguments Argument-Payload Hashmap generated by CommandParser
     * @param session   Session object which is an arraylist of Countdowns
     */
    public ResumeCommand(HashMap<String, String> arguments, Session session) {
        super(arguments);
        this.session = session;
        this.focusUi = new FocusUi();
    }

    /**
     * Identifies this Command's keyword.
     * Override this in subclasses so toString() returns the correct String representation.
     *
     * @return String Keyword of this Command
     */
    @Override
    protected String getCommandKeyword() {
        return COMMAND_KEYWORD;
    }

    /**
     * Identifies the feature that this Command is associated with.
     * Override this in subclasses so toString() returns the correct String representation.
     *
     * @return String Keyword for the feature associated with this Command
     */
    @Override
    protected String getFeatureKeyword() {
        return FocusManager.FEATURE_NAME;
    }

    /**
     * Resumes the current countdown.
     * Prints the time left to be completed in the current countdown.
     */
    @Override
    public void execute() {
        try {
            validateCommand(super.getArguments());
        } catch (BadCommandException badCommandException) {
            focusUi.printErrorFor(badCommandException, COMMAND_INVALID_COMMAND_NOTE);
            return;
        }
        assert super.getArguments().containsKey(COMMAND_KEYWORD) : COMMAND_KEYWORD_ASSERTION;
        if (!session.hasAnyCountdown() || !session.isSessionPaused()) {
            focusUi.printOutputMessage(ERROR_NOT_PAUSED);
            return;
        }
        int minutes = session.getCurrentCountdown().getMinutes();
        int seconds = session.getCurrentCountdown().getSeconds();
        focusUi.printOutputMessage(RESUME_OUTPUT + String.format("%d:%d", minutes, seconds));
        session.getCurrentCountdown().setStart();
    }

    /**
     * Validate the arguments and payloads from a commandMap generated by CommandParser.
     * User input is valid if no arguments are thrown.
     */
    public void validateCommand(HashMap<String, String> arguments) throws BadCommandException {
        if (arguments.size() != COMMAND_NUM_OF_ARGUMENTS) {
            throw new BadCommandException(COMMAND_INVALID_ARGUMENTS_MESSAGE);
        }
        if (!arguments.containsKey(COMMAND_KEYWORD)) {
            throw new BadCommandException(COMMAND_INVALID_COMMAND_MESSAGE);
        }
        if (!arguments.get(COMMAND_KEYWORD).equals("")) {
            throw new BadCommandException(COMMAND_INVALID_PAYLOAD);
        }
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
}
