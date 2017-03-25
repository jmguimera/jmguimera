/** @author José Miguel Guimerá Padrón. PROG 1º DAM */
package tareaprog09;

// clase para el calculo del digitrol de control de la cuentas CCC
public class CalculoDigitoControl {
     //metodo main que recibe como parametro la cuenta a analizar
        public static String calculoDigCtrl(String codCuenta){
    
    // array que contendran los 8 primeros digitos de la cuenta
        int cifra[]=new int[10];
        
        for(int i=0; i<cifra.length;i++) cifra[i]=0 ;
        
        String digito1=""; // contendrá el primer digito de control
        String digito2=""; // contendrá el segundo digito de control
        
       // se calcula la parte de la entidad
        for(int i=0;i<4;i++){ // for entidad
            
            // se asigna valor del digito indicado según su posicion en el string
            cifra[i]=Integer.parseInt(codCuenta.substring(i,i+1));

        switch(i){ // switch entidad
        
                case 0:
                    cifra[i]*=4;
                    break;
                case 1:
                    cifra[i]*=8;
                    break;
                case 2:
                    cifra[i]*=5;
                    break;
                case 3:
                    cifra[i]*=10;
                    break;                
        
            } // fin del switch entidad
            
        } // fin del for entidad
        
        // se calcula la parte de la oficina
        for(int i=4;i<8;i++){ // for oficina
            // se asigna valor del digito indicado según su posicion en el string
            cifra[i]=Integer.parseInt(codCuenta.substring(i,i+1));

        switch(i){ //switch oficina
        
                case 4:
                    cifra[i]*=9;
                    break;
               case 5:
                    cifra[i]*=7;
                    break;
                case 6:
                    cifra[i]*=3;
                    break;
                case 7:
                    cifra[i]*=6;
                    break;                
        
           }//fin del switch oficina
         
        } // fin for oficina
        
        int suma=0;
        
        for(int valor: cifra){ // se suman todos los resultados

            suma+=valor;

        }

        suma=11-(suma%11); // se divide entre 11 y al resto se le resta 11
        
        // si el resultado es 10 primer digito de control de la cta
        // será igual a uno, sino el valor de suma llevado a string
        //digito1=(suma==10)? "1" : Integer.toString(suma);
        if(suma==10){digito1="1";}
        else if(suma==11){digito1="0";}
        else{digito1=Integer.toString(suma);}        
        
//para depurar el prog  System.out.println("Primer digito de control: "+digito1);

       // calculo del segundo digito de control 
        for(int i=0;i<10;i++){
            cifra[i]=Integer.parseInt(codCuenta.substring(i+10,i+11));

            switch(i){
        
                case 0:
                    cifra[i]*=1;
                    break;
                case 1:
                    cifra[i]*=2;
                    break;
                case 2:
                    cifra[i]*=4;
                    break;
                case 3:
                    cifra[i]*=8;
                    break;                
                case 4:
                    cifra[i]*=5;
                    break;
                case 5:
                    cifra[i]*=10;
                    break;
                case 6:
                    cifra[i]*=9;
                    break;
                case 7:
                    cifra[i]*=7;
                    break; 
                case 8:
                    cifra[i]*=3;
                    break;
                case 9:
                    cifra[i]*=6;
                    break;                 
            } // fin del switch 
//  para depurar el prog  System.out.println("indice: "+i+" valor cifra: "+cifra[i]);            
        } // fin del for
        
        suma=0;
        
        for(int valor: cifra){ // se suman todos los resultados

            suma+=valor;

        }//fin del for each
        
        suma=11-(suma%11); // se divide entre 11 y al resto se le resta 11
        
        // si el resultado es 10 segundo digito de control de la cta
        // será igual a uno, sino el valor de suma llevado a string
        // digito2=(suma==10)? "1" : Integer.toString(suma); 
        if(suma==10){digito2="1";}
            else if(suma==11){digito2="0";}
                else{digito2=Integer.toString(suma);}
                
        return digito1.toString()+digito2.toString();
        
    }//fin metodo calculoDigCtrl
    
}// fin de la clase CaculoDigitoControl 
