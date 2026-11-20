package es.unican.ps.uc_hoteles.dominio;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class DatosPago implements Serializable {
    private static final long serialVersionUID = 1L;

    private String numTarjeta; // O long/String según prefieras para números de tarjeta
    private String cvc;
    private int mesCaducidad;
    private int anhoCaducidad;
    private TipoTarjeta tipo;
}