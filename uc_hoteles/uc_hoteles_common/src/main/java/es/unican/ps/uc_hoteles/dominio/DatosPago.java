package es.unican.ps.uc_hoteles.dominio;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable 
public class DatosPago implements Serializable {

    @Column(name = "nTarjeta")
    private String numTarjeta;

    private String cvc;

    @Column(name = "mesCad")
    private int mesCaducidad;

    @Column(name = "anhoCad")
    private int anhoCaducidad;

    @Enumerated(EnumType.STRING)
    private TipoTarjeta tipo;
}