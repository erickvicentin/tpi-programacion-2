package vehiculos;

public class Camioneta extends Vehiculo {
    private double capacidad;

    public Camioneta(String marca, String modelo, int anioFabricacion, String color, double kilometraje, double capacidad) {
        super(marca, modelo, anioFabricacion, color, kilometraje);
        this.capacidad = capacidad;
    }

    public double getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(double capacidad) {
        this.capacidad = capacidad;
    }

}
