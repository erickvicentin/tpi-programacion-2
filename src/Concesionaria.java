import vehiculos.Vehiculo;
import excepciones.VehiculoNoEncontradoException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

public class Concesionaria {

    private List<Vehiculo> inventario = new ArrayList<>();
    private Queue<Vehiculo> colaTaller;

    public Concesionaria(Queue<Vehiculo> colaTaller) {
        this.colaTaller = colaTaller;
    }

    public void agregarVehiculo(Vehiculo v) {
        inventario.add(v);
        if (v.isUsado()) colaTaller.add(v);
    }

    public Vehiculo buscar(String modelo) throws VehiculoNoEncontradoException {
        Optional<Vehiculo> v = inventario.stream()
                .filter(x -> x.getModelo().equalsIgnoreCase(modelo))
                .findFirst();

        if (v.isEmpty()) throw new VehiculoNoEncontradoException("Vehículo no encontrado");
        return v.get();
    }

    public void eliminar(String modelo) throws VehiculoNoEncontradoException {
        Vehiculo v = buscar(modelo);
        inventario.remove(v);
    }

    public List<Vehiculo> listar() {
        return inventario;
    }
}
