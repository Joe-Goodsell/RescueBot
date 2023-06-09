public class Person extends Character {

    enum AgeCategory {
        BABY, CHILD, ADULT, SENIOR
    }
    enum Profession {
        DOCTOR, CEO, CRIMINAL, HOMELESS,
        UNEMPLOYED, PROGRAMMER, NURSE, TEACHER, NONE
    }
    private final Profession DEFAULT_PROFESSION = Profession.NONE;
    private Profession profession;
    private AgeCategory ageCategory;

    private boolean pregnant;

   public Person() {
       // initialises with default values
        super();
        initAgeCategory();
        this.pregnant = pregnant;
        this.profession = DEFAULT_PROFESSION;
   }

   public void initRandom() {
       super.initRandom();
        // classify person based on age

       if (this.gender.equals(Gender.FEMALE)) {
           // if Person is female, they are pregnant with p(0.1)
           double rn = Math.random();
           this.pregnant = rn > 0.9;
       }
   }

   private void initAgeCategory() {
       if (age <= 4) {
           this.ageCategory = AgeCategory.BABY;
       } else if (age <= 16) {
           this.ageCategory = AgeCategory.CHILD;
       } else if (age <= 68) {
           this.ageCategory = AgeCategory.ADULT;
       } else {
           this.ageCategory = AgeCategory.SENIOR;
       }
   }
    public String toString() {
        String out = String.join(" ", bodyType.toString().toLowerCase(), ageCategory.toString().toLowerCase());
        if (!profession.equals(Profession.NONE)) {
            out = String.join(" ", out, profession.toString().toLowerCase());
        }
        out = String.join(" ", out, gender.toString().toLowerCase());
        if (pregnant) {
            out = String.join(" ", out, "pregnant");
        }
        return out;
    }
}
