package wellnus.reflection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import wellnus.command.Command;
import wellnus.exception.BadCommandException;

/**
 * Command to get a set of 5 random questions.
 *
 * @@author wenxin-c
 */
public class GetCommand extends Command {
    private static final Logger LOGGER = Logger.getLogger("ReflectGetCommandLogger");
    private static final String FEATURE_NAME = "reflect";
    private static final String COMMAND_KEYWORD = "get";
    private static final String PAYLOAD = "";
    private static final String INVALID_COMMAND_MSG = "Command is invalid.";
    private static final String INVALID_COMMAND_NOTES = "Please check the available commands "
            + "and the format of commands.";
    private static final String EMPTY_ARGUMENT_PAYLOAD_ASSERTION = "The argument-payload pair cannot be empty!";
    private static final String COMMAND_KEYWORD_ASSERTION = "The key should be get.";
    private static final String COMMAND_PAYLOAD_ASSERTION = "The payload should be empty.";
    private static final String NUM_SELECTED_QUESTIONS_ASSERTION = "The number of selected questions should be 5.";
    private static final String DOT = ".";
    private static final String EMPTY_STRING = "";
    private static final int NUM_OF_RANDOM_QUESTIONS = 5;
    private static final int ARGUMENT_PAYLOAD_SIZE = 1;
    private static final int ONE_OFFSET = 1;
    private static final ReflectUi UI = new ReflectUi();
    private HashMap<String, String> argumentPayload;
    private Set<Integer> randomQuestionIndexes;
    private QuestionList questionList;

    /**
     * Set up the argument-payload pairs for this command.<br/>
     * Pass in a questionList object from ReflectionManager to access the list of questions.
     *
     * @param arguments Argument-payload pairs from users
     * @param questionList Object that contains the data about questions
     * @throws BadCommandException If an invalid command is given
     */
    public GetCommand(HashMap<String, String> arguments, QuestionList questionList) throws BadCommandException {
        super(arguments);
        this.argumentPayload = getArguments();
        this.questionList = questionList;
        assert !argumentPayload.isEmpty() : EMPTY_ARGUMENT_PAYLOAD_ASSERTION;
    }

    /**
     * Get the command itself.
     *
     * @return Command: get
     */
    @Override
    protected String getCommandKeyword() {
        return COMMAND_KEYWORD;
    }

    /**
     * Get the name of the feature in which this get command is generated.
     *
     * @return Feature name: reflect
     */
    @Override
    protected String getFeatureKeyword() {
        return FEATURE_NAME;
    }

    /**
     * Entry point to this command.<br/>
     * Trigger the generation of five random questions and print to users.<br/>
     */
    @Override
    public void execute() {
        try {
            validateCommand(this.argumentPayload);
        } catch (BadCommandException invalidCommand) {
            LOGGER.log(Level.INFO, INVALID_COMMAND_MSG);
            UI.printErrorFor(invalidCommand, INVALID_COMMAND_NOTES);
            return;
        }
        assert argumentPayload.containsKey(COMMAND_KEYWORD) : COMMAND_KEYWORD_ASSERTION;
        assert argumentPayload.get(COMMAND_KEYWORD).equals(PAYLOAD) : COMMAND_PAYLOAD_ASSERTION;
        String outputString = convertQuestionsToString();
        UI.printOutputMessage(outputString);
    }

    /**
     * Validate the command.<br/>
     * <br/>
     * Conditions for command to be valid:<br/>
     * <li>Only one argument-payload pair
     * <li>The pair contains key: get
     * <li>Payload is empty
     * Whichever mismatch will cause the command to be invalid.
     *
     * @param commandMap Argument-Payload map generated by CommandParser
     * @throws BadCommandException If an invalid command is given
     */
    @Override
    public void validateCommand(HashMap<String, String> commandMap) throws BadCommandException {
        if (commandMap.size() != ARGUMENT_PAYLOAD_SIZE) {
            throw new BadCommandException(INVALID_COMMAND_MSG);
        } else if (!commandMap.containsKey(COMMAND_KEYWORD)) {
            throw new BadCommandException(INVALID_COMMAND_MSG);
        } else if (!commandMap.get(COMMAND_KEYWORD).equals(PAYLOAD)) {
            throw new BadCommandException(INVALID_COMMAND_MSG);
        }
    }

    /**
     * Use questionList object to generate a set of 5 random integers(0-9) which will then be used as indexes to get
     * a set of 5 random questions.
     *
     * @return The selected sets of random questions
     */
    public ArrayList<ReflectionQuestion> getRandomQuestions() {
        this.questionList.setRandomQuestionIndexes();
        this.randomQuestionIndexes = questionList.getRandomQuestionIndexes();
        ArrayList<ReflectionQuestion> selectedQuestions = new ArrayList<>();
        ArrayList<ReflectionQuestion> questions = questionList.getAllQuestions();
        for (int index : this.randomQuestionIndexes) {
            selectedQuestions.add(questions.get(index));
        }
        assert selectedQuestions.size() == NUM_OF_RANDOM_QUESTIONS : NUM_SELECTED_QUESTIONS_ASSERTION;
        return selectedQuestions;
    }

    /**
     * Convert all five questions to a single string to be printed.
     *
     * @return Single string that consists of all questions
     */
    private String convertQuestionsToString() {
        ArrayList<ReflectionQuestion> selectedQuestions = getRandomQuestions();
        String questionString = EMPTY_STRING;
        for (int i = 0; i < selectedQuestions.size(); i += 1) {
            questionString += ((i + ONE_OFFSET) + DOT + selectedQuestions.get(i).toString()
                    + System.lineSeparator());
        }
        return questionString;
    }
}

