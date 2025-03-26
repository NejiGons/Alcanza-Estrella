package edu.universidad.alcanceestrella.alcance_estrella.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tablero {
    private static final int FILAS = 4;
    private static final int COLUMNAS = 5;
    private static final int TOTAL_CASILLAS = FILAS * COLUMNAS;
    
    private List<Casilla> casillas;

    public Tablero(List<Pregunta> preguntas) {
        this.casillas = new ArrayList<>();
        inicializarCasillas(preguntas);
    }

    private void inicializarCasillas(List<Pregunta> preguntas) {
        Random random = new Random();
        List<Pregunta> copiaPreguntas = new ArrayList<>(preguntas);

        for (int i = 1; i <= TOTAL_CASILLAS; i++) {
            if (copiaPreguntas.isEmpty()) {
                copiaPreguntas.addAll(preguntas); // Volver a llenar la lista si se acaban las preguntas
            }
            Pregunta pregunta = copiaPreguntas.remove(random.nextInt(copiaPreguntas.size()));
            casillas.add(new Casilla(i, pregunta));
        }
    }


    public Casilla getCasilla(int numero) {
        return (numero >= 1 && numero <= TOTAL_CASILLAS) ? casillas.get(numero - 1) : null;
    }

    public void mostrarTablero() {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                int numero = i * COLUMNAS + j + 1;
                System.out.printf("[%2d] ", numero);
            }
            System.out.println();
        }
    }

    @Override
    public String toString() {
        return "Tablero con " + TOTAL_CASILLAS + " casillas.";
    }
}
