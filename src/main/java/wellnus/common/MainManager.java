package wellnus.common;

import wellnus.command.Command;
import wellnus.command.CommandParser;
import wellnus.command.ExitCommand;
import wellnus.command.HelpCommand;
import wellnus.exception.BadCommandException;
import wellnus.manager.Manager;
import wellnus.reflection.ReflectionManager;
import wellnus.ui.TextUi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class MainManager extends Manager {
    private static final String EXIT_COMMAND_KEYWORD = "exit";
    private static final String GREETING_MESSAGE = "Enter a command to start using WellNUS++! Try 'help' "
            + "if you're new, or just unsure.";
    private static final String HELP_COMMAND_KEYWORD = "help";
    private static final String INVALID_COMMAND_MESSAGE = "Don't recognise that command?";
    private static final String INVALID_COMMAND_ADDITIONAL_MESSAGE = "Try 'help' for some guidance";
    private static final String INVALID_FEATURE_KEYWORD_MESSAGE = "Feature keyword can't be empty dear";
    private static final String WELLNUS_FEATURE_NAME = "";
    private ArrayList<Manager> featureManagers;
    private final TextUi textUi;

    public MainManager() {
        this.featureManagers = new ArrayList<>();
        this.textUi = new TextUi();
        this.setSupportedFeatureManagers();
    }

    private static String getBriefAppDescription() {
        return "WellNUS++ helps you keep track and improve your physical and mental wellness.";
    }

    private static String getLongAppDescription() {
        return "WellNUS++ is a Command Line Interface(CLI) app for NUS Computing students to "
                + "keep track and improve their physical and mental wellness in various aspects."
                + " If you can type fast, WellNUS++ can update their wellness progress faster than "
                + "traditional Graphical User Interface(GUI) apps.";
    }

    /**
     * Continuously reads user's commands and executes those that are supported
     *     by WellNUS++ until the `exit` command is given.<br>
     *
     * If an unrecognised command is given, a warning is printed on the user's screen.
     */
    private void executeCommands() {
        boolean isExit = false;
        CommandParser parser = new CommandParser();
        while (!isExit) {
            String NO_ADDITIONAL_MESSAGE = "";
            try {
                String nextCommand = this.getTextUi().getCommand();
                String featureKeyword = parser.getMainArgument(nextCommand);
                System.out.println(featureKeyword);
                Optional<Manager> featureManager = this.getManagerFor(featureKeyword);
                if (featureManager.isEmpty() && !this.isSupportedCommand(featureKeyword)) {
                    BadCommandException badCommandException =
                            new BadCommandException(MainManager.INVALID_COMMAND_MESSAGE);
                    this.getTextUi().printErrorFor(badCommandException,
                            MainManager.INVALID_COMMAND_ADDITIONAL_MESSAGE);
                    continue;
                }
                featureManager.ifPresent((manager) -> {
                    // TODO: Consider if there's a way to avoid this extra try-catch?
                    try {
                        manager.runEventDriver();
                    } catch (BadCommandException badCommandException) {
                        this.getTextUi().printErrorFor(badCommandException, NO_ADDITIONAL_MESSAGE);
                    }
                });
                Command mainCommand = this.getMainCommandFor(nextCommand);
                mainCommand.execute();
                isExit = ExitCommand.isExit(mainCommand);
            } catch (BadCommandException badCommandException) {
                this.getTextUi().printErrorFor(badCommandException, NO_ADDITIONAL_MESSAGE);
            }
        }
    }

    /**
     * Parses the given command String issued by the user and returns the corresponding
     *     Command object that can execute it.
     * @param command Command issued by the user
     * @return Command object that can execute the user's command
     * @throws BadCommandException If command issued is not supported or invalid
     */
    private Command getMainCommandFor(String command) throws BadCommandException {
        String commandKeyword = getCommandParser().getMainArgument(command);
        HashMap<String, String> arguments = getCommandParser().parseUserInput(command);
        switch (commandKeyword) {
        case MainManager.HELP_COMMAND_KEYWORD:
            return new HelpCommand(arguments, this);
        case MainManager.EXIT_COMMAND_KEYWORD:
            return new ExitCommand(arguments);
        default:
            throw new BadCommandException(MainManager.INVALID_COMMAND_MESSAGE);
        }
    }

    private List<String> getSupportedCommandKeywords() {
        List<String> commandKeywords = new ArrayList<>();
        // TODO: Consider if there's a better way than exposing a static variable(a helper method?)
        commandKeywords.add(MainManager.HELP_COMMAND_KEYWORD);
        commandKeywords.add(MainManager.EXIT_COMMAND_KEYWORD);
        return commandKeywords;
    }

    private List<Manager> getSupportedFeatureManagers() {
        return this.featureManagers;
    }

    private TextUi getTextUi() {
        return this.textUi;
    }

    private void greet() {
        this.getTextUi().printOutputMessage(MainManager.GREETING_MESSAGE);
    }

    private boolean isSupportedCommand(String commandKeyword) {
        List<String> cmdKeywords = this.getSupportedCommandKeywords();
        for (String cmdKeyword : cmdKeywords) {
            if (commandKeyword.equals(cmdKeyword)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a brief description of the WellNUS++ app
     *
     * @return Brief description of this app
     */
    @Override
    public String getBriefDescription() {
        return MainManager.getBriefAppDescription();
    }

    /**
     * Returns the name of this feature. In this case, it's just empty(not any particular feature).
     *
     * @return Empty String to imply that this Manager is not associated with any feature
     */
    @Override
    public String getFeatureName() {
        return WELLNUS_FEATURE_NAME;
    }

    /**
     * Returns a full description of the WellNUS++ app.
     *
     * @return Full description of the app
     */
    @Override
    public String getFullDescription() {
        // TODO: Call other feature's Managers to build a complete full description of WellNUS++.
        //     getLongAppDescription() is an overall app description, it doesn't include features.
        return MainManager.getLongAppDescription();
    }

    public Optional<Manager> getManagerFor(String featureKeyword) {
        assert (featureKeyword != null && !featureKeyword.isBlank())
                : MainManager.INVALID_FEATURE_KEYWORD_MESSAGE;
        for (Manager featureManager : this.getSupportedFeatureManagers()) {
            if (featureManager.getFeatureName().equals(featureKeyword)) {
                return Optional.of(featureManager);
            }
        }
        return Optional.empty();
    }

    public boolean isSupportedFeature(String featureKeyword) {
        return this.getManagerFor(featureKeyword).isPresent();
    }

    /**
     * Executes the basic commands(e.g. <code>help</code>) as well as any feature-specific
     *     commands, which are delegated to the corresponding features' Managers.<br>
     * <br>
     * This method will keep reading the user's command until the exit command is given.
     */
    @Override
    public void runEventDriver() {
        this.greet();
        this.executeCommands();
    }

    /**
     * Returns a list of basic commands supported by WellNUS++(e.g. <code>help</code>) <br>
     * <br>
     * Suggested implementation: <br>
     * <code> this.supportedCommands.add([cmd1, cmd2, ...]); </code>
     */
    @Override
    protected void setSupportedCommands() {
        // TODO: Implement once basic Command subclasses are in
        // Unable to implement this now since Command subclasses don't exist
    }

    /**
     * Returns a list of features supported by WellNUS++ <br>
     * <br>
     * Suggested implementation: <br>
     * <code> this.supportedManagers.add([mgr1, mgr2, ...]); </code>
     */
    protected void setSupportedFeatureManagers() {
        // TODO: Implement once all Managers are in
        // e.g. super.getSupportedFeatureManagers().add(new AtomicHabitManager());
        this.getSupportedFeatureManagers().add(new ReflectionManager());
    }

}
