import excepciones.VehiculoNoEncontradoException;
import vehiculos.Vehiculo;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.UUID;

public class Concesionaria {

    private final List<Vehiculo> inventario = new ArrayList<>();
    private final Queue<Vehiculo> colaTaller;

    public Concesionaria(Queue<Vehiculo> colaTaller) {
        this.colaTaller = colaTaller;
    }

    public void agregarVehiculo(Vehiculo v) {
        if (!inventario.contains(v)) {
            inventario.add(v);
            if (v.isUsado()) colaTaller.add(v);
        }
    }

    // Para carga inicial desde archivo
    public void agregarSiNoExiste(Vehiculo v) {
        if (!inventario.contains(v)) {
            inventario.add(v);
            if (v.isUsado()) colaTaller.add(v);
        }
    }

    public List<Vehiculo> buscarMultiples(String marca, String modelo, Integer anio, Boolean usado) {

        return inventario.stream()
                .filter(v -> marca == null
                        || v.getMarca().equalsIgnoreCase(marca)
                        || v.getMarca().toLowerCase().contains(marca.toLowerCase()))
                .filter(v -> modelo == null
                        || v.getModelo().equalsIgnoreCase(modelo)
                        || v.getModelo().toLowerCase().contains(modelo.toLowerCase()))
                .filter(v -> anio == null || v.getAnioFabricacion() == anio)
                .filter(v -> usado == null || v.isUsado() == usado)
                .toList();
    }

    public void eliminarPorId(UUID id) throws VehiculoNoEncontradoException {
        boolean eliminado = inventario.removeIf(v -> v.getIdVehiculo().equals(id));

        if (!eliminado) {
            throw new VehiculoNoEncontradoException("No se encontró un vehículo con ese ID.");
        }
    }

    public List<Vehiculo> listar() {
        return new ArrayList<>(inventario);
    }
}
