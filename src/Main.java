import enums.Color;
import enums.TipoCarroceriaAuto;
import enums.TipoCarroceriaCamioneta;
import enums.TipoMotocicleta;
import excepciones.ColaVaciaException;
import persistencia.IOFilesUtils;
import servicios.Lavadero;
import servicios.Taller;
import utils.EnumUtils;
import utils.InputUtils;
import utils.PrintUtils;
import vehiculos.Automovil;
import vehiculos.Camioneta;
import vehiculos.Motocicleta;
import vehiculos.Vehiculo;

import java.io.File;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.ArrayList;


public class Main {

    private static final Queue<Vehiculo> colaTaller = new LinkedList<>();
    private static final Concesionaria concesionaria = new Concesionaria(colaTaller);
    private static final Lavadero lavadero = new Lavadero();
    private static final Taller taller = new Taller(colaTaller, lavadero);

    public static void main(String[] args) {
        cargarInventarioInicial();

        int opcion;
        do {
            mostrarMenuPrincipal();
            opcion = InputUtils.leerEntero("Opción: ");

            switch (opcion) {
                case 1 -> agregarVehiculo();
                case 2 -> listarVehiculos();
                case 3 -> buscarVehiculo();
                case 4 -> eliminarVehiculo();
                case 5 -> procesarTaller();
                case 6 -> modificarVehiculo();
                case 0 -> PrintUtils.ok("Saliendo del sistema...");
                default -> PrintUtils.error("Opción inválida.");
            }

        } while (opcion != 0);

        guardarInventarioAutomatico();
    }

    // ============================================================
    // MENÚS
    // ============================================================

    private static void mostrarMenuPrincipal() {
        PrintUtils.titulo("Concesionaria");
        System.out.println("1. Agregar vehículo");
        System.out.println("2. Listar vehículos");
        System.out.println("3. Buscar vehículo");
        System.out.println("4. Eliminar vehículo");
        System.out.println("5. Procesar vehículo en taller");
        System.out.println("6. Modificar vehículo");
        System.out.println("0. Salir");
    }

    private static void mostrarMenuTipos() {
        PrintUtils.subtitulo("Tipos de vehículo");
        System.out.println("1. Automóvil");
        System.out.println("2. Camioneta");
        System.out.println("3. Motocicleta");
        System.out.println("0. Salir");
    }

    private static void cargarInventarioInicial() {
        File f = new File("vehiculos.dat");

        if (!f.exists()) {
            PrintUtils.error("No existe inventario previo. Arrancando vacío.");
            return;
        }

        try {
            List<Vehiculo> lista = IOFilesUtils.leer("vehiculos.dat");
            lista.forEach(concesionaria::agregarVehiculo);
            PrintUtils.ok("Inventario cargado automáticamente.");
        } catch (Exception e) {
            PrintUtils.error("Error cargando inventario: " + e.getMessage());
        }
    }

    private static void guardarInventarioAutomatico() {
        try {
            IOFilesUtils.guardar(concesionaria.listarVehiculos(), "vehiculos.dat");
            PrintUtils.ok("Inventario guardado automáticamente.");
        } catch (Exception e) {
            PrintUtils.error("Error guardando inventario: " + e.getMessage());
        }
    }

    private static void agregarVehiculo() {
        mostrarMenuTipos();
        int tipo = InputUtils.leerEntero("Seleccione el tipo: ");
        if (tipo == 0) return;

        String marca = InputUtils.leerString("Marca: ");
        String modelo = InputUtils.leerString("Modelo: ");
        int anio = InputUtils.leerAnioValido("Año: ");
        boolean usado = InputUtils.leerBoolean("¿Es usado? (1=Sí, 0=No): ");

        PrintUtils.subtitulo("Colores disponibles");
        System.out.println(EnumUtils.generarStringDeEnumGenerico(Color.class));

        Color color = Color.values()[EnumUtils.leerEnum("Seleccione el color: ", Color.class)];

        Vehiculo v = null;

        switch (tipo) {
            case 1 -> {
                PrintUtils.subtitulo("Carrocerías de automóvil");
                System.out.println(EnumUtils.generarStringDeEnumGenerico(TipoCarroceriaAuto.class));

                TipoCarroceriaAuto car =
                        TipoCarroceriaAuto.values()[EnumUtils.leerEnum("Seleccione carrocería: ", TipoCarroceriaAuto.class)];

                v = new Automovil(marca, modelo, anio, usado, color, car);
            }
            case 2 -> {
                PrintUtils.subtitulo("Carrocerías de camioneta");
                System.out.println(EnumUtils.generarStringDeEnumGenerico(TipoCarroceriaCamioneta.class));

                TipoCarroceriaCamioneta car =
                        TipoCarroceriaCamioneta.values()[EnumUtils.leerEnum("Seleccione carrocería: ", TipoCarroceriaCamioneta.class)];

                int carga = InputUtils.leerEntero("Capacidad de carga (kg): ");
                v = new Camioneta(marca, modelo, anio, usado, color, car, carga);
            }
            case 3 -> {
                PrintUtils.subtitulo("Tipos de motocicleta");
                System.out.println(EnumUtils.generarStringDeEnumGenerico(TipoMotocicleta.class));

                TipoMotocicleta tipoMoto =
                        TipoMotocicleta.values()[EnumUtils.leerEnum("Seleccione tipo: ", TipoMotocicleta.class)];

                int cil = InputUtils.leerEntero("Cilindrada (cc): ");
                v = new Motocicleta(marca, modelo, anio, usado, color, tipoMoto, cil);
            }
            default -> {
                PrintUtils.error("Tipo de vehiculo inválido.");
                return;
            }
        }

        concesionaria.agregarVehiculo(v);
        guardarInventarioAutomatico();
        PrintUtils.ok("Vehículo agregado correctamente.");
    }

