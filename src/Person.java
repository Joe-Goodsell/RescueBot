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


    /*
    CONSTRUCTORS
     */

   public Person() {
       // initialises with default values
        super();
        initAgeCategory();
        this.pregnant = pregnant;
        this.profession = DEFAULT_PROFESSION;
   }


   public Person(Gender gender, int age, BodyType bodyType, boolean pregnant, Profession profession) {
       super(gender, age, bodyType);
       this.initAgeCategory();
       this.setIsPregnant(pregnant);
       this.profession = profession;
   }

   public Person(Person that) {
       this(that.gender, that.age, that.bodyType, that.pregnant, that.profession);
       this.initAgeCategory();
   }

   /*
   METHODS
    */
    @Override
    public Person copy() {
        return new Person(this);
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

   /*
   GETTERS AND SETTERS
    */
   public void setProfession(Profession profession) {
       this.profession = profession;
   }
   public void setProfession() {
       this.profession = DEFAULT_PROFESSION;
   }
   public void setIsPregnant(boolean isPregnant) {
       this.pregnant = isPregnant && this.gender == Gender.FEMALE;
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
