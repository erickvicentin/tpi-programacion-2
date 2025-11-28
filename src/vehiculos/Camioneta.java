package vehiculos;

import enums.Color;
import enums.TipoCarroceria;

public class Camioneta extends Vehiculo {

    private final TipoCarroceria carroceria;

    public Camioneta(String marca, String modelo, int anioFabricacion, boolean usado, Color color, TipoCarroceria carroceria) {
        super(marca, modelo, anioFabricacion, usado, color);
        this.carroceria = carroceria;
    }
}
