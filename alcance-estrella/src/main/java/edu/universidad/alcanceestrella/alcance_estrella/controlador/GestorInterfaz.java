package edu.universidad.alcanceestrella.alcance_estrella.controlador;

import java.util.Optional;
import java.util.function.Consumer;

import edu.universidad.alcanceestrella.alcance_estrella.modelo.Casilla;
import edu.universidad.alcanceestrella.alcance_estrella.modelo.DadoFX;
import edu.universidad.alcanceestrella.alcance_estrella.modelo.Juego;
import edu.universidad.alcanceestrella.alcance_estrella.modelo.Jugador;
import edu.universidad.alcanceestrella.alcance_estrella.modelo.Pregunta;
import edu.universidad.alcanceestrella.alcance_estrella.vista.InterfazJuego;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class GestorInterfaz {

    public static void manejarLanzamientoDado(Juego juego, Label mensaje, VBox parent, InterfazJuego interfaz, DadoFX dadoFX) {
        // Lanza el dado y espera a que termine la animación
        dadoFX.lanzar(resultado -> moverJugadorYVerificar(juego, resultado, mensaje, interfaz));
    }

    private static void moverJugadorYVerificar(Juego juego, int resultado, Label mensaje, InterfazJuego interfaz) {
        int pasos = resultado;
        
        // Animar el movimiento casilla por casilla
        Timeline moverFicha = new Timeline();
        for (int i = 0; i < pasos; i++) {
            int pasoActual = i + 1;
            moverFicha.getKeyFrames().add(new KeyFrame(Duration.seconds(0.5 * pasoActual), e -> {
                juego.moverJugador(1); // Mueve de una en una
                interfaz.actualizarTablero();
                
                if (juego.getJugadorActual().getPosicion() >= 20) {
                    interfaz.mostrarMensajeGanador(juego.getJugadorActual());
                    moverFicha.stop();
                }
            }));
        }

        // Cuando termine de moverse, mostrar la pregunta
        moverFicha.setOnFinished(e -> {
            if (juego.getJugadorActual().getPosicion() >= 20) {
                return; // No seguir ejecutando si el jugador ha ganado
            }
            new Timeline(new KeyFrame(Duration.seconds(0.7), ev -> { // ⏳ Delay antes de la pregunta
                Casilla casillaActual = juego.getTablero().getCasilla(juego.getJugadorActual().getPosicion());

                mostrarPregunta(juego, casillaActual, respuestaCorrecta -> {
                    if (respuestaCorrecta) {
                    	juego.getJugadorActual().aumentarAciertos();
                        interfaz.actualizarPanelJugadores();
                        // ✅ Mostrar mensaje "Correcto" en verde por 1 segundo
                        mensaje.setText("✅ ¡Correcto!");
                        mensaje.setStyle("-fx-text-fill: green; -fx-font-size: 24px; -fx-font-weight: bold;");

                        new Timeline(new KeyFrame(Duration.seconds(1), ev2 -> {
                            juego.avanzarTurno();
                            mensaje.setText("Turno de: " + juego.getJugadorActual().getNombre());
                            mensaje.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
                            interfaz.actualizarTablero();
                            new Timeline(new KeyFrame(Duration.seconds(0.5), ev3 -> interfaz.getDadoFX().setDisable(false))).play();
                        })).play();
                    } else {
                        // ❌ Aplicar castigo si la respuesta es incorrecta
                        String castigo = casillaActual.aplicarCastigo();
                        int casillasRetroceder = 0;
                        String mensajeCastigo = "";

                        switch (castigo) {
                            case "Puente":
                                casillasRetroceder = 3;
                                mensajeCastigo = "❌ ¡Incorrecto! -3 casillas";
                                break;
                            case "Resbalon":
                                casillasRetroceder = 2;
                                mensajeCastigo = "❌ ¡Incorrecto! -2 casillas";
                                break;
                            case "Calavera":
                                juego.getJugadorActual().setPosicion(1);
                                mensajeCastigo = "💀 ¡Calavera! Vuelves a la casilla 1";
                                break;
                        }

                        // Mostrar mensaje del castigo en rojo por 1 segundo
                        mensaje.setText(mensajeCastigo);
                        mensaje.setStyle("-fx-text-fill: red; -fx-font-size: 24px; -fx-font-weight: bold;");

                        final int casillaRetrocederFinal = casillasRetroceder;
                        new Timeline(new KeyFrame(Duration.seconds(1), ev2 -> {


                            // Si no es "Calavera", hacer que retroceda casillas normalmente
                            if (!castigo.equals("Calavera")) {
                                juego.getJugadorActual().retroceder(casillaRetrocederFinal);
                            }
                            juego.avanzarTurno();
                            mensaje.setText("Turno de: " + juego.getJugadorActual().getNombre());
                            mensaje.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
                            interfaz.actualizarTablero();
                            new Timeline(new KeyFrame(Duration.seconds(0.5), e3 -> interfaz.getDadoFX().setDisable(false))).play();
                        })).play();
                    }
                });
            })).play();
        });

        moverFicha.play();
    }




    public static void mostrarPregunta(Juego juego, Casilla casilla, Consumer<Boolean> callback) {
        Pregunta pregunta = casilla.getPregunta();
        if (pregunta == null) {
            callback.accept(true);
            return;
        }

        Platform.runLater(() -> { // 🔹 Se ejecuta en el hilo correcto después de la animación
            ButtonType opcionA = new ButtonType("A");
            ButtonType opcionB = new ButtonType("B");
            ButtonType opcionC = new ButtonType("C");

            Alert alerta = new Alert(Alert.AlertType.NONE);
            alerta.setTitle("Pregunta");
            alerta.setHeaderText("Responde la siguiente pregunta:");

            // 🔹 Usamos un TextArea para que la pregunta se ajuste correctamente
            TextArea textArea = new TextArea(pregunta.getEnunciado() + "\n\n" +
                                             "A) " + pregunta.getOpciones()[0] + "\n" +
                                             "B) " + pregunta.getOpciones()[1] + "\n" +
                                             "C) " + pregunta.getOpciones()[2]);
            textArea.setWrapText(true);
            textArea.setEditable(false);
            textArea.setPrefWidth(400); // 🔹 Ancho personalizado
            textArea.setPrefHeight(150); // 🔹 Altura ajustable según contenido

            alerta.getDialogPane().setContent(textArea);
            alerta.getButtonTypes().setAll(opcionA, opcionB, opcionC);

            Optional<ButtonType> respuesta = alerta.showAndWait();

            boolean esCorrecta = respuesta.isPresent() && !respuesta.get().getText().isEmpty() &&
                                 pregunta.validarRespuesta(respuesta.get().getText().toLowerCase().charAt(0));

            callback.accept(esCorrecta); // 🔹 Llama al callback con el resultado
        });
    }



    public static void aplicarCastigo(Juego juego, String castigo) {
        Jugador jugador = juego.getJugadorActual();
        
        if (jugador.getPosicion() == 1) {
            System.out.println("⚠️ No se aplica castigo porque el jugador ya está en la casilla 1.");
            return;
        }

        juego.aplicarCastigo(jugador, castigo);
    }

    public static void esperarYManejarTurno(Juego juego, Label mensaje, VBox parent, InterfazJuego interfaz, DadoFX dadoFX) {
        dadoFX.lanzar(resultado -> manejarLanzamientoDado(juego, mensaje, parent, interfaz, dadoFX));
    }
}
