package edu.universidad.alcanceestrella.alcance_estrella.vista;

import java.io.File;
import java.util.List;

import edu.universidad.alcanceestrella.alcance_estrella.controlador.GestorMenu;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class MenuPrincipal {
    private final GestorMenu gestor;
    private final String[] emojis = {"🚀", "🎲", "🌟","🎶","👾","🎸","📒"};
    private ComboBox<String> selectorArchivos;
    private Label carpetaSeleccionada;
    private Stage ventana;

    public MenuPrincipal(GestorMenu gestor, Stage ventana) {
        this.gestor = gestor;
        this.ventana = ventana;
        this.ventana.setFullScreen(true);
    }

    public Scene crearEscena() {
        Text title = new Text("¡Bienvenido a Alcance la Estrella!");
        title.setFont(new Font("Arial", 30));
        title.setStyle("-fx-fill: white;");

        TextField nombreJugador1 = new TextField();
        nombreJugador1.setPromptText("Nombre del Jugador 1");
        nombreJugador1.setStyle("-fx-font-size: 16px;");

        ChoiceBox<String> avatarJugador1 = new ChoiceBox<>();
        avatarJugador1.getItems().addAll(emojis);
        avatarJugador1.setValue(emojis[0]);
        avatarJugador1.setStyle("-fx-font-size: 16px;");

        TextField nombreJugador2 = new TextField();
        nombreJugador2.setPromptText("Nombre del Jugador 2");
        nombreJugador2.setStyle("-fx-font-size: 16px;");

        ChoiceBox<String> avatarJugador2 = new ChoiceBox<>();
        avatarJugador2.getItems().addAll(emojis);
        avatarJugador2.setValue(emojis[1]);
        avatarJugador2.setStyle("-fx-font-size: 16px;");

        carpetaSeleccionada = new Label("Carpeta actual: src/main/resources/preguntas");
        carpetaSeleccionada.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
        
        Button seleccionarCarpeta = new Button("📁 Seleccionar Carpeta");
        seleccionarCarpeta.setStyle("-fx-background-color: #555; -fx-text-fill: white; -fx-font-size: 16px;");
        seleccionarCarpeta.setOnMouseEntered(e -> seleccionarCarpeta.setStyle("-fx-background-color: #777; -fx-text-fill: white;"));
        seleccionarCarpeta.setOnMouseExited(e -> seleccionarCarpeta.setStyle("-fx-background-color: #555; -fx-text-fill: white;"));
        seleccionarCarpeta.setOnAction(e -> elegirCarpeta());

        selectorArchivos = new ComboBox<>();
        selectorArchivos.setPromptText("Seleccionar archivo de preguntas");
        selectorArchivos.setStyle("-fx-font-size: 16px;");
        actualizarListaArchivos();

        Button iniciarJuego = new Button("🚀 Iniciar Juego");
        iniciarJuego.setStyle("-fx-background-color: #ffcc00; -fx-text-fill: black; -fx-font-size: 18px; -fx-font-weight: bold;");
        iniciarJuego.setOnMouseEntered(e -> iniciarJuego.setStyle("-fx-background-color: #ffaa00; -fx-text-fill: black; -fx-font-size: 18px; -fx-font-weight: bold;"));
        iniciarJuego.setOnMouseExited(e -> iniciarJuego.setStyle("-fx-background-color: #ffcc00; -fx-text-fill: black; -fx-font-size: 18px; -fx-font-weight: bold;"));
        iniciarJuego.setOnAction(e -> {
            String nombre1 = nombreJugador1.getText();
            String nombre2 = nombreJugador2.getText();
            String avatar1 = avatarJugador1.getValue();
            String avatar2 = avatarJugador2.getValue();
            String archivoSeleccionado = selectorArchivos.getValue();

            String error = gestor.validarDatos(nombre1, avatar1, nombre2, avatar2);
            if (error != null) {
                mostrarAlerta("Error", error);
            } else if (archivoSeleccionado == null) {
                mostrarAlerta("Error", "Debe seleccionar un archivo de preguntas.");
            } else {
                gestor.iniciarJuego(nombre1, avatar1, nombre2, avatar2, archivoSeleccionado);
            }
        });

        VBox layout = new VBox(20, title, nombreJugador1, avatarJugador1, nombreJugador2, avatarJugador2, 
                seleccionarCarpeta, carpetaSeleccionada, selectorArchivos, iniciarJuego);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #222; -fx-padding: 40px;");

        return new Scene(layout, 800, 600);
    }
    
    private void elegirCarpeta() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Seleccionar carpeta de preguntas");
        File carpetaSeleccionadaFile = directoryChooser.showDialog(ventana);

        if (carpetaSeleccionadaFile != null) {
            gestor.establecerCarpetaPreguntas(carpetaSeleccionadaFile.getAbsolutePath());
            carpetaSeleccionada.setText("Carpeta actual: " + carpetaSeleccionadaFile.getAbsolutePath());
            actualizarListaArchivos();
        }
    }

    private void actualizarListaArchivos() {
        List<String> archivos = gestor.obtenerArchivosPreguntas();
        selectorArchivos.getItems().clear();
        selectorArchivos.getItems().addAll(archivos);
        selectorArchivos.setDisable(archivos.isEmpty());
    }
    
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
