package com.latam.alura.tiendav2.modelo;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="clientes")
public class Cliente {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
		
	
	/*
	 Conectamos DatosPersonales esta clase que puede ser embutida con la clase Cliente a través de la notación @Embedded. De esta forma, estamos indicando a JPA que esta clase, de DatosPersonales 
	 está siendo inyectada dentro del atributo datosPersonales. De esa forma, nosotros no creamos una nueva tabla, sino que simplemente estamos colocando los atributos de la clase DatosPersonales, dentro de esta clase Cliente. 
	 */
	
	@Embedded
	private DatosPersonales datosPersonales;
	
	public Cliente() {
	}
	
	public Cliente(String nombre, String dni) {
		this.datosPersonales = new DatosPersonales(nombre, dni);
	}
	

	//Getters and Setters
	
		public Long getId() {
			return id;
		}

		public String getNombre() {
			return datosPersonales.getNombre();
		}


		public void setNombre(String nombre) {
			this.datosPersonales.setNombre(nombre);
		}


		public String getDni() {
			return datosPersonales.getDni();
		}


		public void setDni(String dni) {
			this.datosPersonales.setDni(dni);
		}
		
		
		

	//Ejemplo de almacenar los atributos en otra clase	
	//Estos datos estan en la clase DatosPersonales. Ejemplo: crearemos los atributos de clientes en una nueva clase llamada DatosPersonales luego importaremos los atributos de DatosPersonales para Clientes en el cual se podra 
	// usar los atributos de DatosPersonales en cliente.
	 
/* 
 		//Atributos de cliente
		private String nombre;
		private String dni;
		  
	//Constructor
	public Cliente() {
	}
	
	public Cliente(String nombre, String dni) {
		this.nombre = nombre;
		this.dni = dni;
	}

	//Getters and Setters
	
	public Long getId() {
		return id;
	}

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
		
*/
	
}
