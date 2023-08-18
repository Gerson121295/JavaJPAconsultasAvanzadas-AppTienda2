package com.latam.alura.tiendav2.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import com.latam.alura.tiendav2.modelo.Pedido;

public class PedidoDao {

	/*
	 * Esta clase va a tener los métodos de consulta, así como de guardar o 
	 * actualizar un registro. Para eso tiene que utilizar el EntityManager, 
	 * por lo que se lo vamos a pasar como atributo hacer un atributo privado 
	 * del tipo EntityManager.
	 * 
	 */
	private EntityManager em;

	public PedidoDao(EntityManager em) {
		this.em = em;
	}
	
	public void guardar(Pedido pedido) {
		this.em.persist(pedido);
	}
	
	public void actualizar(Pedido pedido) {
        this.em.merge(pedido);
    }

    public void remover(Pedido pedido) {
        pedido=this.em.merge(pedido);
        this.em.remove(pedido);
    }
    
    /*//code dado
    public void remover(Pedido pedido) {
        categoria=this.em.merge(pedido);
        this.em.remove(pedido);
    }
	*/
    
    //Consulta por id
    public Pedido consultaPorId(Long id) {
    	return em.find(Pedido.class, id);
    }
	
    //Consultar todos los elementos(productos) de la tabla Pedido
    public List<Pedido> consultarTodos(){
    	String jpql = "SELECT P FROM Pedido AS P "; //en JPQL no se utiliza el * si no la letra del alias definido: SELECT * FROM Pedido P;  //lista a traves de JPQL (Java Persistence Query Language)
    	return em.createQuery(jpql, Pedido.class).getResultList();
    }
    
    //Metodo para calcular el total vendido
    public BigDecimal valorTotalVendido() {
    	String jpql = "SELECT SUM(p.valorTotal) FROM Pedido p";  //SUM(valor total de la sumatoria de valorTotal) el nombre de la tabla debe estar escrito como definimos el nombre de la Clase Pedido.
    	return em.createQuery(jpql,BigDecimal.class).getSingleResult();
    }
    
  //Metodo para calcular el valor Maximo vendido -- Se agrego MAX(devuelve el valor mas Alto) al code anterior: Probar: AVG(promedio), COUNT(Numero de registros), SUM(suma de todos los valores), MIN(Valor mas bajo)
    public BigDecimal valorMAXVendido() {
    	String jpql = "SELECT MAX(p.valorTotal) FROM Pedido p";  //MAX(valor total maximo) el nombre de la tabla debe estar escrito como definimos el nombre de la Clase Pedido.
    	return em.createQuery(jpql,BigDecimal.class).getSingleResult();
    }
    
    //Metodo para calcular el valor Maximo vendido -- Se agrego MAX(devuelve el valor mas Alto) al code anterior: Probar: AVG(promedio), COUNT(Numero de registros), SUM(suma de todos los valores), MIN(Valor mas bajo)
    public Double valorPromVendido() {
    	String jpql = "SELECT AVG(p.valorTotal) FROM Pedido p";  //AVG(promedio) el nombre de la tabla debe estar escrito como definimos el nombre de la Clase Pedido.
    	return em.createQuery(jpql,Double.class).getSingleResult();
    }
    
    // Consultas para relatorio
    
    /*Consulta para generar una tabla con los siguientes campos:
    ---------------------------------------------------------
    | Producto 			| Cantidad Vendida 	| Ultima Venta 	|
    | Celular Samsung	|		5    		|	01/02/23	|
    ---------------------------------------------------------
    */
    public List<Object[]> relatorioDeVentas(){ //El retorno de la lista sera un retorno de Objetos
    	String jpql = "SELECT producto.nombre, " //producto es un token nombre es el atributo de la clase Producto
    				+ "SUM(item.cantidad), "
    				+ "MAX(pedido.fecha) "
    				//Definimos de donde traemos los datos
    				+ "FROM Pedido pedido "
    				+ "JOIN pedido.items item " //Concatenar pedido con items -> pedido tiene relacion con items y item es un token para obtener el dato.
    				+ "JOIN item.producto producto " //Concatenar item con producto y producto es el token
    				//Agrupar los datos
    				+ "GROUP BY producto.nombre "
    				//Ordenar los datos por la cantidad de elementos vendidos
    				+ "ORDER BY item.cantidad DESC";
    	return em.createQuery(jpql,Object[].class).getResultList(); //Object-> devuleve un arreglo de objetos, y obtenemos una lista de resultados.
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    //Consulta por nombre de Categoria -- Este no aplica pero podemos hacer consulta por nombre de Cliente(estos metodos se copiaron del ProductoDao).
    public List<Pedido> consultaPorNombreDeCategoria(String nombre){
    	String jpql="SELECT p FROM Pedido AS p WHERE p.categoria.nombre=:nombre";
    	//return em.createQuery(jpql).setParameter("nombre", nombre).getResultList(); //Funciona sin enviarle donde esta la consulta jpql el tipo de retono Pedido, pero marca una alerta.
    	return em.createQuery(jpql, Pedido.class).setParameter("nombre", nombre).getResultList(); //se envia la consulta jpql, y el tipo de retorno(Pedido), setParameter envia los parametros la posicion"nombre" y la variable a buscar nombre y como resultado devuelve una lista de resultados.
        
    }
    
    //Consulta del precio por nombre del pedido -- Este no aplica pero podemos hacer consulta precio por nombre de Cliente(estos metodos se copiaron del ProductoDao).
    public BigDecimal consultarPrecioPorNombreDeProducto(String nombre) {
    	String jpql="SELECT P.precio FROM Pedido AS P WHERE P.nombre=:nombre"; //P.precio seleccionar el precio de P.nombre donde sea igual al nombre dado
    	return em.createQuery(jpql,BigDecimal.class).setParameter("nombre", nombre).getSingleResult(); //Se le envia la consulta jpql y el tipo de retorno BigDecimal, y retorna un resultadoUnico.
    }
    
    
}




