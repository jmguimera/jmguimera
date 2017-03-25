/** @author José Miguel Guimerá Padrón. PROG 1º DAM */
package tareaprog09;

import java.util.Date;

public class CuentaAhorro extends CuentaBancaria{
    
    private double interes_anual; // interes anual que devenga el tipo de cuenta

        //Constructo por defecto
    public CuentaAhorro(){
        super(); // constructor de variables en el padre
    }
    //Constrcutor con parametros
    public CuentaAhorro(String nro_cuenta, String tipo_cuenta,
                        double saldo_inicial, String nombre, String apellidos, Date fec_nac,
                        int interes_anual) {// Constructor con parametros en el padre
        super(nro_cuenta, tipo_cuenta, saldo_inicial, nombre, apellidos, fec_nac);
        this.interes_anual = interes_anual; // variable de la clase
    }

    // metodo que se hereda y estamos obligaods de definir en la clase
    @Override
    public String ToString() {
        String mensa;
        mensa="Número de cuenta: "+this.getNro_cuenta()+", Tipo: Ahorro, Titular: "+
                this.getCliente().getApellidos()+", "+this.getCliente().getNombre()+"\n Saldo Actual: "+
                this.getSaldo()+" Tipo Interés Anual: "+this.getInteres_anual()+"\n";
        return mensa;
    }

    // gettes y setters
    public double getInteres_anual() {
        return interes_anual;
    }

    public void setInteres_anual(double interes_anual) {
        this.interes_anual = interes_anual;
    }

}
