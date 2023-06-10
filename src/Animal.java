public class Animal extends Character {

    enum Species {
        KOALA, KANGAROO, POSSUM, PLATYPUS, CAT, WALLABY, DOG, DINGO, UNKNOWN
    }
    public final Species DEFAULT_SPECIES = Species.UNKNOWN;
    private boolean isPet;
    private Species species;

    public Animal() {
        // initialises with default values
        super();
        isPet = false;
        species = DEFAULT_SPECIES;
    }

    public String toString() {
        String out = species.toString().toLowerCase();
        if (isPet) {
            out = String.join(" ", out, "is pet");
        }
        return out;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }
    public void setSpecies() {
        this.species = DEFAULT_SPECIES;
    }
    public void setIsPet(boolean isPet) {
        this.isPet = isPet;
    }
    public void setIsPet() {
        this.isPet = false;
    }
}
