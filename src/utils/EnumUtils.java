package utils;

import java.util.Scanner;
import java.lang.Enum;

public class EnumUtils {

    // --- 1. Generación de String (Método anterior, solo para visualización) ---

    /**
     * Genera un String formateado [1-VALOR, 2-VALOR, ...] para cualquier Enum.
     * @param enumClass La clase del Enum a procesar.
     * @param <T> El tipo del Enum (debe extender Enum).
     * @return Un String con el formato deseado.
     */
    public static <T extends Enum<T>> String generarStringDeEnumGenerico(Class<T> enumClass) {
        T[] valores = enumClass.getEnumConstants();

        if (valores == null || valores.length == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < valores.length; i++) {
            // El número es la posición (ordinal()) + 1
            int numero = valores[i].ordinal() + 1;
            String nombre = valores[i].name();

            sb.append("[").append(numero).append("-").append(nombre).append("]");

            if (i < valores.length - 1) {
                sb.append(", ");
            }
        }

        return sb.toString();
    }

    // --------------------------------------------------------------------------

    // --- 2. Lectura y Validación (El método que pide la opción) ---

    /**
     * Muestra las opciones de un Enum, pide una entrada al usuario y valida el rango.
     *
     * @param msg El mensaje de instrucción a mostrar.
     * @param enumClass La clase del Enum a procesar (ej: Colores.class).
     * @param <T> El tipo del Enum (debe extender Enum).
     * @return El número de la opción seleccionada (base 1).
     */
    public static <T extends Enum<T>> int leerEnum(String msg, Class<T> enumClass) {

        T[] valores = enumClass.getEnumConstants();
        int maxOpciones = valores.length - 1;

        if (maxOpciones == 0) {
            System.err.println("Error: El Enum " + enumClass.getSimpleName() + " no tiene opciones.");
            return -1;
        }

        // Podríamos usar el primer método para generar el string, pero aquí lo mostramos línea por línea
        System.out.println("\n--- " + msg + " ---");
        for (int i = 0; i < maxOpciones; i++) {
            System.out.println((i) + ". " + valores[i].name());
        }
        System.out.print("Ingrese su opción (1 a " + maxOpciones + "): ");

        // Lógica de Lectura y Validación (usando Scanner para I/O)
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                if (scanner.hasNextInt()) {
                    int opcionElegida = scanner.nextInt();

                    if (opcionElegida >= 1 && opcionElegida <= maxOpciones) {
                        return opcionElegida;
                    } else {
                        System.out.println("Error: Opción fuera de rango. Ingrese un número entre 1 y " + maxOpciones + ".");
                    }
                } else {
                    scanner.next(); // Consume la entrada no válida
                    System.out.println("Error: Ingrese un valor numérico entero. Intente de nuevo.");
                }

                System.out.print("Ingrese su opción (1 a " + maxOpciones + "): ");
            }
        } catch (Exception e) {
            System.err.println("Ocurrió un error de lectura: " + e.getMessage());
            return -1;
        }
    }
}