package com.latam.alura.tiendav2.prueba;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import com.latam.alura.tiendav2.dao.CategoriaDao;
import com.latam.alura.tiendav2.dao.ProductoDao;
import com.latam.alura.tiendav2.modelo.Categoria;
import com.latam.alura.tiendav2.modelo.Producto;
import com.latam.alura.tiendav2.utils.JPAUtils;

public class RegistroProducto {

	public static void main(String[] args) {
		
		//Para Consultas con JPQL
		
		registrarProducto(); //Registra el producto
		
		EntityManager em = JPAUtils.getEntityManager();	 //Metodo getEntityManager creado en JPAUtils, Mantener la conexion de los DAOS para evitar la duplicacion de codigo, ya que ellos lo pueden utilizar.
		ProductoDao productoDao = new ProductoDao(em); //se le pasa el parametro em que es el EntityManager
		
		//Consulta por id
		Producto producto = productoDao.consultaPorId(1l);
			System.out.println(producto.getNombre());
			
		//Consultar todos los elementos de la tabla producto
		List<Producto> productos = productoDao.consultarTodos();	
		productos.forEach(prod->System.out.println(prod.getDescripcion()));
		
		//Consulta por nombre
		List<Producto> productos1 = productoDao.consultaPorNombre("Samsung");	//enviamos el nombre a buscar
		productos1.forEach(prod->System.out.println(prod.getDescripcion()));  //devuelve la descripcion
		
		//Consulta por nombre de Categoria
		List<Producto> productos2 = productoDao.consultaPorNombreDeCategoria("CELULARES");	//enviamos el nombre a buscar
		productos2.forEach(prod->System.out.println(prod.getNombre()));  //devuelve el nombre
				
		//Consultar el precio por medio del nombre
		BigDecimal precio = productoDao.consultarPrecioPorNombreDeProducto("Samsung");
		System.out.println(precio);
		
		
	}

	
	
	//Crear metodo en base a un bloque de codigo: Seleccionar codigo --> Refactor --> Extract Methods --> Escribir el nombre del metodo registrarProducto y clic en ok.
	
	private static void registrarProducto() {
		Categoria celulares = new Categoria("CELULARES");
		
		Producto celular = new Producto("Samsung", "telefono usado", new BigDecimal("1000"), celulares); //como el tipo del precio era BigDecimal se debio importar para escribir el precio
			 
		EntityManager em = JPAUtils.getEntityManager();	 //Metodo getEntityManager creado en JPAUtils, Mantener la conexion de los DAOS para evitar la duplicacion de codigo, ya que ellos lo pueden utilizar.
		
		ProductoDao productoDao = new ProductoDao(em); //se le pasa el parametro em que es el EntityManager
		CategoriaDao categoriaDao = new CategoriaDao(em);
				
		em.getTransaction().begin(); //indicarle para que deben iniciar las transacciones
		
		categoriaDao.guardar(celulares);//Guarda la instancia celulares que son las categorias antes que el producto ya que el producto esta relacionado con categoria.
		productoDao.guardar(celular); //Realizar la 1ra. Persistencia. Guarda el producto utilizando ProductoDao
		
			
		em.getTransaction().commit(); //obtenemos la transaccion y realizamos un commit, El commit envia los valores que fueron configurados, los envia a la BD
		em.close();//cerrando la transaccion
	}
	

}






