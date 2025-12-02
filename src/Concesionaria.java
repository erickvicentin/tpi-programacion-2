import excepciones.VehiculoNoEncontradoException;
import vehiculos.Vehiculo;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.UUID;
import java.util.stream.Collectors;

public class Concesionaria {

    private final List<Vehiculo> inventario = new ArrayList<>();
    private final Queue<Vehiculo> colaTaller;

    public Concesionaria(Queue<Vehiculo> colaTaller) {
        this.colaTaller = colaTaller;
    }

    // ============================================================
    // AGREGAR VEHÍCULOS
    // ============================================================

    public void agregarVehiculo(Vehiculo v) {
        agregarSiNoExiste(v);
    }

    /**
     * Agrega el vehículo al inventario solo si no existe uno con el mismo UUID.
     * También lo agrega a la cola del taller si está usado.
     */
    public void agregarSiNoExiste(Vehiculo v) {
        boolean existe = inventario.stream()
                .anyMatch(x -> x.getIdVehiculo().equals(v.getIdVehiculo()));

        if (!existe) {
            inventario.add(v);
            if (v.isUsado()) colaTaller.add(v);
        }
    }

    // ============================================================
    // BÚSQUEDA AVANZADA
    // ============================================================

    /**
     * Búsqueda flexible combinando marca, modelo, año y estado.
     * Acepta coincidencias parciales (contains) para marca y modelo.
     */
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
                .collect(Collectors.toList());
    }

    // ============================================================
    // ELIMINACIÓN
    // ============================================================

    /**
     * Elimina el vehículo que tenga el UUID especificado.
     */
    public void eliminarPorId(UUID id) throws VehiculoNoEncontradoException {

        Vehiculo encontrado = inventario.stream()
                .filter(v -> v.getIdVehiculo().equals(id))
                .findFirst()
                .orElse(null);

        if (encontrado == null) {
            throw new VehiculoNoEncontradoException("No se encontró un vehículo con ese ID.");
        }

        inventario.remove(encontrado);
        colaTaller.remove(encontrado); // en caso de que estuviera en la cola
    }

    // ============================================================
    // LISTADO
    // ============================================================
    public List<Vehiculo> listar() {
        return new ArrayList<>(inventario);
    }
}
