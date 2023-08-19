package com.latam.alura.tiendav2.modelo;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "electronicos")
//Utilizaremos herencia palabra reservada extends ya que la clase Electronico tendra los los mismos atributos y propiedades que la entidad producto. 
public class Electronico extends Producto {// Electrónico(clase hija) extendiende de Producto(Clase padre).

	//Atributos propios de la clase hija Electronico
		private String marca;
		private String modelo;
		
		//Constructores
		public Electronico() {
		}

		public Electronico(String marca, String modelo) {
			this.marca = marca;
			this.modelo = modelo;
		}

		//Getters and setters
		public String getMarca() {
			return marca;
		}

		public void setMarca(String marca) {
			this.marca = marca;
		}

		public String getModelo() {
			return modelo;
		}

		public void setModelo(String modelo) {
			this.modelo = modelo;
		}
		
		
		
}
