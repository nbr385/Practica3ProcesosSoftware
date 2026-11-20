package es.unican.ps.uc_hoteles.dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;


@Getter 
@Setter
@NoArgsConstructor
public class Hotel implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nombre;
    private String direccion;
    private String localidad;
    private Categoria estrellas;
    private List<Habitacion> habitaciones = new ArrayList<>();

    public Hotel(String nombre, String direccion, String localidad, Categoria estrellas) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.localidad = localidad;
        this.estrellas = estrellas;
    }

    public void addHabitacion(Habitacion h) {
        this.habitaciones.add(h);
    }

    public void eliminarHabitacion(Habitacion h) {
        this.habitaciones.remove(h);
    }
}