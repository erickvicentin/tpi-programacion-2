import enums.Color;
import enums.TipoCarroceriaAuto;
import enums.TipoCarroceriaCamioneta;
import enums.TipoMotocicleta;
import excepciones.ColaVaciaException;
import persistencia.ArchivoUtil;
import servicios.Lavadero;
import servicios.Taller;
import utils.EnumUtils;
import utils.InputUtils;
import vehiculos.Automovil;
import vehiculos.Camioneta;
import vehiculos.Motocicleta;
import vehiculos.Vehiculo;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    private static final Queue<Vehiculo> colaTaller = new LinkedList<>();
    private static final Concesionaria concesionaria = new Concesionaria(colaTaller);
    private static final Lavadero lavadero = new Lavadero();
    private static final Taller taller = new Taller(colaTaller, lavadero);

    public static void main(String[] args) throws InterruptedException {
        //al iniciar el programa ya cargamos el inventario, si existe.
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
                case 0 -> {
                    try {
                        System.out.println("Saliendo del sistema...");
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
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
        System.out.println("6. Modificar vehículo");
        System.out.println("0. Salir");
    }

    private static void mostrarMenuTipos() {
        System.out.println("\n--- TIPOS DE VEHÍCULO ---");
        System.out.println("1. Automóvil");
        System.out.println("2. Camioneta");
        System.out.println("3. Motocicleta");
        System.out.println("0. Salir");
    }

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

    private static void agregarVehiculo() {
        mostrarMenuTipos();
        int tipo = InputUtils.leerEntero("Seleccione el tipo: ");
        boolean salir = false;

        while (tipo != 0 && !salir) {
            String marca = InputUtils.leerString("Marca: ");
            String modelo = InputUtils.leerString("Modelo: ");
            int anio = InputUtils.leerAnioValido("Año: ");
            boolean usado = InputUtils.leerBoolean("¿Es usado? (1=Sí, 0=No): ");

            System.out.println("Colores disponibles: " + EnumUtils.generarStringDeEnumGenerico(Color.class));
            Color color = Color.values()[EnumUtils.leerEnum("Seleccione el color: ", Color.class)];

            Vehiculo v = null;

            switch (tipo) {
                case 1 -> {
                    System.out.println("Carrocerías: " + EnumUtils.generarStringDeEnumGenerico(TipoCarroceriaAuto.class));
                    TipoCarroceriaAuto carroceria =
                            TipoCarroceriaAuto.values()[EnumUtils.leerEnum("Seleccione carrocería: ", TipoCarroceriaAuto.class)];
                    v = new Automovil(marca, modelo, anio, usado, color, carroceria);
                }
                case 2 -> {
                    System.out.println("Carrocerías: " + EnumUtils.generarStringDeEnumGenerico(TipoCarroceriaCamioneta.class));
                    TipoCarroceriaCamioneta carroceria =
                            TipoCarroceriaCamioneta.values()[EnumUtils.leerEnum("Seleccione carrocería: ", TipoCarroceriaCamioneta.class)];

                    int carga = InputUtils.leerEntero("Capacidad de carga (kg): ");
                    v = new Camioneta(marca, modelo, anio, usado, color, carroceria, carga);
                }
                case 3 -> {
                    System.out.println("Tipos de moto: " + EnumUtils.generarStringDeEnumGenerico(TipoMotocicleta.class));
                    TipoMotocicleta tipoMoto =
                            TipoMotocicleta.values()[EnumUtils.leerEnum("Tipo de moto: ", TipoMotocicleta.class)];
                    int cilindrada = InputUtils.leerEntero("Cilindrada: ");
                    v = new Motocicleta(marca, modelo, anio, usado, color, tipoMoto, cilindrada);
                }
                default -> System.out.println("Tipo inválido.");
            }

            if (v != null) {
                concesionaria.agregarVehiculo(v);
                guardarInventarioAutomatico();
                System.out.println("Vehículo agregado correctamente.");
                salir = true;
            }
        }
    }

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

        int op = InputUtils.leerEntero("Opción: ");

        String marca = null;
        String modelo = null;
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
            case 7 -> {
                System.out.println("1. Usado");
                System.out.println("0. Nuevo");
                usado = InputUtils.leerBoolean("Seleccione estado (1=Usado, 0=Nuevo): ");
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
            mostrarDetallesVehiculo(resultados.get(0));
            return;
        }

        int seleccion = InputUtils.leerEntero("Seleccione un número para ver detalles (0 para salir): ");

        if (seleccion == 0) return;
        if (seleccion < 1 || seleccion > resultados.size()) {
            System.out.println("Opción inválida.");
            return;
        }

        Vehiculo elegido = resultados.get(seleccion - 1);

        System.out.println("\n--- DETALLES DEL VEHÍCULO ---");
        mostrarDetallesVehiculo(elegido);
    }

    //Eliminacion de vehiculo
    private static void eliminarVehiculo() {

        System.out.println("\n--- ELIMINAR VEHÍCULO ---");
        System.out.println("1. Por marca");
        System.out.println("2. Por modelo");
        System.out.println("3. Por año");

        int op = InputUtils.leerEntero("Opción: ");

        String marca = null;
        String modelo = null;
        Integer anio = null;

        switch (op) {
            case 1 -> marca = InputUtils.leerString("Marca: ");
            case 2 -> modelo = InputUtils.leerString("Modelo: ");
            case 3 -> anio = InputUtils.leerEntero("Año: ");
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

        int seleccion = InputUtils.leerEntero("Seleccione el número del vehículo a eliminar: ") - 1;

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

    //Modificacion de vehiculo
    private static void modificarVehiculo() {

        System.out.println("\n--- MODIFICAR VEHÍCULO ---");
        System.out.println("Primero busquemos el vehículo a modificar.");

        // Buscamos usando la misma lógica que búsqueda avanzada
        System.out.println("1. Por marca");
        System.out.println("2. Por modelo");
        System.out.println("3. Por año");
        System.out.println("4. Combinado (marca + modelo)");
        System.out.println("5. Combinado (marca + año)");
        System.out.println("6. Combinado (modelo + año)");
        System.out.println("7. Por estado (nuevo/usado)");

        int op = InputUtils.leerEntero("Opción: ");

        String marca = null;
        String modelo = null;
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
                System.out.println("Opción inválida.");
                return;
            }
        }

        List<Vehiculo> resultados = concesionaria.buscarMultiples(marca, modelo, anio, usado);

        if (resultados.isEmpty()) {
            System.out.println("No se encontraron vehículos.");
            return;
        }

        System.out.println("\nVehículos encontrados:");
        for (int i = 0; i < resultados.size(); i++) {
            System.out.println((i + 1) + ") " + resultados.get(i));
        }

        int seleccion = InputUtils.leerEntero("Seleccione uno para modificar (0 para salir): ");
        if (seleccion == 0) return;
        if (seleccion < 1 || seleccion > resultados.size()) {
            System.out.println("Opción inválida.");
            return;
        }

        Vehiculo v = resultados.get(seleccion - 1);

        System.out.println("\n--- DETALLES DEL VEHÍCULO ---");
        mostrarDetallesVehiculo(v);

        System.out.println("\n--- ¿QUÉ DESEA MODIFICAR? ---");
        System.out.println("1. Marca");
        System.out.println("2. Modelo");
        System.out.println("3. Año");
        System.out.println("4. Color");
        System.out.println("5. Estado (Nuevo/Usado)");

        int baseOptions = 5;

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
                Color color = Color.values()[EnumUtils.leerEnum("Seleccione color: ", Color.class)];
                v.setColor(color);
            }
            case 5 -> v.setUsado(InputUtils.leerBoolean("1=Usado, 0=Nuevo: "));

            // Automóvil
            case 6 -> {
                if (v instanceof Automovil a) {
                    System.out.println("Carrocerías de auto: " + EnumUtils.generarStringDeEnumGenerico(TipoCarroceriaAuto.class));
                    TipoCarroceriaAuto carro =
                            TipoCarroceriaAuto.values()[EnumUtils.leerEnum("Seleccione: ", TipoCarroceriaAuto.class)];
                    a.setCarroceria(carro);
                }
                else if (v instanceof Camioneta c) {
                    System.out.println("Carrocerías de camioneta: " + EnumUtils.generarStringDeEnumGenerico(TipoCarroceriaCamioneta.class));
                    TipoCarroceriaCamioneta carro =
                            TipoCarroceriaCamioneta.values()[EnumUtils.leerEnum("Seleccione: ", TipoCarroceriaCamioneta.class)];
                    c.setCarroceria(carro);
                }
                else if (v instanceof Motocicleta m) {
                    System.out.println("Tipos de moto: " + Arrays.toString(TipoMotocicleta.values()));
                    TipoMotocicleta tipoMoto =
                            TipoMotocicleta.valueOf(InputUtils.leerString("Nuevo tipo: ").toUpperCase());
                    m.setTipo(tipoMoto);
                }
            }

            case 7 -> {
                if (v instanceof Camioneta c) {
                    int nuevaCarga = InputUtils.leerEntero("Nueva capacidad de carga: ");
                    c.setCapacidadDeCarga(nuevaCarga);
                } else if (v instanceof Motocicleta m) {
                    int nuevaCil = InputUtils.leerEntero("Nueva cilindrada: ");
                    m.setCilindrada(nuevaCil);
                } else {
                    System.out.println("Opción no válida para este tipo de vehículo.");
                }
            }

            default -> System.out.println("Opción inválida.");
        }

        guardarInventarioAutomatico();
        System.out.println("Vehículo modificado correctamente.");
    }


    //Funcion de taller

    private static void procesarTaller() {

        if (colaTaller.isEmpty()) {
            System.out.println("No hay vehículos usados para procesar.");
            return;
        }

        System.out.println("\n--- VEHÍCULOS EN COLA DE TALLER ---");
        List<Vehiculo> lista = new ArrayList<>(colaTaller);

        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + ") " + lista.get(i));
        }

        int seleccion = InputUtils.leerEntero("Seleccione un vehículo para procesar (0 para salir): ");

        if (seleccion == 0) return;

        if (seleccion < 1 || seleccion > lista.size()) {
            System.out.println("Opción inválida.");
            return;
        }

        try {
            taller.procesarPorIndice(seleccion - 1);
            guardarInventarioAutomatico();
        } catch (ColaVaciaException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void mostrarDetallesVehiculo(Vehiculo v) {
        System.out.println("\nVehículo seleccionado:");

        if (v instanceof Automovil a) {
            System.out.println("├─ Tipo: Automóvil");
        } else if (v instanceof Camioneta c) {
            System.out.println("├─ Tipo: Camioneta");
        } else if (v instanceof Motocicleta m) {
            System.out.println("├─ Tipo: Motocicleta");
        }

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
