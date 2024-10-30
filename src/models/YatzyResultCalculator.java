package models;

/**
 * Used to calculate the score of throws with 5 dice
 */
public class YatzyResultCalculator {
    private Die[] dice;

    public YatzyResultCalculator(Die[] dice) {
        this.dice = dice;
    }

    public int upperSectionScore(int eyes) {
        int sum = 0;
        for (Die die : dice) {
            if (die.getEyes() == eyes) {
                sum += eyes;
            }
        }
        return sum;
    }

    public int onePairScore() {
        //Kører igennem dice array, med terning 'index'
        for (int index = 0; index < dice.length; index++) {
            //Laver en varaibel der er lige med antal øjne på den terning vi er nået til i for loopet
            int dieOne = dice[index].getEyes();

            //nested for loop (index+1 for at tage fat i den næste terning)
            for (int index2 = index + 1; index2 < dice.length; index2++) {
                //if statement som tjekker om der er to ens terninger
                if (dieOne == dice[index2].getEyes()) {
                    //Gange med to, for at lægge sammen
                    return dieOne * 2;
                }
            }
        }
        //return 0, hvis ovenstående ikke er tilfældet
        return 0;
    }

    public int twoPairScore() {
        //Definer par et med metoden onePairScore
        int firstPair = onePairScore();
        //Hvis firstpair=0, returner intet
        if (firstPair == 0) {
            return 0;
        }
        //Laver variabel secondPair
        int secondPair;
        //for loop der kører ignenem array
        for (int index = 0; index < dice.length; index++) {
            //if statement - hvis dice[index] er forskellig fra firstpair (OBS: divider firstpair med 2, da vi kun skal tjekke den ene terning)
            if (dice[index].getEyes() != firstPair / 2) {
                //Nested For loop for den næste terning
                for (int index2 = index + 1; index2 < dice.length; index2++) {
                    //if statement der tjekker om der er to ens terninger
                    if (dice[index].getEyes() == dice[index2].getEyes()) {
                        //definer secondPair og ganger med to
                        secondPair = dice[index2].getEyes() * 2;
                        return firstPair + secondPair;
                    }
                }
            }
        }
        return 0;
    }

    public int threeOfAKindScore() {
        for (int index = 0; index < dice.length; index++) {
            int counter = 0;

            for (int index2 = index; index2 < dice.length; index2++) {
                if (dice[index].getEyes() == dice[index2].getEyes()) {
                    counter++;
                }
                if (counter >= 3) {
                    return dice[index].getEyes() * 3;
                }
            }
        }

        return 0;
    }

    public int fourOfAKindScore() {
        for (int index = 0; index < dice.length; index++) {
            int counter = 0;

            for (int index2 = index; index2 < dice.length; index2++) {
                if (dice[index].getEyes() == dice[index2].getEyes()) {
                    counter++;
                }
                if (counter >= 4) {
                    return dice[index].getEyes() * 4;
                }
            }
        }
        return 0;
    }

    public int smallStraightScore() {
        //Laver et array der indeholder 6 pladser, for at tjekke om der er én af hver terning
        int[] oneOfEach = new int[6];
        //Kører igennem terninger
        for (int index = 0; index < dice.length; index++) {
            //Den terning der bliver slået, bliver plusset med 1 på tilhørende plads. Dvs. slår en 5'er, bliver der sat et 1 tal på plads 1.
            //-1, da det ikke skal placeres på plads 7
            oneOfEach[dice[index].getEyes() - 1]++;
        }
        //For loop der kører igennem pladserne. -1 for ikke at tjekke den 6. plads
        for (int index2 = 0; index2 < oneOfEach.length - 1; index2++) {
            //If statement; hvis én a pladserne lig med 0 returneres 0, aka der er ikke lille straight :(
            if (oneOfEach[index2] == 0) {
                return 0;
            }
        }
        return 15;
    }

    public int largeStraightScore() {
        int[] oneOfEach = new int[6];

        for (int index = 0; index < dice.length; index++) {
            oneOfEach[dice[index].getEyes() - 1]++;
        }

        //Index=1, da den skal springe index 0 over. Fjerner ligeledes - 1, da den nu skal tjekke den 6. plads
        for (int index2 = 1; index2 < oneOfEach.length; index2++) {
            if (oneOfEach[index2] == 0) {
                return 0;
            }
        }
        return 20;
    }

    public int fullHouseScore() {
        //Laver variabler der kalder på disse to metoder
        int threeOfTheSame = threeOfAKindScore();
        int twoOfTheSame = onePairScore();

        //If statement hvis ikke der er 3 ens eller 2 ens
        if (threeOfTheSame == 0 || twoOfTheSame == 0) {
            return 0;
        }
        //If statement hvis de er alle ens tal
        if (threeOfTheSame / 3 == twoOfTheSame / 2) {
            return 0;
        }

        return threeOfTheSame + twoOfTheSame;
    }

    public int chanceScore() {
        int sum = 0;
        for (int index = 0; index < dice.length; index++) {
            sum += dice[index].getEyes();
        }
        return sum;
    }

    public int yatzyScore() {
        //5 ens - dermed gentager koden for 3 og 4 ens. Dog skal det blot returner 50
        for (int index = 0; index < dice.length; index++) {
            int counter = 0;

            for (int index2 = index; index2 < dice.length; index2++) {
                if (dice[index].getEyes() == dice[index2].getEyes()) {
                    counter++;
                }
                if (counter >= 5) {
                    return 50;
                }
            }
        }
        return 0;
    }
}
