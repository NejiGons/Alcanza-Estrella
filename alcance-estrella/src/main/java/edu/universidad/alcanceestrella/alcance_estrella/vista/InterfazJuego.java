package edu.universidad.alcanceestrella.alcance_estrella.vista;

import edu.universidad.alcanceestrella.alcance_estrella.controlador.GestorInterfaz;
import edu.universidad.alcanceestrella.alcance_estrella.modelo.DadoFX;
import edu.universidad.alcanceestrella.alcance_estrella.modelo.Juego;
import edu.universidad.alcanceestrella.alcance_estrella.modelo.Jugador;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class InterfazJuego {
    private static final int FILAS = 4;
    private static final int COLUMNAS = 5;
    private Juego juego;
    private Label mensaje;
    private GridPane tablero;
    private VBox layout;
    private Stage ventana;
    private DadoFX dadoFX;
    private VBox panelJugadores; // Panel para mostrar jugadores y aciertos

    public InterfazJuego(Juego juego, Stage ventana) {
        this.juego = juego;
        this.ventana = ventana;
    }

    public Scene crearEscena() {
        tablero = crearTablero();
        tablero.setAlignment(Pos.CENTER);

        mensaje = new Label("Turno de: " + juego.getJugadorActual().getNombre());
        mensaje.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Inicializar el dado sin callback en el constructor
        dadoFX = new DadoFX();

        // ✅ Delegar el clic al controlador
        dadoFX.setOnMouseClicked(e -> {
            if (!dadoFX.isDisabled()) {
                dadoFX.setDisable(true);
                GestorInterfaz.manejarLanzamientoDado(juego, mensaje, layout, this, dadoFX);
            }
        });
        
        panelJugadores = crearPanelJugadores();
        HBox contenedorPrincipal = new HBox(50, tablero, panelJugadores);
        contenedorPrincipal.setAlignment(Pos.CENTER);

        layout = new VBox(20, mensaje, tablero, dadoFX, panelJugadores);

        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #888484; -fx-padding: 20px;");
        VBox.setMargin(tablero, new Insets(20, 0, 20, 0));

        Scene escena = new Scene(layout);
        ventana.setScene(escena);
        ventana.setMaximized(true);
        return escena;
    }

    public GridPane crearTablero() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        
        for (int fila = 0; fila < FILAS; fila++) {
            for (int col = 0; col < COLUMNAS; col++) {
                int numeroCasilla = fila * COLUMNAS + col + 1;
                
                // Crea el fondo con el número de la casilla
                Label fondo = new Label(String.valueOf(numeroCasilla));
                fondo.setStyle("-fx-font-size: 30px; -fx-text-fill: rgba(128, 128, 128, 0.5);"); // Gris y semi-transparente
                
                // Crea el contenido con los avatares
                StringBuilder contenido = new StringBuilder();
                for (Jugador jugador : juego.getJugadores()) {
                    if (jugador.getPosicion() == numeroCasilla) {
                        contenido.append(jugador.getAvatar()).append(" ");
                    }
                }
                Label casilla = new Label(contenido.toString());
                casilla.setStyle("-fx-font-size: 20px; -fx-text-fill: black;"); // Color normal para los avatares
                
                // Contenedor con los dos labels superpuestos
                StackPane contenedor = new StackPane();
                contenedor.setMinSize(80, 80);
                contenedor.setMaxSize(80, 80);
                contenedor.setStyle("-fx-border-color: black; -fx-background-color: white;");
                
                contenedor.getChildren().addAll(fondo, casilla); // Fondo debajo, contenido arriba
                
                grid.add(contenedor, col, fila);
            }
        }
        return grid;
    }

    public void actualizarTablero() {
        layout.getChildren().remove(tablero); // Eliminar el tablero viejo
        tablero = crearTablero(); // Crear el nuevo tablero actualizado
        layout.getChildren().add(1, tablero); // Insertar el nuevo tablero en la posición correcta
    }
    
    public void mostrarMensajeGanador(Jugador jugador) {
        Label ganadorMensaje = new Label("¡" + jugador.getNombre() + " ha ganado!");
        ganadorMensaje.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: green;");

        VBox layoutGanador = new VBox(20, ganadorMensaje);
        layoutGanador.setAlignment(Pos.CENTER);
        layoutGanador.setStyle("-fx-background-color: #888484; -fx-padding: 20px;");

        Scene escenaGanador = new Scene(layoutGanador, 600, 600);
        ventana.setScene(escenaGanador);
    }
    public DadoFX getDadoFX() {
        return dadoFX;
    }
    
    private VBox crearPanelJugadores() {
        VBox panel = new VBox(10);
        panel.setPadding(new Insets(10));
        panel.setStyle("-fx-background-color: #DDDDDD; -fx-padding: 10px; -fx-border-color: black;");
        actualizarPanelJugadores(panel);
        return panel;
    }

    public void actualizarPanelJugadores() {
        panelJugadores.getChildren().clear(); // Limpiar panel antes de actualizar
        actualizarPanelJugadores(panelJugadores);
    }
    
    private void actualizarPanelJugadores(VBox panel) {
        for (Jugador jugador : juego.getJugadores()) {
            Label etiqueta = new Label(jugador.getNombre() + ": " + jugador.getAciertos() + " aciertos");
            etiqueta.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
            panel.getChildren().add(etiqueta);
        }
    }
}
