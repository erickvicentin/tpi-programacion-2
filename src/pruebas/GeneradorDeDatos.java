package pruebas;

import enums.Color;
import enums.TipoCarroceriaAuto;
import enums.TipoCarroceriaCamioneta;
import enums.TipoMotocicleta;
import persistencia.IOFilesUtils;
import vehiculos.Vehiculo;
import vehiculos.Camioneta;
import vehiculos.Automovil;
import vehiculos.Motocicleta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneradorDeDatos {

    private static final Random R = new Random();
    private static final String ARCHIVO = "vehiculos.dat";

    // Arrays para datos aleatorios de marca/modelo
    private static final String[] MARCAS_AUTO = {"Ford", "Renault", "Peugeot", "VW"};
    private static final String[] MODELOS_AUTO = {"Focus", "Clio", "208", "Gol"};
    private static final String[] MARCAS_CAMIONETA = {"Toyota", "Chevrolet", "RAM"};
    private static final String[] MODELOS_CAMIONETA = {"Hilux", "S10", "1500"};
    private static final String[] MARCAS_MOTO = {"Honda", "Yamaha", "Motomel"};
    private static final String[] MODELOS_MOTO = {"Titan", "FZ", "Skua"};

    public static void main(String[] args) {
        List<Vehiculo> inventario = new ArrayList<>();

        System.out.println("Generando 15 vehículos de prueba...");

        // Generar 5 Automóviles
        for (int i = 0; i < 5; i++) {
            inventario.add(crearAutomovilRandom());
        }
        // Generar 5 Camionetas
        for (int i = 0; i < 5; i++) {
            inventario.add(crearCamionetaRandom());
        }
        // Generar 5 Motocicletas
        for (int i = 0; i < 5; i++) {
            inventario.add(crearMotocicletaRandom());
        }

        System.out.println("Inventario generado (" + inventario.size() + " vehículos).");

        // Guardar la lista de vehículos
        try {
            IOFilesUtils.guardar(inventario, ARCHIVO);
            System.out.println("Datos guardados correctamente en " + ARCHIVO);
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo: " + e.getMessage());
        }
    }

    // ============================================================
    // METODOS AUXILIARES
    // ============================================================

    private static <T extends Enum<T>> T getEnumRandom(Class<T> enumClass) {
        T[] values = enumClass.getEnumConstants();
        return values[R.nextInt(values.length)];
    }

    private static Automovil crearAutomovilRandom() {
        String marca = MARCAS_AUTO[R.nextInt(MARCAS_AUTO.length)];
        String modelo = MODELOS_AUTO[R.nextInt(MODELOS_AUTO.length)];
        int anio = R.nextInt(26) + 2000; // 2000 a 2025
        boolean usado = R.nextBoolean();
        Color color = getEnumRandom(Color.class);
        TipoCarroceriaAuto carroceria = getEnumRandom(TipoCarroceriaAuto.class);

        return new Automovil(marca, modelo, anio, usado, color, carroceria);
    }

    private static Camioneta crearCamionetaRandom() {
        String marca = MARCAS_CAMIONETA[R.nextInt(MARCAS_CAMIONETA.length)];
        String modelo = MODELOS_CAMIONETA[R.nextInt(MODELOS_CAMIONETA.length)];
        int anio = R.nextInt(15) + 2010; // 2010 a 2025
        boolean usado = R.nextBoolean();
        Color color = getEnumRandom(Color.class);
        TipoCarroceriaCamioneta carroceria = getEnumRandom(TipoCarroceriaCamioneta.class);
        int carga = R.nextInt(2500) + 500; // 500 a 3000 kg

        return new Camioneta(marca, modelo, anio, usado, color, carroceria, carga);
    }

    private static Motocicleta crearMotocicletaRandom() {
        String marca = MARCAS_MOTO[R.nextInt(MARCAS_MOTO.length)];
        String modelo = MODELOS_MOTO[R.nextInt(MODELOS_MOTO.length)];
        int anio = R.nextInt(10) + 2015; // 2015 a 2025
        boolean usado = R.nextBoolean();
        Color color = getEnumRandom(Color.class);
        TipoMotocicleta tipo = getEnumRandom(TipoMotocicleta.class);
        int cilindrada = R.nextInt(875) + 125; // 125 a 1000 cc

        return new Motocicleta(marca, modelo, anio, usado, color, tipo, cilindrada);
    }
}