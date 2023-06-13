import java.util.*;

public class Decider {
    private HashMap<Person.Profession ,Integer> PROFESSION_WEIGHT;
    private HashMap<Character.BodyType,Integer> BODYTYPE_WEIGHT;
    private HashMap<String, Integer> SAPIEN_WEIGHT;
    private HashMap<Animal.Species,Integer> SPECIES_WEIGHT;
    private HashMap<Person.AgeCategory,Integer> AGECATEGORY_WEIGHT;

    public Decider() {
        PROFESSION_WEIGHT = new HashMap<>();
        for ( Person.Profession key : Person.Profession.values() ) {
            PROFESSION_WEIGHT.put(key, 0);
        }
        PROFESSION_WEIGHT.put(Person.Profession.CRIMINAL, 10);
        PROFESSION_WEIGHT.put(Person.Profession.HOMELESS, 8);
        PROFESSION_WEIGHT.put(Person.Profession.UNEMPLOYED, 6);
        PROFESSION_WEIGHT.put(Person.Profession.CEO, -10);
        PROFESSION_WEIGHT.put(Person.Profession.PROGRAMMER, -8);
        PROFESSION_WEIGHT.put(Person.Profession.NURSE, 4);

        BODYTYPE_WEIGHT = new HashMap<>();
        for ( Character.BodyType key : Character.BodyType.values() ) {
            BODYTYPE_WEIGHT.put(key, 0);
        }
        BODYTYPE_WEIGHT.put(Character.BodyType.OVERWEIGHT, 10);
        BODYTYPE_WEIGHT.put(Character.BodyType.AVERAGE, 5);
        BODYTYPE_WEIGHT.put(Character.BodyType.ATHLETIC, -10);


        AGECATEGORY_WEIGHT = new HashMap<>();
        for ( Person.AgeCategory key : Person.AgeCategory.values() ) {
            AGECATEGORY_WEIGHT.put(key, 0);
        }
        AGECATEGORY_WEIGHT.put(Person.AgeCategory.SENIOR, 20);
        AGECATEGORY_WEIGHT.put(Person.AgeCategory.BABY, -15);

        SAPIEN_WEIGHT = new HashMap<>();
        SAPIEN_WEIGHT.put("person", -10);
        SAPIEN_WEIGHT.put("animal", 10);

        SPECIES_WEIGHT = new HashMap<>();
        for ( Animal.Species key : Animal.Species.values() ) {
            SPECIES_WEIGHT.put(key, 0);
        }
        SPECIES_WEIGHT.put(Animal.Species.KOALA, 50);
    }


    public int decide(Scenario scenario) {
        ArrayList<Location> locations = scenario.getLocations();
        int[] weights = new int[locations.size()];
        for (int i = 0; i < locations.size(); i++) {
            Location location = locations.get(i);
           int locationWeight = 0;
           for ( Character character : location.getCharacters() ) {
               locationWeight += BODYTYPE_WEIGHT.get(character.getBodyType());
               locationWeight += SAPIEN_WEIGHT.get(character.getClass().getCanonicalName().toLowerCase());
               if (character instanceof Person) {
                   locationWeight += PROFESSION_WEIGHT.get(((Person) character).getProfession());
                   locationWeight += AGECATEGORY_WEIGHT.get(((Person) character).getAgeCategory());
               } else {
                   locationWeight += SPECIES_WEIGHT.get(((Animal) character).getSpecies());
               }
           }
           weights[i] = locationWeight;
        }

        // find index of maximum weight
        int max = 0;
        int pos = 0;
        for (int i = 0; i < weights.length; i++) {
            if (weights[i] > max) {
                max = weights[i];
                pos = i;
            }
        }
        return pos + 1;
    }
}
