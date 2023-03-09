package seedu.duke;

import java.util.Scanner;

public class WellNus {
    private static final String[] supportedFeatures = {"help", "exit"};

    // TODO: Move this feature to WellNus' main Manager when it's available
    public static boolean isSupportedFeature(String keyword) {
        boolean SUPPORTED_FEATURE = true;
        boolean UNSUPPORTED_FEATURE = false;
        for (String feature : supportedFeatures) {
            if (feature.equals(keyword)) {
                return SUPPORTED_FEATURE;
            }
        }
        return UNSUPPORTED_FEATURE;
    }

    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
        String logo = "\n" +
                ",--.   ,--.       ,--.,--.,--.  ,--.,--. ,--. ,---.     |  |        |  |     \n" +
                "|  |   |  | ,---. |  ||  ||  ,'.|  ||  | |  |'   .-',---|  |---.,---|  |---. \n" +
                "|  |.'.|  || .-. :|  ||  ||  |' '  ||  | |  |`.  `-.'---|  |---''---|  |---' \n" +
                "|   ,'.   |\\   --.|  ||  ||  | `   |'  '-'  '.-'    |   |  |        |  |     \n" +
                "'--'   '--' `----'`--'`--'`--'  `--' `-----' `-----'    `--'        `--'     \n" ;

        System.out.println("Hello from\n" + logo);
        System.out.println("What is your name?");

        Scanner in = new Scanner(System.in);
        System.out.println("Hello " + in.nextLine());
    }
}
