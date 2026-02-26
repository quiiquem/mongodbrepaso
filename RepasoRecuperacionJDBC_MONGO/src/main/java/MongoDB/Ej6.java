package MongoDB;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class Ej6 {
	
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
		
		/*  Actualiza todas las motos en estado "mantenimiento" 
		 * para añadirles un campo nuevo revision_pendiente con valor true.*/

		//Pillar los q se llaman igual
		Bson ver_mantenimiento = Filters.eq("estado","mantenimiento");

		//Actualizar TODOS los q tengan ese rol con el nuevo campo
		motos.updateMany(ver_mantenimiento,
			    new Document("$set", new Document("revision_pendiente", true)));

			System.out.println("Actualizados!");
		
		System.out.println("Actualizados!");
	
	}

}
