import java.util.Arrays;
import java.util.Random;

public class Character {
    protected Gender gender;
    protected int age;
    protected BodyType bodyType;

    enum BodyType {
        AVERAGE,
        ATHLETIC,
        OVERWEIGHT,
        UNSPECIFIED
    }

    enum Gender {
        MALE,
        FEMALE,
        UNKNOWN
    }

    protected final Gender DEFAULT_GENDER = Gender.UNKNOWN;
    protected final BodyType DEFAULT_BODYTYPE = BodyType.UNSPECIFIED;
    protected final int DEFAULT_AGE = 10;


    public Character() {
        // initialise Character with defaults
        this.gender = DEFAULT_GENDER;
        this.age = DEFAULT_AGE;
        this.bodyType = DEFAULT_BODYTYPE;
    }

    public void initRandom() {
        this.age = new Random().nextInt(99);

        Gender[] gendersArr = (Gender[]) Arrays.stream(Gender.values()).filter(p -> !p.equals(Gender.UNKNOWN)).toArray();
        gender = gendersArr[new Random().nextInt(gendersArr.length)];

        BodyType[] btArray = (BodyType[]) Arrays.stream(BodyType.values()).filter(p -> !p.equals(BodyType.UNSPECIFIED)).toArray();
        bodyType = btArray[new Random().nextInt(btArray.length)];
    }
}
