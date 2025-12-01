public class Grupos {
    
    private int ID;
    private String nombre;

    public Grupos(int ID, String nombre){
        this.ID = ID;
        this.nombre = nombre;
    }

    public Grupos(){
        this.ID = 0;
        this.nombre = "";
    }

    public int getID(){
        return ID;
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
