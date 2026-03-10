package CCOADB.FP.vista;

import CCOADB.FP.controlador.Controlador;

public class Main {
    public static void main(String[] args) {
        Controlador controlador = new Controlador(); // el vuestro, intacto
        MenuConsola menu = new MenuConsola(controlador);
        menu.iniciar();
    }
}