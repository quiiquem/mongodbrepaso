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

public class Ej3 {

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
		
		/*
		 *  Lista todas las motos cuyo estado NO sea "disponible". 
		 *  Muestra matrícula, marca y estado, ordenadas alfabéticamente por marca.*/
		
		Bson nodisponible = Filters.ne("estado","disponible");
		
		//ordenar ascendentemente
		cursor = motos.find(nodisponible).sort(Sorts.ascending("marca")).iterator();
		

		while(cursor.hasNext()) {

			//Pillar datos del doc (deben estar dentro del bucle btw)
			Document doc = cursor.next();
			String matricula = doc.getString("matricula");
			String marca = doc.getString("marca");
			String estado = doc.getString("estado");
			
			 System.out.println("-------------------------");
			 System.out.println("MATRICULA:"+matricula
					+"\nMARCA: "+marca
					+"\nESTADO: "+estado);
			
		}
		   
		
		
	}
}
