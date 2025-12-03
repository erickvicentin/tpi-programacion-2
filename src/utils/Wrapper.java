package utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Wrapper<T> implements Serializable {

    private final List<T> elementos = new ArrayList<>();

    public void agregar(T e) { elementos.add(e); }
    public List<T> getLista() { return elementos; }
}
