package vehiculos;

import enums.Color;
import enums.TipoCarroceria;
import excepciones.DatoInvalidoException;

import java.io.Serializable;

public class Camioneta extends Vehiculo implements Serializable {

    private TipoCarroceria carroceria;
    private int capacidadDeCarga;

    public Camioneta(
            String marca,
            String modelo,
            int anioFabricacion,
            boolean usado,
            Color color,
            TipoCarroceria carroceria,
            int capacidadDeCarga) {
        super(marca, modelo, anioFabricacion, usado, color);
        this.carroceria = carroceria;
        this.setCapacidadDeCarga(capacidadDeCarga);
    }

    public void setCarroceria(TipoCarroceria c) {
        this.carroceria = c;
    }

    public TipoCarroceria getCarroceria() {
        return carroceria;
    }

    public double getCapacidadDeCarga() {
        return capacidadDeCarga;
    }

    public void setCapacidadDeCarga(int capacidadDeCarga) {
        try {
            if (capacidadDeCarga <= 0) {
                throw new DatoInvalidoException("La capacidad de carga debe ser mayor a 0.");
            } else {
                this.capacidadDeCarga = capacidadDeCarga;
            }
        } catch (DatoInvalidoException e) {
            e.printStackTrace();
        }
    }
}
