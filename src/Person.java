import java.util.Arrays;
import java.util.Random;

public class Person extends Character {

    enum AgeCategory {
        BABY, CHILD, ADULT, SENIOR
    }
    enum Profession {
        DOCTOR, CEO, CRIMINAL, HOMELESS, STUDENT, PROFESSOR,
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
        this.pregnant = false;
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

   public Person(boolean isRandom) {
       super(true);
       Random rand = new Random();
       this.initAgeCategory();
       if (this.ageCategory.equals(AgeCategory.ADULT) || this.ageCategory.equals(AgeCategory.SENIOR)) {
           Object[] professionArr = Arrays.stream(Person.Profession.values()).filter(p -> !p.equals(DEFAULT_PROFESSION)).toArray();
           this.profession = (Profession) professionArr[rand.nextInt(professionArr.length)];
       } else {
           this.profession = DEFAULT_PROFESSION;
       }
       if (this.gender.equals(Gender.FEMALE) && this.ageCategory.equals(AgeCategory.ADULT)) {
           // if Person is adult female, they are pregnant with p(0.2)
           double rn = Math.random();
           this.pregnant = rn > 0.8;
       }

   }


    /*
   METHODS
    */
    @Override
    public Person copy() {
        return new Person(this);
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
    @Override
    public String toLogString() {
        return String.join(",",
                "human",
                gender.toString().toLowerCase(),
                Integer.toString(age),
                bodyType.toString().toLowerCase(),
                profession.toString().toLowerCase(),
                Boolean.toString(pregnant).toUpperCase(),
                "",
                ""
        );
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

    public AgeCategory getAgeCategory() {
       return this.ageCategory;
    }
    public Profession getProfession() {
       return this.profession;
    }

    public boolean getIsPregnant() {
       return pregnant;
    }
   public String toString() {
        String out = String.join(" ", bodyType.toString().toLowerCase(), ageCategory.toString().toLowerCase());
        if (!profession.equals(DEFAULT_PROFESSION)) {
            out = String.join(" ", out, profession.toString().toLowerCase());
        }
        out = String.join(" ", out, gender.toString().toLowerCase());
        if (pregnant) {
            out = String.join(" ", out, "pregnant");
        }
        return out;
    }
}
