package servicios;

import excepciones.ColaVaciaException;
import vehiculos.Vehiculo;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Taller {

    private final Queue<Vehiculo> cola;
    private final Lavadero lavadero;

    public Taller(Queue<Vehiculo> cola, Lavadero lavadero) {
        this.cola = cola;
        this.lavadero = lavadero;
    }

    public void procesarPorIndice(int index) throws ColaVaciaException {
        if (cola.isEmpty())
            throw new ColaVaciaException("No hay vehículos en cola.");

        if (index < 0 || index >= cola.size())
            throw new ColaVaciaException("Indice fuera de rango.");

        // convertimos la cola a lista para poder acceder por indices (Queue no soporta esto)
        List<Vehiculo> lista = new ArrayList<>(cola);

        Vehiculo seleccionado = lista.get(index);

        // eliminamos el vehículo elegido de la cola real
        cola.remove(seleccionado);

        lavadero.lavarVehiculo(seleccionado);
        seleccionado.setMantenimientoRealizado(true);
    }

}
