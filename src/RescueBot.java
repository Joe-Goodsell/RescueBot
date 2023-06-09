import java.io.*;
import java.util.Scanner;

/**
 * COMP90041, Sem1, 2023: Final Project
 * author:
 * student id:
 * student email:
 */
public class RescueBot {

    public static LogfileHandler logfileHandler;
    public static ScenarioManager scenariosFileHandler;

    /**
     * Decides whether to save the passengers or the pedestrians
     * param Scenario scenario: the ethical dilemma
     * return Decision: which group to save
     */
//    public static Location decide(Scenario scenario) {
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
        scenariosFileHandler = new ScenarioManager();

        parseArgs(args);

        try {
            printWelcome();
        } catch (FileNotFoundException e) {
            System.exit(1);
        }

    }

    private static void printWelcome() throws FileNotFoundException {
        File welcome = new File("welcome.ascii");
        Scanner printStream = null;
        try {
            printStream = new Scanner(new FileInputStream(welcome));
            while (printStream.hasNextLine()) {
                String line = printStream.nextLine();
                System.out.println(line);
            }
            printStream.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("welcome text file not found.");
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
                    System.out.println("Error: no scenario file provided.");
                    printHelp();
                    System.exit(1);
                } else {
                    try {
                        scenariosFileHandler.checkFile(args[++i]);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else if (args[i].equalsIgnoreCase("-l") || args[i].equalsIgnoreCase("--logfile")) {
                if (i == args.length-1) {
                    printHelp();
                    System.exit(1);
                } else {
                    try {
                        logfileHandler.checkFile(args[++i]);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}