    private static void listarVehiculos() {
        List<Vehiculo> lista = concesionaria.listarVehiculos();

        if (lista.isEmpty()) {
            PrintUtils.error("No hay vehículos cargados.");
            return;
        }

        PrintUtils.subtitulo("Listado de vehículos");
        lista.forEach(System.out::println);
    }

    private static void buscarVehiculo() {
        PrintUtils.subtitulo("Buscar vehículo");
        PrintUtils.listarOpcionesDeBusqueda();

        int op = InputUtils.leerEntero("Opción: ");

        String marca = null, modelo = null;
        Integer anio = null;
        Boolean usado = null;

        switch (op) {
            case 1 -> marca = InputUtils.leerString("Marca: ");
            case 2 -> modelo = InputUtils.leerString("Modelo: ");
            case 3 -> anio = InputUtils.leerEntero("Año: ");
            case 4 -> {
                marca = InputUtils.leerString("Marca: ");
                modelo = InputUtils.leerString("Modelo: ");
            }
            case 5 -> {
                marca = InputUtils.leerString("Marca: ");
                anio = InputUtils.leerEntero("Año: ");
            }
            case 6 -> {
                modelo = InputUtils.leerString("Modelo: ");
                anio = InputUtils.leerEntero("Año: ");
            }
            case 7 -> usado = InputUtils.leerBoolean("1=Usado, 0=Nuevo: ");
            default -> {
                PrintUtils.error("Opción inválida.");
                return;
            }
        }

        List<Vehiculo> res = concesionaria.buscarVehiculo(marca, modelo, anio, usado);

        if (res.isEmpty()) {
            PrintUtils.error("No se encontraron coincidencias.");
            return;
        }

        PrintUtils.subtitulo("Resultados de búsqueda");
        PrintUtils.listaNumerada(res);

        int sel = InputUtils.leerEntero("\nSeleccione un número para ver detalles (0 para salir): ");
        if (sel == 0) return;
        if (sel < 1 || sel > res.size()) {
            PrintUtils.error("Opción inválida.");
            return;
        }

        PrintUtils.detalleVehiculo(res.get(sel - 1));
    }

    private static void eliminarVehiculo() {
        PrintUtils.subtitulo("Eliminar vehículo");
        System.out.println("1. Por marca");
        System.out.println("2. Por modelo");
        System.out.println("3. Por año");

        int op = InputUtils.leerEntero("Opción: ");

        String marca = null, modelo = null;
        Integer anio = null;

        switch (op) {
            case 1 -> marca = InputUtils.leerString("Marca: ");
            case 2 -> modelo = InputUtils.leerString("Modelo: ");
            case 3 -> anio = InputUtils.leerEntero("Año: ");
            default -> {
                PrintUtils.error("Opción inválida.");
                return;
            }
        }

        List<Vehiculo> res = concesionaria.buscarVehiculo(marca, modelo, anio, null);

        if (res.isEmpty()) {
            PrintUtils.error("No hubo coincidencias.");
            return;
        }

        PrintUtils.subtitulo("Coincidencias");
        PrintUtils.listaNumerada(res);

        int sel = InputUtils.leerEntero("Seleccione vehículo a eliminar: ") - 1;

        if (sel < 0 || sel >= res.size()) {
            PrintUtils.error("Opción inválida.");
            return;
        }

        try {
            concesionaria.eliminarVehiculo(res.get(sel).getIdVehiculo());
            guardarInventarioAutomatico();
            PrintUtils.ok("Vehículo eliminado.");
        } catch (Exception e) {
            PrintUtils.error("Error eliminando: " + e.getMessage());
        }
    }

