package com.example.lab3_1;

import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.BlockingQueue;

public class ServerConnection extends Thread {
    public int port = 8102;
    private BlockingQueue<Socket> clientQueue;
    private Quiz quiz;
    private TextArea logArea;

    public ServerConnection(BlockingQueue<Socket> clientQueue, Quiz quiz, TextArea logArea) {
        this.clientQueue = clientQueue;
        this.quiz = quiz;
        this.logArea = logArea;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            serverSocket.setSoTimeout(1000);
            logArea.appendText("Serwer rozpoczyna pracę na porcie " + port + "\n");
            logArea.appendText(this.quiz.questions.get(0).content + "\n");

            while(quiz.currentQuestion < quiz.questions.size()) {
                try {
                    clientQueue.add(serverSocket.accept());
                } catch(SocketTimeoutException ignored) {}
            }
            logArea.appendText("Serwer kończy pracę\n");
        }
        catch(IOException e) {
            logArea.appendText("Nastąpił błąd serwera\n");
            e.printStackTrace();
        }
    }
}
