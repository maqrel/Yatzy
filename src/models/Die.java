package models;

import java.util.Random;

public class Die {
    private int eyes = 0;
    private final Random random = new Random();

    //Opretter en terning med bestemt antal øjne
    public Die(int eyes) {
        this.eyes = eyes;
    }

    //Opretter en terning
    public Die() {
    }

    //Returner antal øjne
    public int getEyes() {
        return eyes;
    }

    //Ruller terningen
    public void roll() {
        eyes = (int) (random.nextDouble(1) * 6 + 1);
    }
}
