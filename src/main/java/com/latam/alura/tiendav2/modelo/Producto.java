package com.latam.alura.tiendav2.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/* @Entity - Hibernate y JPA, ellos realizan un mapeamento de los elementos existentes en 
nuestra clase para compararlos con el modelo en la base de datos, entonces por 
default Hibernate entiende y JPA, entiende que el nombre de la clase es el mismo
 nombre que existe para la tabla.
*/

@Entity
//La tabla en la BD se llama productos, si la tama se llama igual que la clase "producto" no hay necesidade de definir la tabla con @table
@Table(name="productos") 

//@NameQuery Sirve para agregar consultas a la BD Solo que que definir el metodo consultaDePrecio en ProductoDao
@NamedQuery(name="Producto.consultaDePrecio", query="SELECT P.precio FROM Producto AS P WHERE P.nombre=:nombre")  // Producto.consultaDePrecio  - Producto(tabla a consultar), consultaDePrecio(nombre de la query)
//@Inheritance(strategy=InheritanceType.SINGLE_TABLE)  //Para Conectar con la entidades(clases hijas) Electronico y libros con la entidad Producto(clase Padre). -InheritanceType.SINGLE_TABLE, nosotros creamos una única tabla con todos los atributos de la tabla producto y las clase hijas: Electronicos y Libros. Esto ayuda en el desempeño y rendimiento de la app. La desventaja es que vamos a tener una gran cantidad de elementos en una única tabla. Eso puede llegar a ser confuso.
@Inheritance(strategy=InheritanceType.JOINED)  //Para Conectar con la entidades(clases hijas) Electronico y libros con la entidad Producto(clase Padre). -InheritanceType.JOINED, con la estrategia de Join, nosotros vamos a construir tablas separadas. De esta forma disminuye el desempeño un poco más lento a la hora de realizar la carga. Pero tenemos todos los elementos de forma más organizada y vamos a tener llaves extranjeras que dentro de las entidades libros y electrónicos identifican esa llave primaria en la entidad producto.
public class Producto { //El nombre de la clase tiene que ser igual que la tabla de la BD, si no es igual entonces la definimos arriba con @Table(name="productos")  - para asi, realizar el mapeamiento.
	//Los atributos de la clase deben de ser igual que los campos de la BD. Sino, entonces se debe definir arriba del atributo de la clase, utilizar @Column(name="nombres")  - para asi, realizar el mapeamiento.
	
	@Id  //Para indicarle a nuestro proyecto cuál va a ser el id o el identificador de cada una de esas filas,
	@GeneratedValue(strategy= GenerationType.IDENTITY) //Indica que quien tiene la responsabilidad de generar ese identificador id va a ser siempre de la BD. Entonces, para nosotros indicarle que la responsabilidad no pertenece al usuario, sino a la base de datos.
	private Long id;
	private String nombre;
	private String descripcion;
	private BigDecimal precio;
	
	private LocalDate fechaDeRegistro=LocalDate.now(); //El método estático LocalDate.now, con eso nosotros aseguramos que al ser instanciado el producto se esté guardando la fecha actual en la que se está instanciando.
	
	//@Enumerated(EnumType.STRING) //No se uso enum ahora es clase categoria - Elemento tipo Enumerador para categoria. Vamos a guardar la palabra como string. Usamos la anotación de JPA @Enumerate. Esa anotación tiene un parámetro (EnumType) del tipo string. Ella una serie de valores y nosotros vamos a usar el string, que nos va a permitir guardar la palabra que está siendo registrada en el enumerador. //Con esto guarda la palabra y no la posicion de la palabra en el arreglo.
	/*
	 * Por motivo de Rendimiento.	 
	 Todos los elementos del tipo ManyToMany por default son eager, trae todos los elementos y si dentro de ese tributo hay otros elementos del tipo ManyToOne también los va a traer. Por lo que hay que agregarle LAZY cargamento perezoso.
	 Ya los elementos del tipo OneToMany o ManyToMany, ellos por default ya son del tipo lazy.	 
	 //con Fetch le indicamos a la app que traeremos esos recursos cuando sea necesario.
	 */
	@ManyToOne(fetch=FetchType.LAZY) //Muchos productos tienen una unica categoria. - Con esto relacionamos la entidad producto con Categoria
	private Categoria categoria; //Tipo Categoria, sera una clase. //No tener creada la clase Categoria da un error entonces: Clic derecho sobre la cateroria y clic en Create enum 'Categoria'
	
	/*Es parte de las buenas prácticas que toda nuestra aplicación sea lazy, para evitar el consumo excesivo de memoria y agilizar aumentar la velocidad de nuestra aplicación, ya que vamos a evitar que estén siendo consultados valores o informaciones que no sean deseados dentro de nuestra consulta.
	 * Sin embargo, uno de los problemas que puede ocurrir cuando nosotros agregamos el parámetro lazy a una notación que es del tipo eager, nos encontremos con una excepción, ya que puede ocurrir que para ese punto el EntityManager se encuentre cerrado.
	 */
	
	
	public Producto() { //para probar el flush de RegistroDeProducto
		
	}
	
	
	// En el constructor no se agrego id ya que se genera automaticamente y fechaDeRegistro se agrega un valor al momento de crear el producto
	public Producto(String nombre, String descripcion, BigDecimal precio, Categoria categoria) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.categoria = categoria;
	}
	
	
	//Getters and Setters
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public BigDecimal getPrecio() {
		return precio;
	}
	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}
	
	

}
