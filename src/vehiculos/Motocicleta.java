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
            if (cilindrada <= 49 || cilindrada > 2000) {
                throw new DatoInvalidoException("El motor no puede tener una cilindrada menor a 50 o mayor a 2000");
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

    @Override
    public double calcularPrecioDeVenta() {
        int anioActual = java.time.LocalDate.now().getYear();
        int antiguedad = anioActual - this.getAnioFabricacion();
        // Extra por cilindrada
        double extraCilindrada = ((double) this.getCilindrada() / 50) * 500;
        if(!this.isUsado()) {
            return extraCilindrada + 7000;
        }

        double base = 5000;
        // Descuento por antigüedad
        double precio = base + extraCilindrada - (antiguedad * 200);
        // Precio mínimo
        return Math.max(precio, 3000);
    }

}
