package MongoDB;

import java.util.Scanner;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class Ej1 {

	public static void main(String[] args) {

		/* Muestra el nombre, apellido y ciudad de origen de alquiler
		 *  de todos los clientes que devolvieron la moto en "Barcelona".*/
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

		// Ver nombre, apellido y ciudad de origen de
		//quienes devolvieron en Barcelona

		// Primer filtro (clientes, filters)

		String ciudad = "Barcelona";
		Bson filtrodevolucion = Filters.eq("alquileres.devol_alquiler", ciudad);
		
		//cursor que busque por todos los que tengan la devolución en Barcelona
		cursor = clientes.find(filtrodevolucion).iterator();  
		
		while(cursor.hasNext()) {
			
			//Pillar datos del doc
			Document doc = cursor.next();
			String nombre = doc.getString("nombre");
			String apellido = doc.getString("apellido");
			
			//Pillar al que esta dentro de aquileres
			Document alquileres = (Document) doc.get("alquileres");
			String ciudad_origen = alquileres.getString("origen_alquiler");
			   
			 System.out.println("-------------------------");
			 System.out.println("NOMBRE:"+nombre
					+"\nAPELLIDO: "+apellido
					+"\nCIUDAD DE ORIGEN: "+ciudad_origen);
			 System.out.println("-------------------------");
		}
	}
}
