import java.util.Arrays;
import java.util.Random;

public class Character {
    protected Gender gender;
    protected int age;
    protected BodyType bodyType;

    enum BodyType {
        AVERAGE, ATHLETIC, OVERWEIGHT, UNSPECIFIED
    }

    enum Gender {
        MALE, FEMALE, UNKNOWN
    }

    protected final Gender DEFAULT_GENDER = Gender.UNKNOWN;
    protected final BodyType DEFAULT_BODYTYPE = BodyType.UNSPECIFIED;
    protected final int DEFAULT_AGE = 10;

    /*
    CONSTRUCTORS
     */
    public Character() {
        // initialise Character with defaults
        this.gender = DEFAULT_GENDER;
        this.age = DEFAULT_AGE;
        this.bodyType = DEFAULT_BODYTYPE;
    }

    public Character(Gender gender, int age, BodyType bodyType) {
        this.gender = gender;
        this.age = age;
        this.bodyType = bodyType;
    }

    public Character(Character that) {
        this(that.getGender(), that.getAge(), that.getBodyType());
    }

    /*
    METHODS
     */
    public Character copy() {
        return new Character(this);
    }
    public String toString() {
       return "a stock character";
    }

    public String toLogString() {
        return "<<< PLACEHOLDER >>>";
    }

    /*
    GETTERS AND SETTERS
     */
    public void setBodyType(BodyType bodyType) {
        this.bodyType = bodyType;
    }
    public void setBodyType() {
        this.bodyType = DEFAULT_BODYTYPE;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setGender() {
        this.gender = DEFAULT_GENDER;
    }

    public Gender getGender() {
        return this.gender;
    }
    public int getAge() {
        return this.age;
    }
    public BodyType getBodyType() {
        return this.bodyType;
    }

    public void setAge(int age) {
        assert age > 0;
        this.age = age;
    }

    public void setAge() {
        this.age = DEFAULT_AGE;
    }

    public void initRandom() {
        this.age = new Random().nextInt(99);

        Gender[] gendersArr = (Gender[]) Arrays.stream(Gender.values()).filter(p -> !p.equals(Gender.UNKNOWN)).toArray();
        gender = gendersArr[new Random().nextInt(gendersArr.length)];

        BodyType[] btArray = (BodyType[]) Arrays.stream(BodyType.values()).filter(p -> !p.equals(BodyType.UNSPECIFIED)).toArray();
        bodyType = btArray[new Random().nextInt(btArray.length)];
    }
}
