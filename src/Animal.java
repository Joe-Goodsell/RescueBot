public class Animal extends Character {

    enum Species {
        KOALA, KANGAROO, POSSUM, PLATYPUS, CAT, WALLABY, DOG, DINGO, UNKNOWN
    }
    public final Species DEFAULT_SPECIES = Species.UNKNOWN;
    private boolean isPet;
    private Species species;

    /*
    CONSTRUCTORS
     */

    public Animal() {
        // initialises with default values
        super();
        isPet = false;
        species = DEFAULT_SPECIES;
    }

    public Animal(Gender gender, int age, BodyType bodyType, Species species, boolean isPet) {
        super(gender, age, bodyType);
        this.species = species;
        this.isPet = isPet;
    }

    public Animal(Animal that) {
        this(that.gender, that.age, that.bodyType, that.species, that.isPet);
    }

    /*
    METHODS
     */
    @Override
    public Animal copy() {
        return new Animal(this);
    }

    public String toString() {
        String out = species.toString().toLowerCase();
        if (isPet) {
            out = String.join(" ", out, "is pet");
        }
        return out;
    }


    @Override
    public String toLogString() {
        return String.join(",",
                "animal",
                gender.toString().toLowerCase(),
                Integer.toString(age),
                bodyType.toString().toLowerCase(),
                "",
                "",
                species.toString().toLowerCase(),
                Boolean.toString(isPet).toUpperCase()
        );
    }

    /*
    GETTERS AND SETTERS
     */
    public Species getSpecies() {
        return this.species;
    }
    public boolean getIsPet() {
        return this.isPet;
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
