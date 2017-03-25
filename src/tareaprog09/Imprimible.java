package tareaprog09;

/** @author José Miguel Guimerá Padrón. PROG 1º DAM */
public interface Imprimible{
    // varibles de la interface y sus valores por defecto    
        public int tamanio=12;
        public String estilo="";
    // metodos a implementar por las clases que usen la interface
         public void imprimir (); // usa los valores anteriores
    // con parametros para usar los valores a gusto del usuario
         public void imprimir (int t, String e);         

} // fin de la interface Imprimible
  