package dto;
public class GrupoDTO {
    
    private int ID;
    private String nombre;

    public GrupoDTO(int ID, String nombre){
        this.ID = ID;
        this.nombre = nombre;
    }

    public GrupoDTO(){
        this.ID = 0;
        this.nombre = "";
    }

    public int getID(){
        return ID;
    }

    public void setID(int ID){
        this.ID = ID;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }
      public void mostrarGrupo() {
        System.out.println(ID + "\t" + nombre + "\t");
    }
}
