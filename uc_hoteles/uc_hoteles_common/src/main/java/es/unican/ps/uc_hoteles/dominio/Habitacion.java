package es.unican.ps.uc_hoteles.dominio;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Habitacion implements Serializable {
    private static final long serialVersionUID = 1L;

    private double precioPorNoche;
    private int disponibles;
    private TipoHabitacion tipo;
}