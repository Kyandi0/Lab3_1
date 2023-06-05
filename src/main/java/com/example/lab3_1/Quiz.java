package com.example.lab3_1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Quiz {
    public final List<Question> questions = new ArrayList<>();
    File quizData = new File("questions.txt");
    public int currentQuestion = 0;

    public Quiz() {
        try(Scanner scan = new Scanner(quizData)) {
            while(scan.hasNextLine()) {
                String line= scan.nextLine();
                String[] questionAndAnswer = line.split("\\|");
                questions.add(new Question(questionAndAnswer[0], questionAndAnswer[1]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
