import java.io.IOException;
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
    while (!opcion.equals("9")) {
        Menu();
        opcion = sc.nextLine();

        switch (opcion) {
            case "1" : 
            crearContacto();
            break;
            case "2" : 
            listarContactos();
            break;
            case "3" : 
            listarContactos();
            buscarContactoEnTodo();
            break;
            case "4" : 
            listarContactos();
            modificarContacto();
            break;
            case "5" : 
            listarContactos();
            borrarContacto();
            break;
            case "6" :
            verGruposDeContacto();
            break; 
            case "7" : 
            cargarCSV();
            break;
            case "8" : 
            System.out.println("-- Contactos --");
            listarContactos();
            System.out.println("-- Grupos --");
            listarGrupos();
            anadirContactoAGrupo();
            break;
            case "9" : 
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
        System.out.println("3. Buscar Contacto (todos los campos)");
        System.out.println("4. Modificar Contacto (ID)");
        System.out.println("5. Eliminar Contacto (ID)");
        System.out.println("6. Encontrar Grupos de Contacto (ID)");
        System.out.println("7. Cargar CSV");
        System.out.println("8. Añadir usuario (ID) a grupo (ID)");
        System.out.println("9. Salir");
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
            listarGrupos();
            modificarGrupo();
            break;
            case "4" : 
            listarGrupos();
            borrarGrupo();
            break;
            case "5" : 
            listarGrupos();
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

    private static void listarContactos() {
        List<ContactoDTO> contactos = ContactoDAO.findAll();
        for(ContactoDTO contacto : contactos){
            contacto.mostrarContacto();
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
    int id;
    while (true) {
        try {
            id = Integer.parseInt(sc.nextLine());
            break;
    } catch (NumberFormatException e){
        System.err.println("Opcion no valida. ID incorrecto.");
        System.out.print("Introduce ID del contacto a actualizar: ");
    }
}

    ContactoDAO dao = new ContactoDAO();
    ContactoDTO contacto = dao.findById(id);

    if (contacto != null) {
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
    
        contacto.setNombre(nombre);
        contacto.setTelefono(telefono);
        contacto.setEmail(email);
        dao.modificarContacto(contacto);
        System.out.println("Contacto actualizado.");

}else {
        System.out.println("Contacto no encontrado.");
    }
    
}

    private static void borrarContacto() {
    System.out.print("Introduce ID del contacto a borrar: ");
    int id;

    while (true) {
        try {
            id = Integer.parseInt(sc.nextLine());
            break;
    } catch (NumberFormatException e){
        System.err.println("Opcion no valida. ID incorrecto.");
        System.out.print("Introduce ID del contacto a actualizar: ");
    }
}

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

    ContactoDAO contactoDAO = new ContactoDAO();
    GrupoDAO grupoDAO = new GrupoDAO();

    int Id_Contacto;
    while (true) {
        System.out.print("Introduce ID del contacto: ");
        try {
            Id_Contacto = Integer.parseInt(sc.nextLine());

            
            if (contactoDAO.findById(Id_Contacto) == null) {
                System.err.println("ERROR: El contacto con ID " + Id_Contacto + " no existe.");
                continue;
            }

            break;
        } catch (NumberFormatException e) {
            System.err.println("Opcion no valida. ID incorrecto.");
        }
    }

    int Id_Grupo;
    while (true) {
        System.out.print("Introduce ID del grupo: ");
        try {
            Id_Grupo = Integer.parseInt(sc.nextLine());

        
            if (grupoDAO.findById(Id_Grupo) == null) {
                System.err.println("ERROR: El grupo con ID " + Id_Grupo + " no existe.");
                continue;
            }

            break;
        } catch (NumberFormatException e) {
            System.err.println("Opcion no valida. ID incorrecto.");
        }
    }

    contactoDAO.anadirContactoGrupo(Id_Contacto, Id_Grupo);
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

private static void buscarContactoEnTodo() {
    System.out.print("Introduce texto de búsqueda: ");
    String texto = sc.nextLine();

    List<ContactoDTO> resultados = ContactoDAO.buscarEnTodo(texto);

    if (resultados.isEmpty()) {
        System.out.println("No se encontraron contactos.");
    } else {
        System.out.println("Resultados encontrados:");
        for (ContactoDTO c : resultados) {
            c.mostrarContacto();
        }
    }
}

private static void verGruposDeContacto() {

    System.out.print("Introduce ID del contacto: ");
    int idContacto;

    while (true) {
        try {
            idContacto = Integer.parseInt(sc.nextLine());
            break;
        } catch (NumberFormatException e) {
            System.err.println("ID no válido. Intenta de nuevo.");
        }
    }

    ContactoDTO c = ContactoDAO.findById(idContacto);
    if (c == null) {
        System.out.println("El contacto no existe.");
        return;
    }

    List<Integer> grupos = ContactoDAO.obtenerGruposDeContacto(idContacto);

    System.out.println("\nEl contacto pertenece a los siguientes grupos:");

    if (grupos.isEmpty()) {
        System.out.println("(No pertenece a ningún grupo)");
    } else {
        for (int i = 0; i < grupos.size(); i++) {
            System.out.println(" - Grupo ID: " + grupos.get(i));
        }
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
        int id;
        while(true){
            try {
            System.out.print("Introduce ID del grupo a actualizar: ");
            id = Integer.parseInt(sc.nextLine());
            break;
            } catch (Exception e) {
                System.err.println("Opcion no valida.");
            }
    
        }
   

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
   int id;
        while(true){
            try {
            System.out.print("Introduce ID del grupo a borrar: ");
            id = Integer.parseInt(sc.nextLine());
            break;
            } catch (Exception e) {
                System.err.println("Opcion no valida.");
            }
    
        }

    GrupoDAO dao = new GrupoDAO();   
    dao.delete(id);                        

    System.out.println("Grupo borrado si existía.");
}

private static void verContactosEnGrupo() {
   int idGrupo;
        while(true){
            try {
            System.out.print("Introduce ID del grupo: ");
            idGrupo = Integer.parseInt(sc.nextLine());
            break;
            } catch (Exception e) {
                System.err.println("Opcion no valida.");
            }
    
        }

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
