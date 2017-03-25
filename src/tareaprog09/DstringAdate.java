/** @author José Miguel Guimerá Padrón. PROG 1º DAM */
package tareaprog09;
// librerias necesarias para coneritr a date varible string con formato definido
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DstringAdate {
    
   public static Date main(String fecha)
    {// formato a usar por el string
        SimpleDateFormat fech = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaaDate = null; // define la variable tipo Date
        try {
            fechaaDate = fech.parse(fecha); // parsea la fecha en string a Date
        } 
        catch (ParseException ex) // captura de errores en el parseo
        {
            System.out.println("Error en la conversión de fecha String a Date "+ex);
        }
        return fechaaDate; // devuelve un objeto del tipo Date con fecha
    }
   
}