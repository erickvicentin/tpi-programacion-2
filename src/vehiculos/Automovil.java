package vehiculos;

import enums.Color;
import enums.TipoCarroceria;

import java.io.Serializable;

public class Automovil extends Vehiculo implements Serializable {

    private TipoCarroceria carroceria;

    public Automovil(String marca, String modelo, int anioFabricacion, boolean usado, Color color, TipoCarroceria carroceria) {
        super(marca, modelo, anioFabricacion, usado, color);
        this.carroceria = carroceria;
    }

    public TipoCarroceria getCarroceria() { return carroceria; }
    public void setCarroceria(TipoCarroceria c) { this.carroceria = c; }
}
