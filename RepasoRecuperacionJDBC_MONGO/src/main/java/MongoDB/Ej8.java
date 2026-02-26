package MongoDB;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class Ej8 {

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
		
		/*  Muestra el nombre, apellido y matrícula de la moto alquilada
		 *  para los clientes cuyo devol_alquiler sea "Sevilla". Para obtener la matrícula
		 *  necesitas cruzar los datos de las dos colecciones — piensa cómo hacerlo con lo que tienes..*/

		
		// PASO 1 — filtro en clientes
		cursor = clientes.find(Filters.eq("alquileres.devol_alquiler", "Sevilla")).iterator();

		while (cursor.hasNext()) {
		    Document cliente = cursor.next();

		    // campos directos de clientes
		    String nombre = cliente.getString("nombre");

		    // objeto anidado de clientes
		    Document alquileres = (Document) cliente.get("alquileres");
		    //Pilla el campo "moto" (es lo q lo enlaza)
		    Integer idMoto = alquileres.getInteger("moto");

		    // PASO 2 — buscar en motos con ese id
		    Document mota = motos.find(Filters.eq("id", idMoto)).first();

		    // campos de motos
		    String matricula = mota.getString("matricula");

		    System.out.println(nombre + " -> " + matricula);
		}
		
	}
}
