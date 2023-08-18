package com.latam.alura.tiendav2.prueba;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import com.latam.alura.tiendav2.dao.CategoriaDao;
import com.latam.alura.tiendav2.dao.ClienteDao;
import com.latam.alura.tiendav2.dao.PedidoDao;
import com.latam.alura.tiendav2.dao.ProductoDao;
import com.latam.alura.tiendav2.modelo.Categoria;
import com.latam.alura.tiendav2.modelo.Cliente;
import com.latam.alura.tiendav2.modelo.ItemsPedido;
import com.latam.alura.tiendav2.modelo.Pedido;
import com.latam.alura.tiendav2.modelo.Producto;
import com.latam.alura.tiendav2.utils.JPAUtils;
import com.latam.alura.tiendav2.vo.RelatorioDeVenta;

public class RegistroDePedido {

	public static void main(String[] args) {
		
		//Para Consultas con JPQL
		
		registrarProducto(); //Registra el producto
		
		//Para crear un constructor tenemos que llamar la clase utilitaria JPAUtils que fabrica con el EntityManager factory, una instancia del EntityManager.
		EntityManager em = JPAUtils.getEntityManager();	 //Metodo getEntityManager creado en JPAUtils, Mantener la conexion de los DAOS para evitar la duplicacion de codigo, ya que ellos lo pueden utilizar.
		//llamar el producto de la BD
		ProductoDao productoDao = new ProductoDao(em); //se le pasa dentro del constructor el parametro em que es el EntityManager
		
		Producto producto = productoDao.consultaPorId(1l); //id 1
		
		ClienteDao clienteDao = new ClienteDao(em);
		PedidoDao pedidoDao = new PedidoDao(em); //instanciar PedidoDao
		
		Cliente cliente = new Cliente("Luis", "k6757kjb");
		Pedido pedido = new Pedido(cliente);
		pedido.agregarItems(new ItemsPedido(5,producto,pedido));
		
		em.getTransaction().begin(); //comenzar la tranzaccion
		
		clienteDao.guardar(cliente);
		pedidoDao.guardar(pedido); //Guardamos el pedido
		em.getTransaction().commit(); //Realizamos un commit este permite sincronizar los valores con la BD
		
		// Valor total vendido
		BigDecimal valorTotal = pedidoDao.valorTotalVendido();
		System.out.println("Valor Total: "+ valorTotal); //Imprime el valor total vendido
		
		//Valor maximo vendido
		BigDecimal valorMax = pedidoDao.valorMAXVendido();
		System.out.println("Valor MAX: "+ valorMax); //Imprime el valor total vendido
		
		//Promedio de venta
		Double valorProm = pedidoDao.valorPromVendido();
		System.out.println("Valor Promedio: "+ valorProm);
		
		
		 // Consultas para relatorio
	    
	    /*Consulta para generar una tabla con los siguientes campos:
	    ---------------------------------------------------------
	    | Producto 			| Cantidad Vendida 	| Ultima Venta 	|
	    | Celular Samsung	|		5    		|	2023-08-18	|
	    ---------------------------------------------------------
	    */
		
		List<Object[]> relatorio = pedidoDao.relatorioDeVentas();
		for(Object[] obj: relatorio) { // variable obj de tipo Object, imprime todo de relatorio
			System.out.println(obj[0]); //posicion 0
			System.out.println(obj[1]);
			System.out.println(obj[2]);
		}
		
		// Consultas para relatorio Opcion 2: utilizando la segunda estrategia, que es a través de la construcción de un VO, que significa value object.  	
		List<RelatorioDeVenta> relatorio2 = pedidoDao.relatorioDeVentasVO();
		//Imprimir todos los elementos que existen en este relatorio2
		relatorio2.forEach(System.out::println);
		
		
		//Consulta Utilizando @NameQuery
		//Consultar el precio por medio del nombre
		BigDecimal precio = productoDao.consultaDePrecioPorNombreDelProducto("Samsung");
		System.out.println(precio);
		
		JPAUtils.closeEntityManager(em); //cierra la conexion a la BD
		
		
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






