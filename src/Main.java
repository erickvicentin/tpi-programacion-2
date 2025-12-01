import enums.Color;
import enums.TipoCarroceria;
import enums.TipoMotocicleta;
import excepciones.ColaVaciaException;
import persistencia.ArchivoUtil;
import servicios.Lavadero;
import servicios.Taller;
import utils.EnumUtils;
import vehiculos.Automovil;
import vehiculos.Camioneta;
import vehiculos.Motocicleta;
import vehiculos.Vehiculo;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final Queue<Vehiculo> colaTaller = new LinkedList<>();
    private static final Concesionaria concesionaria = new Concesionaria(colaTaller);
    private static final Lavadero lavadero = new Lavadero();
    private static final Taller taller = new Taller(colaTaller, lavadero);

    public static void main(String[] args) {

        cargarInventarioInicial();   //🔥 CARGA AUTOMÁTICA

        int opcion;
        do {
            mostrarMenuPrincipal();
            opcion = leerEntero("Opción: ");

            switch (opcion) {
                case 1 -> agregarVehiculo();
                case 2 -> listarVehiculos();
                case 3 -> buscarVehiculo();
                case 4 -> eliminarVehiculoAvanzado(); //🔥 Eliminación avanzada por ID
                case 5 -> procesarTaller();
                case 0 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción inválida.");
            }

        } while (opcion != 0);

        guardarInventarioAutomatico(); //🔥 GUARDADO AUTOMÁTICO
    }

    // ============================================================
    // MENÚS
    // ============================================================

    private static void mostrarMenuPrincipal() {
        System.out.println("\n========= CONCESIONARIA =========");
        System.out.println("1. Agregar vehículo");
        System.out.println("2. Listar vehículos");
        System.out.println("3. Buscar vehículo");
        System.out.println("4. Eliminar vehículo");
        System.out.println("5. Procesar vehículo en taller");
        System.out.println("0. Salir");
    }

    private static void mostrarMenuTipos() {
        System.out.println("\n--- TIPOS DE VEHÍCULO ---");
        System.out.println("1. Automóvil");
        System.out.println("2. Camioneta");
        System.out.println("3. Motocicleta");
    }

    // ============================================================
    // CARGA Y GUARDADO AUTOMÁTICO
    // ============================================================

    private static void cargarInventarioInicial() {
        File f = new File("vehiculos.dat");

        if (!f.exists()) {
            System.out.println("No existe inventario previo. Arrancando vacío.");
            return;
        }

        try {
            List<Vehiculo> lista = ArchivoUtil.leer("vehiculos.dat");
            lista.forEach(concesionaria::agregarSiNoExiste); // evita duplicados
            System.out.println("Inventario cargado automáticamente.");
        } catch (Exception e) {
            System.out.println("Error cargando inventario: " + e.getMessage());
        }
    }

    private static void guardarInventarioAutomatico() {
        try {
            ArchivoUtil.guardar(concesionaria.listar(), "vehiculos.dat");
            System.out.println("Inventario guardado automáticamente.");
        } catch (Exception e) {
            System.out.println("Error guardando inventario: " + e.getMessage());
        }
    }

    // ============================================================
    // AGREGAR
    // ============================================================

    private static void agregarVehiculo() {
        mostrarMenuTipos();
        int tipo = leerEntero("Seleccione el tipo: ");

        String marca = leerString("Marca: ");
        String modelo = leerString("Modelo: ");
        int anio = leerEntero("Año: ");
        boolean usado = leerBoolean("¿Es usado? (1=Sí, 0=No): ");

        System.out.println("Colores disponibles: " + EnumUtils.generarStringDeEnumGenerico(Color.class));
        Color color = Color.values()[EnumUtils.leerEnum("Seleccione el color: ", Color.class)];

        Vehiculo v = null;

        switch (tipo) {
            case 1 -> {
                System.out.println("Carrocerías: " + EnumUtils.generarStringDeEnumGenerico(TipoCarroceria.class));
                TipoCarroceria carroceria =
                        TipoCarroceria.values()[EnumUtils.leerEnum("Seleccione carrocería: ", TipoCarroceria.class)];
                v = new Automovil(marca, modelo, anio, usado, color, carroceria);
            }
            case 2 -> {
                System.out.println("Carrocerías: " + EnumUtils.generarStringDeEnumGenerico(TipoCarroceria.class));
                TipoCarroceria carroceria =
                        TipoCarroceria.values()[EnumUtils.leerEnum("Seleccione carrocería: ", TipoCarroceria.class)];

                int carga = leerEntero("Capacidad de carga (kg): ");
                v = new Camioneta(marca, modelo, anio, usado, color, carroceria, carga);
            }
            case 3 -> {
                System.out.println("Tipos de moto: " + EnumUtils.generarStringDeEnumGenerico(TipoMotocicleta.class));
                TipoMotocicleta tipoMoto =
                        TipoMotocicleta.values()[EnumUtils.leerEnum("Tipo de moto: ", TipoMotocicleta.class)];
                int cilindrada = leerEntero("Cilindrada: ");
                v = new Motocicleta(marca, modelo, anio, usado, color, tipoMoto, cilindrada);
            }
            default -> System.out.println("Tipo inválido.");
        }

        if (v != null) {
            concesionaria.agregarVehiculo(v);
            guardarInventarioAutomatico();
            System.out.println("Vehículo agregado correctamente.");
        }
    }

    // ============================================================
    // LISTAR / BUSCAR
    // ============================================================

    private static void listarVehiculos() {
        List<Vehiculo> lista = concesionaria.listar();

        if (lista.isEmpty()) {
            System.out.println("No hay vehículos cargados.");
            return;
        }

        System.out.println("\n--- LISTADO DE VEHÍCULOS ---");
        lista.forEach(System.out::println);
    }

    private static void buscarVehiculo() {

        System.out.println("\n--- BUSCAR VEHÍCULO ---");
        System.out.println("1. Por marca");
        System.out.println("2. Por modelo");
        System.out.println("3. Por año");
        System.out.println("4. Combinado (marca + modelo)");
        System.out.println("5. Combinado (marca + año)");
        System.out.println("6. Combinado (modelo + año)");
        System.out.println("7. Por estado (nuevo/usado)");

        int op = leerEntero("Opción: ");

        String marca = null;
        String modelo = null;
        Integer anio = null;
        Boolean usado = null;

        switch (op) {
            case 1 -> marca = leerString("Marca: ");
            case 2 -> modelo = leerString("Modelo: ");
            case 3 -> anio = leerEntero("Año: ");
            case 4 -> {
                marca = leerString("Marca: ");
                modelo = leerString("Modelo: ");
            }
            case 5 -> {
                marca = leerString("Marca: ");
                anio = leerEntero("Año: ");
            }
            case 6 -> {
                modelo = leerString("Modelo: ");
                anio = leerEntero("Año: ");
            }
            case 7 -> {
                System.out.println("1. Usado");
                System.out.println("0. Nuevo");
                usado = leerBoolean("Seleccione estado (1=Usado, 0=Nuevo): ");
            }
            default -> {
                System.out.println("Opción inválida.");
                return;
            }
        }

        List<Vehiculo> resultados =
                concesionaria.buscarMultiples(marca, modelo, anio, usado);

        if (resultados.isEmpty()) {
            System.out.println("\nNo se encontraron vehículos con esos criterios.");
            return;
        }

        System.out.println("\n--- RESULTADOS ---");
        for (int i = 0; i < resultados.size(); i++) {
            System.out.println((i + 1) + ") " + resultados.get(i));
        }

        if (resultados.size() == 1) {
            System.out.println("\nSe encontró 1 coincidencia.");
            return;
        }

        int seleccion = leerEntero("Seleccione un número para ver detalles (0 para salir): ");

        if (seleccion == 0) return;
        if (seleccion < 1 || seleccion > resultados.size()) {
            System.out.println("Opción inválida.");
            return;
        }

        Vehiculo elegido = resultados.get(seleccion - 1);

        System.out.println("\n--- DETALLES DEL VEHÍCULO ---");
        System.out.println(elegido);
    }

    // ============================================================
    // ELIMINACIÓN AVANZADA POR ID Y BÚSQUEDA MULTIPLE
    // ============================================================

    private static void eliminarVehiculoAvanzado() {

        System.out.println("\n--- ELIMINAR VEHÍCULO ---");
        System.out.println("1. Por marca");
        System.out.println("2. Por modelo");
        System.out.println("3. Por año");

        int op = leerEntero("Opción: ");

        String marca = null;
        String modelo = null;
        Integer anio = null;

        switch (op) {
            case 1 -> marca = leerString("Marca: ");
            case 2 -> modelo = leerString("Modelo: ");
            case 3 -> anio = leerEntero("Año: ");
            default -> {
                System.out.println("Opción inválida.");
                return;
            }
        }

        List<Vehiculo> resultados = concesionaria.buscarMultiples(marca, modelo, anio, null);

        if (resultados.isEmpty()) {
            System.out.println("No hubo coincidencias.");
            return;
        }

        System.out.println("\nCoincidencias encontradas:");
        for (int i = 0; i < resultados.size(); i++) {
            Vehiculo v = resultados.get(i);
            System.out.println((i + 1) + ") " + v);
        }

        int seleccion = leerEntero("Seleccione el número del vehículo a eliminar: ") - 1;

        if (seleccion < 0 || seleccion >= resultados.size()) {
            System.out.println("Opción inválida.");
            return;
        }

        Vehiculo elegido = resultados.get(seleccion);

        try {
            concesionaria.eliminarPorId(elegido.getIdVehiculo());
            guardarInventarioAutomatico(); // se guarda automáticamente
            System.out.println("Vehículo eliminado correctamente.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    // ============================================================
    // TALLER
    // ============================================================

    private static void procesarTaller() {
        try {
            taller.procesar();
        } catch (ColaVaciaException e) {
            System.out.println("No hay vehículos usados en la cola.");
        }
    }

    // ============================================================
    // UTILIDADES
    // ============================================================

    private static int leerEntero(String msg) {
        while (true) {
            System.out.print(msg);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número válido.");
            }
        }
    }

    private static String leerString(String msg) {
        System.out.print(msg);
        return sc.nextLine().trim();
    }

    private static boolean leerBoolean(String msg) {
        while (true) {
            System.out.print(msg);
            String input = sc.nextLine().trim();
            if (input.equals("1")) return true;
            if (input.equals("0")) return false;
            System.out.println("Ingrese 1 (sí) o 0 (no).");
        }
    }
}
