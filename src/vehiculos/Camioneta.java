package vehiculos;

import enums.Color;
import enums.TipoCarroceriaCamioneta;
import excepciones.DatoInvalidoException;

import java.io.Serializable;

public class Camioneta extends Vehiculo implements Serializable {

    private TipoCarroceriaCamioneta carroceria;
    private int capacidadDeCarga;

    public Camioneta(String marca, String modelo, int anioFabricacion, boolean usado, Color color, TipoCarroceriaCamioneta carroceria, int capacidadDeCarga) {
        super(marca, modelo, anioFabricacion, usado, color);
        this.carroceria = carroceria;
        this.setCapacidadDeCarga(capacidadDeCarga);
    }

    public void setCarroceria(TipoCarroceriaCamioneta c) {
        this.carroceria = c;
    }

    public TipoCarroceriaCamioneta getCarroceria() {
        return carroceria;
    }

    public double getCapacidadDeCarga() {
        return capacidadDeCarga;
    }

    public void setCapacidadDeCarga(int capacidadDeCarga) {
        try {
            if (capacidadDeCarga <= 0 || capacidadDeCarga > 5000) {
                throw new DatoInvalidoException("La capacidad de carga debe ser mayor a 0 y menor o igual a 5000.");
            } else {
                this.capacidadDeCarga = capacidadDeCarga;
            }
        } catch (DatoInvalidoException e) {
            e.printStackTrace();
        }
    }
}
