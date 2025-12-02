package vehiculos;

import enums.Color;
import enums.TipoCarroceriaAuto;

import java.io.Serializable;

public class Automovil extends Vehiculo implements Serializable {

    private TipoCarroceriaAuto carroceria;

    public Automovil(String marca, String modelo, int anioFabricacion, boolean usado, Color color, TipoCarroceriaAuto carroceria) {
        super(marca, modelo, anioFabricacion, usado, color);
        this.carroceria = carroceria;
    }

    public TipoCarroceriaAuto getCarroceria() {
        return carroceria;
    }

    public void setCarroceria(TipoCarroceriaAuto c) {
        this.carroceria = c;
    }
}
