package com.latam.alura.tiendav2.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
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
	private BigDecimal valorTotal;
	
	@ManyToOne  //Muchos a uno.  
	private Cliente cliente;  //1 cliente tiene muchos pedidos
	

	//Relacion con: Pedidos --> Productos (muchos a muchos): Un pedido puede tener múltiples productos y esos múltiples productos pueden encontrarse en muchos pedidos.
	// Cuando tenemos una relacion Muchos a muchos generamos una nueva tabla. Seria pedidos uno --a-> muchos items_pedido(nueva tabla) y de esta va de muchos --a-> uno con productos.
	
	/*//Forma de relacionar 2 tablas con relacion muchos a muchos sin necesidad de crear una nueva entidad(clase). relacion a sus campos: producto_id y pedido_id 
	@ManyToMany  //Relacion de Muchos a Muchos
	@JoinTable(name="items_pedido") //Con esto mapeamos producto y pedido. //Para nosotros no crear una nueva entidad JPA genera de forma automática esa nueva tabla a través de la notación @JoinTable y le podemos dar el nombre de la nueva tabla. Esta nueva tabla que permite relacionar los productos con los pedidos, es creada a través de la notación @JoinTable.
	private List<Producto> productos; //como vamos a tener múltiples productos, tenemos que realizar una lista que almacene elementos del tipo producto.
	*/
	
	//Forma de relacionar 2 tablas con relacion muchos a muchos. Necesidad de crear una nueva tabla en la cual se le agregar otros atributos aparte de los id  de las tablas a unir.	
	@OneToMany //Del otro lado colocamos: ManyToOne entonces aqui va OneToOne  -- Si eliminamos el One en amabas queda: ManyToToMany (muchos a muchos) :    [ ManyToOne OneToMany (eliminando el One en ambas) ManyToToMany(muchos a muchos) ]
	private List<ItemsPedidos> items; //Siempre que tengamos una lista es OneToMany o es un elemento que termina en ToMany. 
	
	
	//Constructor sin parametros
	public Pedido() {
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
	
	
}
