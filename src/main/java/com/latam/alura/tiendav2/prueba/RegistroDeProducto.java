package com.latam.alura.tiendav2.prueba;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.latam.alura.tiendav2.dao.CategoriaDao;
import com.latam.alura.tiendav2.dao.ProductoDao;
import com.latam.alura.tiendav2.modelo.Categoria;
import com.latam.alura.tiendav2.modelo.Producto;
import com.latam.alura.tiendav2.utils.JPAUtils;

public class RegistroDeProducto {
	
	//Practica ejemplo del: Ciclo de vida de la entidad
	public static void main(String[] args) {
		Categoria celulares = new Categoria("CELULARES");
		
		//Producto celular = new Producto("Samsung", "telefono usado", new BigDecimal("1000"), celulares); //como el tipo del precio era BigDecimal se debio importar para escribir el precio
			 
		EntityManager em = JPAUtils.getEntityManager();	 //Metodo getEntityManager creado en JPAUtils, Mantener la conexion de los DAOS para evitar la duplicacion de codigo, ya que ellos lo pueden utilizar.
		
		//ProductoDao productoDao = new ProductoDao(em); //se le pasa el parametro em que es el EntityManager
		//CategoriaDao categoriaDao = new CategoriaDao(em);
				
		em.getTransaction().begin(); //indicarle para que deben iniciar las transacciones
		
		//categoriaDao.guardar(celulares);//Guarda la instancia celulares que son las categorias antes que el producto ya que el producto esta relacionado con categoria.
		//productoDao.guardar(celular); //Realizar la 1ra. Persistencia. Guarda el producto utilizando ProductoDao
		
		em.persist(celulares);
		celulares.setNombre("LIBROS");
		
		//em.getTransaction().commit(); //obtenemos la transaccion y realizamos un commit, El commit envia los valores que fueron configurados, los envia a la BD
		//em.close();//cerrando la transaccion
		
		em.flush(); //flush si hay un error permite hacer un rollback
		em.clear();
		
		//Necesita un constructor vacio para cada entidad Producto y Categoria.
		//em.merge(celulares); //Crea un nuevo registro //cuando se usa merge se necesita hacer un select para hacer modificaciones en la BD, por lo que se agrega un constructor en la tabla Producto y Categoria.		
		celulares = em.merge(celulares); //Update del registro se tuvo que reasignar a celulares.
		celulares.setNombre("SOFTWARE"); //esta no es considerada en los registros. esta fuera. usando commit, con flush arriba y abajo y el merge si lo acepta.
		em.flush(); //entidad en estado manage
		em.clear(); //detached
		
		celulares = em.merge(celulares); //merge trae el valor de la BD a estado managed
		em.remove(celulares);//borrar el registro se envia la entidad. Borrar debe estar en estado managed(administrado) y debe existir el dato.
		em.flush(); //sincronizar el valor. Como utilizo el metodo flush en la siguiente linea puedo hacer un rollback, eliminando la ultima transaccion volviendo a tener el valor antes. Si hubiera usado el commit la transaccion hubiera sido definitiva.
		
		
		
	}

}



