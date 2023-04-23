package nl.hva.amsserver.models.counting;

/**
 * The CountingPublicSpaces model
 *
 * @author Jordy Mol
 */
public class CountingPublicSpaces {

    private int cane;
    private int walker;
    private int touchStick;
    private int dog;
    private int manualWheelchair;
    private int electricWheelchair;
    private int scooter;
    private int childParam;

    /**
     * Instantiates a new CountingPublicSpaces object
     */
    public CountingPublicSpaces() {}

    /**
     * Instantiates a new CountingPublicSpaces object
     *
     * @param cane                  The amount of people counted with a cane
     * @param walker                The amount of people counted with a walker
     * @param touchStick            The amount of people counted with a touch stick
     * @param dog                   The amount of people counted with an assistance dog
     * @param manualWheelchair      The amount of people counted with a manual wheelchair
     * @param electricWheelchair    The amount of people counted with an electric wheelchair
     * @param scooter               The amount of people counted with a mobility scooter
     * @param childParam            The amount of people counted with a child param
     */
    public CountingPublicSpaces(int cane, int walker, int touchStick, int dog, int manualWheelchair, int electricWheelchair, int scooter, int childParam) {
        this.cane = cane;
        this.walker = walker;
        this.touchStick = touchStick;
        this.dog = dog;
        this.manualWheelchair = manualWheelchair;
        this.electricWheelchair = electricWheelchair;
        this.scooter = scooter;
        this.childParam = childParam;
    }

    public int getCane() {
        return cane;
    }

    public void setCane(int cane) {
        this.cane = cane;
    }

    public int getWalker() {
        return walker;
    }

    public void setWalker(int walker) {
        this.walker = walker;
    }

    public int getTouchStick() {
        return touchStick;
    }

    public void setTouchStick(int touchStick) {
        this.touchStick = touchStick;
    }

    public int getDog() {
        return dog;
    }

    public void setDog(int dog) {
        this.dog = dog;
    }

    public int getManualWheelchair() {
        return manualWheelchair;
    }

    public void setManualWheelchair(int manualWheelchair) {
        this.manualWheelchair = manualWheelchair;
    }

    public int getElectricWheelchair() {
        return electricWheelchair;
    }

    public void setElectricWheelchair(int electricWheelchair) {
        this.electricWheelchair = electricWheelchair;
    }

    public int getScooter() {
        return scooter;
    }

    public void setScooter(int scooter) {
        this.scooter = scooter;
    }

    public int getChildParam() {
        return childParam;
    }

    public void setChildParam(int childParam) {
        this.childParam = childParam;
    }
}
