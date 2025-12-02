package vehiculos;

import enums.Color;
import interfaces.Lavable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public abstract class Vehiculo implements Lavable, Serializable, Comparable<Vehiculo> {

    private final UUID idVehiculo;
    private String marca;
    private String modelo;
    private int anioFabricacion;
    private boolean usado;
    private boolean mantenimientoRealizado;
    private Color color;

    public Vehiculo(String marca, String modelo, int anioFabricacion, boolean usado, Color color) {
        this.idVehiculo = UUID.randomUUID();
        this.marca = marca;
        this.modelo = modelo;
        this.anioFabricacion = anioFabricacion;
        this.usado = usado;
        this.color = color;
        this.mantenimientoRealizado = !usado;
    }

    public UUID getIdVehiculo() {
        return idVehiculo;
    }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public int getAnioFabricacion() {
        return anioFabricacion;
    }

    public void setAnioFabricacion(int anioFabricacion) {
        this.anioFabricacion = anioFabricacion;
    }

    public boolean isUsado() { return usado; }
    public void setUsado(boolean usado) { this.usado = usado; }

    public boolean isMantenimientoRealizado() { return mantenimientoRealizado; }
    public void setMantenimientoRealizado(boolean m) { this.mantenimientoRealizado = m; }

    public Color getColor() { return color; }
    public void setColor(Color color) { this.color = color; }

    public String getShortDescription() {
        return marca + " " + modelo + " (" + anioFabricacion + " - " + color + ")";
    }

    @Override
    public String toString() {
        return "ID: " + idVehiculo +
                " | " + marca + " " + modelo +
                " (" + anioFabricacion + ")" +
                " - Color: " + color +
                " - Usado: " + usado;
    }

    @Override
    public void lavar() {
        try {
            System.out.println("Lavando vehículo: " + this.getShortDescription());
            Thread.sleep(3500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int compareTo(Vehiculo v) {
        return this.idVehiculo.compareTo(v.idVehiculo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vehiculo vehiculo)) return false;
        return idVehiculo.equals(vehiculo.idVehiculo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idVehiculo);
    }
}
