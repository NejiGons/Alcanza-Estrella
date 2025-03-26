package edu.universidad.alcanceestrella.alcance_estrella.controlador;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.universidad.alcanceestrella.alcance_estrella.modelo.Pregunta;

public class GestorPreguntas {
    private final List<Pregunta> preguntas;
    private int indiceActual;
    private final String rutaArchivo;

    public GestorPreguntas(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        this.preguntas = new ArrayList<>();
        cargarPreguntas();
    }

    private void cargarPreguntas() {
        Path path = Paths.get(rutaArchivo);

        if (!Files.exists(path)) {
            System.err.println("⚠️ Archivo de preguntas no encontrado: " + path.toAbsolutePath());
            return;
        }

        List<Pregunta> nuevasPreguntas = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(path)) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("\\|");
                if (partes.length == 5) {
                    String enunciado = partes[0];
                    String opcionA = partes[1];
                    String opcionB = partes[2];
                    String opcionC = partes[3];
                    char respuestaCorrecta = partes[4].trim().charAt(0);
                    nuevasPreguntas.add(new Pregunta(enunciado, opcionA, opcionB, opcionC, respuestaCorrecta));
                } else {
                    System.err.println("⚠️ Formato incorrecto en línea: " + linea);
                }
            }

            if (!nuevasPreguntas.isEmpty()) {
                preguntas.clear();
                preguntas.addAll(nuevasPreguntas);
                Collections.shuffle(preguntas);
                indiceActual = 0;
            } else {
                System.err.println("⚠️ No se cargaron preguntas válidas desde el archivo.");
            }
        } catch (IOException e) {
            System.err.println("❌ Error al leer el archivo de preguntas: " + e.getMessage());
        }
    }

    public Pregunta obtenerPregunta() {
        if (preguntas.isEmpty()) {
            System.err.println("⚠️ No hay preguntas cargadas. Intentando recargar...");
            cargarPreguntas();
            if (preguntas.isEmpty()) {
                System.err.println("❌ No se pudieron cargar preguntas. Verifica el archivo.");
                return null;
            }
        }

        if (indiceActual >= preguntas.size()) {
            Collections.shuffle(preguntas);
            indiceActual = 0;
        }

        return preguntas.get(indiceActual++);
    }

    public List<Pregunta> obtenerTodasLasPreguntas() {
        if (preguntas.isEmpty()) {
            System.err.println("⚠️ No hay preguntas disponibles. Intentando recargar...");
            cargarPreguntas();
        }
        return new ArrayList<>(preguntas);
    }
}
