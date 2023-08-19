package com.latam.alura.tiendav2.modelo;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

//Clase de ejemplo utilizando Mapeamiento de llaves compuestas

@Embeddable //todos los elementos que sean @Embeddable deben implementar Serializable ya que son elementos que van a ser serializados en bits para transitar en la app. 
public class CategoriaId implements Serializable{
	
	private static final long serialVersionUID = 4198020985304539350L;
	
	private String nombre;
	private String password;
	
	//Constructores
	public CategoriaId() {
	}
	
	public CategoriaId(String nombre, String password) {
		this.nombre = nombre;
		this.password = password;
	}
	
	//Getters and Setters
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	//Para identificar que son elementos únicos, tenemos que implementar el método hashcode.
	
	//Generamos el metodo HashCode
	//Clic derechos -> Source y clic en Generate hashCode an equal, no tener checkeado ninguna opcion -> ok
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CategoriaId other = (CategoriaId) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}


	

	

}
