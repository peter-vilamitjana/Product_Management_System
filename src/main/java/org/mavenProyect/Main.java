package org.mavenProyect;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
// src/main/java/Main.java
import controller.ProductoController;
import view.ProductoView;

public class Main {
    public static void main(String[] args) {
        ProductoView vista = new ProductoView();
        ProductoController controlador = new ProductoController(vista);
        controlador.iniciar();
    }
}
