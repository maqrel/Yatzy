package models;

public class Rafflecup {
    private Die[] dice = new Die[5];
    private int numberOfThrowsLeft;

    public Rafflecup() {
        for (int index = 0; index < dice.length; index++) {
            dice[index] = new Die();
        }
        this.numberOfThrowsLeft=3;
    }

    public void throwDice() {
        for (Die die : dice) {
            die.roll();
        }
        numberOfThrowsLeft--;
    }

    public Die[] getDice() {
        return dice;
    }

    public int getNumberOfThrowsLeft(){
        return numberOfThrowsLeft;
    }

}
