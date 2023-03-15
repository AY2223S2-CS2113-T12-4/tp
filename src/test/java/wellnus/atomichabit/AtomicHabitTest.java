package wellnus.atomichabit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import wellnus.atomichabit.command.AddCommand;
import wellnus.atomichabit.command.UpdateCommand;
import wellnus.command.Command;
import wellnus.command.CommandParser;
import wellnus.exception.AtomicHabitException;
import wellnus.atomichabit.feature.AtomicHabitList;
import wellnus.atomichabit.feature.AtomicHabitManager;
import wellnus.exception.WellNusException;
import wellnus.ui.TextUi;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;

public class AtomicHabitTest {
    private static final String UPDATE_HABIT_COMMAND = "hb update";
    private final AtomicHabitList habitList;
    private final ByteArrayOutputStream outputStreamCaptor;
    private final CommandParser parser;

    public AtomicHabitTest() {
        this.habitList = new AtomicHabitList();
        this.outputStreamCaptor = new ByteArrayOutputStream();
        this.parser = new CommandParser();
    }

    private String getMessageFrom(String uiOutput) {
        TextUi textUi = new TextUi();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        textUi.printSeparator();
        String separator = outputStream.toString().trim();
        StringBuilder resultBuilder = new StringBuilder();
        String[] outputLines = uiOutput.split(System.lineSeparator());
        for (String outputLine : outputLines) {
            String trimmedOutputLine = outputLine.trim();
            if (!trimmedOutputLine.equals(separator)) {
                resultBuilder.append(trimmedOutputLine).append(System.lineSeparator());
            }
        }
        return resultBuilder.toString().trim();
    }

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    /**
     * Test AddCommand with a standard payload to check output printed
     */
    @Test
    public void addHabit_checkOutput_success() throws WellNusException {
        String payload = "junit test";
        String expectedOutput = "Yay! You have added a new habit:"
                + System.lineSeparator()
                + "'"
                + payload
                + "'"
                + " was successfully added";
        String testCommand = "hb add --name " + payload;
        HashMap<String, String> arguments = parser.parseUserInput(testCommand);
        Command command = new AddCommand(arguments, habitList);
        command.execute();
        Assertions.assertEquals(expectedOutput, getMessageFrom(outputStreamCaptor.toString()));
    }

    /**
     * Test AddCommand to throw {@link AtomicHabitException} when an invalid command is given to the AtomicHabitManager
     */
    @Test
    public void addHabit_invalidCommand_atomicHabitExceptionThrown() {
        // Test false command by user
        AtomicHabitManager atomicHabitManager = new AtomicHabitManager();
        String command = "sleep";
        Assertions.assertThrows(AtomicHabitException.class, () -> {
            atomicHabitManager.testInvalidCommand(command);
        }, "The following is an invalid command:\n"
                + command);
    }

    /**
     * Test UpdateCommand with a standard payload and default increment to check output printed
     */
    @Test
    public void updateHabit_checkOutput_success() throws WellNusException {
        addHabit_checkOutput_success();
        String payload = "junit test";
        String habitIndex = "1";
        String testUpdateCommand = String.format("%s --id %s", UPDATE_HABIT_COMMAND, habitIndex)
                + System.lineSeparator();
        HashMap<String, String> arguments = parser.parseUserInput(testUpdateCommand);
        Command updateCommand = new UpdateCommand(arguments, habitList);
        String expectedUpdateHabitOutput = "The following habit has been incremented! Keep up the good work!"
                + System.lineSeparator()
                + habitIndex + "." + payload + " " + "[2]";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        updateCommand.execute();
        Assertions.assertEquals(expectedUpdateHabitOutput, getMessageFrom(outputStream.toString()));
    }

    /**
     * Test UpdateCommand to throw {@link AtomicHabitException} when a non-integer index is given to the UpdateCommand
     */
    @Test
    public void updateHabit_indexNotInteger_atomicHabitExceptionThrown() throws WellNusException {
        // Test false command by user
        addHabit_checkOutput_success();
        String habitIndex = "a";
        String testIndexCommand = String.format("%s --id %s", UPDATE_HABIT_COMMAND, habitIndex)
                + System.lineSeparator();
        HashMap<String, String> arguments = parser.parseUserInput(testIndexCommand);
        Command updateCommand = new UpdateCommand(arguments, habitList);
        Assertions.assertThrows(AtomicHabitException.class, updateCommand::execute);
    }

    /**
     * Test UpdateCommand to throw {@link AtomicHabitException} when an out-of-bounds index is given
     * to the UpdateCommand
     */
    @Test
    public void updateHabit_indexOutOfBounds_atomicHabitExceptionThrown() throws WellNusException {
        // Test false command by user
        addHabit_checkOutput_success();
        String largeHabitIndex = "100000000" + System.lineSeparator();
        String negativeHabitIndex = "-100000000" + System.lineSeparator();
        String testLargeIndexCommand = String.format("%s --id %s", UPDATE_HABIT_COMMAND, largeHabitIndex)
                + System.lineSeparator();
        String testNegativeIndexCommand = String.format("%s --id %s", UPDATE_HABIT_COMMAND, negativeHabitIndex)
                + System.lineSeparator();
        HashMap<String, String> arguments = parser.parseUserInput(testLargeIndexCommand);
        Command updateCommandForLargeIndex = new UpdateCommand(arguments, habitList);
        Assertions.assertThrows(AtomicHabitException.class, updateCommandForLargeIndex::execute);

        arguments = parser.parseUserInput(testNegativeIndexCommand);
        Command updateCommandForNegativeIndex = new UpdateCommand(arguments, habitList);
        Assertions.assertThrows(AtomicHabitException.class, updateCommandForNegativeIndex::execute);
    }
}



