package es.unican.ps.uc_hoteles.dominio;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ReservasTipoHabitacion")
public class ReservaTipoHabitacion implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name ="nHab", nullable = false)
    private int numHabitaciones;

    @ManyToOne
    @JoinColumn(name = "Habitacion_FK")
    private Habitacion tipoHabitacion;


    public ReservaTipoHabitacion(int numHabitaciones, Habitacion tipoHabitacion) {
        this.numHabitaciones = numHabitaciones;
        this.tipoHabitacion = tipoHabitacion;
    }
}