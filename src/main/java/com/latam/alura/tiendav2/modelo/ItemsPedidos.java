package com.latam.alura.tiendav2.modelo;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "items_pedido")
public class ItemsPedidos {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private int cantidad;
	private BigDecimal precioUnitario;
	
	//Relaciones
	@ManyToOne //
	private Producto producto;
	
	@ManyToOne //
	private Pedido pedido;
	
	//Cada ves que se instancia un itemspedido:
	//itemped 1 prod 1 ped 2  //Instancia 1 va a tener producto 1 pedido 2
	//itemped 2 prod 2 ped 2  //Instancia 2 va a tener producto 2 pedido 2  vemos que se va a tener el mismo identificador 2 (producto y pedido) pero tendran diferentes productos
	//itemped 3 prod 1 ped 1
	//itemped 4 prod 3 ped 1
	/*Entonces vemos que este pedido va a tener el mismo identificador pero diferentes productos. Entonces, vemos que tenemos un pedido con diferentes productos, eso nos recuerda 
	 * a un cliente con muchos pedidos o un producto con una categoría que pertenece a muchos productos. 
	 */
	
	public ItemsPedidos() {
	}

	public ItemsPedidos(int cantidad, Producto producto, Pedido pedido) {
		this.cantidad = cantidad;
		this.producto = producto;
		this.pedido = pedido;
	}
	
	//Getters and Setters

	public Long getId() {
		return id;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(BigDecimal precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	
	
}
