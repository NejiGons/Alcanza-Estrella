package edu.universidad.alcanceestrella.alcance_estrella.modelo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.scene.layout.StackPane;
import java.util.function.Consumer;

public class DadoFX extends StackPane {
    private Label numero;
    private int[] animacionNumeros = {1, 5, 3, 2, 6, 1, 5};
    private int resultadoFinal;

    public DadoFX() { // Elimina el callback en el constructor
        Rectangle fondo = new Rectangle(80, 80);
        fondo.setFill(Color.WHITE);
        fondo.setStroke(Color.BLACK);

        numero = new Label("?");
        numero.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");

        this.getChildren().addAll(fondo, numero);
        this.setOnMouseClicked(e -> lanzar(resultado -> {})); // Default: no hace nada
    }

    public void lanzar(Consumer<Integer> callback) {
        resultadoFinal = (int) (Math.random() * 6) + 1; // Genera el número del dado

        Timeline timeline = new Timeline();
        for (int i = 0; i < animacionNumeros.length; i++) {
            int valor = animacionNumeros[i];
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(i * 100), 
                e -> numero.setText(String.valueOf(valor))
            ));
        }
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(animacionNumeros.length * 100), 
            e -> {
                mostrarResultadoFinal();
                callback.accept(resultadoFinal); // Llama al callback con el resultado final
            }
        ));

        timeline.play();
    }

    private void mostrarResultadoFinal() {
        numero.setText(String.valueOf(resultadoFinal));
    }

    public int getResultado() {
        return resultadoFinal;
    }
}
