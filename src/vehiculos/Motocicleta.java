package vehiculos;

import enums.Color;
import enums.TipoMotocicleta;
import excepciones.DatoInvalidoException;

import java.io.Serializable;

public class Motocicleta extends Vehiculo implements Serializable {

    private TipoMotocicleta tipo;
    private int cilindrada;

    public Motocicleta(String marca, String modelo, int anioFabricacion, boolean usado, Color color, TipoMotocicleta tipo, int cilindrada) {
        super(marca, modelo, anioFabricacion, usado, color);
        this.tipo = tipo;
        this.setCilindrada(cilindrada);
    }

    public void setTipo(TipoMotocicleta t) {
        this.tipo = t;
    }

    public TipoMotocicleta getTipo() {
        return tipo;
    }

    public void setCilindrada(int cilindrada) {
        try {
            if (cilindrada <= 0) {
                throw new DatoInvalidoException("El motor no puede tener una cilindrada menor o igual a 0");
            } else {
                this.cilindrada = cilindrada;
            }
        } catch (DatoInvalidoException e) {
            e.printStackTrace();
        }
    }

    public int getCilindrada() {
        return cilindrada;
    }
}
