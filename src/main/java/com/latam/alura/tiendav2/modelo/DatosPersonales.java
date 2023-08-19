package com.latam.alura.tiendav2.modelo;

import javax.persistence.Embeddable;

/*
 Creada nuestra clase, ahora necesitamos indicarle a JPA que esta clase va a ser inyectada dentro de la clase cliente, que puede ser una clase que puede ser inyectada dentro de la clase cliente.
 Entonces para eso vamos a utilizar la notación @Embedable de JPA.
 Y para conectarla con la clase cliente tenemos que agregar ese nuevo atributo que va a ser del tipo DatosPersonales. Pero ese atributo no es una entidad ni es un dato primitivo sino que es una clase propia.
 */

@Embeddable
public class DatosPersonales {

	private String nombre;
	private String dni;
	
	
	public DatosPersonales() {
	}


	public DatosPersonales(String nombre, String dni) {
		this.nombre = nombre;
		this.dni = dni;
	}

	// 	//Getters and Setters
	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getDni() {
		return dni;
	}


	public void setDni(String dni) {
		this.dni = dni;
	}
	
	

	
	
	
}
