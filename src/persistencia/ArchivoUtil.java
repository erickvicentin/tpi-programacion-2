package persistencia;

import servicios.Wrapper;
import vehiculos.Vehiculo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArchivoUtil {

    // ============================================================
    // GUARDAR INVENTARIO
    // ============================================================
    public static void guardar(List<Vehiculo> lista, String archivo) throws IOException {

        // Convertimos la lista en Wrapper
        Wrapper<Vehiculo> wrapper = new Wrapper<>();
        lista.forEach(wrapper::agregar);

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(archivo))) {

            oos.writeObject(wrapper);
        }
    }

    // ============================================================
    // LEER INVENTARIO
    // ============================================================
    @SuppressWarnings("unchecked")
    public static List<Vehiculo> leer(String archivo)
            throws IOException, ClassNotFoundException {

        File file = new File(archivo);

        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(file))) {

            Object obj = ois.readObject();

            if (obj instanceof Wrapper<?> wrapperObj) {
                // Convertimos wrapper -> lista
                return ((Wrapper<Vehiculo>) wrapperObj).getLista();
            } else {
                throw new IOException("El archivo no contiene datos válidos.");
            }

        } catch (EOFException e) {
            return new ArrayList<>();
        }
    }
}
