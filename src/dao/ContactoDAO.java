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

public class ContactoDAO {

    public void create(ContactoDTO contacto) {
        String sql = "INSERT INTO Contactos(Nombre, Telefono, Email) VALUES (?, ? , ?)";
        try (Connection conn = ConnectionAgenda.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, contacto.getNombre());
            ps.setString(2, contacto.getTelefono());
            ps.setString(3, contacto.getEmail());


            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    contacto.setId(rs.getInt(1));
                }
            }


        } catch (SQLException e) {
            System.err.println("Error al CREAR contacto.");
            e.printStackTrace();
        }
    }

     public static List<ContactoDTO> findAll() {
        List<ContactoDTO> contactos = new ArrayList<>();
        String sql = "SELECT ID, nombre, telefono, email FROM Contactos";

        try (Connection conn = ConnectionAgenda.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ContactoDTO contacto = new ContactoDTO();
                contacto.setId(rs.getInt("ID"));
                contacto.setNombre(rs.getString("Nombre"));
                contacto.setTelefono(rs.getString("Telefono"));
                contacto.setEmail(rs.getString("Email"));
                contactos.add(contacto);
            }

        } catch (SQLException e) {
            System.err.println("ERROR al BUSCAR contacto.");
            e.printStackTrace();
        }
        return contactos;
    }

     public ContactoDTO findById(int id) {
        String sql = "SELECT ID, Nombre, Telefono, Email FROM Contactos WHERE ID = ?";
        try (Connection conn = ConnectionAgenda.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ContactoDTO contacto = new ContactoDTO();
                    contacto.setId(rs.getInt("ID"));
                    contacto.setNombre(rs.getString("Nombre"));
                    contacto.setTelefono(rs.getString("Telefono"));
                    contacto.setEmail(rs.getString("Email"));
                    return contacto;
                }
            }

        } catch (SQLException e) {
            System.err.println("ERROR al BUSCAR contacto.");
            e.printStackTrace();
        }
        return null;
    }


    public void modificarContacto(ContactoDTO contacto) {
    String sql = "UPDATE Contactos SET Nombre = ?, Telefono = ?, Email = ? WHERE ID = ?";

    try (Connection conn = ConnectionAgenda.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, contacto.getNombre());
        ps.setString(2, contacto.getTelefono());
        ps.setString(3, contacto.getEmail());
        ps.setInt(4, contacto.getID()); 

        ps.executeUpdate();

    } catch (SQLException e) {
        System.err.println("ERROR al MODIFICAR el CONTACTO.");
        e.printStackTrace();
    }
}

   public void delete(int id) {
    String query = "DELETE FROM Contacto_Grupo WHERE Id_Contacto = ?";
    String query2 = "DELETE FROM Contactos WHERE ID = ?";

    try(Connection conn = ConnectionAgenda.getConnection()) {
       
        conn.setAutoCommit(false);

        try (PreparedStatement psFA = conn.prepareStatement(query); PreparedStatement psA = conn.prepareStatement(query2)) {
            psFA.setInt(1, id);
            psFA.executeUpdate();
            
            psA.setInt(1, id);
            psA.executeUpdate();
            
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

    public void anadirContactoGrupo(int Id_Contacto, int Id_Grupo) {
    String sql = "INSERT INTO Contacto_Grupo(Id_Contacto, Id_Grupo) VALUES (?, ?)";

    try (Connection conn = ConnectionAgenda.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, Id_Contacto);
        ps.setInt(2, Id_Grupo);

        ps.executeUpdate();

        System.out.println("Contacto agregado al grupo correctamente.");

    } catch (SQLException e) {
        System.err.println("ERROR al añadir CONTACTO A GRUPO.");
        e.printStackTrace();
    }
}
public ContactoDTO buscarDuplicado(String telefono, String email) {
    String sql = "SELECT * FROM Contactos WHERE Telefono = ? OR Email = ?";

    try (Connection conn = ConnectionAgenda.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, telefono);
        ps.setString(2, email);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                ContactoDTO c = new ContactoDTO();
                c.setId(rs.getInt("ID"));
                c.setNombre(rs.getString("Nombre"));
                c.setTelefono(rs.getString("Telefono"));
                c.setEmail(rs.getString("Email"));
                return c; 
            }
        }
       
    } catch (SQLException e) {
        System.err.println("ERROR al BUSCAR DUPLICADOS.");
        e.printStackTrace();
    }
    return null; 
}

public List<ContactoDTO> buscarEnTodo(String texto) {
    List<ContactoDTO> lista = new ArrayList<>();
    String sql = "SELECT * FROM Contactos WHERE Nombre LIKE ? OR Telefono LIKE ? OR Email LIKE ?";

    try (Connection conn = ConnectionAgenda.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        String patron = "%" + texto + "%";

        ps.setString(1, patron);
        ps.setString(2, patron);
        ps.setString(3, patron);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ContactoDTO contacto = new ContactoDTO();
                contacto.setId(rs.getInt("ID"));
                contacto.setNombre(rs.getString("Nombre"));
                contacto.setTelefono(rs.getString("Telefono"));
                contacto.setEmail(rs.getString("Email"));
                lista.add(contacto);
            }
        }
        
    } catch (SQLException e) {
        System.err.println("ERROR al buscar igualdades.");
        e.printStackTrace();
    }

    return lista;
}


}