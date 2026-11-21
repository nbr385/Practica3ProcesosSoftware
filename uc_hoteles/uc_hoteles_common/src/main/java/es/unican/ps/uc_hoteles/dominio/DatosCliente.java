package es.unican.ps.uc_hoteles.dominio;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "Clientes")
public class DatosCliente implements Serializable {

    @Id
    @Column(nullable = false)
    private String dni;

    @Column(nullable = false)
    private String nombre;

    @Column(name = "ap1", nullable = false)
    private String primerApellido;

    @Column(name = "ap2")
    private String segundoApellido;

    @Column(nullable = false)
    private String email;
}