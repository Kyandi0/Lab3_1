package com.example.lab3_1;

import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class ServerAnswerChecker extends Thread {
    private BlockingQueue<Socket> clientQueue;
    private Quiz quiz;
    private TextArea logArea;

    public ServerAnswerChecker(BlockingQueue<Socket> clientQueue, Quiz quiz, TextArea logArea) {
        this.clientQueue = clientQueue;
        this.quiz = quiz;
        this.logArea = logArea;
    }

    @Override
    public void run() {
        while(quiz.currentQuestion < quiz.questions.size()) {
            try(Socket clientSocket = clientQueue.take()) {
                byte[] clientInput = clientSocket.getInputStream().readAllBytes();
                String clientInputString = new String(clientInput);

                String[] inputSplit = clientInputString.split(";");
                String username = inputSplit[0];
                String answer = inputSplit[1];
                Question currentQuestion = quiz.questions.get(quiz.currentQuestion);

                if (currentQuestion.correctAnswer.equals(answer)) {
                    quiz.currentQuestion+=1;
                    logArea.appendText("Użytkownik " + username + " odpowiedział poprawnie!\n");
                    showNextQuestion();
                } else {
                    logArea.appendText("Użytkownik " + username + " odpowiedział niepoprawnie!\n");
                }
            }
            catch(InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showNextQuestion() {
        if (this.quiz.currentQuestion < this.quiz.questions.size()) {
            this.logArea.appendText(this.quiz.questions.get(this.quiz.currentQuestion).content);
            this.logArea.appendText("\n");
        }
    }
}
