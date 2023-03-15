package wellnus.reflection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class SelfReflectionTest {
    private static final String DEFAULT_SEPARATOR = "========================================"
            + "====================";
    private static final String LOGO =
            "  _____ ______ _      ______   _____  ______ ______ _      ______ _____ _______ _____ ____  _   _ \n"
                    + " / ____|  ____| |    |  ____| |  __ \\|  ____|  ____| |    "
                    + "|  ____/ ____|__   __|_   _/ __ \\| \\ | |\n"
                    + "| (___ | |__  | |    | |__    | |__) | |__  | |__  | |    "
                    + "| |__ | |       | |    | || |  | |  \\| |\n"
                    + " \\___ \\|  __| | |    |  __|   |  _  /|  __| |  __| | "
                    + "|    |  __|| |       | |    | || |  | | . ` |\n"
                    + " ____) | |____| |____| |      | | \\ \\| |____| |    | "
                    + "|____| |___| |____   | |   _| || |__| | |\\  |\n"
                    + "|_____/|______|______|_|      |_|  \\_\\______|_|    "
                    + "|______|______\\_____|  |_|  |_____\\____/|_| \\_|";

    private static final String GREETING_MESSAGE = "    Welcome to WellNUS++ Self Reflection section :D"
            + System.lineSeparator() + "    Feel very occupied and cannot find time to self reflect?"
            + System.lineSeparator() + "    No worries, this section will give you the opportunity to reflect "
            + "and improve on yourself!!";
    private static final int EMPTY_ARRAY_LENGTH = 0;
    private static final int FULL_ARRAY_LENGTH = 10;

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    // Test the correct number of questions are loaded into the list
    @Test
    void setUpQuestions_checkArrayLength_success() {
        SelfReflection selfReflection = new SelfReflection();
        ArrayList<ReflectionQuestion> questions = selfReflection.getQuestions();
        int fullArrayLength = questions.size();
        selfReflection.clearQuestions();
        int emptyArrayLength = questions.size();
        assertEquals(EMPTY_ARRAY_LENGTH, emptyArrayLength);
        assertEquals(FULL_ARRAY_LENGTH, fullArrayLength);
    }
}

