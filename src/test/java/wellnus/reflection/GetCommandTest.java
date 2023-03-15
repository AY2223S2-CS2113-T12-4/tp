package wellnus.reflection;

import org.junit.jupiter.api.Test;
import wellnus.command.CommandParser;
import wellnus.exception.BadCommandException;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GetCommandTest {
    private static final int EXPECTED_ARRAY_LENGTH = 5;
    private static final int EXPECTED_ARGUMENT_PAYLOAD_SIZE = 1;
    private static final String GET_COMMAND = "get";
    private static final String EMPTY_PAYLOAD = "";
    private static final String GET_COMMAND_WRONG_FORMAT = "get reflect";
    private final CommandParser commandParser;

    public GetCommandTest() {
        this.commandParser = new CommandParser();
    }

    // Test whether the get command is properly generated
    @Test
    void createGetObject_checkArgumentPayload_success() throws BadCommandException {
        HashMap<String, String> getCmdArgumentPayload = commandParser.parseUserInput(GET_COMMAND);
        GetCommand get = new GetCommand(getCmdArgumentPayload);
        assertEquals(EXPECTED_ARGUMENT_PAYLOAD_SIZE, getCmdArgumentPayload.size());
        assertTrue(getCmdArgumentPayload.containsKey(GET_COMMAND));
        assertEquals(EMPTY_PAYLOAD, getCmdArgumentPayload.get(GET_COMMAND));
    }

    // Test the number of questions being generated
    @Test
    void getRandomQuestions_checkLength_expectFive() throws BadCommandException {
        HashMap<String, String> getCmdArgumentPayload = commandParser.parseUserInput(GET_COMMAND);
        GetCommand get = new GetCommand(getCmdArgumentPayload);
        ArrayList<ReflectionQuestion> selectedQuestions = get.getRandomQuestions();
        assertEquals(EXPECTED_ARRAY_LENGTH, selectedQuestions.size());
    }

    // Test whether command is validated properly.
    @Test
    void validateCommand_getCommand_expectException() throws BadCommandException {
        HashMap<String, String> getCmdArgumentPayload =
                commandParser.parseUserInput(GET_COMMAND_WRONG_FORMAT);
        GetCommand get = new GetCommand(getCmdArgumentPayload);
        assertThrows(BadCommandException.class,
                () -> get.validateCommand(getCmdArgumentPayload));
    }
}

