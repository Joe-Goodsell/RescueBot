import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * COMP90041, Sem1, 2023: Final Project
 * author:
 * student id:
 * student email:
 */
public class RescueBot {

    public static LogfileHandler logfileHandler;
    public static ScenarioManager scenarioManager;
    public static Scanner keyboard = new Scanner(System.in);

    /**
     * Decides whether to save the passengers or the pedestrians
     * param Scenario scenario: the ethical dilemma
     * return Decision: which group to save
     */
//    public static Coordinates decide(Scenario scenario) {
//        // a very simple decision engine
//        // TODO: take into account at least 5 characteristics
//
//        // 50/50
//        if(Math.random() > 0.5) {
//            return scenario.getLocation(1);
//        } else {
//            return scenario.getLocation(2);
//        }
//    }

    /**
     * Program entry
     */
    public static void main(String[] args){

        logfileHandler = new LogfileHandler();
        scenarioManager = new ScenarioManager();

        parseArgs(args);
        printWelcome();
        printWarnings();
        printScenariosImported();
        printMainMenu();
        awaitUserInput();
    }

    private static void awaitUserInput() {
        System.out.print("› ");
        String input = keyboard.next();
        switch (input) {
            case "judge", "j" -> {
                //
                break;
            }
            case "run", "r" -> {
                //
                break;
            }
            case "audit", "a" -> {
                //
                break;
            }
            case "quit", "q" -> {
                //
                break;
            }
            default -> {
                System.out.println("Invalid command! Please enter one of the following commands to continue:");
                printMainMenu();
                awaitUserInput();
            }
        }
    }


    private static void printWarnings() {
        ArrayList<String> warnings = scenarioManager.getWarnings();
        for (String warning : warnings) {
           System.out.println(warning);
        }
    }

    private static void printScenariosImported() {
        int numScenariosImported = scenarioManager.getNumScenarios();
        System.out.printf("%d scenarios imported.%n", numScenariosImported);
    }

    private static void printMainMenu() {
        System.out.println("Please enter one of the following commands to continue:");
        System.out.println("- judge scenarios: [judge] or [j]");
        System.out.println("- run simulations with the in-built decision algorithm: [run] or [r]");
        System.out.println("- show audit from history: [audit] or [a]");
        System.out.println("- quit the program: [quit] or [q]");
    }

    private static void printWelcome() {
        File welcome = new File("welcome.ascii");
        try {
            Scanner printStream = new Scanner(new FileInputStream(welcome));
            while (printStream.hasNextLine()) {
                String line = printStream.nextLine();
                System.out.println(line);
            }
        }
        catch (FileNotFoundException e) {
            System.err.println("ERROR: Welcome text file not found.");
            System.exit(1);
        }
    }

    private static void printHelp() {
       System.out.println("RescueBot - COMP90041 - Final Project");
       System.out.println();
       System.out.println("Usage: java RescueBot [arguments]");
       System.out.println();
       System.out.println("Arguments:");
       System.out.println("-s or --scenarios    Optional: path to scenario file");
       System.out.println("-h or --help        Optional: Print Help (this message) and exit");
       System.out.println("-l or --log        Optional: path to data log file");
    }

    private static void parseArgs(String[] args) {
        for (int i=0; i<args.length; i++) {
            if (args[i].equalsIgnoreCase("-h") || args[i].equalsIgnoreCase("--help")) {
                printHelp();
                System.exit(0);
            } else if (args[i].equalsIgnoreCase("-s") || args[i].equalsIgnoreCase("--scenarios")) {
                if (i == args.length-1) {
                    System.err.println("ERROR: no scenario file provided.");
                    printHelp();
                    System.exit(1);
                } else {
                    File file;
                    file = new File(args[++i]);
                   try {
                       scenarioManager.loadFromFile(file);
                    } catch (FileNotFoundException e) {
                       System.err.println(e.getClass().getCanonicalName() + ": could not find scenarios file.");
                       System.exit(1);
                    }
                }
            } else if (args[i].equalsIgnoreCase("-l") || args[i].equalsIgnoreCase("--logfile")) {
                if (i == args.length-1) {
                    System.err.println("ERROR: no scenario file provided.");
                    printHelp();
                    System.exit(1);
                } else {
                    try {
                        logfileHandler.checkFile(args[++i]);
                    } catch (FileNotFoundException e) {
                        System.err.println("WARNING: logfile not found!");
                    }
                }
            }
        }
    }
}
