package com.latam.alura.tiendav2.modelo;

import javax.persistence.Entity;

@Entity
public class Libros extends Producto{// Libros(clase hija) extendiende de Producto(Clase padre).

	private String autor;
	private int pagina;
	
	//Constructores
	public Libros() {
	}

	public Libros(String autor, int pagina) {
		this.autor = autor;
		this.pagina = pagina;
	}

	//Getters and Setters
	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public int getPagina() {
		return pagina;
	}

	public void setPagina(int pagina) {
		this.pagina = pagina;
	}
	
	
}
