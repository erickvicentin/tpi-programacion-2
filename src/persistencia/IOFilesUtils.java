package persistencia;

import utils.Wrapper;
import vehiculos.Vehiculo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.EOFException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class IOFilesUtils {

    public static void guardar(List<Vehiculo> lista, String archivo) throws IOException {
        // Convertimos la lista en Wrapper
        Wrapper<Vehiculo> wrapper = new Wrapper<>();
        // es lo mismo que forEach( obj => wrapper.agregar(obj); )
        lista.forEach(wrapper::agregar);
        //usamos try-with-resources para cerrar OOS y FOS luego del bloque
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(wrapper);
        }
    }

    //evitamos el msg del cast forzado con /unchecked/, diciendole a java que estamos seguros del cast a Wrapper<Vehiculo>
    @SuppressWarnings("unchecked")
    public static List<Vehiculo> leer(String archivo) throws IOException, ClassNotFoundException {
        File file = new File(archivo);
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            if (obj instanceof Wrapper<?> wrapperObj) {
                // Convertimos el wrapper a lista
                return ((Wrapper<Vehiculo>) wrapperObj).getLista();
            } else {
                throw new IOException("El archivo no contiene datos válidos.");
            }
        } catch (EOFException e) {
            return new ArrayList<>();
        }
    }
}
