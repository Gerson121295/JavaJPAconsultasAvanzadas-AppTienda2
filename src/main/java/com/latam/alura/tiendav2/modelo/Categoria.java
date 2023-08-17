package com.latam.alura.tiendav2.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/* public enum Categoria {SOFTWARES, LIBROS,CELULARES} */ //Clase para el elemento tipo Enumerador


@Entity
@Table(name="categorias")
public class Categoria {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	

	//Constructor
	public Categoria(String nombre) {
		this.nombre = nombre;
	}
	
	public Categoria() { //Para probar el Flush de RegistroDePersonas	
	}
	
	
	//Getters and Setters
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
} 
