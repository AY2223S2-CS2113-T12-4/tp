package wellnus.atomichabit.command;

import java.io.InputStream;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import wellnus.atomichabit.feature.AtomicHabit;
import wellnus.atomichabit.feature.AtomicHabitList;
import wellnus.atomichabit.feature.AtomicHabitManager;
import wellnus.atomichabit.feature.AtomicHabitUi;
import wellnus.command.Command;
import wellnus.common.WellNusLogger;
import wellnus.exception.AtomicHabitException;
import wellnus.exception.BadCommandException;
import wellnus.ui.TextUi;

/**
 * The DeleteCommand class is a command class that deletes a habit
 * has been preformed.<br>
 */
public class DeleteCommand extends Command {
    public static final String COMMAND_DESCRIPTION = "delete - Delete the habit you don't want to continue.";
    public static final String COMMAND_USAGE = "usage: delete --id habit-index";
    public static final String COMMAND_KEYWORD = "delete";
    private static final String COMMAND_INDEX_ARGUMENT = "id";
    private static final int COMMAND_NUM_OF_ARGUMENTS = 2;
    private static final String COMMAND_INVALID_COMMAND_MESSAGE = "Invalid command issued, expected 'delete'";
    private static final String COMMAND_INVALID_PAYLOAD = "Invalid payload given to 'delete'!";
    private static final String FEEDBACK_STRING = "The following habit has been deleted:";
    private static final String FEEDBACK_STRING_TWO = "has been successfully deleted";
    private static final String FEEDBACK_INDEX_NOT_INTEGER_ERROR = "Invalid index payload given, expected a valid integer";
    private static final String FEEDBACK_INDEX_OUT_OF_BOUNDS_ERROR = "Invalid index payload given, "
            + "index is out of range!";
    private static final String FEEDBACK_EMPTY_LIST_UPDATE = "There are no habits to delete! "
            + "Please `add` a habit first!";
    private static final int INDEX_OFFSET = 1;
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String DELETE_INVALID_ARGUMENTS_MESSAGE = "Invalid arguments given to 'delete'";
    private static final String COMMAND_INVALID_COMMAND_NOTE = "delete command " + COMMAND_USAGE;
    private static final Logger LOGGER = WellNusLogger.getLogger("DeleteAtomicHabitLogger");
    private static final String LOG_STR_INPUT_NOT_INTEGER = "Input string is not an integer."
            + "This should be properly handled";

    private static final String LOG_INDEX_OUT_OF_BOUNDS = "Input index is out of bounds."
            + "This should be properly handled";
    private final AtomicHabitList atomicHabits;
    private final AtomicHabitUi atomicHabitUi;

    /**
     * Constructs an DeleteCommand object with the given arguments and AtomicHabitList.<br>
     *
     * @param arguments    Argument-Payload map generated by CommandParser.
     * @param atomicHabits The AtomicHabitList object containing habit to be deleted.
     */
    public DeleteCommand(HashMap<String, String> arguments, AtomicHabitList atomicHabits) {
        super(arguments);
        this.atomicHabits = atomicHabits;
        this.atomicHabitUi = new AtomicHabitUi();
    }

    /**
     * Constructs an DeleteCommand object with the given InputStream, arguments and AtomicHabitList.<br>
     *
     * @param inputStream  An InputStream object representing the input stream to be used.
     * @param arguments    Argument-Payload map generated by CommandParser.
     * @param atomicHabits The AtomicHabitList object containing habit to be deleted.
     */
    public DeleteCommand(InputStream inputStream, HashMap<String, String> arguments,
                         AtomicHabitList atomicHabits) {
        super(arguments);
        this.atomicHabits = atomicHabits;
        this.atomicHabitUi = new AtomicHabitUi(inputStream);
    }

    private AtomicHabitList getAtomicHabits() {
        return this.atomicHabits;
    }

    private TextUi getTextUi() {
        return this.atomicHabitUi;
    }


    private int getIndexFrom(HashMap<String, String> arguments) throws BadCommandException, NumberFormatException {
        if (!arguments.containsKey(COMMAND_INDEX_ARGUMENT)) {
            throw new BadCommandException(DELETE_INVALID_ARGUMENTS_MESSAGE);
        }
        String indexString = arguments.get(COMMAND_INDEX_ARGUMENT);
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
     * Executes the delete command for atomic habits.<br>
     * <p>
     * This command is interactive, so user will continue providing arguments via
     * further prompts provided.
     */
    @Override
    public void execute() throws AtomicHabitException {
        try {
            validateCommand(super.getArguments());
        } catch (BadCommandException badCommandException) {
            getTextUi().printErrorFor(badCommandException, COMMAND_INVALID_COMMAND_NOTE);
            return;
        }
        try {
            if (getAtomicHabits().getAllHabits().isEmpty()) {
                getTextUi().printOutputMessage(FEEDBACK_EMPTY_LIST_UPDATE);
                return;
            }
            int index = this.getIndexFrom(super.getArguments()) - INDEX_OFFSET;
            AtomicHabit habitToDelete = getAtomicHabits().getHabitByIndex(index);
            atomicHabits.deleteAtomicHabit(habitToDelete);
            String stringOfDeletedHabit = habitToDelete + " " + "[" + habitToDelete.getCount() + "]" + " "
                    + FEEDBACK_STRING_TWO
                    + LINE_SEPARATOR;
            getTextUi().printOutputMessage(FEEDBACK_STRING + LINE_SEPARATOR
                    + stringOfDeletedHabit);
        } catch (NumberFormatException numberFormatException) {
            LOGGER.log(Level.INFO, LOG_STR_INPUT_NOT_INTEGER);
            throw new AtomicHabitException(FEEDBACK_INDEX_NOT_INTEGER_ERROR);
        } catch (IndexOutOfBoundsException e) {
            LOGGER.log(Level.INFO, LOG_INDEX_OUT_OF_BOUNDS);
            throw new AtomicHabitException(FEEDBACK_INDEX_OUT_OF_BOUNDS_ERROR);
        } catch (BadCommandException badCommandException) {
            getTextUi().printErrorFor(badCommandException, COMMAND_INVALID_COMMAND_NOTE);
        }
    }

    /**
     * Validate the arguments and payloads from a commandMap generated by CommandParser.<br>
     * <p>
     * If no exceptions are thrown, arguments are valid.
     *
     * @param arguments Argument-Payload map generated by CommandParser
     * @throws BadCommandException If the arguments have any issues
     */
    @Override
    public void validateCommand(HashMap<String, String> arguments) throws BadCommandException {
        if (!arguments.containsKey(COMMAND_KEYWORD)) {
            throw new BadCommandException(COMMAND_INVALID_COMMAND_MESSAGE);
        }
        if (!arguments.get(COMMAND_KEYWORD).equals("")) {
            throw new BadCommandException(COMMAND_INVALID_PAYLOAD);
        }
        if (arguments.size() != COMMAND_NUM_OF_ARGUMENTS) {
            throw new BadCommandException(DELETE_INVALID_ARGUMENTS_MESSAGE);
        }
        if (!arguments.containsKey(COMMAND_INDEX_ARGUMENT)) {
            throw new BadCommandException(DELETE_INVALID_ARGUMENTS_MESSAGE);
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
