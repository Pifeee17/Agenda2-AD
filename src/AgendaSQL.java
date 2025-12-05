import java.util.List;
import java.util.Scanner;

import dao.ContactoDAO;
import dto.ContactoDTO;

public class AgendaSQL {

    private static final Scanner sc = new Scanner(System.in);
    private static final ContactoDAO actorDAO = new ContactoDAO();

    public static void main(String[] args) throws Exception {


        String opcion = "0";


    while (true) {
        PrimerMenu();
        opcion = sc.nextLine();

        switch (opcion) {
            case "1" -> menuContactos();
            case "2" -> System.out.println("Menu grupos");
            case "3" -> {System.out.println("Saliendo..."); 
            return;}
              
            default -> System.out.println("Opción no válida.");
        }
    }
}

private static void menuContactos() {
    String opcion = "";
    while (!opcion.equals("8")) {
        Menu();
        opcion = sc.nextLine();

        switch (opcion) {
            case "1" -> crearContacto();
            case "2" -> listarActores();
            case "3" -> buscarEnGrupoID();
            case "4" -> modificarContacto();
            case "5" -> borrarActor();
            case "6" -> System.out.println("Metodo aun no implementado");
            case "7" -> System.out.println("Metodo aun no implementado");
            case "8" -> System.out.println("Volviendo al menú principal...");
            default -> System.out.println("Opción no válida.");
        }
    }
}


    private static void Menu() {
        System.out.println("\n--- Menú de Contactos ---");
        System.out.println("1. Crear Contacto");
        System.out.println("2. Listar Contacto");
        System.out.println("3. Buscar Contacto (ID)");
        System.out.println("4. Modificar Contacto (ID)");
        System.out.println("5. Eliminar Contacto (ID)");
        System.out.println("6. Cargar CSV");
        System.out.println("7. Añadir usuario (ID) a grupo (ID)");
        System.out.println("8. Salir");
        System.out.print("Elige una opción: ");
    }

    private static void listarActores() {
        List<ContactoDTO> contactos = ContactoDAO.findAll();
        for(ContactoDTO contacto : contactos){
            contacto.mostrarContacto();
        }
    }

    private static void buscarEnGrupoID() {
        System.out.print("Introduce ID del grupo: ");
        int id_Grupo = sc.nextInt();
        sc.nextLine();
       List<ContactoDTO> contactos = ContactoDAO.buscarEnGrupoID(id_Grupo);

        if (contactos != null) {
            System.out.println(contactos);
        } else {
            System.out.println("Contactos no encontrados.");
        }
    }

    private static void crearContacto() {
        System.out.print("Introduce nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Introduce telefono: ");
        String telefono = sc.nextLine();
        System.out.print("Introduce email: ");
        String email = sc.nextLine();
        ContactoDTO nuevo = new ContactoDTO();
        nuevo.setNombre(nombre);
        nuevo.setTelefono(telefono);
        nuevo.setEmail(email);
        actorDAO.create(nuevo);
        System.out.println("Contacto creado con ID: " + nuevo.getID());
    }

private static void modificarContacto() {
    System.out.print("Introduce ID del contacto a actualizar: ");
    int id = sc.nextInt();
    sc.nextLine();

    ContactoDAO dao = new ContactoDAO();
    ContactoDTO contacto = dao.findById(id);

    if (contacto != null) {
        System.out.print("Nuevo nombre (" + contacto.getNombre() + "): ");
        String nombre = sc.nextLine();
        contacto.setNombre(nombre);

        System.out.print("Nuevo teléfono (" + contacto.getTelefono() + "): ");
        String telefono = sc.nextLine();
        contacto.setTelefono(telefono);

        System.out.print("Nuevo email (" + contacto.getEmail() + "): ");
        String email = sc.nextLine();
        contacto.setEmail(email);
        dao.modificarContacto(contacto);
        System.out.println("Contacto actualizado.");
    } 
    else {
        System.out.println("Contacto no encontrado.");
    }
}

    private static void borrarActor() {
    System.out.print("Introduce ID del contacto a borrar: ");
    int id = sc.nextInt();
    sc.nextLine();

    ContactoDAO dao = new ContactoDAO();   
    dao.delete(id);                        

    System.out.println("Actor borrado si existía.");
}
    private static void PrimerMenu() {
        System.out.println("\n--- Menú ---");
        System.out.println("1. Contactos");
        System.out.println("2. Grupo");
        System.out.println("3. Salir");
        System.out.print("Elige una opción: ");
    }
}
