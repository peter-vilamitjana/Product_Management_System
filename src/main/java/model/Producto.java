package model;

public class Producto {
    private String proveedor;
    private String marca;
    private String nombre;
    private String codigo;
    private double precio;

    public Producto(String proveedor, String marca, String nombre, String codigo, double precio) {
        this.proveedor = proveedor;
        this.marca = marca;
        this.nombre = nombre;
        this.codigo = codigo;
        this.precio = precio;
    }

    // Getters y setters


    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "proveedor='" + proveedor + '\'' +
                ", marca='" + marca + '\'' +
                ", nombre='" + nombre + '\'' +
                ", codigo='" + codigo + '\'' +
                ", precio=" + precio +
                '}';
    }
}
