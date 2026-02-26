package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import excepciones.BusinessException;
import jdbc.ConexionJdbc;
import POJOS.Alumno;
import POJOS.Practica;
import POJOS.Presentan;
import dao.DaoGenerico;

/**
 * DAO para la tabla 'presentan' (PK compuesta: CodAl + CodP).
 * Sigue el patrón de DaoHorario/DaoGrupo, con métodos específicos para manejar la clave compuesta.
 */
public class DaoPresentan extends DaoGenerico<Presentan, String> {

	public List<Presentan> buscarPorAlumno(Integer codAl) throws BusinessException {
	    List<Presentan> result = new ArrayList<>();
	    Connection con = ConexionJdbc.getConnection();
	    PreparedStatement pstm = null;
	    ResultSet rs = null;
	    try {
	        String sql = "SELECT * FROM presentan WHERE CodAl = ?";
	        pstm = con.prepareStatement(sql);
	        pstm.setInt(1, codAl);
	        rs = pstm.executeQuery();
	        while (rs.next()) {
	            Presentan p = new Presentan();
	            p.setCodAl(rs.getInt("CodAl"));
	            p.setCodP(rs.getString("CodP"));
	            
	            // Nota puede ser NULL, para evitar que pete 
	            Object notaObj = rs.getObject("Nota");
	            p.setNota(notaObj != null ? ((Number) notaObj).intValue() : null);
	            
	            p.setFechaEntrega(rs.getDate("Fecha_entrega"));
	            result.add(p);
	        }
	        return result;
	    } catch (SQLException ex) {
	        throw new BusinessException("Error al consultar presentan");
	    } finally {
	        ConexionJdbc.cerrar(pstm);
	    }
	}
	
	
	
    @Override
    public void grabar(Presentan pr) throws BusinessException {
        Connection con = ConexionJdbc.getConnection();
        PreparedStatement pstm = null;
        try {
            String sql = "INSERT INTO presentan (CodAl, CodP, Nota, Fecha_entrega) VALUES (?, ?, ?, ?)";
            pstm = con.prepareStatement(sql);
            pstm.setInt(1, pr.getCodAl());
            pstm.setString(2, pr.getCodP());

            if (pr.getNota() != null) {
                pstm.setInt(3, pr.getNota());
            } else {
                pstm.setNull(3, Types.INTEGER);
            }

            if (pr.getFechaEntrega() != null) {
                pstm.setDate(4, new java.sql.Date(pr.getFechaEntrega().getTime()));
            } else {
                pstm.setDate(4, null);
            }

            pstm.executeUpdate();
        } catch (SQLException ex) {
            throw new BusinessException("Error al insertar entrega de práctica");
        } finally {
            ConexionJdbc.cerrar(pstm);
        }
    }

    @Override
    public void actualizar(Presentan pr) throws BusinessException {
        Connection con = ConexionJdbc.getConnection();
        PreparedStatement pstm = null;
        try {
            String sql = "UPDATE presentan SET Nota = ?, Fecha_entrega = ? WHERE CodAl = ? AND CodP = ?";
            pstm = con.prepareStatement(sql);

            if (pr.getNota() != null) {
                pstm.setInt(1, pr.getNota());
            } else {
                pstm.setNull(1, Types.INTEGER);
            }

            if (pr.getFechaEntrega() != null) {
                pstm.setDate(2, new java.sql.Date(pr.getFechaEntrega().getTime()));
            } else {
                pstm.setDate(2, null);
            }

            pstm.setInt(3, pr.getCodAl());
            pstm.setString(4, pr.getCodP());

            pstm.executeUpdate();
        } catch (SQLException ex) {
            throw new BusinessException("Error al actualizar entrega de práctica");
        } finally {
            ConexionJdbc.cerrar(pstm);
        }
    }

    @Override
    public void borrar(Presentan pr) throws BusinessException {
        borrar(pr.getCodAl(), pr.getCodP());
    }

    // Manejo explícito de la PK compuesta
    public void borrar(Integer codAl, String codP) throws BusinessException {
        Connection con = ConexionJdbc.getConnection();
        PreparedStatement pstm = null;
        try {
            String sql = "DELETE FROM presentan WHERE CodAl = ? AND CodP = ?";
            pstm = con.prepareStatement(sql);
            pstm.setInt(1, codAl);
            pstm.setString(2, codP);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            throw new BusinessException("Error al eliminar entrega de práctica");
        } finally {
            ConexionJdbc.cerrar(pstm);
        }
    }

