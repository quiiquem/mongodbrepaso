package MongoDB;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class Ej2 {

	public static void main(String[]args) {
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
		
		/*1.2 (0.5p) — Lista las motos cuyo estado sea "disponible" y cuyo tipo 
 * (dentro de propiedades) sea "scooter". Muestra solo matrícula, marca y cilindrada.*/
		
		//Solo estado disponible y tipo scooter
		Bson filtroEj2 =  Filters.and(Filters.eq("estado", "disponible"),
				Filters.eq("propiedades.tipo","scooter"));
		
				cursor = motos.find(filtroEj2).iterator();  
				
				while(cursor.hasNext()) {
					
					//Pillar datos del doc
					Document doc = cursor.next();
					
					String matricula = doc.getString("matricula");
					String marca = doc.getString("marca");
					
					Document propiedades = (Document)doc.get("propiedades");
					
					String cilindrada = propiedades.getString("cilindrada");
					
					   
					 System.out.println("-------------------------");
					 System.out.println("MATRICULA:"+matricula
							+"\nMARCA: "+marca
							+"\nCILINDRADA: "+cilindrada);
					 System.out.println("-------------------------");
				}
	}
}
