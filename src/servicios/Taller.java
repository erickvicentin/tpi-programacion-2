package servicios;

import excepciones.ColaVaciaException;
import interfaces.Mantenible;
import vehiculos.Vehiculo;

import java.util.Queue;

public class Taller {

    private final Queue<Vehiculo> cola;
    private final Lavadero lavadero;

    public Taller(Queue<Vehiculo> cola, Lavadero lavadero) {
        this.cola = cola;
        this.lavadero = lavadero;
    }

    public void procesar() throws ColaVaciaException {
        if (cola.isEmpty()) throw new ColaVaciaException("No hay vehículos en la cola.");

        Vehiculo v = cola.poll();
        if (v instanceof Mantenible m) {
            m.realizarMantenimiento();
        }
        lavadero.lavarVehiculo(v);
    }
}
