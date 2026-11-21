package es.unican.ps.uc_hoteles.dominio;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
@Table(name = "Habitaciones")
public class Habitacion implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "preNoche", nullable = false)
    private double precioPorNoche;

    @Column(name = "disp", nullable = false)
    private int disponibles;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoHabitacion tipo;

    public Habitacion(double precioPorNoche, int disponibles, TipoHabitacion tipo) {
        this.precioPorNoche = precioPorNoche;
        this.disponibles = disponibles;
        this.tipo = tipo;
    }

}