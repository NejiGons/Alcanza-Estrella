package edu.universidad.alcanceestrella.alcance_estrella.modelo;

public class Pregunta {
    private String enunciado;
    private String[] opciones;
    private char respuestaCorrecta; // 'a', 'b' o 'c'

    public Pregunta(String enunciado, String opcionA, String opcionB, String opcionC, char respuestaCorrecta) {
        this.enunciado = enunciado;
        this.opciones = new String[]{opcionA, opcionB, opcionC};
        this.respuestaCorrecta = Character.toLowerCase(respuestaCorrecta);
    }

    public String getEnunciado() {
        return enunciado;
    }

    public String[] getOpciones() {
        return opciones;
    }

    public char getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    public boolean validarRespuesta(char respuestaJugador) {
        return Character.toLowerCase(respuestaJugador) == respuestaCorrecta;
    }

    @Override
    public String toString() {
        return enunciado + "\n" +
               "a) " + opciones[0] + "\n" +
               "b) " + opciones[1] + "\n" +
               "c) " + opciones[2];
    }
}
