package vehiculos;

import enums.TipoDeMotocicleta;

public class Motocicleta extends Vehiculo {
    private TipoDeMotocicleta tipoMotocicleta;

    public Motocicleta(String marca, String modelo, int anioFabricacion, String color, double kilometraje,  TipoDeMotocicleta tipoMotocicleta) {
        super(marca, modelo, anioFabricacion, color, kilometraje);
        this.tipoMotocicleta = tipoMotocicleta;
    }

    public TipoDeMotocicleta getTipoMotocicleta() {
        return tipoMotocicleta;
    }

    public void setTipoMotocicleta(TipoDeMotocicleta tipoMotocicleta) {
        this.tipoMotocicleta = tipoMotocicleta;
    }

    @Override
    public String toString() {
        return "motocicleta{marca: " + this.getMarca() +
                ", modelo: " + this.getModelo() +
                ", tipoMotocicleta: " + this.getTipoMotocicleta() + '}';
    }
}
