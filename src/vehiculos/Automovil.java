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

    @Override
    public double calcularPrecioDeVenta() {
        int anioActual = java.time.LocalDate.now().getYear();
        int antiguedad = anioActual - this.getAnioFabricacion();
        if (!this.isUsado()) {
            return 20000;
        }

        double base =  15000;
        // Descuento por antigüedad
        double precio = base - (antiguedad * 500);
        // Precio mínimo
        return Math.max(precio, 7500);
    }
}