    private static void modificarVehiculo() {
        PrintUtils.subtitulo("Modificar vehículo");
        PrintUtils.listarOpcionesDeBusqueda();

        int op = InputUtils.leerEntero("Opción: ");

        String marca = null, modelo = null;
        Integer anio = null;
        Boolean usado = null;

        switch (op) {
            case 1 -> marca = InputUtils.leerString("Marca: ");
            case 2 -> modelo = InputUtils.leerString("Modelo: ");
            case 3 -> anio = InputUtils.leerEntero("Año: ");
            case 4 -> {
                marca = InputUtils.leerString("Marca: ");
                modelo = InputUtils.leerString("Modelo: ");
            }
            case 5 -> {
                marca = InputUtils.leerString("Marca: ");
                anio = InputUtils.leerEntero("Año: ");
            }
            case 6 -> {
                modelo = InputUtils.leerString("Modelo: ");
                anio = InputUtils.leerEntero("Año: ");
            }
            case 7 -> usado = InputUtils.leerBoolean("1=Usado, 0=Nuevo: ");
            default -> {
                PrintUtils.error("Opción inválida.");
                return;
            }
        }

        List<Vehiculo> lista = concesionaria.buscarVehiculo(marca, modelo, anio, usado);

        if (lista.isEmpty()) {
            PrintUtils.error("No se encontraron vehículos.");
            return;
        }

        PrintUtils.subtitulo("Coincidencias encontradas");
        PrintUtils.listaNumerada(lista);

        int sel = InputUtils.leerEntero("Seleccione uno para modificar: ");
        if (sel < 1 || sel > lista.size()) {
            PrintUtils.error("Opción inválida.");
            return;
        }

        Vehiculo v = lista.get(sel - 1);
        PrintUtils.detalleVehiculo(v);

        PrintUtils.subtitulo("Opciones de modificación");
        System.out.println("1. Marca");
        System.out.println("2. Modelo");
        System.out.println("3. Año");
        System.out.println("4. Color");
        System.out.println("5. Estado");

        if (v instanceof Automovil)
            System.out.println("6. Carrocería (Auto)");

        if (v instanceof Camioneta) {
            System.out.println("6. Carrocería (Camioneta)");
            System.out.println("7. Capacidad de carga");
        }

        if (v instanceof Motocicleta) {
            System.out.println("6. Tipo de motocicleta");
            System.out.println("7. Cilindrada");
        }

        int mod = InputUtils.leerEntero("Opción: ");

        switch (mod) {
            case 1 -> v.setMarca(InputUtils.leerString("Nueva marca: "));
            case 2 -> v.setModelo(InputUtils.leerString("Nuevo modelo: "));
            case 3 -> v.setAnioFabricacion(InputUtils.leerAnioValido("Nuevo año: "));
            case 4 -> {
                System.out.println("Colores: " + EnumUtils.generarStringDeEnumGenerico(Color.class));
                Color col = Color.values()[EnumUtils.leerEnum("Seleccione color: ", Color.class)];
                v.setColor(col);
            }
            case 5 -> v.setUsado(InputUtils.leerBoolean("1=Usado, 0=Nuevo: "));
            case 6 -> {
                if (v instanceof Automovil a) {
                    System.out.println("Carrocerías (Auto): " + EnumUtils.generarStringDeEnumGenerico(TipoCarroceriaAuto.class));
                    TipoCarroceriaAuto car =
                            TipoCarroceriaAuto.values()[EnumUtils.leerEnum("Seleccione: ", TipoCarroceriaAuto.class)];
                    a.setCarroceria(car);
                } else if (v instanceof Camioneta c) {
                    System.out.println("Carrocerías (Camioneta): " + EnumUtils.generarStringDeEnumGenerico(TipoCarroceriaCamioneta.class));
                    TipoCarroceriaCamioneta car =
                            TipoCarroceriaCamioneta.values()[EnumUtils.leerEnum("Seleccione: ", TipoCarroceriaCamioneta.class)];
                    c.setCarroceria(car);
                } else if (v instanceof Motocicleta m) {
                    System.out.println("Tipos de moto: " + Arrays.toString(TipoMotocicleta.values()));
                    TipoMotocicleta tm =
                            TipoMotocicleta.valueOf(InputUtils.leerString("Nuevo tipo: ").toUpperCase());
                    m.setTipo(tm);
                }
            }
            case 7 -> {
                if (v instanceof Camioneta c) {
                    int nuevaCarga = InputUtils.leerEntero("Nueva capacidad de carga: ");
                    c.setCapacidadDeCarga(nuevaCarga);
                } else if (v instanceof Motocicleta m) {
                    int nuevaCil = InputUtils.leerEntero("Nueva cilindrada: ");
                    m.setCilindrada(nuevaCil);
                }
            }
            default -> PrintUtils.error("Opción inválida.");
        }

        guardarInventarioAutomatico();
        PrintUtils.ok("Vehículo modificado correctamente.");
    }

    private static void procesarTaller() {
        if (colaTaller.isEmpty()) {
            PrintUtils.error("No hay vehículos usados para procesar.");
            return;
        }

        PrintUtils.subtitulo("Vehículos en cola de taller");
        List<Vehiculo> lista = new ArrayList<>(colaTaller);
        PrintUtils.listaNumerada(lista);

        int seleccion = InputUtils.leerEntero("Seleccione un vehículo para procesar (0 para salir): ");
        if (seleccion == 0) return;

        if (seleccion < 1 || seleccion > lista.size()) {
            PrintUtils.error("Opción inválida.");
            return;
        }

        try {
            taller.procesarPorIndice(seleccion - 1);
            guardarInventarioAutomatico();
        } catch (ColaVaciaException e) {
            PrintUtils.error(e.getMessage());
        }
    }
}
