package servicios;

import vehiculos.Vehiculo;

public class Lavadero {

    public void lavarVehiculo(Vehiculo v) {
        try {
            v.lavar();
            System.out.println("Lavado completo.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
