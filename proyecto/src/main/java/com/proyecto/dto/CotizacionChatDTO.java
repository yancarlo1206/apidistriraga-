package com.proyecto.dto;

import java.util.List;

import lombok.Data;

@Data
public class CotizacionChatDTO {
	private String edificio;
    private String apartamento;
    private Double total;
    private String telefono;
    private String cliente;
    private List<EspacioDTO> espacio;
}
