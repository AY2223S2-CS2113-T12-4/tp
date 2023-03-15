package wellnus.atomichabit.command;

import wellnus.atomichabit.feature.AtomicHabit;
import wellnus.atomichabit.feature.AtomicHabitList;
import wellnus.atomichabit.feature.AtomicHabitManager;
import wellnus.command.Command;
import wellnus.command.CommandParser;
import wellnus.exception.AtomicHabitException;
import wellnus.exception.BadCommandException;
import wellnus.ui.TextUi;

import java.io.InputStream;
import java.util.HashMap;

import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdateCommand extends Command {
    private static final String COMMAND_KEYWORD = "update";
    private static final String COMMAND_DETAILED_DESCRIPTION = "";
    private static final String COMMAND_INCREMENT_ARGUMENT = "inc";
    private static final String COMMAND_INDEX_ARGUMENT = "id";
    private static final int COMMAND_MIN_NUM_OF_ARGUMENTS = 2;
    private static final String COMMAND_SUPPORTED_ARGUMENTS = "--id <index> --inc <increment>";
    private static final String COMMAND_INVALID_COMMAND_MESSAGE = "Wrong command issued, expected 'hb update'?";
    private static final String DOT = ".";
    private static final int DEFAULT_INCREMENT = 1;
    private static final String FEEDBACK_STRING = "The following habit has been incremented! Keep up the good work!";
    private static final String FEEDBACK_INDEX_NOT_INTEGER_ERROR = "Invalid index! Please enter an integer";
    private static final String FEEDBACK_INDEX_OUT_OF_BOUNDS_ERROR = "Index out of Range! Please enter a valid index";
    private static final int INDEX_OFFSET = 1;
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String UPDATE_INVALID_ARGUMENTS_MESSAGE = "Invalid arguments for updating, no update shall "
            + "be performed.";
    private static final String REGEX_INTEGER_ONLY_PATTERN = "\\s*-?\\d+\\s*";
    private static final Logger logger = Logger.getLogger("UpdateAtomicHabitLogger");
    private static final String LOG_STR_INPUT_NOT_INTEGER = "Input string is not an integer."
            + "This should be properly handled";

    private static final String LOG_INDEX_OUT_OF_BOUNDS = "Input index is out of bounds."
            + "This should be properly handled";
    private final AtomicHabitList atomicHabits;
    private String indexString;
    private final CommandParser parser;
    private final TextUi textUi;

    public UpdateCommand(HashMap<String, String> arguments, AtomicHabitList atomicHabits) {
        super(arguments);
        this.atomicHabits = atomicHabits;
        this.parser = new CommandParser();
        this.textUi = new TextUi();
    }

    public UpdateCommand(InputStream inputStream, HashMap<String, String> arguments,
                AtomicHabitList atomicHabits) {
        super(arguments);
        this.atomicHabits = atomicHabits;
        this.parser = new CommandParser();
        this.textUi = new TextUi(inputStream);
    }

    private AtomicHabitList getAtomicHabits() {
        return this.atomicHabits;
    }

    private TextUi getTextUi() {
        return this.textUi;
    }

    private CommandParser getParser() {
        return parser;
    }

    private int getIncrementCountFrom(HashMap<String, String> arguments)
            throws BadCommandException, NumberFormatException {
        if (!arguments.containsKey(UpdateCommand.COMMAND_INCREMENT_ARGUMENT)) {
            throw new BadCommandException(UpdateCommand.UPDATE_INVALID_ARGUMENTS_MESSAGE);
        }
        String incrementCountString = arguments.get(UpdateCommand.COMMAND_INCREMENT_ARGUMENT);
        return Integer.parseInt(incrementCountString);
    }

    private int getIndexFrom(HashMap<String, String> arguments)
            throws BadCommandException, NumberFormatException {
        if (!arguments.containsKey(UpdateCommand.COMMAND_INDEX_ARGUMENT)) {
            throw new BadCommandException(UpdateCommand.UPDATE_INVALID_ARGUMENTS_MESSAGE);
        }
        String indexString = arguments.get(UpdateCommand.COMMAND_INDEX_ARGUMENT);
        return Integer.parseInt(indexString);
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
     * Returns a detailed user-friendly description of what this specific command does.
     *
     * @return String Detailed explanation of this command
     */
    @Override
    protected String getDetailedDescription() {
        return COMMAND_DETAILED_DESCRIPTION;
    }

    /**
     * Identifies the feature that this Command is associated with. Override
     * this in subclasses so toString() returns the correct String representation.
     *
     * @return String Keyword for the feature associated with this Command
     */
    @Override
    protected String getFeatureKeyword() {
        return AtomicHabitManager.FEATURE_NAME;
    }

    /**
     * Returns all the supported arguments for this Command.
     *
     * @return String All supported arguments for this Command
     */
    @Override
    protected String getSupportedCommandArguments() {
        return COMMAND_SUPPORTED_ARGUMENTS;
    }

    /**
     * Executes the update command for atomic habits.<br>
     *
     * This command is interactive, so user will continue providing arguments via
     *     further prompts provided.
     */
    @Override
    public void execute() throws AtomicHabitException {
        try {
            validateCommand(super.getArguments());
        } catch (BadCommandException badCommandException) {
            String NO_ADDITIONAL_MESSAGE = "";
            getTextUi().printErrorFor(badCommandException, NO_ADDITIONAL_MESSAGE);
            return;
        }
        try {
            int incrementCount = DEFAULT_INCREMENT;
            if (super.getArguments().containsKey(UpdateCommand.COMMAND_INCREMENT_ARGUMENT)) {
                incrementCount = this.getIncrementCountFrom(super.getArguments());
            }
            int index = this.getIndexFrom(super.getArguments()) - INDEX_OFFSET;
            AtomicHabit habit = getAtomicHabits().getHabitByIndex(index);
            habit.increaseCount(incrementCount);
            String stringOfUpdatedHabit = (index + 1) + DOT + habit + " " + "[" + habit.getCount() + "]"
                    + LINE_SEPARATOR;
            getTextUi().printOutputMessage(FEEDBACK_STRING + LINE_SEPARATOR
                    + stringOfUpdatedHabit);
        } catch (NumberFormatException numberFormatException) {
            logger.log(Level.INFO, LOG_STR_INPUT_NOT_INTEGER);
            throw new AtomicHabitException(FEEDBACK_INDEX_NOT_INTEGER_ERROR);
        } catch (IndexOutOfBoundsException e) {
            logger.log(Level.INFO, LOG_INDEX_OUT_OF_BOUNDS);
            throw new AtomicHabitException(FEEDBACK_INDEX_OUT_OF_BOUNDS_ERROR);
        } catch (BadCommandException badCommandException) {
            String NO_ADDITIONAL_MESSAGE = "";
            getTextUi().printErrorFor(badCommandException, NO_ADDITIONAL_MESSAGE);
        }
    }

    /**
     * Validate the arguments and payloads from a commandMap generated by CommandParser.<br>
     *
     * If no exceptions are thrown, arguments are valid.
     *
     * @param arguments Argument-Payload map generated by CommandParser
     * @throws BadCommandException If the arguments have any issues
     */
    @Override
    public void validateCommand(HashMap<String, String> arguments) throws BadCommandException {
        String commandKeyword = arguments.get(AtomicHabitManager.FEATURE_NAME);
        if (!commandKeyword.equals(UpdateCommand.COMMAND_KEYWORD)) {
            throw new BadCommandException(UpdateCommand.COMMAND_INVALID_COMMAND_MESSAGE);
        }
        if (arguments.size() < UpdateCommand.COMMAND_MIN_NUM_OF_ARGUMENTS) {
            throw new BadCommandException(UpdateCommand.COMMAND_INVALID_COMMAND_MESSAGE);
        }
        if (!arguments.containsKey(UpdateCommand.COMMAND_INDEX_ARGUMENT)) {
            throw new BadCommandException(UpdateCommand.COMMAND_INVALID_COMMAND_MESSAGE);
        }
        if (arguments.containsKey(UpdateCommand.COMMAND_INCREMENT_ARGUMENT)) {
            String incrementString = arguments.get(COMMAND_INCREMENT_ARGUMENT);
            if (incrementString.isBlank()) {
                throw new BadCommandException(UpdateCommand.COMMAND_INVALID_COMMAND_MESSAGE);
            }
        }
    }

}
