package utils;

import java.util.Scanner;

public class EnumUtils {

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