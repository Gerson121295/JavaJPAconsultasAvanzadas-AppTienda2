package com.latam.alura.tiendav2.prueba;

import java.io.FileNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.latam.alura.tiendav2.dao.PedidoDao;
import com.latam.alura.tiendav2.modelo.Pedido;
import com.latam.alura.tiendav2.utils.JPAUtils;

public class PruebaDeDesempenho {

	public static void main(String[] args) throws FileNotFoundException {
		LoadRecords.cargarRegistros(); //Cargamos los registros.
		
		EntityManager em = JPAUtils.getEntityManager(); //Instanciamos el EntityManager

		//Prueba de Desempeño imprimimos un pedido #3
		
		//Como se le agrego Fetch: @ManyToOne (fetch=FetchType.LAZY) ya no se utiliza el metodo find. ahora se crea un nuevo metodo en PedidoDao para crear el pedido.
		//Pedido pedido = em.find(Pedido.class, 3l); 
		
		PedidoDao pedidoDao = new PedidoDao(em);//Instanciar el PedidoDao
		Pedido pedidoConCliente = pedidoDao.consultarPedidoConCliente(2l);
		
		//al cerrar el EntityManager(conexion a BD) nos genera una excepcion ya que a los @ManyToOne(que es tipo por default eager) se agrego Fetch: @ManyToOne (fetch=FetchType.LAZY)
		em.close(); 
		
		
		//System.out.println(pedido.getFecha());  //De ese pedido obtenemos la fecha para ver como realiza la consulta
		//System.out.println(pedido.getItems().size()); //itemPedido para ver el tamaño de la lista.
		System.out.println(pedidoConCliente.getCliente().getNombre());
		
		
		
	}

}
