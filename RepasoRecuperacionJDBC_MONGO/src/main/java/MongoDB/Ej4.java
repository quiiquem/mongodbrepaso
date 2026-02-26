package MongoDB;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;

public class Ej4 {

public static void main(String[]args){
	
	MongoClient conectarmongo = MongoClients.create();
	System.out.println("Conectado al mongoclient");
	// Conectar a BD
	MongoDatabase database = conectarmongo.getDatabase("repasomongo");
	System.out.println("Conectado a la base de datos del exámen");

	// Conectar colecciones
	MongoCollection<Document> clientes = database.getCollection("Cliente");
	System.out.println("Coleccion clientes conectado");
	MongoCollection<Document> motos = database.getCollection("Motos");
	System.out.println("Coleccion motos conectado");
	

	MongoCursor<Document> cursor = null; // cursor para recorrer todo
	
	/* Lista todos los clientes cuyo alquiler empezó a partir del 
	 * 10 de noviembre de 2023 (fecha_pres >= 2023-11-10). 
	 * Muestra nombre, apellido y fecha de préstamo, ordenados por fecha de préstamo ascendente.*/
	
	Bson fechasuperior = Filters.gt("alquileres.fecha_pres", "2023-11-10");
	
	cursor = clientes.find(fechasuperior).sort(Sorts.ascending("alquileres.fecha_pres")).iterator();
	
	while (cursor.hasNext()) {
		//Pillar datos del doc (deben estar dentro del bucle btw)
		Document doc = cursor.next();
		String nombre = doc.getString("nombre");
		String apellido = doc.getString("apellido");
		Document alquileres = (Document) doc.get("alquileres");
		String fecha = alquileres.getString("fecha_pres");
		
		 System.out.println("-------------------------");
		 System.out.println("NOMBRE:"+nombre
				+"\nAPELLIDO: "+apellido
				+"\nFECHA PRESTAMO: "+fecha);
		
	}

}
}
