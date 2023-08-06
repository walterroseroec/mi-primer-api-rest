package com.std.ec.model.dto;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
@Builder
public class ClienteDto implements Serializable {

    private Integer idCliente;
    private String nombre;
    private String apellido;
    private String correo;
    private Date fechaRegistro;

}
