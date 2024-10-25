package models;

/**
 * Used to calculate the score of throws with 5 dice
 */
public class YatzyResultCalculator {
    private Die[] dice;
    /**
     *
     * @param dice
     */
    public YatzyResultCalculator(Die[] dice) {
        this.dice = dice;
    }

    /**
     * Calculates the score for Yatzy uppersection
     * @param eyes eye value to calculate score for. eyes should be between 1 and 6
     * @return the score for specified eye value
     */
    public int upperSectionScore(int eyes) {
        int sum = 0;
        for (Die die : dice) {
            if(die.getEyes() == eyes) {
                sum += eyes;
            }
        }
        return sum;

    }

    public int onePairScore() {
        //TODO: implement onePairScore method.
        return 0;
    }

    public int twoPairScore() {
        //TODO: implement twoPairScore method.
        return 0;
    }

    public int threeOfAKindScore() {
        //TODO: implement threeOfAKindScore method.
        return 0;
    }

    public int fourOfAKindScore() {
        //TODO: implement fourOfAKindScore method.
        return 0;
    }

    public int smallStraightScore() {
        //TODO: implement smallStraightScore method.
        return 0;
    }

    public int largeStraightScore() {
        //TODO: implement largeStraightScore method.
        return 0;
    }

    public int fullHouseScore() {
        //TODO: implement fullHouseScore method.
        return 0;
    }

    public int chanceScore() {
        //TODO: implement chanceScore method.
        return 0;
    }

    public int yatzyScore() {
        //TODO: implement yatzyScore method.
        return 0;
    }
}
