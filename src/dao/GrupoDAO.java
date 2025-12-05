package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ConnectionAgenda.ConnectionAgenda;
import dto.GrupoDTO;

public class GrupoDAO {
      public void create(GrupoDTO grupos) {
        String sql = "INSERT INTO Grupo(Nombre) VALUES (?)";
        try (Connection conn = ConnectionAgenda.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, grupos.getNombre());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    grupos.setID(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

     public static List<GrupoDTO> findAll() {
        List<GrupoDTO> grupos = new ArrayList<>();
        String sql = "SELECT ID, nombre FROM Grupos";

        try (Connection conn = ConnectionAgenda.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                GrupoDTO grupo = new GrupoDTO();
                grupo.setID(rs.getInt("ID"));
                grupo.setNombre(rs.getString("Nombre"));
                grupos.add(grupo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grupos;
    }

     public void modificarGrupo(GrupoDTO grupo) {
    String sql = "UPDATE Grupos SET Nombre = ? WHERE ID = ?";

    try (Connection conn = ConnectionAgenda.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, grupo.getNombre());
        ps.setInt(2, grupo.getID()); 

        ps.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace();
    }
}


   public void delete(int id) {
    String query = "DELETE FROM Contacto_Grupo WHERE Id_Grupo = ?";
    String query2 = "DELETE FROM Grupos WHERE ID = ?";

    try(Connection conn = ConnectionAgenda.getConnection()) {
       
        conn.setAutoCommit(false);

        try (PreparedStatement psCG = conn.prepareStatement(query); PreparedStatement psG = conn.prepareStatement(query2)) {
            psCG.setInt(1, id);
            psCG.executeUpdate();
            
            psG.setInt(1, id);
            psG.executeUpdate();
            
            conn.commit();
        } catch(SQLException e){
        e.printStackTrace();

        try {
            if (conn != null) {
                conn.rollback();
                System.err.println("Se hace ROLLBACK");
            }
        } catch (SQLException er) {
            System.err.println("ERROR haciendo ROLLBACK");
            er.printStackTrace();
        }

    } catch (Exception e) {
        System.err.println("ERROR de conexión");
        e.printStackTrace();

        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

}
}
