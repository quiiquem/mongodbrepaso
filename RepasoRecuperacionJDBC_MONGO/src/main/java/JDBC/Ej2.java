package JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import DAO.DaoPresentan;
import POJOS.Practica;
import POJOS.Presentan;
import excepciones.BusinessException;
import jdbc.ConexionJdbc;

public class Ej2 {

    public static void main(String[] args) {
		
        ConexionJdbc conJdbc = null;
        DaoPresentan pr = new DaoPresentan();
        Scanner sc = new Scanner(System.in);
        
        try {
            conJdbc = new ConexionJdbc("Configuracion/propiedadesInventario.txt");
            conJdbc.conectar();
    	    
            System.out.println("Inserta el codigo del alumno");
            
            Integer CodA = sc.nextInt();
            
            //Llamada al DAO
            List<Presentan> resultado = pr.buscarPorAlumno(CodA);
            
            // Mostrar resultados
            for (Presentan p : resultado) {
                System.out.println(p.getCodAl()+"-"+p.getNota()+"-\n"+p.getCodP());
            }

            sc.close();
            conJdbc.desconectar();
    	    
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
