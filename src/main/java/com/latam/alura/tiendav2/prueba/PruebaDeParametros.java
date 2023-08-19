package com.latam.alura.tiendav2.prueba;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import com.latam.alura.tiendav2.dao.CategoriaDao;
import com.latam.alura.tiendav2.dao.ProductoDao;
import com.latam.alura.tiendav2.modelo.Categoria;
import com.latam.alura.tiendav2.modelo.Producto;
import com.latam.alura.tiendav2.utils.JPAUtils;

public class PruebaDeParametros {

	
	//Prueba para consultas con Parametros dinamicos
	
	
	public static void main(String[] args) {
		
		cargarBancoDeDatos();
		
		EntityManager em = JPAUtils.getEntityManager();
		ProductoDao productoDao = new ProductoDao(em);
		
		//Consultas con Parametros dinamicos
		List<Producto> resultado = productoDao.consultarPorParametros("FIFA", null, null);
		System.out.println(resultado.get(0).getDescripcion());
		
		//Consultas con Criteria API Basada en la Anterior(Consultas con Parametros dinamicos)
		List<Producto> resultado1 = productoDao.consultarPorParametrosConAPICriteria("X", null, null);
		System.out.println(resultado1.get(0).getDescripcion()); //Obtiene el primer elemento de la lista e imprime la descripcion
		
		
		
		
	}
	
	
	private static void cargarBancoDeDatos() {
        Categoria celulares = new Categoria("CELULARES");
        Categoria videoJuegos = new Categoria("VIDEO_JUEGOS");
        Categoria electronicos = new Categoria("ELECTRONICOS");

        Producto celular = new Producto("X", "producto nuevo", new BigDecimal(10000), celulares);
        Producto videoJuego = new Producto("FIFA", "2000", new BigDecimal(10000), videoJuegos);
        Producto memoria = new Producto("memoria ram", "30 GB", new BigDecimal(10000), electronicos);

        EntityManager em = JPAUtils.getEntityManager();
        ProductoDao productoDao = new ProductoDao(em);
        CategoriaDao categoriaDao = new CategoriaDao(em);

        em.getTransaction().begin();

        categoriaDao.guardar(celulares);
        categoriaDao.guardar(videoJuegos);
        categoriaDao.guardar(electronicos);

        productoDao.guardar(celular);
        productoDao.guardar(videoJuego);
        productoDao.guardar(memoria);

        em.getTransaction().commit();
        em.close();
    }
	
	

}
