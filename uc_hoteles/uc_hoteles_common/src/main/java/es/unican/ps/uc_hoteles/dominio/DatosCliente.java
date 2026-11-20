package es.unican.ps.uc_hoteles.dominio;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class DatosCliente implements Serializable {
    private static final long serialVersionUID = 1L;

    private String dni;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private String email;
}