import java.io.*;
import java.util.*;

class InvalidInputException extends Exception {
    public InvalidInputException() {
        super();
    }
}

/**
 * COMP90041, Sem1, 2023: Final Project
 * author:
 * student id:
 * student email:
 */
public class RescueBot {

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
                judge();
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
                System.out.println("Thank you for using RescueBot.");
                System.exit(0);
            }
            default -> {
                System.out.println("Invalid command! Please enter one of the following commands to continue:");
                printMainMenu();
                awaitUserInput();
            }
        }
    }

    private static void judge() {
        boolean consent = collectConsent();
        ArrayList<Scenario> scenarios = scenarioManager.getScenarios();
        for (int i = 0; i < scenarioManager.getNumScenarios(); i++) {
            if (i == 3) {
                HashMap<String, Statistic> statistic = mergeStatistic(i, true);
                printStatistic(i, true, statistic);
                // printStatistic();
                System.out.println("Would you like to continue? (yes/no)");
                while (true) {
                    try {
                        System.out.print("› ");
                        String input = keyboard.next();
                        switch (input.toLowerCase()) {
                            case "yes" -> { break; }
                            case "no" -> { printMainMenu(); return; }
                            default -> { throw new InvalidInputException(); }
                        }
                    } catch (InvalidInputException e) {
                        System.out.println("Invalid response! Would you like to continue? (yes/no)");
                    }
                }
            }
           Scenario scenario = scenarios.get(i);
           scenario.ppScenario();
           System.out.println("To which location should RescueBot be deployed?");
           while (true) {
               System.out.print("› ");
               try {
                   int input = keyboard.nextInt();
                   if ( input >= 1 && input <= scenario.getLocations().size() + 1 ) {
                       scenario.setUserChoice(input);
                       break;
                   } else throw new InvalidInputException();
               } catch (InvalidInputException | InputMismatchException e) {
                   System.out.println("Invalid response! To which location should RescueBot be deployed?");
               }
           }
        }
        HashMap<String, Statistic> statistic = mergeStatistic(scenarioManager.getNumScenarios(), true);
        printStatistic(scenarioManager.getNumScenarios(), true, statistic);
    }

    private static void printStatistic(int nRuns, boolean user, HashMap<String, Statistic> stats) {

        ArrayList<String> stringList = new ArrayList<>();

        System.out.println("======================================");
        System.out.println("# Statistic");
        System.out.println("======================================");
        System.out.printf("- %% SAVED AFTER %d RUNS%n", nRuns);

        for (String key : stats.keySet()) {
            String string = String.format("%s: %3.2f", key, stats.get(key).getRatio());
            stringList.add(string);
        }

        Comparator<String> stringComparator = Comparator
                .comparing(s -> s.substring(s.indexOf(":")));
        stringList.sort(stringComparator.reversed().thenComparing(s -> s.substring(0, s.indexOf(":"))));

        for (String line : stringList) {
            System.out.println(line.toLowerCase());
        }
        System.out.println("--");
    }

    private static HashMap<String, Statistic> mergeStatistic(int nRuns, boolean user) {
        nRuns = Math.min(nRuns, scenarioManager.getNumScenarios());

        ArrayList<Scenario> scenarios = scenarioManager.getScenarios();
        HashMap<String, Statistic> hm = (user) ? scenarios.get(0).getUserStatistics() : scenarios.get(0).getAlgoStatistics();
        HashSet<String> keySet = new HashSet<>(hm.keySet());

        for (int i = 1; i < nRuns; i++) {
            Scenario scenario = scenarios.get(i);
            HashMap<String, Statistic> hm2 = (user) ? scenario.getUserStatistics() : scenario.getAlgoStatistics();
            keySet.addAll(new HashSet<>(hm2.keySet()));
            for (String key : keySet) {
                Statistic stat1 = hm.get(key);
                Statistic stat2 = hm2.get(key);
                if (stat1 != null && stat2 != null) {
                    stat1.merge(stat2);
                    hm.put(key, stat1);
                } else if (stat1 != null) {
                    hm.put(key, stat1);
                } else {
                    hm.put(key, stat2);
                }
            }
        }
        return hm;
    }

    private static boolean collectConsent() {
        System.out.println("Do you consent to have your decisions saved to a file? (yes/no)");

        while (true) {
            try {
                System.out.print("› ");
                String input = keyboard.next();
                switch (input.toLowerCase()) {
                    case "yes" -> { return true; }
                    case "no" -> { return false; }
                    default -> { throw new InvalidInputException(); }
                }
            } catch (InvalidInputException e) {
                System.out.println("Invalid response! Do you consent to have your decisions saved to a file? (yes/no)");
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
                       scenarioManager.loadFromScenariosFile(file);
                    } catch (FileNotFoundException e) {
                       System.err.println(e.getClass().getCanonicalName() + ": could not find scenarios file.");
                       System.exit(1);
                    }
                }
            } else if (args[i].equalsIgnoreCase("-l") || args[i].equalsIgnoreCase("--logfile")) {
                if (i == args.length-1) {
                    System.err.println("ERROR: no logfile provided.");
                    printHelp();
                    System.exit(1);
                } else {
                    try {
                        //TODO: read logfile
                        // scenarioManager.checkFile(args[++i]);
                        throw new FileNotFoundException();
                    } catch (FileNotFoundException e) {
                        System.err.println("WARNING: logfile not found!");
                    }
                }
            }
        }
    }
}
