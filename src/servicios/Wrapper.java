package servicios;

import java.util.ArrayList;
import java.util.List;

public class Wrapper<T> {

    private List<T> elementos = new ArrayList<>();

    public void agregar(T e) { elementos.add(e); }
    public T obtener(int i) { return elementos.get(i); }
    public int tamanio() { return elementos.size(); }
    public List<T> getLista() { return elementos; }
}
