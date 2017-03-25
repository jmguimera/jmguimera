/** @author José Miguel Guimerá Padrón. PROG 1º DAM */
package tareaprog09;
import java.io.Serializable; // se utiliza apar apoder ser usado con archivos
import java.util.Date; // para poder convertir String a fecha

// Clase abstract que implementa las interfaces Imprimble y Serializable
public abstract class Persona implements Imprimible,Serializable{
        
    // atributos de la clase
        private Date fec_nac;
        private String apellidos;
        private String nombre;

//Constructor por defecto
    public Persona() {
        // se pone por defecto fecha del dia
        this.fec_nac=new Date();
        this.apellidos = "sinapellidos";
        this.nombre = "sinnombre";

    }
//Constructor con parametros
    public Persona(String nombre, String apellidos,Date fec_nac) {
        this.nombre = nombre;
        this.apellidos=apellidos;
        this.fec_nac=fec_nac;
    }

    // que se nos obliga a sobrescribir por las interfaces en este caso imprimible
    // serializable no tiene metodos que implementar
    @Override
    public void imprimir() {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir(int t, String e) {
     //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // GETTERS Y SETTERS
    public Date getFec_nac() {
        return fec_nac;
    }

    public void setFec_nac(Date fec_nac) {
        this.fec_nac = fec_nac;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
} //fin de la clase Persona
