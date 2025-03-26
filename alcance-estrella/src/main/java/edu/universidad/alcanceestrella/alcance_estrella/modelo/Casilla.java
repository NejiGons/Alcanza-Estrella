package edu.universidad.alcanceestrella.alcance_estrella.modelo;

import java.util.Random;

public class Casilla {
    private int numero;
    private Pregunta pregunta;
    private static final String[] castigosPonderados = {
    	    "Resbalon", "Resbalon", "Resbalon", "Resbalon", // 40% de probabilidad
    	    "Puente", "Puente", "Puente", // 30% de probabilidad
    	    "Calavera", "Calavera" // 20% de probabilidad
    	};
    public Casilla(int numero, Pregunta pregunta) {
        this.numero = numero;
        this.pregunta = pregunta;
    }

    public int getNumero() {
        return numero;
    }

    public Pregunta getPregunta() {
        return pregunta;
    }

    public String aplicarCastigo() {
        Random random = new Random();
        return castigosPonderados[random.nextInt(castigosPonderados.length)];
    }

    @Override
    public String toString() {
        return "Casilla " + numero + " - Pregunta: " + (pregunta != null ? pregunta.getEnunciado() : "Sin pregunta");
    }
}
