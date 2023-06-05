package com.example.lab3_1;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ServerController {
    public Quiz quiz = new Quiz();
    public BlockingQueue<Socket> clientQueue = new ArrayBlockingQueue<>(10);
    private @FXML TextArea logArea;

    public void initialize() {
        Thread t1 = new ServerConnection(clientQueue, quiz, logArea);
        Thread t2 = new ServerAnswerChecker(clientQueue, quiz, logArea);

        t1.setDaemon(true);
        t2.setDaemon(true);

        t1.start();
        t2.start();
    }
}
