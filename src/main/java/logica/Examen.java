package logica;

import java.util.List;

public class Examen {
    private int id;
    private String nombre;
    private List<ResultadoExamen> resultados;

    public Examen() {
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<ResultadoExamen> getResultados() {
        return resultados;
    }

    public void setResultados(List<ResultadoExamen> resultados) {
        this.resultados = resultados;
    }
}
