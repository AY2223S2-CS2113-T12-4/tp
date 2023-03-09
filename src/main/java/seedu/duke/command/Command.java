package seedu.duke.command;

import java.util.HashMap;
import java.util.Map;

/**
 * Superclass for all supported commands in Duke.<br>
 *
 * Each Command is initialised with the arguments issued
 *   by the user. Execute the specified Command by calling
 *   execute().<br>
 *
 * Child classes must provide the static isValidCommand() method for checking whether a set of
 *   arguments are valid for a given command.
 */
public abstract class Command {
    // Command is abstract, it has no command keyword of its own
    private static final String ARGUMENT_DELIMITER = "--";
    private static final String DELIMITER_FOR_WORDS = " ";
    private final HashMap<String, String> arguments;

    public Command(HashMap<String, String> arguments) {
        this.arguments = arguments;
    }

    protected HashMap<String, String> getArguments() {
        return this.arguments;
    }

    /**
     * Identifies this Command's keyword. Override this in subclasses so
     *   toString() returns the correct String representation.
     *
     * @return String Keyword of this Command
     */
    protected abstract String getCommandKeyword();

    /**
     * Returns a detailed user-friendly description of what this specific command does.
     *
     * @return String Detailed explanation of this command
     */
    protected abstract String getDetailedDescription();

    /**
     * Identifies the feature that this Command is associated with. Override
     *   this in subclasses so toString() returns the correct String representation.
     *
     * @return String Keyword for the feature associated with this Command
     */
    protected abstract String getFeatureKeyword();

    /**
     * Returns all the supported arguments for this Command.
     *
     * @return String All supported arguments for this Command
     */
     protected abstract String getSupportedCommandArguments();

    /**
     * Executes the specified command from the user.
     *
     * May throw Exceptions if command fails.
     */
    public abstract void execute();

    /**
     * Very basic specialised toString() method for commands that returns
     *   a formatted list of all arguments issued by the user.
     *
     * @return String Representation of this Command that includes all
     *   given arguments
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getFeatureKeyword());
        builder.append(Command.DELIMITER_FOR_WORDS);
        builder.append(this.getCommandKeyword());
        for (Map.Entry<String, String> set : this.getArguments().entrySet()) {
            builder.append(Command.DELIMITER_FOR_WORDS);
            builder.append(Command.ARGUMENT_DELIMITER);
            builder.append(set.getKey());
            builder.append(Command.DELIMITER_FOR_WORDS);
            builder.append(set.getValue());
        }
        return builder.toString();
    }
}
