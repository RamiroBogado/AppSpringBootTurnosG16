package com.unla.tp_oo2_g16.models.dtos.request;

import lombok.*;

@Data
public class ClienteRequestDTO {
	
    private String apellidoCompleto;
    private String nombreCompleto;
    private String dni;
    private String eMail;
    private String tContacto;

}

