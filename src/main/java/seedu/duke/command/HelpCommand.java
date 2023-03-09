package seedu.duke.command;

import seedu.duke.WellNus;

import java.util.HashMap;

/**
 * Implementation of WellNus' <code>help</code> command. Explains to the user what commands are supported
 *   by WellNus and how to use each command.
 */
public class HelpCommand extends Command {
    private static final String BAD_COMMAND_MESSAGE = "Invalid arguments given for %s command";
    private static final String COMMAND_KEYWORD = "help";
    private static final String COMMAND_ARGUMENTS = "<feature>";
    private static final String COMMAND_BRIEF_DESCRIPTION = "Lists ";
    private static final String COMMAND_DETAILED_DESCRIPTION = "";
    private static final String NO_FEATURE_KEYWORD = "";

    public HelpCommand(HashMap<String, String> arguments) throws BadCommandException {
        super(arguments);
        if (!HelpCommand.isValidCommand(arguments)) {
            throw new BadCommandException(String.format(HelpCommand.BAD_COMMAND_MESSAGE,
                    HelpCommand.COMMAND_KEYWORD));
        }
    }

    private String getBriefDescription() {
        return HelpCommand.COMMAND_BRIEF_DESCRIPTION;
    }

    /*
     * Checks the command arguments for this help command to determine if
     *   the user wants an overall description of WellNus's supported features
     *   or a detailed description of a specific feature.
     *
     * @return boolean Whether the user just wants an overall (brief) description of WellNus's features
     */
    private boolean isBriefHelp() {
        String featureKeywordIfExists = super.getArguments().get(this.getCommandKeyword());
        return featureKeywordIfExists.isBlank();
    }

    private void printBriefHelp() {
        // TODO: As new commands are added to WellNus, add their help messages below
        String helpCommandBriefDescription = String.format("%s %s",
                this.getCommandKeyword(), this.getBriefDescription());
        // TODO: Change how we print when Ui class becomes available
        System.out.println(helpCommandBriefDescription);
    }

    private void printDetailedHelp() {
        /* TODO: Similar to printBriefHelp(), add help messages for new commands as they
         *   are added to WellNus */
        String helpCommandDetailedDescription = String.format("%s %s %s",
                this.getCommandKeyword(), this.getSupportedCommandArguments(),
                this.getDetailedDescription());
        // TODO: Change how we print when Ui class becomes available
        System.out.println(helpCommandDetailedDescription);
    }

    @Override
    protected String getCommandKeyword() {
        return HelpCommand.COMMAND_KEYWORD;
    }

    @Override
    protected String getDetailedDescription() {
        return HelpCommand.COMMAND_DETAILED_DESCRIPTION;
    }

    @Override
    protected String getFeatureKeyword() {
        return HelpCommand.NO_FEATURE_KEYWORD;
    }

    @Override
    protected String getSupportedCommandArguments() {
        return HelpCommand.COMMAND_ARGUMENTS;
    }

    /**
     * Checks whether the given arguments are valid for our help command.
     *
     * @param arguments Arguments issued by the user
     * @return boolean Whether arguments issued by the user are valid
     */
    public static boolean isValidCommand(HashMap<String, String> arguments) {
        int NO_ARGUMENTS_LENGTH = 1;
        boolean INVALID_COMMAND = false;
        /*
         * If arguments are given, then we need to be provided the keyword of one
         *   of WellNus' known features
         */
        if (arguments.size() > NO_ARGUMENTS_LENGTH) {
            String featureKeyword = arguments.get(HelpCommand.COMMAND_KEYWORD);
            return WellNus.isSupportedFeature(featureKeyword);
        }
        return INVALID_COMMAND;
    }

    /**
     * Executes the issued help command.<br>
     *
     * Prints a brief description of all of WellNus' supported commands if
     *   the basic 'help' command was issued.<br>
     *
     * Prints a detailed description of a specific feature if the specialised
     *   'help' command was issued.
     */
    @Override
    public void execute() {
        if (isBriefHelp()) {
            this.printBriefHelp();
        } else {
            this.printDetailedHelp();
        }
    }
}
