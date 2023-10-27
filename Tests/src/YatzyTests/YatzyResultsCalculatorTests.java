package YatzyTests;

import models.Die;
import models.YatzyResultCalculator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class YatzyResultsCalculatorTests {
    @Test
    public void upperSectionScoreTests() {
        YatzyResultCalculator sut = new YatzyResultCalculator(new Die[]{
                new Die(3),
                new Die(1),
                new Die(3),
                new Die(3),
                new Die(1) }
        );
        int actual = sut.upperSectionScore(1);
        assertEquals(2, actual, "A throw with two ones, should give a score of 2");
        actual = sut.upperSectionScore(2);
        assertEquals(0, actual, "A throw with zero twos, should give a score of 0");
        actual = sut.upperSectionScore(3);
        assertEquals(9, actual, "A throw with three threes, should give a score of 9");
    }

    @Test
    public void onePairScoreTests() {
        YatzyResultCalculator sut = new YatzyResultCalculator(new Die[]{
                new Die(1),
                new Die(5),
                new Die(3),
                new Die(4),
                new Die(2) }
        );
        int actual = sut.onePairScore();
        assertEquals(0, actual, "A throw without a pair, should give a score of 0");
        sut = new YatzyResultCalculator(new Die[]{
                new Die(3),
                new Die(1),
                new Die(3),
                new Die(3),
                new Die(1) }
        );
        actual = sut.onePairScore();
        assertEquals(6, actual, "A throw with several pairs, should return the score of the higher value");
    }

    @Test
    public void twoPairScoreTests() {
        YatzyResultCalculator sut = new YatzyResultCalculator(new Die[]{
                new Die(6),
                new Die(2),
                new Die(3),
                new Die(3),
                new Die(1) }
        );
        int actual = sut.twoPairScore();
        assertEquals(0, actual, "A throw without two pairs, should return a score of 0");
        sut = new YatzyResultCalculator(new Die[]{
                new Die(3),
                new Die(1),
                new Die(3),
                new Die(3),
                new Die(1) }
        );
        actual = sut.twoPairScore();
        assertEquals(8, actual, "A throw with two pairs, should return the total of the eyes used");
    }

    @Test
    public void threeOfAKindScoreTests(){
        YatzyResultCalculator sut = new YatzyResultCalculator(new Die[]{
                new Die(6),
                new Die(1),
                new Die(3),
                new Die(4),
                new Die(3) }
        );
        int actual = sut.threeOfAKindScore();
        assertEquals(0, actual, "A throw without three of a kind, should return a score of 0");
        sut = new YatzyResultCalculator(new Die[]{
                new Die(3),
                new Die(1),
                new Die(3),
                new Die(4),
                new Die(3) }
        );
        actual = sut.threeOfAKindScore();
        assertEquals(9, actual, "A throw with three of a kind, should return the total of the eyes used");
        sut = new YatzyResultCalculator(new Die[]{
                new Die(6),
                new Die(6),
                new Die(6),
                new Die(6),
                new Die(6) }
        );
        actual = sut.threeOfAKindScore();
        assertEquals(18, actual, "A throw with more than three of a kind, should only return the value of three dice");
    }

    @Test
    public void fourOfAKindScoreTests() {
        YatzyResultCalculator sut = new YatzyResultCalculator(new Die[]{
                new Die(6),
                new Die(1),
                new Die(3),
                new Die(4),
                new Die(3) }
        );
        int actual = sut.fourOfAKindScore();
        assertEquals(0, actual, "A throw without four of a kind, should return a score of 0");
        sut = new YatzyResultCalculator(new Die[]{
                new Die(3),
                new Die(3),
                new Die(3),
                new Die(4),
                new Die(3) }
        );
        actual = sut.fourOfAKindScore();
        assertEquals(12, actual, "A throw with four of a kind, should return the total of the eyes used");
        sut = new YatzyResultCalculator(new Die[]{
                new Die(6),
                new Die(6),
                new Die(6),
                new Die(6),
                new Die(6) }
        );
        actual = sut.fourOfAKindScore();
        assertEquals(18, actual, "A throw with more than four of a kind, should only return the value of four dice");
    }


}
