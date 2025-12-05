package utils;

import com.opencsv.CSVReader;

import dto.ContactoDTO;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class LeerCSV {
        public static List<ContactoDTO> loadContactosFromCsv(String path) {
        List<ContactoDTO> contactos = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(path))) {
            String[] row;

            // Saltar cabecera si la hay
            reader.readNext();

            while ((row = reader.readNext()) != null) {
                String nombre = row[0];
                String telefono = row[1];
                String email = row[2];
                ContactoDTO contacto = new ContactoDTO();
                contacto.setNombre(nombre);
                contacto.setTelefono(telefono);
                contacto.setEmail(email);
                contactos.add(contacto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return contactos;
    }
}