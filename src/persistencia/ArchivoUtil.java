package persistencia;

import vehiculos.Vehiculo;

import java.io.*;
import java.util.List;

public class ArchivoUtil {

    public static void guardar(List<Vehiculo> lista, String archivo) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo));
        oos.writeObject(lista);
        oos.close();
    }

    public static List<Vehiculo> leer(String archivo) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo));
        List<Vehiculo> lista = (List<Vehiculo>) ois.readObject();
        ois.close();
        return lista;
    }
}
