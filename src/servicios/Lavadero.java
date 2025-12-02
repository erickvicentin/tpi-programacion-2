package servicios;

import vehiculos.Vehiculo;

public class Lavadero {

    public void lavarVehiculo(Vehiculo v) {
        v.lavar();
        System.out.println("Lavado completo.");
    }
}
