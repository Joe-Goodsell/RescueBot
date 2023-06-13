import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ScenarioTest {

    @Test
    void averageAgeTest() {
        ScenarioManager scenarioManager = new ScenarioManager();
        try {
            scenarioManager.parseCSV(new File("/Users/josephgoodsell/IdeaProjects/RescueBot/src/scenarios.csv"), false);
        } catch (FileNotFoundException e) {

        }
        Scenario scen = scenarioManager.getScenarios().get(1);
        try {
            scen.setUserChoice(1);
        } catch (InvalidInputException e) {

        }
        Integer[] ageList = new Integer[] {
                71, 37, 41, 34, 59, 28
        };
        double p1 = scen.getAvgAge();
        double p2 = Arrays.stream(ageList).mapToDouble(a -> a).average().getAsDouble();
        try {
            assertEquals(p1, p2);
        } catch (AssertionError e) {
            System.out.println(String.join(" | ", Double.toString(p1), Double.toString(p2)));
            throw new AssertionError();
        }
    }
}