import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
            e.printStackTrace();
        }
    }

     public List<ContactoDTO> findAll() {
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
            e.printStackTrace();
        }
        return contactos;
    }

    public void modificarContacto(ContactoDTO contacto){
        String sql = "UPDATE Contactos SET Nombre = ?, Telefono = ?, Email = ? WHERE ID = ?";
        try (Connection conn = ConnectionAgenda.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, contacto.getID());
            ps.setString(2, contacto.getNombre());
            ps.setString(3, contacto.getTelefono());
            ps.setString(4, contacto.getEmail());


            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    contacto.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarContacto(ContactoDTO contacto){
        String sql = "DELETE FROM Contactos where ID = ?";
         try (Connection conn = ConnectionAgenda.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, contacto.getID());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   public List<ContactoDTO> buscarEnGrupoID(int idGrupo) {
    List<ContactoDTO> lista = new ArrayList<>();
    String sql = "SELECT Contactos.* FROM Contactos JOIN Grupos ON Grupos.ID = Contactos.ID_GRUPOWHERE Grupos.ID = ?";

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
                lista.add(contacto);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return lista;
}

}

