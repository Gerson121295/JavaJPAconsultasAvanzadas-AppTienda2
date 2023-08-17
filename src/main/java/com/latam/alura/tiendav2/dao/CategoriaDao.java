package com.latam.alura.tiendav2.dao;

import javax.persistence.EntityManager;

import com.latam.alura.tiendav2.modelo.Categoria;

public class CategoriaDao {

	/*
	 * Esta clase va a tener los métodos de consulta, así como de guardar o 
	 * actualizar un registro. Para eso tiene que utilizar el EntityManager, 
	 * por lo que se lo vamos a pasar como atributo hacer un atributo privado 
	 * del tipo EntityManager.
	 * 
	 */
	private EntityManager em;

	public CategoriaDao(EntityManager em) {
		this.em = em;
	}
	
	public void guardar(Categoria categoria) {
		this.em.persist(categoria);
	}
	
	public void actualizar(Categoria categoria) {
		this.em.merge(categoria);
	}
	
	public void remover(Categoria categoria) {
		categoria = this.em.merge(categoria); //Garantizando que la entidad se encuentre como managed
		this.em.remove(categoria);
	}
	
}
