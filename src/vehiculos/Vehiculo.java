package vehiculos;

public abstract class Vehiculo {
    private String marca;
    private String modelo;
    private final int anioFabricacion;
    private String color;
    private double kilometraje;
    private boolean usado;
    private boolean mantenimientoRealizado;

    public Vehiculo(String marca, String modelo, int anioFabricacion, String color, double kilometraje) {
        this.marca = marca;
        this.modelo = modelo;
        this.anioFabricacion = anioFabricacion;
        this.color = color;
        this.kilometraje = kilometraje;
        this.usado = kilometraje > 0;
        this.mantenimientoRealizado = false;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getFechaFabricacion() {
        return anioFabricacion;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(double kilometraje) {
        this.kilometraje = kilometraje;
    }

    public boolean isUsado() {
        return usado;
    }

    public void setUsado(boolean usado) {
        this.usado = usado;
    }

    public boolean isMantenimientoRealizado() {
        return mantenimientoRealizado;
    }

    public void setMantenimientoRealizado(boolean mantenimientoRealizado) {
        this.mantenimientoRealizado = mantenimientoRealizado;
    }
}
