package nl.hva.amsserver.models.levels;

/**
 * The LevelsLockedJSON model
 *
 * @author Jordy Mol
 */
public class LevelsLockedJSON {

    private boolean levelOne;
    private boolean levelTwo;
    private boolean levelThree;
    private boolean levelFour;
    private boolean levelFive;
    private boolean levelSix;

    /**
     * Instantiates a new LevelsLockedJSON object
     *
     * @param levelOne      If level one is locked or not
     * @param levelTwo      If level two is locked or not
     * @param levelThree    If level three is locked or not
     * @param levelFour     If level four is locked or not
     * @param levelFive     If level five is locked or not
     * @param levelSix      If level six is locked or not
     */
    public LevelsLockedJSON(boolean levelOne, boolean levelTwo, boolean levelThree, boolean levelFour, boolean levelFive,
                            boolean levelSix) {
        this.levelOne = levelOne;
        this.levelTwo = levelTwo;
        this.levelThree = levelThree;
        this.levelFour = levelFour;
        this.levelFive = levelFive;
        this.levelSix = levelSix;
    }

    public boolean isLevelOne() {
        return levelOne;
    }

    public void setLevelOne(boolean levelOne) {
        this.levelOne = levelOne;
    }

    public boolean isLevelTwo() {
        return levelTwo;
    }

    public void setLevelTwo(boolean levelTwo) {
        this.levelTwo = levelTwo;
    }

    public boolean isLevelThree() {
        return levelThree;
    }

    public void setLevelThree(boolean levelThree) {
        this.levelThree = levelThree;
    }

    public boolean isLevelFour() {
        return levelFour;
    }

    public void setLevelFour(boolean levelFour) {
        this.levelFour = levelFour;
    }

    public boolean isLevelFive() {
        return levelFive;
    }

    public void setLevelFive(boolean levelFive) {
        this.levelFive = levelFive;
    }

    public boolean isLevelSix() {
        return levelSix;
    }

    public void setLevelSix(boolean levelSix) {
        this.levelSix = levelSix;
    }
}
