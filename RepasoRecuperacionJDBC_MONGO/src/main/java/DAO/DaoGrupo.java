package DAO;

import java.sql.*;
import java.util.*;
import excepciones.BusinessException;
import jdbc.ConexionJdbc;
import POJOS.Grupo;
import dao.DaoGenerico;

public class DaoGrupo extends DaoGenerico<Grupo, String> {

    @Override
    public void grabar(Grupo g) throws BusinessException {
        Connection con = ConexionJdbc.getConnection();
        PreparedStatement pstm = null;
        try {
            String sql = "INSERT INTO grupos (CodG, CodAsig, CodH) VALUES (?, ?, ?)";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, g.getCodG());
            pstm.setString(2, g.getCodAsig());
            pstm.setString(3, g.getCodH());
            pstm.executeUpdate();
        } catch (SQLException ex) {
            throw new BusinessException("Error al insertar grupo");
        } finally {
            ConexionJdbc.cerrar(pstm);
        }
    }

    @Override
    public void actualizar(Grupo g) throws BusinessException {
        Connection con = ConexionJdbc.getConnection();
        PreparedStatement pstm = null;
        try {
            String sql = "UPDATE grupos SET CodAsig = ?, CodH = ? WHERE CodG = ?";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, g.getCodAsig());
            pstm.setString(2, g.getCodH());
            pstm.setString(3, g.getCodG());
            pstm.executeUpdate();
        } catch (SQLException ex) {
            throw new BusinessException("Error al actualizar grupo");
        } finally {
            ConexionJdbc.cerrar(pstm);
        }
    }

    @Override
    public void borrar(Grupo g) throws BusinessException {
        borrar(g.getCodG());
    }

    @Override
    public void borrar(String codG) throws BusinessException {
        Connection con = ConexionJdbc.getConnection();
        PreparedStatement pstm = null;
        try {
            String sql = "DELETE FROM grupos WHERE CodG = ?";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, codG);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            throw new BusinessException("Error al eliminar grupo");
        } finally {
            ConexionJdbc.cerrar(pstm);
        }
    }

    @Override
    public Grupo buscarPorId(String codG) throws BusinessException {
        Grupo g = null;
        Connection con = ConexionJdbc.getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM grupos WHERE CodG = ?";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, codG);
            rs = pstm.executeQuery();
            if (rs.first()) {
                g = new Grupo();
                g.setCodG(rs.getString("CodG"));
                g.setCodAsig(rs.getString("CodAsig"));
                g.setCodH(rs.getString("CodH"));
            }
            return g;
        } catch (SQLException ex) {
            throw new BusinessException("Error al consultar grupo");
        } finally {
            ConexionJdbc.cerrar(pstm);
        }
    }

    @Override
    public List<Grupo> buscarTodos() throws BusinessException {
        List<Grupo> result = new ArrayList<>();
        Connection con = ConexionJdbc.getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM grupos ORDER BY CodG";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                 Grupo g = new Grupo();
                g.setCodG(rs.getString("CodG"));
                g.setCodAsig(rs.getString("CodAsig"));
                g.setCodH(rs.getString("CodH"));
                result.add(g);
            }
            return result;
        } catch (SQLException ex) {
            throw new BusinessException("Error al consultar grupos");
        } finally {
            ConexionJdbc.cerrar(pstm);
        }
    }
    
    public List<Grupo> buscarGporGrupAsig(String codAsig) throws BusinessException { //Método para que pueda conectarlo a asignatura
        Grupo g = null;
        List<Grupo> grupos = new ArrayList<Grupo>(); //Lista para guardar todos los CodG luego
        Connection con = ConexionJdbc.getConnection();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT CodG from grupos where CodAsig = ?"; //Interrogante para evitar inyecciones SQL
            pstm = con.prepareStatement(sql);
            pstm.setString(1, codAsig);
            rs = pstm.executeQuery();
             	
            while (rs.next()) { //Hacer que vaya avanzando fila por fila (teniendo "while" porque si es "if" solo ira el primero)
                g = new Grupo();
                g.setCodG(rs.getString("CodG")); //Necesito pillar a este de aqui si quiero conectarme entre columnas
                grupos.add(g); 
            }
            
            return grupos;
        } catch (SQLException ex) {
            throw new BusinessException("Error al consultar grupo");
        } finally {
            ConexionJdbc.cerrar(pstm);
        }
}
}

