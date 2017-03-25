/** @author José Miguel Guimerá Padrón. PROG 1º DAM */
package tareaprog09;
// para contener entidades autorizadas en una coleccion
import java.util.Hashtable;

// hereda de CuentaBancaria
public class CuentaEmpresa extends CuentaBancaria{
        //atributos propias de la clase
        private Hashtable<String,Double> entidadesAutorizadas=new Hashtable<String,Double>();
        private double comisionDescubierto;
        private double interesDescubierto;
        private double maximoDescubierto;
    
        //implementacion obligada por herencia de metodo abstracto
    @Override
    public String ToString() {
        String mensa;
    mensa="Número de cuenta: "+this.getNro_cuenta()+", Tipo Empresa, Titular: "+this.getCliente().getApellidos()+
            ", "+this.getCliente().getNombre()+"\n Saldo Actual: "+this.getSaldo()+
            " Comisión por Descubierto: "+this.getComisionDescubierto()+
            " Interés por Descubierto: "+this.getInteresDescubierto()+
            " Máximo Descubierto: "+this.getMaximoDescubierto()+"\n";
            return mensa;
    }
    
    
    //GETTERS Y SETTERS
    
    public void agregarOficina(String cuenta, Double monto) {
        this.entidadesAutorizadas.put(cuenta,monto);
    }    

    public double getComisionDescubierto() {
        return comisionDescubierto;
    }

    public void setComisionDescubierto(double comisionDescubierto) {
        this.comisionDescubierto = comisionDescubierto;
    }

    public double getInteresDescubierto() {
        return interesDescubierto;
    }

    public void setInteresDescubierto(double interesDescubierto) {
        this.interesDescubierto = interesDescubierto;
    }

    public double getMaximoDescubierto() {
        return maximoDescubierto;
    }

    public void setMaximoDescubierto(double maximoDescubierto) {
        this.maximoDescubierto = maximoDescubierto;
    }

    public Hashtable<String,Double> getEntidadesAutorizadas() {
        return entidadesAutorizadas;
    }

    public void setEntidadesAutorizadas(Hashtable<String,Double> entidadesAutorizadas) {
        this.entidadesAutorizadas = entidadesAutorizadas;
    }
   
}// fin Clase CuentaEmpresa
