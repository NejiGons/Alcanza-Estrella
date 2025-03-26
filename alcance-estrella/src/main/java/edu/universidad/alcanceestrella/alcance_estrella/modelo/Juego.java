package edu.universidad.alcanceestrella.alcance_estrella.modelo;

import java.util.List;

public class Juego {
    private Tablero tablero;
    private List<Jugador> jugadores;
    private Dado dado;
    private int turnoActual;

    public Juego(List<Jugador> jugadores, Tablero tablero) {
        this.jugadores = jugadores;
        this.tablero = tablero;
        this.dado = new Dado();
        this.turnoActual = 0; // Comienza el primer jugador
    }

    public int lanzarDado() {
        return dado.lanzar();
    }

    public void moverJugador(int avance) {
        Jugador jugador = getJugadorActual();
        jugador.avanzar(avance);

    }

    public void aplicarCastigo(Jugador jugador, String castigo) {
        switch (castigo) {
            case "Puente":
                jugador.retroceder(2);
                break;
            case "Resbalón":
                jugador.retroceder(2);
                break;
            case "Calavera":
                jugador.retroceder(jugador.getPosicion()-1);
                break;
        }
    }

    public void cambiarTurno() {
        turnoActual = (turnoActual + 1) % jugadores.size();
    }

    public boolean haGanado(Jugador jugador) {
        return jugador.getPosicion() >= 20;
    }
    
    public Tablero getTablero() {
        return tablero;
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public Jugador getJugadorActual() {
        return jugadores.get(turnoActual);
    }

    public void avanzarTurno() {
        turnoActual = (turnoActual + 1) % jugadores.size();
    }

}
