package com.latam.alura.tiendav2.modelo;

import javax.persistence.EmbeddedId;
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
	
	
	
	//Ejemplo utilizando Mapeamiento de LLave Compuestas - Se creo Clase CategoriaId y se prueba en RegistroProducto.
	/*
	@EmbeddedId// vamos a definir nuestra categoriaId como llave primaria a través de la notación @EmbeddedId
	private CategoriaId categoriaId;
	
	public Categoria() { //Para probar el Flush de RegistroDePersonas	
	}
	
	public Categoria(String nombre) { //Para probar el Flush de RegistroDePersonas	
		this.categoriaId=new CategoriaId(nombre,"456");
	}
	
	//Getters and Setters
	
	public String getNombre() {
		return categoriaId.getNombre();
	}
	public void setNombre(String nombre) {
		this.categoriaId.setNombre(nombre);
	}
	*/
	
	
} 
