package edu.universidad.alcanceestrella.alcance_estrella.modelo;

public class Jugador {
    private String nombre;
    private String avatar;
    private int posicion;
    private int aciertos;

    public Jugador(String nombre, String avatar) {
        this.nombre = nombre;
        this.avatar = avatar;
        this.posicion = 1; 
        this.aciertos = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public String getAvatar() {
        return avatar;
    }

    public int getPosicion() {
        return posicion;
    }

    public int getAciertos() {
        return aciertos;
    }

    public void avanzar(int casillas) {
        this.posicion += casillas;
        if (this.posicion > 20) { 
            this.posicion = 20;
        }
    }

    public void retroceder(int casillas) {
        this.posicion -= casillas;
        if (this.posicion < 1) { 
            this.posicion = 1;
        }
    }

    public void aumentarAciertos() {
        this.aciertos++;
    }

    @Override
    public String toString() {
        return nombre + " (" + avatar + ") - Posición: " + posicion + " | Aciertos: " + aciertos;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }
}
