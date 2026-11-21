package es.unican.ps.uc_hoteles.dominio;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "Reservas")
public class Reserva implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "fechaIni", nullable = false)
    private Date fechaEntrada;

    @Column(name = "fechaFin", nullable = false)
    private Date fechaSalida;

    @ManyToOne
    @JoinColumn(name = "DatosCliente_FK")
    private DatosCliente cliente;

    @Embedded
    private DatosPago datosPago;

    @ManyToOne 
    @JoinColumn(name = "Hotel_FK")
    private Hotel hotelReservado;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ReservaTipoHabitacion_FK")
    private List<ReservaTipoHabitacion> reservasPorTipo = new ArrayList<>();

    public void addReservaTipoHabitacion(ReservaTipoHabitacion reservaTipoHabitacion) {
        this.reservasPorTipo.add(reservaTipoHabitacion);
    }

    public List<ReservaTipoHabitacion> getReservasTipoHabitacion() {
        return reservasPorTipo;
    }

    /**
     * Calcula el importe total multiplicando el número de noches por el subtotal
     * de cada línea de tipo de habitación.
     */
    public float getImporte() {
        float subtotalPorNoche = 0;
        float noches = calcularNoches();
        for (ReservaTipoHabitacion subReserva : reservasPorTipo) {
            subtotalPorNoche += subReserva.getTipoHabitacion().getPrecioPorNoche() * subReserva.getNumHabitaciones();
        }
        return subtotalPorNoche * noches;
    }

    /**
     * Calcula el numero de noches que dura una reserva
     */
    private float calcularNoches() {
        if (fechaEntrada == null || fechaSalida == null) {
            return 0;
        }
        ZoneId zone = ZoneId.systemDefault();
        LocalDate entrada = fechaEntrada.toInstant().atZone(zone).toLocalDate();
        LocalDate salida = fechaSalida.toInstant().atZone(zone).toLocalDate();
        return ChronoUnit.DAYS.between(entrada, salida);
    }
}