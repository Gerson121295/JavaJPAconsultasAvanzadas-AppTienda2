package com.latam.alura.tiendav2.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

//Clase responsable de crear el EntityManager
public class JPAUtils {

	private static EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory("tienda"); //archivo Persistence con BD: tienda
	
	public static EntityManager getEntityManager() { //metodo estatico publico llamado getEntityManager que retorna un EntityManager
		return FACTORY.createEntityManager();
	}
	
}
