package utils;

import java.time.LocalDate;
import java.util.Scanner;

public class InputUtils {

    private static final Scanner sc = new Scanner(System.in);

    public static int leerEntero(String msg) {
        while (true) {
            System.out.print(msg);
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número válido.");
            }
        }
    }

    public static String leerString(String msg) {
        System.out.print(msg);
        return sc.nextLine();
    }

    public static boolean leerBoolean(String msg) {
        while (true) {
            System.out.print(msg);
            String input = sc.nextLine().trim();
            if (input.equals("1")) return true;
            if (input.equals("0")) return false;
            System.out.println("Ingrese una opción válida (1 o 0).");
        }
    }

    public static int leerAnioValido(String msg) {
        LocalDate fecha = LocalDate.now();
        int anioActual = fecha.getYear();

        while (true) {
            int anio = leerEntero(msg);

            if (anio >= 1900 && anio <= anioActual) {
                return anio;
            }

            System.out.println("Ingrese un año entre 1900 y " + anioActual + ".");
        }
    }
}
