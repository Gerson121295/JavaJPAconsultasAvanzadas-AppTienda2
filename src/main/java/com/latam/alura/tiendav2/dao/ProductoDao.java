package com.latam.alura.tiendav2.dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.latam.alura.tiendav2.modelo.Producto;

public class ProductoDao {

	/*
	 * Esta clase va a tener los métodos de consulta, así como de guardar o 
	 * actualizar un registro. Para eso tiene que utilizar el EntityManager, 
	 * por lo que se lo vamos a pasar como atributo hacer un atributo privado 
	 * del tipo EntityManager.
	 * 
	 */
	private EntityManager em;

	public ProductoDao(EntityManager em) {
		this.em = em;
	}
	
	public void guardar(Producto producto) {
		this.em.persist(producto);
	}
	
	public void actualizar(Producto producto) {
        this.em.merge(producto);
    }

    public void remover(Producto producto) {
        producto=this.em.merge(producto);
        this.em.remove(producto);
    }
    
    /*//code dado
    public void remover(Producto producto) {
        categoria=this.em.merge(producto);
        this.em.remove(producto);
    }
	*/
    
    //Consulta por id
    public Producto consultaPorId(Long id) {
    	return em.find(Producto.class, id);
    }
	
    //Consultar todos los elementos(productos) de la tabla Producto
    public List<Producto> consultarTodos(){
    	String jpql = "SELECT P FROM Producto AS P "; //en JPQL no se utiliza el * si no la letra del alias definido: SELECT * FROM Producto P;  //lista a traves de JPQL (Java Persistence Query Language)
    	return em.createQuery(jpql, Producto.class).getResultList();
    }
    
    //Consulta por Nombre
    public List<Producto> consultaPorNombre(String nombre){ //enviamos el nombre a buscar
    	String jpql = " SELECT P FROM Producto AS P WHERE P.nombre=:nombre"; //si queremos agregar mas parametros a buscar seria asi:  (WHERE  P.nombre=:nombre AND P.descripcion=:descripcion";
    	//return em.createQuery(jpql).setParameter("nombre", nombre).getResultList(); // recibe la posicion("nombre") y se le envia la variable(nombre) que contiene la palabra a buscar, y obtiene una lista de resultados.  funciona sin enviarle el tipo de retorno Producto.class donde va la consulta. Funciona pero marca alerta.
    	return em.createQuery(jpql,Producto.class).setParameter("nombre", nombre).getResultList(); //se envia la consulta jpql, y el tipo de retorno(Producto.class), setParameter envia los parametros la posicion"nombre" y la variable a buscar nombre y como resultado devuelve una lista de resultados.
    }
    
    //Consulta por nombre de Categoria  
    public List<Producto> consultaPorNombreDeCategoria(String nombre){
    	String jpql="SELECT p FROM Producto AS p WHERE p.categoria.nombre=:nombre"; //el nombre de la tabla debe estar escrito como definimos el nombre de la Clase Producto.  
    	//return em.createQuery(jpql).setParameter("nombre", nombre).getResultList(); //Funciona sin enviarle donde esta la consulta jpql el tipo de retono Producto, pero marca una alerta.
    	return em.createQuery(jpql, Producto.class).setParameter("nombre", nombre).getResultList(); //se envia la consulta jpql, y el tipo de retorno(Producto), setParameter envia los parametros la posicion"nombre" y la variable a buscar nombre y como resultado devuelve una lista de resultados.
        
    }
    
