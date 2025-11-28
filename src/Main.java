import enums.Color;
import enums.TipoCarroceria;
import vehiculos.Automovil;

public class Main {
    public static void main(String[] args) {
        Automovil auto = new Automovil(
                "Ford",
                "Fiesta",
                2014,
                true,
                Color.ROJO,
                TipoCarroceria.HATCHBACK
        );

        System.out.println(auto);
    }
}