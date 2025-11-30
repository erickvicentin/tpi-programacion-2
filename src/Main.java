import java.io.File;
import java.io.IOException;
import java.util.*;

import utils.EnumUtils;
import vehiculos.*;
import excepciones.*;
import enums.*;
import servicios.*;
import persistencia.*;

public class Main {

    private static Scanner sc = new Scanner(System.in);
    private static Queue<Vehiculo> colaTaller = new LinkedList<>();
    private static Concesionaria concesionaria = new Concesionaria(colaTaller);
    private static Lavadero lavadero = new Lavadero();
    private static Taller taller = new Taller(colaTaller, lavadero);

    public static void main(String[] args) {

        int opcion;
        do {
            mostrarMenuPrincipal();
            opcion = leerEntero("Opción: ");

            switch (opcion) {
                case 1 -> agregarVehiculo();
                case 2 -> listarVehiculos();
                case 3 -> buscarVehiculo();
                case 4 -> eliminarVehiculo();
                case 5 -> procesarTaller();
                case 6 -> guardarInventario();
                case 7 -> cargarInventario();
                case 0 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción inválida.");
            }

        } while (opcion != 0);
    }

    // ============================================================
    // MENÚS
    // ============================================================

    private static void mostrarMenuPrincipal() {
        System.out.println("\n========= CONCESIONARIA =========");
        System.out.println("1. Agregar vehiculo");
        System.out.println("2. Listar vehiculos");
        System.out.println("3. Buscar vehiculo");
        System.out.println("4. Eliminar vehiculo");
        System.out.println("5. Procesar vehiculo en taller");
        System.out.println("6. Guardar inventario");
        System.out.println("7. Cargar inventario");
        System.out.println("0. Salir");
    }

    private static void mostrarMenuTipos() {
        System.out.println("\n--- TIPOS DE VEHICULOS ---");
        System.out.println("1. Automovil");
        System.out.println("2. Camioneta");
        System.out.println("3. Motocicleta");
    }

    // ============================================================
    // OPCIONES DEL MENU
    // ============================================================

    private static void agregarVehiculo() {
        mostrarMenuTipos();
        int tipo = leerEntero("Seleccione el tipo: ");
        String marca = leerString("Marca: ");
        String modelo = leerString("Modelo: ");
        int anio = leerEntero("anio: ");
        boolean usado = leerBoolean("¿Es usado? (1=Si, 0=No): ");

        System.out.println("Colores disponibles: " + EnumUtils.generarStringDeEnumGenerico(Color.class));
        Color color = Color.values()[EnumUtils.leerEnum("Seleccione el color: ", Color.class)];


        Vehiculo v = null;

        switch (tipo) {
            case 1 -> {
                System.out.println("Carrocerias: " + EnumUtils.generarStringDeEnumGenerico(TipoCarroceria.class));
                TipoCarroceria carroceria = TipoCarroceria.values()[EnumUtils.leerEnum("Seleccione carroceria: ", TipoCarroceria.class)];
                v = new Automovil(marca, modelo, anio, usado, color, carroceria);
            }
            case 2 -> {
                System.out.println("Carrocerias: " + EnumUtils.generarStringDeEnumGenerico(TipoCarroceria.class));
                TipoCarroceria carroceria = TipoCarroceria.values()[EnumUtils.leerEnum("Seleccione carroceria: ", TipoCarroceria.class)];
                int capacidadDeCarga = leerEntero("Capacidad de carga: ");
                v = new Camioneta(marca, modelo, anio, usado, color, carroceria, capacidadDeCarga);
            }
            case 3 -> {
                System.out.println("Tipos de motocicleta: " + Arrays.toString(TipoMotocicleta.values()));
                TipoMotocicleta tipoMoto = TipoMotocicleta.valueOf(leerString("Tipo: ").toUpperCase());
                int cilindrada = leerEntero("Cantidad de cilindradas: ");
                v = new Motocicleta(marca, modelo, anio, usado, color, tipoMoto, cilindrada);
            }
            default -> System.out.println("Tipo invalido.");
        }

        if (v != null) {
            concesionaria.agregarVehiculo(v);
            System.out.println("Vehiculo agregado correctamente.");
        }
    }

    private static void listarVehiculos() {
        System.out.println("\n--- LISTADO DE VEHICULOS ---");
        concesionaria.listar().forEach(System.out::println);
    }

    private static void buscarVehiculo() {
        String modelo = leerString("Modelo a buscar: ");
        try {
            Vehiculo v = concesionaria.buscar(modelo);
            System.out.println("Encontrado: " + v);
        } catch (VehiculoNoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void eliminarVehiculo() {
        String modelo = leerString("Modelo a eliminar: ");
        try {
            concesionaria.eliminar(modelo);
            System.out.println("Vehiculo eliminado.");
        } catch (VehiculoNoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void procesarTaller() {
        try {
            taller.procesar();
        } catch (ColaVaciaException e) {
            System.out.println("No hay vehiculos usados para procesar.");
        }
    }

    private static void guardarInventario() {
        try {
            ArchivoUtil.guardar(concesionaria.listar(), "vehiculos.dat");
            System.out.println("Inventario guardado exitosamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar archivo: " + e.getMessage());
        }
    }


    private static void cargarInventario() {
        File f = new File("vehiculos.dat");

        if (!f.exists()) {
            System.out.println("Todavia no existe un archivo de inventario para cargar.");
            return;
        }

        try {
            var lista = ArchivoUtil.leer("vehiculos.dat");
            lista.forEach((vehiculo) -> {
                System.out.println(vehiculo);
                concesionaria.agregarVehiculo(vehiculo);
            });
            System.out.println("Inventario cargado correctamente.");
        } catch (Exception e) {
            System.out.println("Error al cargar el inventario: " + e.getMessage());
        }
    }


    // ============================================================
    // UTILIDADES
    // ============================================================


    private static int leerEntero(String msg) {
        while (true) {
            System.out.print(msg);
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero valido.");
            }
        }
    }

    private static String leerString(String msg) {
        System.out.print(msg);
        return sc.nextLine();
    }

    private static boolean leerBoolean(String msg) {
        while (true) {
            System.out.print(msg);
            String input = sc.nextLine().trim();
            if (input.equals("1")) return true;
            if (input.equals("0")) return false;
            System.out.println("Ingrese una opcion valida.");
        }
    }
}
