package edu.universidad.alcanceestrella.alcance_estrella.modelo;

import java.util.Random;

import javafx.scene.layout.StackPane;

public class Dado extends StackPane{
    private Random random;

    public Dado() {
        this.random = new Random();
    }

    public int lanzar() {
        return random.nextInt(6) + 1;
    }
}

