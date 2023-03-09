package seedu.duke.command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class CommandParserTest {

    @Test
    public void parseUserInput_validInput() {
        // The following commands should be able to pass
        String[] validCommandInputs = {
            "mainCommand",
            "mainCommand payload",
            "main --arg1 pay1",
            "main --arg1 pay1 --arg2",
            "main --arg1 pay1 --arg2 --arg3 pay3",
        };

        // The following tests check if adversarial inputs are processed correctly
        String[] validTrickyInputs = {
            "mainCommand pay--load",
            "mainCommand --argument1 payload--",
            "  mainCommand --arg--1 pay1 --arg2 pay2",
        };

        CommandParser parser = new CommandParser();
        for (String validCommand : validCommandInputs) {
            try {
                parser.parseUserInput(validCommand);
            } catch (BadCommandException exception) {
                fail("CommandParser threw exception on valid input:\n" + exception);
            }
        }
        for (String validCommand : validTrickyInputs) {
            try {
                parser.parseUserInput(validCommand);
            } catch (BadCommandException exception) {
                fail("CommandParser threw exception on valid input:\n" + exception);
            }
        }
    }

    @Test
    public void parseUserInput_emptyInput_exceptionThrown() {
        CommandParser parser = new CommandParser();
        // Test on empty user input
        String command = "";
        Assertions.assertThrows(BadCommandException.class, () -> {
            parser.parseUserInput(command);
        }, "BadCommandException was expected for input:\n" + command);
    }

    @Test
    public void parseUserInput_emptyArgument_exceptionThrown() {
        CommandParser parser = new CommandParser();
        // Test on empty argument
        String commandEmptyArg = "mainCommand payload --";
        Assertions.assertThrows(BadCommandException.class, () -> {
            parser.parseUserInput(commandEmptyArg);
        }, "BadCommandException was expected for input:\n" + commandEmptyArg);

        // Test on empty argument with padding
        String commandEmptyArgPadded = "mainCommand payload -- ";
        Assertions.assertThrows(BadCommandException.class, () -> {
            parser.parseUserInput(commandEmptyArgPadded);
        }, "BadCommandException was expected for input:\n" + commandEmptyArgPadded);

        // Test on empty argument, payload exists
        String emptyCmdWithPayload = "mainCommand payload -- payload1";
        Assertions.assertThrows(BadCommandException.class, () -> {
            parser.parseUserInput(emptyCmdWithPayload);
        }, "BadCommandException was expected for input:\n" + emptyCmdWithPayload);
    }

    @Test
    public void parseUserInput_noMainArgument_exceptionThrown() {
        CommandParser parser = new CommandParser();
        // Test on empty user input without padding
        String command = "--arg1 payload";
        Assertions.assertThrows(BadCommandException.class, () -> {
            parser.parseUserInput(command);
        }, "BadCommandException was expected for input:\n" + command);

        // Test on empty user input with padding
        String commandPadded = " --arg1 payload";
        Assertions.assertThrows(BadCommandException.class, () -> {
            parser.parseUserInput(commandPadded);
        }, "BadCommandException was expected for input:\n" + commandPadded);
    }

    @Test
    public void getMainArgumentTest() {
        CommandParser parser = new CommandParser();
        String target = "mainCommand";
        String command = "mainCommand payload --argument payload1";
        try {
            String result1 = parser.getMainArgument(command);
            assertEquals(target, result1);
        } catch (BadCommandException exception) {
            fail(exception.getMessage());
        }
    }

}
