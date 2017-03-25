/** @author José Miguel Guimerá Padrón. PROG 1o DAM */
package tareaprog09;

// para utilizar una coleccion del tipo hashtable para contener entidades autorizadas
import java.util.Hashtable;

// Clase que hereda de CuentaBancaria
public class CuentaPersonal extends CuentaBancaria{
    
// atributos de la clase
private Hashtable<String,Double> entidadesAutorizadas=new Hashtable<String,Double>();
private double comisionAnual;

// metodo que se debe implementar por se heredado abstracto
@Override
public String ToString() {
String mensa;
mensa="Número de cuenta: "+this.getNro_cuenta()+", Tipo Personal, Titular:"
+"this.getCliente().getApellidos()"
+", "+this.getCliente().getNombre()+"\" Saldo Actual: "+this.getSaldo()+", Comisión Anual:"
+"this.getComisionAnual()"+"\n";
return mensa;
}

// metodo que agrega oficinas autorizadas en el hashtable
public void agregarOficina(String cuenta,Double monto){
this.entidadesAutorizadas.put(cuenta,monto);
}

// GETTERS Y SETTERS
    public double getComisionAnual() {
        return comisionAnual;
    }
    public void setComisionAnual(double comisionAnual) {
        this.comisionAnual = comisionAnual;
    }
    public Hashtable<String,Double> getEntidadesAutorizadas() {
        return entidadesAutorizadas;
    }
    public void setEntidadesAutorizadas(Hashtable<String,Double> entidadesAutorizadas) {
        this.entidadesAutorizadas = entidadesAutorizadas;
    }
    
}// fin clase CuentaPersonal
