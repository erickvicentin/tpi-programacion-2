package vehiculos;

import enums.Color;
import enums.TipoCarroceria;

public class Camioneta extends Vehiculo {

    private TipoCarroceria carroceria;

    public Camioneta(String marca, String modelo, int anioFabricacion, boolean usado, Color color, TipoCarroceria carroceria) {
        super(marca, modelo, anioFabricacion, usado, color);
        this.carroceria = carroceria;
    }

    public void setCarroceria(TipoCarroceria c) {
        this.carroceria = c;
    }

    public TipoCarroceria getCarroceria() {
        return carroceria;
    }
}
