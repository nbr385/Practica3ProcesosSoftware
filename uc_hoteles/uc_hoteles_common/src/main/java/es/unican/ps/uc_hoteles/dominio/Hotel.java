package es.unican.ps.uc_hoteles.dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;


@Getter 
@Setter
@NoArgsConstructor
@Entity
@Table(name="Hoteles")
public class Hotel implements Serializable {

    @Id
    @GeneratedValue 
    private long id;

	@Column(nullable = false)
    private String nombre;

    @Column(name = "dir", nullable = false)
    private String direccion;

    @Column(name = "loc", nullable = false)
    private String localidad;

    @Enumerated(EnumType.STRING)
    @Column(name = "cat", nullable = false)
    private Categoria estrellas;

    @OneToMany 
    @JoinColumn(name="Hotel_FK")
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