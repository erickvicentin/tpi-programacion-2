package utils;

import vehiculos.*;
import enums.*;

public class PrintUtils {

    public static void titulo(String texto) {
        System.out.println("\n========= " + texto.toUpperCase() + " =========");
    }

    public static void subtitulo(String texto) {
        System.out.println("\n--- " + texto + " ---");
    }

    public static void linea() {
        System.out.println("----------------------------------------");
    }

    public static void error(String msg) {
        System.out.println("❌ " + msg);
    }

    public static void ok(String msg) {
        System.out.println("✔ " + msg);
    }

    public static <T> void listaNumerada(java.util.List<T> elementos) {
        for (int i = 0; i < elementos.size(); i++) {
            System.out.println((i + 1) + ") " + elementos.get(i));
        }
    }

    public static void detalleVehiculo(Vehiculo v) {

        System.out.println("\nVehículo seleccionado:");

        if (v instanceof Automovil)
            System.out.println("├─ Tipo: Automóvil");
        else if (v instanceof Camioneta)
            System.out.println("├─ Tipo: Camioneta");
        else if (v instanceof Motocicleta)
            System.out.println("├─ Tipo: Motocicleta");

        System.out.println("├─ Datos generales:");
        System.out.println("│  ├─ Marca: " + v.getMarca());
        System.out.println("│  ├─ Modelo: " + v.getModelo());
        System.out.println("│  ├─ Año: " + v.getAnioFabricacion());
        System.out.println("│  ├─ Color: " + v.getColor());
        System.out.println("│  ├─ Usado: " + (v.isUsado() ? "Sí" : "No"));

        if (v instanceof Automovil a) {
            System.out.println("├─ Específicos de automóvil:");
            System.out.println("│  ├─ Carrocería: " + a.getCarroceria());
        }

        if (v instanceof Camioneta c) {
            System.out.println("├─ Específicos de camioneta:");
            System.out.println("│  ├─ Carrocería: " + c.getCarroceria());
            System.out.println("│  ├─ Capacidad de carga: " + c.getCapacidadDeCarga() + " kg");
        }

        if (v instanceof Motocicleta m) {
            System.out.println("├─ Específicos de motocicleta:");
            System.out.println("│  ├─ Tipo: " + m.getTipo());
            System.out.println("│  ├─ Cilindrada: " + m.getCilindrada() + " cc");
        }
    }
}
