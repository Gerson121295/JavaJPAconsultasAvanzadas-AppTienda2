package com.latam.alura.tiendav2.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="pedidos")
public class Pedido {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private LocalDate fecha = LocalDate.now(); //= LocalDate.now()  Para que al crear o instanciar un pedido se agregue automaticamente la fecha.
	private BigDecimal valorTotal = new BigDecimal(0); //valorTotal; estaba nulo por lo que se debio inicializar: valorTotal = new BigDecimal(0); 
	
	/*
	 Debido a que Todos los elementos que tengan la anotación del tipo ToOne, ya sean ManyToOne o OneToOne. tienen una estrategia de cargamento que es del 
	 tipo eager, una estrategia de cargamento anticipada.
	 Es decir, siempre que nosotros instanciemos una entidad, llamemos una tabla de la base de datos, y que uno de sus atributos, en este caso es cliente, tenga la notación del tipo ToOne, 
	o sea, OneToOne, ManyToOne, él va a ser un join con todos los atributos que tengan esas notaciones.
	Entonces si lo tengo acá cinco atributos del tipo ManyToOne, él va a realizar un join con esas cinco entidades a pesar de que nosotros no las estemos utilizando.

	 Para corregir error de desempeño, y es parte de las buenas prácticas es utilizar un atributo en la notación ManyToOne, que es el atributo fetch=FetchType.LAZY.	
	 Para las anotaciones del tipo ManyToOne, nosotros vamos a utilizar el cargamento o la estrategia de cargamento del tipo lazy o perezoso, que nos va a permitir llamar los elementos de cliente únicamente cuando sean solicitados.  
	 */	
	@ManyToOne (fetch=FetchType.LAZY) //Muchos a uno. //tipo lazy o cargamento perezoso.  
	private Cliente cliente;  //1 cliente tiene muchos pedidos
	
	/*	 
	 Entonces, todos los elementos del tipo ManyToMany por default son eager, trae todos los elementos y si dentro de ese tributo hay otros elementos del tipo ManyToOne también los va a traer. Por lo que hay que agregarle LAZY cargamento perezoso.
	 Ya los elementos del tipo OneToMany o ManyToMany, ellos por default ya son del tipo lazy.	 
	 //con Fetch le indicamos a la app que traeremos esos recursos cuando sea necesario.
	 */
	/*Es parte de las buenas prácticas que toda nuestra aplicación sea lazy, para evitar el consumo excesivo de memoria y agilizar aumentar la velocidad de nuestra aplicación, ya que vamos a evitar que estén siendo consultados valores o informaciones que no sean deseados dentro de nuestra consulta.
	 * Sin embargo, uno de los problemas que puede ocurrir cuando nosotros agregamos el parámetro lazy a una notación que es del tipo eager, nos encontremos con una excepción, ya que puede ocurrir que para ese punto el EntityManager se encuentre cerrado. 
	 */
	
	
	//Relacion con: Pedidos --> Productos (muchos a muchos): Un pedido puede tener múltiples productos y esos múltiples productos pueden encontrarse en muchos pedidos.
	// Cuando tenemos una relacion Muchos a muchos generamos una nueva tabla. Seria pedidos uno --a-> muchos items_pedido(nueva tabla) y de esta va de muchos --a-> uno con productos.
	
	/*//Forma de relacionar 2 tablas con relacion muchos a muchos sin necesidad de crear una nueva entidad(clase). relacion a sus campos: producto_id y pedido_id 
	@ManyToMany  //Relacion de Muchos a Muchos
	@JoinTable(name="items_pedido") //Con esto mapeamos producto y pedido. //Para nosotros no crear una nueva entidad JPA genera de forma automática esa nueva tabla a través de la notación @JoinTable y le podemos dar el nombre de la nueva tabla. Esta nueva tabla que permite relacionar los productos con los pedidos, es creada a través de la notación @JoinTable.
	private List<Producto> productos; //como vamos a tener múltiples productos, tenemos que realizar una lista que almacene elementos del tipo producto.
	*/
	
	//Forma de relacionar 2 tablas con relacion muchos a muchos. Necesidad de crear una nueva tabla en la cual se le agregar otros atributos aparte de los id  de las tablas a unir.	
	
	/* Hay una propiedad en la notación OneToMany que nos permite que a la hora de realizar una acción en la entidad pedido, realice esa acción en cascada para la entidad que está 
	relacionada, que en este caso sería la entidad items_pedido. Entonces esa propiedad se llama cascade.
	
	Y el tipo cascade=CascadeType, tenemos una serie de opciones. Esa alteración en cascada, esa acción en cascada puede ocurrir cuando yo elimine un archivo de pedido, cuando yo guarde 
	por primera vez un archivo en pedido, cuando actualice, cuando hago el merge que lo traigo de la base de datos o en todos los casos(ALL), que es el que me interesa.

 	cascade=CascadeType.ALL  - Cada vez que yo realice una operación con pedido, que haga una alteración en items_pedido. 
	 */
	

	@OneToMany(mappedBy = "pedido", cascade=CascadeType.ALL) //Del otro lado colocamos: ManyToOne entonces aqui va OneToOne  -- Si eliminamos el One en amabas queda: ManyToToMany (muchos a muchos) :    [ ManyToOne OneToMany (eliminando el One en ambas) ManyToToMany(muchos a muchos) ]
	//mappedBy = "pedido" sirve para indicarle que la lista se encuentra mapeada por el elemento pedido existente en la entidad items_pedido. //Con esto conectamos esta lista con items_pedido.
	private List<ItemsPedido> items = new ArrayList<>(); //Siempre que tengamos una lista es OneToMany o es un elemento que termina en ToMany. //con: = new ArrayList<>() inicializamos la lista para que sea una lista vacia ya sin esto seria una lista nula.
	
	
	//Constructor sin parametros
	public Pedido() {
	}
	
	//Agregar valores a la lista itemsPedido.
	public void agregarItems(ItemsPedido item) {
		item.setPedido(this);  //el item debe de estar relacionado con el pedido por lo que le pasamos el pedido
		this.items.add(item);
		this.valorTotal = this.valorTotal.add(item.getValor());
	}
	
	//Constructor con parametro cliente. El id, fecha no se agrego por serán generados automaticamente por eso no es necesario crearlos en el constructor y el valor será calculado.
	public Pedido(Cliente cliente) {
		this.cliente = cliente;
	}
	
	
	//Getters and Setters
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDate getFecha() {
		return fecha;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	public BigDecimal getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<ItemsPedido> getItems() {
		return items;
	}

	public void setItems(List<ItemsPedido> items) {
		this.items = items;
	}
	
	
}
