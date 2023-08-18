package com.latam.alura.tiendav2.prueba;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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


/* Para practicar #03 Performance de Consultas : 

-- Para las pruebas de Desempeño - Entendiendo Lazy / Eager
- Se creo la Carpeta Utils con los archivos .txt categoria, cliente, producto en carpeta src/main/resources
- Se creo la clase LoadRecords.java en la carpeta Prueba
- Se creo la clase PruebaDeDesempeño.java en la carpeta Prueba.

Nosotros en esta parte cargamos unos registros desde unos archivos de texto, con una clase que 
importa utilizar instancia los Dao y lee esos archivos de texto para cargarlos en la base de 
datos. Nosotros creamos una clase main para realizar nuestras pruebaDeDesempeño, cargamos 
todos nuestros registros, instanciamos el EntityManager.

 */


public class LoadRecords {

	public static void cargarRegistros() throws FileNotFoundException {
		EntityManager em = JPAUtils.getEntityManager();
		CategoriaDao categoriaDao = new CategoriaDao(em);
		ProductoDao productoDao = new ProductoDao(em);
		ClienteDao clienteDao = new ClienteDao(em);
		PedidoDao pedidoDao = new PedidoDao(em);
		em.getTransaction().begin();
		
		loadCategoria("categoria",categoriaDao,em);
		
		loadProducto("producto",productoDao,categoriaDao,em);
		
		loadCliente("cliente",clienteDao,em);
		
		List<Cliente> clientesList = clienteDao.consultarTodos();
		List<Pedido> pedidoList= new ArrayList<>();
		
		for(Cliente cl:clientesList) {
			pedidoList.add(new Pedido(cl));
		}
		
		for(int i=0;i<pedidoList.size();i++) {
			pedidoList.get(i).agregarItems(new ItemsPedido(i+1,productoDao.consultaPorId((long) (i+1)),pedidoList.get(i)));
			pedidoDao.guardar(pedidoList.get(i));
		}
		
		em.getTransaction().commit();
		em.close();
		
	}
	
	private static void loadProducto(String type, ProductoDao productoDao,CategoriaDao categoriaDao, EntityManager em) throws FileNotFoundException {
		List<String> productosTxt =readFile(type);
		for(int i=0;i<productosTxt.size();i++) {
			String[] line = productosTxt.get(i).split(";");
			if(line.length>1) {
				Categoria categoria=categoriaDao.consultaPorNombre(line[3]);
				Producto producto = new Producto(line[4],line[0],new BigDecimal(line[1]),categoria);
				productoDao.guardar(producto);
				em.flush();
			}
		}
	}

	private static void loadCategoria(String type, CategoriaDao categoriaDao,EntityManager em) throws FileNotFoundException {
		List<String> categoriasTxt =readFile(type);		
		for(int i=0;i<categoriasTxt.size();i++) {
			String[] line = categoriasTxt.get(i).split(";");
			if(line.length==1) {
				Categoria categoria = new Categoria(categoriasTxt.get(i));
				categoriaDao.guardar(categoria);
				em.flush();	
			}
		}
	}

	private static void loadCliente(String type, ClienteDao clienteDao,EntityManager em) throws FileNotFoundException {
		List<String> clientesTxt =readFile(type);		
		for(int i=0;i<clientesTxt.size();i++) {
			String[] line = clientesTxt.get(i).split("~");
			System.out.println(line[0]+line[1]);
			if(line.length>1) {
				Cliente cliente= new Cliente(line[0],line[1]);
				clienteDao.guardar(cliente);
				em.flush();	
			}
		}
	}
	
	private static List<String> readFile(String type) throws FileNotFoundException {
		//File file = new File("C:\\Users\\Public\\Alura\\jpa\\"+type+".txt");
		File file = new File("C:\\Users\\Hp01\\OneDrive\\Escritorio\\Cursos\\Alura\\JavaBackend-SpringBoot\\JavaJPAConsultasAvanzadas-appTiendaV2\\src\\main\\resources\\utils\\"+type+".txt");
		Scanner scan = new Scanner(file);
		List<String> pedido= new ArrayList<>();
		while(scan.hasNextLine()){
			pedido.add(scan.nextLine());
		}
		scan.close();
		return pedido;
	}

}





