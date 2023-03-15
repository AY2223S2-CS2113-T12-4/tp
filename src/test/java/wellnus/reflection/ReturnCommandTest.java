package wellnus.reflection;

import org.junit.jupiter.api.Test;
import wellnus.command.CommandParser;
import wellnus.exception.BadCommandException;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ReturnCommandTest {
    private static final String RETURN_COMMAND = "return";
    private static final String RETURN_COMMAND_WRONG_FORMAT = "return back";
    private static final boolean IS_NOT_EXIT = false;
    private static final boolean IS_EXIT = true;
    private final CommandParser commandParser;

    public ReturnCommandTest() {
        this.commandParser = new CommandParser();
    }

    // Test whether ReturnCommand execute() method can terminate self reflection or not.
    @Test
    void execute_checkWillReturn_success() throws BadCommandException {
        HashMap<String, String> returnArgumentPayload = commandParser.parseUserInput(RETURN_COMMAND);
        ReturnCommand returnCmd = new ReturnCommand(returnArgumentPayload);
        try {
            returnCmd.validateCommand(returnArgumentPayload);
        } catch (BadCommandException badCommandException) {
            fail();
        }
    }

    // Test whether wrong format command exception is caught or not.
    @Test
    void execute_checkWrongCmdFormat_expectException() throws BadCommandException {
        HashMap<String, String> returnArgumentPayload = commandParser.parseUserInput(RETURN_COMMAND_WRONG_FORMAT);
        ReturnCommand returnCmd = new ReturnCommand(returnArgumentPayload);
        assertThrows(BadCommandException.class, () -> {
            returnCmd.validateCommand(returnArgumentPayload);
        });
    }
}

