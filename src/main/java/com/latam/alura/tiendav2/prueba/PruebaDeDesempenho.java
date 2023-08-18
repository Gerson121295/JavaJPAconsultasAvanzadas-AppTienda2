package com.latam.alura.tiendav2.prueba;

import java.io.FileNotFoundException;

import javax.persistence.EntityManager;

import com.latam.alura.tiendav2.modelo.Pedido;
import com.latam.alura.tiendav2.utils.JPAUtils;

public class PruebaDeDesempenho {

	public static void main(String[] args) throws FileNotFoundException {
		LoadRecords.cargarRegistros(); //Cargamos los registros.
		
		EntityManager em = JPAUtils.getEntityManager(); //Instanciamos el EntityManager

		//Prueba de Desempeño imprimimos un pedido #3
		Pedido pedido = em.find(Pedido.class, 3l);
		//System.out.println(pedido.getFecha());  //De ese pedido obtenemos la fecha para ver como realiza la consulta
		//System.out.println(pedido.getItems().size()); //itemPedido para ver el tamaño de la lista.
		System.out.println(pedido.getCliente().getNombre());
	}

}
