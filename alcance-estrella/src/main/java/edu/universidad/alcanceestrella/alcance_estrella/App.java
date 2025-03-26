package edu.universidad.alcanceestrella.alcance_estrella;

import javax.ws.rs.ApplicationPath;

import edu.universidad.alcanceestrella.alcance_estrella.controlador.AdministradorJuego;
import edu.universidad.alcanceestrella.alcance_estrella.vista.MenuPrincipal;

@ApplicationPath("app")
public class App {
    public static void main(String[] args) {
        AdministradorJuego.main(args);
    }
}


