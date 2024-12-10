package controller;

import model.Producto;
import model.ProductoModelo;
import view.ProductoView;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ProductoController {
    private ProductoModelo model;
    private ProductoView view;
    private static final String EXCEL_FILE_PATH = "src/main/resources/productos.xlsx";

    public ProductoController(ProductoView vista) {
        this.model = new ProductoModelo();
        this.view = vista;
        cargarProductosDesdeExcel();
    }

    public void iniciar() {
        while (true) {
            view.mostrarMenu();
            int opcion = view.leerEntero();

            switch (opcion) {
                case 1:
                    agregarProducto();
                    break;
                case 2:
                    eliminarProducto();
                    break;
                case 3:
                    modificarProducto();
                    break;
                case 4:
                    buscarProducto();
                    break;
                case 5:
                    cambiarPrecioPorProveedor();
                    break;
                case 6:
                    this.listarProductosYEstadisticas();
                    break;
                case 7:
                    this.guardarProductosEnExcel(); // Salir
                    return;
                default:
                    view.mostrarMensaje("Opción no válida. Intente de nuevo.");
            }
        }
    }

    private void agregarProducto() {
        view.mostrarMensaje("Ingrese proveedor:");
        String proveedor = view.leerTexto();
        view.mostrarMensaje("Ingrese marca:");
        String marca = view.leerTexto();
        view.mostrarMensaje("Ingrese nombre del producto:");
        String nombre = view.leerTexto();
        view.mostrarMensaje("Ingrese código/ISBN:");
        String codigo = view.leerTexto();
        view.mostrarMensaje("Ingrese precio:");
        double precio = view.leerDouble();

        Producto producto = new Producto(proveedor, marca, nombre, codigo, precio);
        model.agregarProducto(producto);
        view.mostrarMensaje("Producto agregado exitosamente.");
    }

    private void eliminarProducto() {
        view.mostrarMensaje("Ingrese código/ISBN del producto a eliminar:");
        String codigo = view.leerTexto();
        if (model.eliminarProducto(codigo)) {
            view.mostrarMensaje("Producto eliminado exitosamente.");
        } else {
            view.mostrarMensaje("Producto no encontrado.");
        }
    }

    private void modificarProducto() {
        view.mostrarMensaje("Ingrese código/ISBN del producto a modificar:");
        String codigo = view.leerTexto();
        Producto producto = model.buscarProducto(codigo);
        if (producto != null) {
            view.mostrarMensaje("Ingrese nuevo proveedor:");
            producto.setProveedor(view.leerTexto());
            view.mostrarMensaje("Ingrese nueva marca:");
            producto.setMarca(view.leerTexto());
            view.mostrarMensaje("Ingrese nuevo nombre del producto:");
            producto.setNombre(view.leerTexto());
            view.mostrarMensaje("Ingrese nuevo precio:");
            producto.setPrecio(view.leerDouble());
            view.mostrarMensaje("Producto modificado exitosamente.");
        } else {
            view.mostrarMensaje("Producto no encontrado.");
        }
    }

    private void buscarProducto() {
        view.mostrarMensaje("Ingrese código/ISBN del producto a buscar:");
        String codigo = view.leerTexto();
        Producto producto = model.buscarProducto(codigo);
        if (producto != null) {
            view.mostrarMensaje(producto.toString());
        } else {
            view.mostrarMensaje("Producto no encontrado.");
        }
    }

    private void cambiarPrecioPorProveedor() {
        view.mostrarMensaje("Ingrese el nombre del proveedor:");
        String proveedor = view.leerTexto();
        view.mostrarMensaje("Ingrese el porcentaje de incremento (ej. 10 para 10%):");
        double porcentaje = view.leerDouble();

        List<Producto> productos = model.obtenerProductos();
        for (Producto producto : productos) {
            if (producto.getProveedor().equalsIgnoreCase(proveedor)) {
                double nuevoPrecio = producto.getPrecio() * (1 + porcentaje / 100);
                producto.setPrecio(nuevoPrecio);
            }
        }

        view.mostrarMensaje("Precios actualizados para el proveedor " + proveedor);
    }

    private void cargarProductosDesdeExcel() {
        try (FileInputStream file = new FileInputStream(EXCEL_FILE_PATH);
             Workbook workbook = new XSSFWorkbook(file)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Saltar la fila de encabezado

                String proveedor = row.getCell(0).getStringCellValue();
                String marca = row.getCell(1).getStringCellValue();
                String nombre = row.getCell(2).getStringCellValue();
                String codigo = row.getCell(3).getStringCellValue();
                double precio = row.getCell(4).getNumericCellValue();

                Producto producto = new Producto(proveedor, marca, nombre, codigo, precio);
                model.agregarProducto(producto);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void guardarProductosEnExcel() {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Productos");
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Proveedor");
            headerRow.createCell(1).setCellValue("Marca");
            headerRow.createCell(2).setCellValue("Nombre");
            headerRow.createCell(3).setCellValue("Codigo");
            headerRow.createCell(4).setCellValue("Precio");

            int rowNum = 1;
            for (Producto producto : model.obtenerProductos()) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(producto.getProveedor());
                row.createCell(1).setCellValue(producto.getMarca());
                row.createCell(2).setCellValue(producto.getNombre());
                row.createCell(3).setCellValue(producto.getCodigo());
                row.createCell(4).setCellValue(producto.getPrecio());
            }

            try (FileOutputStream fileOut = new FileOutputStream(EXCEL_FILE_PATH)) {
                workbook.write(fileOut);
                view.mostrarMensaje("Productos guardados en el archivo Excel exitosamente.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listarProductosYEstadisticas() {
        List<Producto> productos = this.model.obtenerProductos();

        if (productos.isEmpty()) {
            this.view.mostrarMensaje("No hay productos registrados.");
            return;
        }

        double sumaPrecios = 0;
        this.view.mostrarMensaje("Lista de productos:");
        for (Producto producto : productos) {
            this.view.mostrarMensaje(producto.toString());
            sumaPrecios += producto.getPrecio();
        }

        int cantidad = productos.size();
        double promedio = sumaPrecios / cantidad;

        this.view.mostrarMensaje("\nCantidad total de productos: " + cantidad);
        this.view.mostrarMensaje("Promedio de precios: " + promedio);
    }

}
