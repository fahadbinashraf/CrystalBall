package com.example.fahadbinashraf.crystalball;

import java.util.Random;

public class CrystalBall {
    private  String answers[] = {"It is certain", "It is decidedly so", "All signs say Yes", "The Stars are not aligned",
            "My reply is NO", "It is doubtful", "Better not tell you now", "Concentrate and ask again", "unable to answer now"};

    public String getAnAnswer(){


        Random randomGenerator = new Random();

       return answers[randomGenerator.nextInt(answers.length)];
    }
}
