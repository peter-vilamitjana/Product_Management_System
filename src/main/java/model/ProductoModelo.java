package model;

import java.util.ArrayList;
import java.util.List;

public class ProductoModelo {
    private List<Producto> productos;

    public ProductoModelo() {
        this.productos = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public boolean eliminarProducto(String codigo) {
        return productos.removeIf(producto -> producto.getCodigo().equals(codigo));
    }

    public Producto buscarProducto(String codigo) {
        for (Producto producto : productos) {
            if (producto.getCodigo().equals(codigo)) {
                return producto;
            }
        }
        return null;
    }

    public List<Producto> obtenerProductos() {
        return productos;
    }
}
