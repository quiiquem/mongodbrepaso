package DAO;

import java.sql.*;
import java.util.*;
import excepciones.BusinessException;
import jdbc.ConexionJdbc;
import POJOS.GrupoMatri;
import dao.DaoGenerico;

public class DaoGrupoMatri extends DaoGenerico<GrupoMatri, String> {

    // En este caso, la PK es compuesta (CodG + CodAl).
    // Para simplificar, usaremos un objeto GrupoMatri en los métodos de búsqueda/borrado.

    @Override
    public void grabar(GrupoMatri gm) throws BusinessException {
        Connection con = ConexionJdbc.getConnection();
        PreparedStatement pstm = null;
        try {
            String sql = "INSERT INTO gruposmatri (CodG, CodAl, CodH) VALUES (?, ?, ?)";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, gm.getCodG());
            pstm.setInt(2, gm.getCodAl());
            pstm.setString(3, gm.getCodH());
            pstm.executeUpdate();
        } catch (SQLException ex) {
            throw new BusinessException("Error al insertar matrícula de grupo");
        } finally {
            ConexionJdbc.cerrar(pstm);
        }
    }

    @Override
    public void actualizar(GrupoMatri gm) throws BusinessException {
        Connection con = ConexionJdbc.getConnection();
        PreparedStatement pstm = null;
        try {
            String sql = "UPDATE gruposmatri SET CodH = ? WHERE CodG = ? AND CodAl = ?";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, gm.getCodH());
            pstm.setString(2, gm.getCodG());
            pstm.setInt(3, gm.getCodAl());
            pstm.executeUpdate();
        } catch (SQLException ex) {
            throw new BusinessException("Error al actualizar matrícula de grupo");
        } finally {
            ConexionJdbc.cerrar(pstm);
        }
    }

    @Override
    public void borrar(GrupoMatri gm) throws BusinessException {
        borrar(gm.getCodG(), gm.getCodAl());
    }

    // Método específico para clave compuesta
    public void borrar(String codG, Integer codAl) throws BusinessException {
        Connection con = ConexionJdbc.getConnection();
        PreparedStatement pstm = null;
        try {
            String sql = "DELETE FROM gruposmatri WHERE CodG = ? AND CodAl = ?";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, codG);
            pstm.setInt(2, codAl);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            throw new BusinessException("Error al eliminar matrícula de grupo");
        } finally {
            ConexionJdbc.cerrar(pstm);
        }
    }

    // Buscar por clave compuesta
    public GrupoMatri buscarPorId(String codG, Integer codAl) throws BusinessException {
        GrupoMatri gm = null;
        Connection con = ConexionJdbc.getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM gruposmatri WHERE CodG = ? AND CodAl = ?";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, codG);
            pstm.setInt(2, codAl);
            rs = pstm.executeQuery();
            if (rs.first()) {
                gm = new GrupoMatri();
                gm.setCodG(rs.getString("CodG"));
                gm.setCodAl(rs.getInt("CodAl"));
                gm.setCodH(rs.getString("CodH"));
            }
            return gm;
        } catch (SQLException ex) {
            throw new BusinessException("Error al consultar matrícula de grupo");
        } finally {
            ConexionJdbc.cerrar(pstm);
        }
    }

    @Override
    public List<GrupoMatri> buscarTodos() throws BusinessException {
        List<GrupoMatri> result = new ArrayList<>();
        Connection con = ConexionJdbc.getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM gruposmatri ORDER BY CodG, CodAl";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                GrupoMatri gm = new GrupoMatri();
                gm.setCodG(rs.getString("CodG"));
                gm.setCodAl(rs.getInt("CodAl"));
                gm.setCodH(rs.getString("CodH"));
                result.add(gm);
            }
            return result;
        } catch (SQLException ex) {
            throw new BusinessException("Error al consultar matrículas de grupos");
        } finally {
            ConexionJdbc.cerrar(pstm);
        }
    }
    
    
    
    //METODO DAO PARA BUSCAR LOS ALUMNOS Y EL HROARIO
    public GrupoMatri buscarAlumnos(String CodG) throws BusinessException {
        GrupoMatri gm = null;
        Connection con = ConexionJdbc.getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT COUNT(CodAl) AS alumnos, h.horario FROM gruposmatri gm JOIN horarios h ON gm.CodH=h.CodH WHERE gm.CodG= ? GROUP BY h.horario";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, CodG);
            rs = pstm.executeQuery();
            while (rs.next()) { //otro while de nuevo para pillar todos los resultados y no solo el primero (esq antes lo tenia asi y tuve q buscar porq en internet)
                gm = new GrupoMatri();
                gm.setCodG(CodG); //pillar el codigo del grupo que nos hemos pasado antes en DAOGrupo
                gm.setCodH(rs.getString("horario")); //Poner el campo de horario para tener el dia
                gm.setCodAl(rs.getInt("alumnos")); //Pillar la cantidad de alumnos con el count
            }
            return gm;
        } catch (SQLException ex) {
            throw new BusinessException("Error al consultar matrícula de grupo");
        } finally {
            ConexionJdbc.cerrar(pstm);
        }
    }
    
}
