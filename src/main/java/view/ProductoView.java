package view;

import java.util.Scanner;

public class ProductoView {
    private Scanner scanner;

    public ProductoView() {
        scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        System.out.println("Menú:");
        System.out.println("1. Agregar producto");
        System.out.println("2. Eliminar producto");
        System.out.println("3. Modificar producto");
        System.out.println("4. Buscar producto");
        System.out.println("5. Cambiar precio según proveedor");
        System.out.println("6. Listar productos y estadísticas");
        System.out.println("7. Salir");
        System.out.print("Seleccione una opción: ");
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public String leerTexto() {
        return scanner.nextLine();
    }

    public double leerDouble() {
        while (!scanner.hasNextDouble()) {
            System.out.println("Entrada inválida. Por favor ingrese un número.");
            scanner.next(); // Limpiar entrada no válida
        }
        double valor = scanner.nextDouble();
        scanner.nextLine(); // Consumir la nueva línea
        return valor;
    }

    public int leerEntero() {
        while (!scanner.hasNextInt()) {
            System.out.println("Entrada inválida. Por favor ingrese un número entero.");
            scanner.next(); // Limpiar entrada no válida
        }
        int valor = scanner.nextInt();
        scanner.nextLine(); // Consumir la nueva línea
        return valor;
    }
}
