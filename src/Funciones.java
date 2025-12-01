import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Funciones {

    public void create(Contacto contacto) {
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

     public List<Contacto> findAll() {
        List<Contacto> contactos = new ArrayList<>();
        String sql = "SELECT ID, nombre, telefono, email FROM Contactos";

        try (Connection conn = ConnectionAgenda.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Contacto contacto = new Contacto();
                contacto.setId(rs.getInt("ID"));
                contacto.setFirstName(rs.getString("first_name"));
                actor.setLastName(rs.getString("last_name"));
                actores.add(actor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return actores;
    }
    
}
