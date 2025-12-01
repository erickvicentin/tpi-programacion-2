package utils;

import java.util.Scanner;

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
            int numero = valores[i].ordinal();
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

        Scanner sc = new Scanner(System.in);
        T[] valores = enumClass.getEnumConstants();
        int max = valores.length - 1;

        while (true) {
            System.out.print(msg + " (0-" + max + "): ");

            try {
                int opcion = Integer.parseInt(sc.nextLine().trim());

                if (opcion >= 0 && opcion <= max) {
                    return opcion;
                }

                System.out.println("Error: opción fuera de rango.");

            } catch (NumberFormatException e) {
                System.out.println("Error: ingrese un número válido.");
            }
        }
    }

}