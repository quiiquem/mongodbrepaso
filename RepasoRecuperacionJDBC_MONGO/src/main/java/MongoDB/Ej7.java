package MongoDB;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;

public class Ej7 {

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
		
		/*  Elimina de la colección todas las motos cuyo estado sea "mantenimiento". 
		 * Imprime por consola cuántas motos se han eliminado..*/
		

		//Pillar los q se llaman igual
		Bson ver_mantenimiento = Filters.eq("estado","mantenimiento");
		
		//Para hacer el count
		DeleteResult resultado = motos.deleteMany(ver_mantenimiento);
		
		//Cargarse a todos los q tengan estado mantenimento
		motos.deleteMany(ver_mantenimiento);

		System.out.println("Borraos"+resultado.getDeletedCount());
	}
	
}
