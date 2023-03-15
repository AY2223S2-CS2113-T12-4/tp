package wellnus.reflection;

import java.util.ArrayList;

/**
 * The main body of self reflect section.<br/>
 * Including pre-defined questions and greeting messages.
 */
public class SelfReflection {

    // Questions are adopted from website: https://www.usa.edu/blog/self-discovery-questions/
    private static final String[] QUESTIONS = {
        "What are three of my most cherished personal values?",
        "What is my purpose in life?",
        "What is my personality type?",
        "Did I make time for myself this week?",
        "Am I making time for my social life?",
        "What scares me the most right now?",
        "What is something I find inspiring?",
        "What is something that brings me joy?",
        "When is the last time I gave back to others?",
        "What matters to me most right now?"
    };

    // TODO: To be changed/updated at later stages
    private static final int TOTAL_NUM_QUESTIONS = 10;
    private static final String TOTAL_NUM_QUESTION_ASSERTIONS = "The total number of questions is 10.";
    private final ReflectUi reflectUi = new ReflectUi();

    private ArrayList<ReflectionQuestion> savedQuestions;

    public SelfReflection() {
        this.savedQuestions = generateQuestions();
        assert savedQuestions.size() == TOTAL_NUM_QUESTIONS : TOTAL_NUM_QUESTION_ASSERTIONS;
    }

    /**
     * Load the questions list with pre-defined reflect questions.
     */
    private ArrayList<ReflectionQuestion> generateQuestions() {
        ArrayList<ReflectionQuestion> questions = new ArrayList<>();
        for (int i = 0; i < QUESTIONS.length; i += 1) {
            ReflectionQuestion newQuestion = new ReflectionQuestion(QUESTIONS[i]);
            questions.add(newQuestion);
        }
        return questions;
    }

    private ReflectUi getReflectUi() {
        return this.reflectUi;
    }

    private ArrayList<ReflectionQuestion> getSavedQuestions() {
        return this.savedQuestions;
    }

    public void addReflectQuestion(ReflectionQuestion question) {
        this.getSavedQuestions().add(question);
    }

    public void clearQuestions() {
        this.getSavedQuestions().clear();
    }

    public ArrayList<ReflectionQuestion> getQuestions() {
        return this.savedQuestions;
    }
}