    // Búsqueda por PK compuesta
    public Presentan buscarPorId(Integer codAl, String codP) throws BusinessException {
        Presentan pr = null;
        Connection con = ConexionJdbc.getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM presentan WHERE CodAl = ? AND CodP = ?";
            pstm = con.prepareStatement(sql);
            pstm.setInt(1, codAl);
            pstm.setString(2, codP);
            rs = pstm.executeQuery();

            if (rs.first()) {
                pr = new Presentan();
                pr.setCodAl(rs.getInt("CodAl"));
                pr.setCodP(rs.getString("CodP"));

                // Nota puede ser NULL: usar getObject para detectar nulls
                Object notaObj = rs.getObject("Nota");
                pr.setNota(notaObj != null ? ((Number) notaObj).intValue() : null);

                pr.setFechaEntrega(rs.getDate("Fecha_entrega"));
            }
            return pr;
        } catch (SQLException ex) {
            throw new BusinessException("Error al consultar entrega de práctica");
        } finally {
            ConexionJdbc.cerrar(pstm);
        }
    }

    @Override
    public List<Presentan> buscarTodos() throws BusinessException {
        List<Presentan> result = new ArrayList<>();
        Connection con = ConexionJdbc.getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM presentan ORDER BY CodAl, CodP";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();

            while (rs.next()) {
                Presentan pr = new Presentan();
                pr.setCodAl(rs.getInt("CodAl"));
                pr.setCodP(rs.getString("CodP"));

                Object notaObj = rs.getObject("Nota");
                pr.setNota(notaObj != null ? ((Number) notaObj).intValue() : null);

                pr.setFechaEntrega(rs.getDate("Fecha_entrega"));
                result.add(pr);
            }
            return result;
        } catch (SQLException ex) {
            throw new BusinessException("Error al consultar entregas de prácticas");
        } finally {
            ConexionJdbc.cerrar(pstm);
        }
    }
    
    
   //METODOS CREADOS POR MI PARA HACER EL B2
    
    //1- BUSCAR SOLO POR CODP PARA COMPROBAR QUE NO FUE PRESENTADO TODAVIA
    public Presentan buscarCodP(String codP) throws BusinessException {
        Presentan pr = null;
        Connection con = ConexionJdbc.getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM presentan WHERE CodP = ?";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, codP);
            rs = pstm.executeQuery();

            if (rs.first()) {
                pr = new Presentan();
                pr.setCodP(rs.getString("CodP"));
                }
            return pr;
        } catch (SQLException ex) {
            throw new BusinessException("Error al consultar entrega de práctica");
        } finally {
            ConexionJdbc.cerrar(pstm);
        }
    }
    
    
    //3- MOSTRAR LA CONSULTA CON TODOS LOS DATOS SI EL USUARIO HA HECHO TODO BIEN
    public Presentan mostrarConsultaB2(String codP) throws BusinessException {
        Presentan pr = null;
        Connection con = ConexionJdbc.getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
        	String sql = "SELECT CodAl,CodP,Nota,Fecha_entrega FROM Presentan WHERE CodP = ? ";
        	//No consigo insertar el nombre del alumno, el sql que tenia planteado para mostrar 
        	//todo era este: String sql = "SELECT alumnos.Nombre,alumnos.CodA,presentan.CodAl,Nota,Fecha_entrega from presentan JOIN alumnos on presentan.CodAl=alumnos.CodA WHERE CodP = ?";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, codP);
            
            rs = pstm.executeQuery();

            if (rs.next()) {
                pr = new Presentan();
                pr.setCodP(rs.getString("CodP"));
                pr.setCodAl(rs.getInt("CodAl"));
                pr.setNota(rs.getInt("Nota")); 
                pr.setFechaEntrega(rs.getDate("Fecha_entrega"));
                }
            return pr;
        } catch (SQLException ex) {
            throw new BusinessException("Error al consultar entrega de práctica");
        } finally {
            ConexionJdbc.cerrar(pstm);
        }
    }
}

/* No consigo subirle el id manualmente , lo he puesto abajo del "3" para que sea mas limpio
 * 
 * 
//2- SUBIRLE EL ID DE FORMA MANUAL PORQ SQL NO LO TIENE EN AUTOINCREMENTAL

public Integer subirCodAl(int codAl) throws BusinessException {
    Presentan pr = null;
    Connection con = ConexionJdbc.getConnection();
    PreparedStatement pstm = null;
    ResultSet rs = null;
    try {
        String sql = "SELECT MAX(?) FROM presentan"; //no se si el (?) esta bn implementado , lo he puesto asi para evitar inyecciones SQL
        pstm = con.prepareStatement(sql);
        pstm.setInt(1, codAl);
        rs = pstm.executeQuery();

        if (rs.first()) {
            pr = new Presentan();
            pr.setCodAl(rs.getInt("CodAl"));
            }
        return pr;
    } catch (SQLException ex) {
        throw new BusinessException("Error al consultar entrega de práctica");
    } finally {
        ConexionJdbc.cerrar(pstm);
    }
}
*/
