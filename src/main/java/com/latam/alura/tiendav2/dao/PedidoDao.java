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
    
    //Consulta por Nombre
    public List<Pedido> consultaPorNombre(String nombre){
    	String jpql = " SELECT P FROM Pedido AS P WHERE P.nombre=:nombre"; //si queremos agregar mas parametros a buscar seria asi:  (WHERE  P.nombre=:nombre AND P.descripcion=:descripcion";
    	//return em.createQuery(jpql).setParameter("nombre", nombre).getResultList(); // recibe la posicion("nombre") y se le envia la variable(nombre) que contiene la palabra a buscar, y obtiene una lista de resultados.  funciona sin enviarle el tipo de retorno Pedido.class donde va la consulta. Funciona pero marca alerta.
    	return em.createQuery(jpql,Pedido.class).setParameter("nombre", nombre).getResultList(); //se envia la consulta jpql, y el tipo de retorno(Pedido.class), setParameter envia los parametros la posicion"nombre" y la variable a buscar nombre y como resultado devuelve una lista de resultados.
    }
    
    //Consulta por nombre de Categoria  
    public List<Pedido> consultaPorNombreDeCategoria(String nombre){
    	String jpql="SELECT p FROM Pedido AS p WHERE p.categoria.nombre=:nombre";
    	//return em.createQuery(jpql).setParameter("nombre", nombre).getResultList(); //Funciona sin enviarle donde esta la consulta jpql el tipo de retono Pedido, pero marca una alerta.
    	return em.createQuery(jpql, Pedido.class).setParameter("nombre", nombre).getResultList(); //se envia la consulta jpql, y el tipo de retorno(Pedido), setParameter envia los parametros la posicion"nombre" y la variable a buscar nombre y como resultado devuelve una lista de resultados.
        
    }
    
    //Consulta del precio por nombre del pedido
    public BigDecimal consultarPrecioPorNombreDeProducto(String nombre) {
    	String jpql="SELECT P.precio FROM Pedido AS P WHERE P.nombre=:nombre"; //P.precio seleccionar el precio de P.nombre donde sea igual al nombre dado
    	return em.createQuery(jpql,BigDecimal.class).setParameter("nombre", nombre).getSingleResult(); //Se le envia la consulta jpql y el tipo de retorno BigDecimal, y retorna un resultadoUnico.
    }
    
}




