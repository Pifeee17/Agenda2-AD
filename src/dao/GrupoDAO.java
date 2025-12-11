package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ConnectionAgenda.ConnectionAgenda;
import dto.ContactoDTO;
import dto.GrupoDTO;

public class GrupoDAO {
      public void create(GrupoDTO grupos) {
        String sql = "INSERT INTO Grupos(Nombre) VALUES (?)";
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
            System.err.println("ERROR al CREAR GRUPO.");
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
            System.err.println("ERROR al BUSCAR GRUPO.");
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
        System.err.println("ERROR al MODIFICAR el grupo.");
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
        System.err.println("ERROR al BORRAR GRUPO.");
        e.printStackTrace();
    }

}

  public static List<ContactoDTO> ContactoGrupo(int idGrupo) {
    List<ContactoDTO> contactos = new ArrayList<>();

    String sql = "SELECT c.ID, c.Nombre, c.Telefono, c.Email FROM Contactos c JOIN Contacto_Grupo cg ON cg.Id_Contacto = c.ID WHERE cg.Id_Grupo = ?";

    try (Connection conn = ConnectionAgenda.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, idGrupo);

        try (ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ContactoDTO contacto = new ContactoDTO();
                contacto.setId(rs.getInt("ID"));
                contacto.setNombre(rs.getString("Nombre"));
                contacto.setTelefono(rs.getString("Telefono"));
                contacto.setEmail(rs.getString("Email"));
                contactos.add(contacto);
            }
        }

    } catch (SQLException e) {
        System.err.println("ERROR al BUSCAR CONTACTO en GRUPO.");
        e.printStackTrace();
    }

    return contactos;
}

 public GrupoDTO findById(int id) {
        String sql = "SELECT ID, Nombre FROM Grupos WHERE ID = ?";
        try (Connection conn = ConnectionAgenda.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                   GrupoDTO grupo = new GrupoDTO();
                    grupo.setID(rs.getInt("ID"));
                    grupo.setNombre(rs.getString("Nombre"));
                    return grupo;
                }
            }

        } catch (SQLException e) {
            System.err.println("ERROR al BUSCAR ID de grupo.");
            e.printStackTrace();
        }
        return null;
    }
}
