/** @author José Miguel Guimerá Padrón. PROG 1º DAM */
package tareaprog09;

import tareaprog09.*;
import java.util.Date;

// clase abstracta que hereda de la Clase Persona y implementa la interface Imprimible
public abstract class CuentaBancaria extends Persona implements Imprimible  {

    //Cosntructor por defecto
    public CuentaBancaria() {
        super();
        this.nro_cuenta = "";
        this.tipo_cuenta = "";
        this.saldo = 0.00;
    }
// constructor con parametros
    public CuentaBancaria(String nro_cuenta, String tipo_cuenta, double saldo_inicial, String nombre, String apellidos, Date fec_nac) {
        super(nombre, apellidos, fec_nac); //Constructor en padre
        
        //si define e instancia el atributo objeto de la clase Persona que tiene esta clase
        this.cliente=new Persona(nombre,apellidos,fec_nac) { // se pasan los parametros
        public void imprimir() {} // se implementan los metodos obligados por la interface
        public void imprimir(int tamanio,String estilo){}
        };
        // variable de la clase
        this.nro_cuenta = nro_cuenta;
        this.tipo_cuenta = tipo_cuenta;
        this.saldo = saldo;
    }
    // Variables de la clases que tendran tambien las clases derivadas
       private Persona cliente;
       private String nro_cuenta;
       private String tipo_cuenta;
       private double saldo;

       //GETTERS Y SETTERS de los atributos
    public String getNro_cuenta() {
        return nro_cuenta;
    }

    public void setNro_cuenta(String nro_cuenta) {
        this.nro_cuenta = nro_cuenta;
    }

    public String getTipo_cuenta() {
        return tipo_cuenta;
    }

    public void setTipo_cuenta(String tipo_cuenta) {
        this.tipo_cuenta = tipo_cuenta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    
    public Persona getCliente() {
        return cliente;
    }

    public void setCliente(Persona cliente) {
        this.cliente = cliente;
    }
    
    // metodo que deben definir las derivadas
    public abstract String ToString();
    
 // metodos que se pueden o no definir en las derivadas
// no se utilizaran en este proyecto    
    
    public void ingreso(){
   
    }

    public void retiro(){
    }    
    
    public void consultarSaldo(){
    
    }           
  
} // fin clase CuentaBancaria