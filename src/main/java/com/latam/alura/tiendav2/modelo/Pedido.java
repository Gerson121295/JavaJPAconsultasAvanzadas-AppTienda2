package com.latam.alura.tiendav2.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="pedidos")
public class Pedido {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private LocalDate fecha = LocalDate.now(); //= LocalDate.now()  para que al crear un pedido se agregue automaticamente la fecha.
	private BigDecimal valorTotal;
	
	@ManyToOne
	private Cliente cliente;  //1 cliente tiene muchos pedidos
	

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
