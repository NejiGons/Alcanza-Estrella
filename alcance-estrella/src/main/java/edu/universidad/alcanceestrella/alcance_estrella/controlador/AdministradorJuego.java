package edu.universidad.alcanceestrella.alcance_estrella.controlador;

import java.util.ArrayList;
import java.util.List;

import edu.universidad.alcanceestrella.alcance_estrella.modelo.Juego;
import edu.universidad.alcanceestrella.alcance_estrella.modelo.Jugador;
import edu.universidad.alcanceestrella.alcance_estrella.modelo.Pregunta;
import edu.universidad.alcanceestrella.alcance_estrella.modelo.Tablero;
import edu.universidad.alcanceestrella.alcance_estrella.vista.InterfazJuego;
import edu.universidad.alcanceestrella.alcance_estrella.vista.MenuPrincipal;
import javafx.application.Application;
import javafx.stage.Stage;

public class AdministradorJuego extends Application {
    private Stage ventana;

    @Override
    public void start(Stage primaryStage) {
        this.ventana = primaryStage;
        mostrarMenuPrincipal();
    }

    private void mostrarMenuPrincipal() {
        GestorMenu gestor = new GestorMenu(this);
        MenuPrincipal menu = new MenuPrincipal(gestor, ventana);
        ventana.setScene(menu.crearEscena());
        ventana.setTitle("Alcance la Estrella - Menú");
        ventana.show();
    }

    public void iniciarJuego(String nombreJugador1, String avatarJugador1, String nombreJugador2, String avatarJugador2, String archivoPreguntas) {
        if (nombreJugador1.isEmpty() || nombreJugador2.isEmpty()) {
            System.out.println("⚠️ Error: Los nombres de los jugadores no pueden estar vacíos.");
            return;
        }

        // Crear jugadores
        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(new Jugador(nombreJugador1, avatarJugador1));
        jugadores.add(new Jugador(nombreJugador2, avatarJugador2));

        // Cargar preguntas desde el archivo seleccionado
        String rutaArchivo = archivoPreguntas;
        GestorPreguntas gestorPreguntas = new GestorPreguntas(rutaArchivo);
        List<Pregunta> preguntas = gestorPreguntas.obtenerTodasLasPreguntas();

        // Crear tablero y juego
        Tablero tablero = new Tablero(preguntas);
        Juego juego = new Juego(jugadores, tablero);

        // Crear interfaz del juego
        InterfazJuego interfaz = new InterfazJuego(juego, ventana);
        ventana.setScene(interfaz.crearEscena());
        ventana.setTitle("Alcance la Estrella - Juego");
        ventana.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