    //Consulta del precio por nombre del producto
    public BigDecimal consultarPrecioPorNombreDeProducto(String nombre) {
    	String jpql="SELECT P.precio FROM Producto AS P WHERE P.nombre=:nombre"; //P.precio seleccionar el precio de P.nombre donde sea igual al nombre dado - //el nombre de la tabla debe estar escrito como definimos el nombre de la Clase Producto.
    	return em.createQuery(jpql,BigDecimal.class).setParameter("nombre", nombre).getSingleResult(); //Se le envia la consulta jpql y el tipo de retorno BigDecimal, y retorna un resultadoUnico.
    }
    
    
    //Consultas utilizando @NamedQquery definida en la entidad o clase Producto, aqui se define el metodo y alla la consulta con el Select
    public BigDecimal consultaDePrecioPorNombreDelProducto(String nombre) {
    	return em.createNamedQuery("Producto.consultaDePrecio",BigDecimal.class).setParameter("nombre", nombre).getSingleResult(); //Se le envia consultaDePrecio nombre de la consulta definida en la entidad o clase Producto y el tipo de retorno BigDecimal, y retorna un resultado Unico.
    }
    
    //Consultas con Parametros dinamicos
    //Queremos una lista de elemento que coincidan con los parametros dados como: nombre, precio, fecha
    public List<Producto> consultarPorParametros(String nombre, BigDecimal precio, LocalDate fecha){
    	StringBuilder jpql=new StringBuilder("SELECT p FROM Producto p WHERE 1=1 "); 
    			if(nombre!= null && !nombre.trim().isEmpty()) {//Nombre no sea nulo y nombre no este vacio.
    				jpql.append("AND p.nombre=:nombre");
    			}
    			if(precio!=null && !precio.equals(new BigDecimal(0))){
    				jpql.append("AND p.precio=:precio");
    			}
    			if(fecha!=null){//se puede agregar que la fecha sea > o < o una fecha especifica
    				jpql.append("AND p.fechaDeRegistro=:fecha");			
    			}
    			
    			TypedQuery<Producto> query = em.createQuery(jpql.toString(),Producto.class);
    			//Establecer los parametros.
    			if(nombre!= null && !nombre.trim().isEmpty()) {//Nombre no sea nulo y nombre no este vacio.
    				query.setParameter("nombre", nombre);
    			}
    			if(precio!=null && !precio.equals(new BigDecimal(0))){
    				query.setParameter("precio", precio);
    			}
    			if(fecha!=null){//se puede agregar que la fecha sea > o < o una fecha especifica
    				query.setParameter("fechaDeRegistro", fecha); //fecha es el nombre del parametro.			
    			}
    			//retorno
    			return query.getResultList();
    
    }
    
    
    //Consultas con Criteria API Basada en la Anterior(Consultas con Parametros dinamicos)
    
  //Queremos una lista de elemento que coincidan con los parametros dados como: nombre, precio, fecha
    public List<Producto> consultarPorParametrosConAPICriteria(String nombre, BigDecimal precio, LocalDate fecha){
			
    		CriteriaBuilder builder =  em.getCriteriaBuilder(); //getCriteriaBuilder permite construir la consulta a lo largo del metodo
    		CriteriaQuery<Producto> query = builder.createQuery(Producto.class);//pasarle cual va a ser el contexto de la consulta es: Producto, query es la variable que guarda.
    		
    		//Establecemos el FROM
    		Root<Producto> from = query.from(Producto.class); //query.from(Producto.class) lo guardamos en la variable from
    		
    		//query.select(from) //para consulta
    		
    		Predicate filtro = builder.and(); //filtro va a estar vacio las condiciones las agrega.
    		//condiciones
    		if(nombre!= null && !nombre.trim().isEmpty()) {//Nombre no sea nulo y nombre no este vacio.
    				filtro=builder.and(filtro,builder.equal(from.get("nombre"), nombre));			
    			}
    			if(precio!=null && !precio.equals(new BigDecimal(0))){
    				filtro=builder.and(filtro,builder.equal(from.get("precio"), precio));	
    			}
    			if(fecha!=null){//se puede agregar que la fecha sea > o < o una fecha especifica
    				filtro=builder.and(filtro,builder.equal(from.get("fechaDeRegistro"), fecha));				
    			}
    			
    			query =query.where(filtro);
    			return em.createQuery(query).getResultList();
    
    }
    
    
    
    
}




