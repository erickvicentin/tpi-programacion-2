package vehiculos;

import enums.Color;

public abstract class Vehiculo {

    private String marca;
    private String modelo;
    private int año;
    private boolean usado;
    private boolean mantenimientoRealizado;
    private Color color;

    public Vehiculo() {}

    public Vehiculo(String marca, String modelo, int año, boolean usado, Color color) {
        this.marca = marca;
        this.modelo = modelo;
        this.año = año;
        this.usado = usado;
        this.color = color;
        this.mantenimientoRealizado = !usado;
    }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public int getAño() { return año; }
    public void setAño(int año) { this.año = año; }

    public boolean isUsado() { return usado; }
    public void setUsado(boolean usado) { this.usado = usado; }

    public boolean isMantenimientoRealizado() { return mantenimientoRealizado; }
    public void setMantenimientoRealizado(boolean m) { this.mantenimientoRealizado = m; }

    public Color getColor() { return color; }
    public void setColor(Color color) { this.color = color; }

    @Override
    public String toString() {
        return marca + " " + modelo + " (" + año + ") - Color: " + color + " - Usado: " + usado;
    }
}
