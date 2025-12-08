import java.util.List;
import java.util.Scanner;

import dao.ContactoDAO;
import dao.GrupoDAO;
import dto.ContactoDTO;
import dto.GrupoDTO;

public class AgendaSQL {

    private static final Scanner sc = new Scanner(System.in);
    private static final ContactoDAO ContactoDAO = new ContactoDAO();
    private static final GrupoDAO GrupoDao = new GrupoDAO();

    public static void main(String[] args) throws Exception {


        String opcion = "0";


    while (true) {
        PrimerMenu();
        opcion = sc.nextLine();

        switch (opcion) {
            case "1" : 
            menuContactos();
            break;
            case "2" :
            MenuGrupos();
            break;
            case "3" : {System.out.println("Saliendo..."); 
            return;}
              
            default : System.out.println("Opción no válida.");
        }
    }
}

private static void menuContactos() {
    String opcion = "";
    while (!opcion.equals("8")) {
        Menu();
        opcion = sc.nextLine();

        switch (opcion) {
            case "1" : 
            crearContacto();
            break;
            case "2" : 
            listarActores();
            break;
            case "3" : 
            buscarEnGrupoID();
            break;
            case "4" : 
            modificarContacto();
            break;
            case "5" : 
            borrarContacto();
            break;
            case "6" : 
            cargarCSV();
            break;
            case "7" : 
            anadirContactoAGrupo();
            break;
            case "8" : 
            System.out.println("Saliendo al menu principal.");
            break;
            default : System.out.println("Opción no válida.");
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
    private static void MenuGrupos() {
    String opcion = "";
    while (!opcion.equals("6")) {
        Menu2();
        opcion = sc.nextLine();

        switch (opcion) {
            case "1" : 
            crearGrupo();
            break;
            case "2" : 
            listarGrupos();
            break;
            case "3" : 
            modificarGrupo();
            break;
            case "4" : 
            borrarGrupo();
            break;
            case "5" : 
            verContactosEnGrupo();
            break;
            case "6" : 
            System.out.println("Saliendo al menu principal.");
            break;
            default : System.out.println("Opción no válida.");
        }
    }
}


    private static void Menu2() {
        System.out.println("\n--- Menú de Grupos ---");
        System.out.println("1. Crear Grupo");
        System.out.println("2. Listar Grupo");
        System.out.println("3. Modificar Grupo (ID)");
        System.out.println("4. Eliminar Grupo (ID)");
        System.out.println("5. Ver Contactos dentro de un Grupo");
        System.out.println("6. Salir");
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

        private static boolean validarNombre(String nombre) {
     return nombre.matches("^[A-Z][a-z]+");
    }

    private static boolean validarTelefono(String telefono) {
     return telefono.matches("[0-9]{9}");
    }

    //Esto es: algo@algo.algo
    private static boolean validarEmail(String email) {
        return email.matches("^.+@.+\\..+$");
    }


   private static void crearContacto() {
    System.out.print("Introduce nombre: ");
    String nombre = sc.nextLine();
    while (!validarNombre(nombre)) {
        System.out.println("Nombre inválido. Debe empezar por mayúscula y solo contener letras.");
        System.out.print("Introduce nombre: ");
        nombre = sc.nextLine();
    }

   System.out.print("Introduce telefono: ");
    String telefono = sc.nextLine();
    while (!validarTelefono(telefono)) {
        System.out.println("Teléfono inválido. Debe contener exactamente 9 dígitos.");
        System.out.print("Introduce telefono: ");
        telefono = sc.nextLine();
    }

    System.out.print("Introduce email: ");
    String email = sc.nextLine();
    while (!validarEmail(email)) {
        System.out.println("Email inválido. Debe contener texto@texto.texto");
        System.out.print("Introduce email: ");
        email = sc.nextLine();
    }

    ContactoDTO duplicado = ContactoDAO.buscarDuplicado(telefono, email);

    if (duplicado != null) {
        System.out.println("\n¡ATENCIÓN! Existe un contacto con el mismo teléfono o email:");
        duplicado.mostrarContacto();

        System.out.print("¿Deseas continuar igualmente? (S/N): ");
        String respuesta = sc.nextLine().trim().toUpperCase();

        if (!respuesta.equals("S")) {
            System.out.println("Creación cancelada.");
            return;
        }
    }

    ContactoDTO nuevo = new ContactoDTO();
    nuevo.setNombre(nombre);
    nuevo.setTelefono(telefono);
    nuevo.setEmail(email);

    ContactoDAO.create(nuevo);
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

    private static void borrarContacto() {
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

    private static void crearGrupo() {
        System.out.print("Introduce nombre: ");
        String nombre = sc.nextLine();
        GrupoDTO nuevo = new GrupoDTO();
       nuevo.setNombre(nombre);
       GrupoDao.create(nuevo);
        System.out.println("Grupo creado con ID: " + nuevo.getID());
    }

    private static void anadirContactoAGrupo() {
    System.out.print("Introduce ID del contacto: ");
    int Id_Contacto = sc.nextInt();
    sc.nextLine();

    System.out.print("Introduce ID del grupo: ");
    int Id_Grupo = sc.nextInt();
    sc.nextLine();

    ContactoDAO.anadirContactoGrupo(Id_Contacto, Id_Grupo);
}

private static void cargarCSV() {
    System.out.print("Introduce la ruta del archivo CSV: ");
    String path = sc.nextLine();

    List<ContactoDTO> contactos = utils.LeerCSV.loadContactosFromCsv(path);
    ContactoDAO dao = new ContactoDAO();

    if (contactos.isEmpty()) {
        System.out.println("No se encontraron contactos en el CSV.");
        return;
    }

    for (ContactoDTO contacto : contactos) {
        dao.create(contacto);
        System.out.println("Contacto cargado: " + contacto.getNombre() + " (ID: " + contacto.getID() + ")");
    }
}



//GRUPOS ->
    private static void listarGrupos() {
        List<GrupoDTO> grupos = GrupoDao.findAll();
        for(GrupoDTO grupo : grupos){
            grupo.mostrarGrupo();
        }
    }

    private static void modificarGrupo() {
    System.out.print("Introduce ID del grupo a actualizar: ");
    int id = sc.nextInt();
    sc.nextLine();

    GrupoDAO dao = new GrupoDAO();
    GrupoDTO grupo = dao.findById(id);

    if (grupo != null) {
        System.out.print("Nuevo nombre (" + grupo.getNombre() + "): ");
        String nombre = sc.nextLine();
        grupo.setNombre(nombre);
        dao.modificarGrupo(grupo);
        System.out.println("Grupo actualizado.");
    } 
    else {
        System.out.println("Grupo no encontrado.");
    }
}
private static void borrarGrupo() {
    System.out.print("Introduce ID del grupo a borrar: ");
    int id = sc.nextInt();
    sc.nextLine();

    GrupoDAO dao = new GrupoDAO();   
    dao.delete(id);                        

    System.out.println("Grupo borrado si existía.");
}
private static void verContactosEnGrupo() {
    System.out.print("Introduce ID del grupo: ");
    int idGrupo = sc.nextInt();
    sc.nextLine();

    List<ContactoDTO> contactos = GrupoDao.ContactoGrupo(idGrupo);

    if (contactos.isEmpty()) {
        System.out.println("No hay contactos en este grupo.");
    } else {
        System.out.println("Contactos en el grupo ID " + idGrupo + ":");
        for (ContactoDTO contacto : contactos) {
            contacto.mostrarContacto();
        }
    }
}

}
