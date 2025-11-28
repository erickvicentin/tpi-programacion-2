package vehiculos;

import enums.Color;
import enums.TipoCarroceria;

public class Automovil extends Vehiculo {

    private TipoCarroceria carroceria;

    public Automovil(String marca, String modelo, int anioFabricacion, boolean usado, Color color, TipoCarroceria carroceria) {
        super(marca, modelo, anioFabricacion, usado, color);
        this.carroceria = carroceria;
    }

    public TipoCarroceria getCarroceria() { return carroceria; }
    public void setCarroceria(TipoCarroceria c) { this.carroceria = c; }
}
