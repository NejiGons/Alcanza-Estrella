package edu.universidad.alcanceestrella.alcance_estrella.controlador;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GestorMenu {
    private final AdministradorJuego administrador;
    private String rutaCarpetaPreguntas = "src/main/resources/preguntas"; // Valor por defecto

    public GestorMenu(AdministradorJuego administrador) {
        this.administrador = administrador;
    }

    public String validarDatos(String nombre1, String avatar1, String nombre2, String avatar2) {
        // Evitar valores null
        nombre1 = Objects.requireNonNullElse(nombre1, "").trim();
        nombre2 = Objects.requireNonNullElse(nombre2, "").trim();
        avatar1 = Objects.requireNonNullElse(avatar1, "");
        avatar2 = Objects.requireNonNullElse(avatar2, "");

        if (nombre1.isEmpty() || nombre2.isEmpty()) {
            return "Ambos jugadores deben ingresar un nombre.";
        }

        if (!nombre1.matches("[a-zA-ZÀ-ÿ\\s]{2,20}") || !nombre2.matches("[a-zA-ZÀ-ÿ\\s]{2,20}")) {
            return "Los nombres solo pueden contener letras y espacios (2-20 caracteres).";
        }

        if (nombre1.equalsIgnoreCase(nombre2)) {
            return "Los jugadores deben tener nombres distintos.";
        }

        if (avatar1.equals(avatar2)) {
            return "Los jugadores deben elegir avatares distintos.";
        }

        return null;
    }

    public void establecerCarpetaPreguntas(String nuevaRuta) {
        this.rutaCarpetaPreguntas = nuevaRuta;
    }
    
    public List<String> obtenerArchivosPreguntas() {
        List<String> archivos = new ArrayList<>();
        File carpeta = new File(rutaCarpetaPreguntas);

        if (carpeta.exists() && carpeta.isDirectory()) {
            for (File archivo : Objects.requireNonNull(carpeta.listFiles())) {
                if (archivo.isFile() && archivo.getName().endsWith(".txt")) {
                    archivos.add(archivo.getName());
                }
            }
        }
        return archivos;
    }
    
    
    
    public void iniciarJuego(String nombre1, String avatar1, String nombre2, String avatar2, String archivoSeleccionado) {
        String rutaArchivo = rutaCarpetaPreguntas + "/" + archivoSeleccionado;
        administrador.iniciarJuego(nombre1, avatar1, nombre2, avatar2, rutaArchivo);
    }

}
