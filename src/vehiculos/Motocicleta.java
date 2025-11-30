package vehiculos;

import enums.Color;
import enums.TipoMotocicleta;

public class Motocicleta extends Vehiculo {

    private TipoMotocicleta tipo;

    public Motocicleta(String marca, String modelo, int anioFabricacion, boolean usado, Color color, TipoMotocicleta tipo) {
        super(marca, modelo, anioFabricacion, usado, color);
        this.tipo = tipo;
    }

    public void setTipo(TipoMotocicleta t) {
        this.tipo = t;
    }

    public TipoMotocicleta getTipo() {
        return tipo;
    }
}
