import enums.TipoDeMotocicleta;
import vehiculos.Motocicleta;
import vehiculos.Vehiculo;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Vehiculo> vehiculos = new ArrayList<>();

        vehiculos.add(
                new Motocicleta(
                        "Honda",
                        "Wave 110s",
                        2018,
                        "Negra",
                        21500,
                        TipoDeMotocicleta.CROSS
                )
        );

        for (Vehiculo vehiculo : vehiculos) {
            System.out.println(vehiculo);
            System.out.println("Es usado el vehiculo: " + (vehiculo.isUsado() ? "si" : "no"));
        }
    }
}