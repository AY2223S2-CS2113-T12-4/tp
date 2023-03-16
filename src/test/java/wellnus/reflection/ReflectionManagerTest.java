package wellnus.reflection;

import org.junit.jupiter.api.Test;
import wellnus.command.Command;
import wellnus.exception.BadCommandException;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class ReflectionManagerTest {
    private static final String EMPTY_STRING = "";
    private static final String EXIT_COMMAND = "exit";
    private static final String GET_COMMAND = "get";
    private static final String INVALID_COMMAND = "test";
    private static final String RETURN_COMMAND = "return";

    // Test whether exceptions are thrown for an invalid command given by the user
    @Test
    void getCommandFor_invalidCommand_expectException() throws BadCommandException {
        ReflectionManager reflectionManager = new ReflectionManager();
        assertThrows(BadCommandException.class,
                () -> reflectionManager.getCommandFor(INVALID_COMMAND));
    }

    // Test whether exceptions are thrown when user gives an empty command
    @Test
    void getCommandFor_emptyString_expectException() {
        ReflectionManager reflectionManager = new ReflectionManager();
        String[] input = EMPTY_STRING.split(" ");
        System.out.println(input.length);
        assertThrows(BadCommandException.class,
                () -> reflectionManager.getCommandFor(EMPTY_STRING));
    }

    /**
     * Test whether ReflectionManager can recognise the 'exit' command correctly.
     */
    @Test
    void getCommandFor_exitCommand_success() {
        ReflectionManager reflectionManager = new ReflectionManager();
        try {
            Command command = reflectionManager.getCommandFor(EXIT_COMMAND);
            assertTrue(command instanceof ExitCommand);
        } catch (BadCommandException badCommandException) {
            fail();
        }
    }

    /**
     * Test whether ReflectionManager can recognise the 'get' command correctly.
     */
    @Test
    void getCommandFor_getCommand_success() {
        ReflectionManager reflectionManager = new ReflectionManager();
        try {
            Command command = reflectionManager.getCommandFor(GET_COMMAND);
            assertTrue(command instanceof GetCommand);
        } catch (BadCommandException badCommandException) {
            fail();
        }
    }

    /**
     * Test whether ReflectionManager can recognise the 'return' command correctly.
     */
    @Test
    void getCommandFor_returnCommand_success() {
        ReflectionManager reflectionManager = new ReflectionManager();
        try {
            Command command = reflectionManager.getCommandFor(RETURN_COMMAND);
            assertTrue(command instanceof ReturnCommand);
        } catch (BadCommandException badCommandException) {
            fail();
        }
    }

    // Test whether supported commands are properly set up.
    @Test
    void setSupportedCommands_checkCommandTypes_success() {
        ReflectionManager reflectionManager = new ReflectionManager();
        ArrayList<Command> supportedCommands = reflectionManager.getSupportedCommands();
        assertTrue(supportedCommands.get(0) instanceof GetCommand);
        assertTrue(supportedCommands.get(1) instanceof ReturnCommand);
        assertTrue(supportedCommands.get(2) instanceof ExitCommand);
    }
}

