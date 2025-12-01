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

    // ============================================================
    // AGREGAR VEHÍCULO (evita duplicados por UUID)
    // ============================================================

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

    // ============================================================
    // BÚSQUEDA POR MODELO
    // ============================================================

    public Vehiculo buscar(String modelo) throws VehiculoNoEncontradoException {
        return inventario.stream()
                .filter(v -> v.getModelo().equalsIgnoreCase(modelo))
                .findFirst()
                .orElseThrow(() -> new VehiculoNoEncontradoException("Vehículo no encontrado."));
    }

    // ============================================================
    // BUSQUEDA MÚLTIPLE: marca / modelo / año
    // ============================================================

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


    // ============================================================
    // ELIMINAR POR ID
    // ============================================================

    public void eliminarPorId(UUID id) throws VehiculoNoEncontradoException {
        boolean eliminado = inventario.removeIf(v -> v.getIdVehiculo().equals(id));

        if (!eliminado) {
            throw new VehiculoNoEncontradoException("No se encontró un vehículo con ese ID.");
        }
    }

    // ============================================================
    // LISTAR
    // ============================================================

    public List<Vehiculo> listar() {
        return new ArrayList<>(inventario);
    }
}
