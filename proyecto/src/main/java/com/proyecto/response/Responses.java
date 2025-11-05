package com.proyecto.response;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Responses<M> {

	private String mensaje;
	private int Status;
	private M data;

}
