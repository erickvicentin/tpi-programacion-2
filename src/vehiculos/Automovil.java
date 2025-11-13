package vehiculos;

public class Automovil extends Vehiculo {
    private String carroceria;

    public Automovil(String marca, String modelo, int anioFabricacion, String color, double kilometraje, String carroceria) {
        super(marca, modelo, anioFabricacion, color, kilometraje);
        this.carroceria = carroceria;
    }

    public String getCarroceria() {
        return carroceria;
    }

    public void setCarroceria(String carroceria) {
        this.carroceria = carroceria;
    }
}
