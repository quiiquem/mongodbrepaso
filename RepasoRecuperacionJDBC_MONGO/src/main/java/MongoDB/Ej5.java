package MongoDB;

import java.util.Scanner;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class Ej5 {
	
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
		
		/*  Inserta una nueva moto en la colección Motos con 
		 * los siguientes datos: matricula "9999ZZZ", marca "Honda",
		 *  estado "disponible", y dentro de propiedades cilindrada "300cc" y tipo "naked".*/

		
		String matricula = "9999ZZZ";
		String marca = "Honda";
		String estado = "disponible";
		String cilindrada = "300cc";
		String tipo = "naked";
		
		Document doc = new Document("matricula", matricula).append("marca", marca)
				.append("estado", estado).append("propiedades", new Document("cilindrada", cilindrada)
						.append("tipo", tipo));
		
		//Insertar
		motos.insertOne(doc);
		
		System.out.println("Moto insertada!!!");
	}
}
