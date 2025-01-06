package frc.robot;

public class Rizzler {

    public static void rizz() {
        System.out.println("  ___   _           _     _   _                                       \r\n" + //
                                " |_ _| | | ___  ___| |_  | |_| |__   ___    __ _  __ _ _ __ ___   ___ \r\n" + //
                                "  | |  | |/ _ \\/ __| __| | __| '_ \\ / _ \\  / _` |/ _` | '_ ` _ \\ / _ \\\r\n" + //
                                "  | |  | | (_) \\__ \\ |_  | |_| | | |  __/ | (_| | (_| | | | | | |  __/\r\n" + //
                                " |___| |_|\\___/|___/\\__|  \\__|_| |_|\\___|  \\__, |\\__,_|_| |_| |_|\\___|\r\n" + //
                                "                                           |___/                      ");
        
                                griddyemail();
        check_skibbidi();
    }

    public static void griddyemail() {
        System.out.println("\n hitting the griddy on the ops since 7627\n");
    }

    public static boolean check_skibbidi() {
        if(Constants.skibbidi_mode) {
            System.out.println("Coach controller enabled!\r\n" + //
                                "________________________________________ \r\n" + //
                                "|                                       |\r\n" + //
                                "| !!!DO NOT USE THIS CODE IN A MATCH!!! |\r\n" + //
                                "|_______________________________________|\r\n" + //
                                "                               \r\n" + //
                                "Robot may attempt to rizz up the ops");
            return true;
        }
        else {
            System.out.println("Coach controller disabled");
            return false;
        }
    }

}