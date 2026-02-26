package JDBC;
import DAO.DaoAlumno;
import DAO.DaoPractica;
import jdbc.ConexionJdbc;
import POJOS.Alumno;
import POJOS.Practica;
import excepciones.BusinessException;
import java.util.List;
import java.util.Scanner;

public class Ej1 {
    public static void main(String[] args) {
        ConexionJdbc conJdbc = null;
        DaoPractica daoPractica = new DaoPractica();
        Scanner datos = new Scanner(System.in);

        try {
            conJdbc = new ConexionJdbc("Configuracion/propiedadesInventario.txt");
            conJdbc.conectar();

            System.out.println("Inserta un curso");
            Scanner sc = new Scanner(System.in);
            
            int curso = sc.nextInt();
            // Llamar al DAO
            List<Practica> resultado = daoPractica.buscarporcurso(curso);

            // Mostrar resultados
            for (Practica p : resultado) {
                System.out.println(p.getCurso()+"-"+p.getPuntos()+"-\n"+p.getFechaLimite());
            }

            datos.close();
            conJdbc.desconectar();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}