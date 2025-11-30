package vehiculos;

import enums.Color;
import enums.TipoCarroceria;
import excepciones.CapacidadDeCargaNoValidaException;

public class Camioneta extends Vehiculo {

    private TipoCarroceria carroceria;
    private double capacidadDeCarga;

    public Camioneta(
            String marca,
            String modelo,
            int anioFabricacion,
            boolean usado,
            Color color,
            TipoCarroceria carroceria,
            double capacidadDeCarga) {
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

    public void setCapacidadDeCarga(double capacidadDeCarga) {
        try {
            if (capacidadDeCarga <= 0) {
                throw new CapacidadDeCargaNoValidaException("La capacidad de carga debe ser mayor a 0.");
            } else {
                this.capacidadDeCarga = capacidadDeCarga;
            }
        } catch (CapacidadDeCargaNoValidaException e) {
            e.printStackTrace();
        }
    }
}
