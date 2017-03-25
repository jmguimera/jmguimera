/** @author José Miguel Guimerá Padrón. PROG 1º DAM */
// paquete que contiene todas las librerias y clases necesarias para el programa
package tareaprog09;
//importacion de librerias necesarias para la ejecucion de sentencias
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import baseDatos.ConexionAOracle;
import java.sql.Connection;
import java.sql.PreparedStatement;

// lcase principal la que contiene el main de arranque
public class AplicacionCuentaBancaria{

    //se define e instancia el arraylist que contendrá la cuentas bancarias
    // el tipo de contenido seran objeto de la clase CuentaBancaria y sus derivadas
    public ArrayList<CuentaBancaria> lista=new ArrayList<CuentaBancaria>();

    // se define e instancia el objeto que el Jframe que contendrá el menu
    // Principal con las opciones pedidas en la tarea
    MenuPrincipal mp=new MenuPrincipal();// inicia el frame
            
    public static void main(String[] args) {// metod main de arranque

    AplicacionCuentaBancaria app; // se define el objeto app que correrá el programa
    app=new AplicacionCuentaBancaria(); // se instacia el objeto e inica el programa
        
    }
   
    public boolean buscarCta(String buscar){
        
        boolean ok=false;
        for(CuentaBancaria ls: lista){
            if(ls.getNro_cuenta().equalsIgnoreCase(buscar)){
//                obj=ls;
                ok=true;
                break;
             }
         }
        return ok;
    } // fin metodo buscarCta        
    
    
    public void listarCuentas(){ // metodo qie lista la cuenta bancarias del arraylist
        
        for(CuentaBancaria ls: lista){ // se recorre el array del tipo CuentaBancaria
          // el metodo ToString a sido sobre escrito en cada clase que heredó
          // de la Clase Abstracta CuentaBancaria
            System.out.println(ls.ToString());
            
         }
      
     }  //fin metodo listarCuentas
    
    public void datosDeUnaCuenta(String que){  //metodo que muestra los datos de una cuenta en
      //concreto, pero el parametro indica que tipo de reporte indicará, si son datos de la cuenta
      // o el saldo
        boolean ok=true;     // logica para si hay o no error en el numero de cuenta a buscar                  
        String buscar="";    // variable que contendra el numero de la cuenta a buscar
        
        // el while solo dejara de hacer el bucle hasta que ok sea igual a false
        // lo que significará que la cuenta que se ha escrito es correcta en formato
        // y digito de control
        while(ok){
        // se obtiene el numero de cuenta concreta
        buscar=JOptionPane.showInputDialog(null,"Indique el número de cuenta CCC a consultar");
                // se verifica que reune el formato, que sean numeros y que sean 20 digitos 
               Pattern numcta=Pattern.compile("([0-9]{20})"); // se compila el patron
               Matcher mat=null; // se crea que el comparador del patron
               try{
               mat= numcta.matcher(buscar); // se compara el patrón con la variable buscar
                                           // mat contendrá el valor logico de la comparacion
                                           // al llamar el metodo matches
               }catch(NullPointerException e){// se captura el error si mat es nulo (null)se pulso
                // el boton cancelar, con el recuadro en blanco provoca un error de exception se 
                // captura, sale del while y continua el programa
                // esto provoca que se termine la ejecución del metodo y envia el control al frame
                // del menu principal
                   System.out.println("El usuario pulso el botón Cancelar");
                break; // sale del while
               }
               
            // si la longitud sin espacios en blanco es diferente de 20 y no pasa el patron
            if(!mat.matches()){ // valor evaluado es false
                ok=true; // la variable logica ok marca que hay error
                // se imprime en una ventana el error
               JOptionPane.showMessageDialog(null,"Error, escriba sólo 20 números","Número de cuenta Erróneo",ERROR_MESSAGE);

                   }else{ // si pasa lo anterior, aqui verifica si la cuenta es valida
                   // Aqui se obtiene el digito de control correcto
                int dc=Integer.parseInt(CalculoDigitoControl.calculoDigCtrl(buscar));
                    // verificacion de que el digito de control es correcto
                if(dc!=Integer.parseInt(buscar.substring(8,10))){
                    ok=true; // el digito no es correcto variabel de control marca que hay error
                   JOptionPane.showMessageDialog(null,"Error, en el número de Cuenta CCC","Digito de Control de cuenta Erróneo",ERROR_MESSAGE);
                    // fin if-else verificacion del Digito de Control       
                  }else{ok=false;}
                }
        }// fin del while
   
        // Aqui seguirá controlandose que exista la cuenta
        String mensa=""; // se colocara los datos de la cuenta si la misma es encontrada
        // se recorre el array lista para buscar la cta a buscar
        for(CuentaBancaria ls: lista){
            //compara la cuenta de la lista con buscar
            if(ls.getNro_cuenta().equalsIgnoreCase(buscar)){ 
                // si encuentra coincidencia determina el tipo de cuenta       
                if(ls.getTipo_cuenta().equalsIgnoreCase("ahorro")){
                 // si es ahorro entra aqui y un un cast para convertir la CuentaBancaria
                 // de la variable ls de la lista en un objeto del tipo de cuenta ahorro
                 // ya que es el tipo que tiene verdaderamente el objeto obtenido en ls
                    CuentaAhorro ah=(CuentaAhorro)ls; // ah es el objeto CuentaAhorro
                    // en esta seccion de ifs se verifica la variable "que", que indica si son datos o saldo
                    if(que.equalsIgnoreCase("datos")){ 
                        // si que =datos, se iguala la variable mensa con los datos a mostrar
                    mensa="Número de Cuenta: "+ah.getNro_cuenta()+
                    "\n Titular de la cuenta: "+ah.getCliente().getNombre()+" "+ah.getCliente().getApellidos()+
                    "\n Tipo de Cuenta: "+ah.getTipo_cuenta()+"\n Tipo de Interes Anual: "+
                    ah.getInteres_anual()+"\n Saldo en Cuenta: "+ah.getSaldo();
                    }else{ // sino la varible por descarte que=saldo aunque puede se cualquier cosa menos la palabra "datos"
                        // mensa se carga con los datos de cuenta y saldo
                        mensa="Número de Cuenta: "+ah.getNro_cuenta()+"\n Saldo en Cuenta: "+ah.getSaldo();
                    }
                  // lo mismo anterior se repite para las siguientes lineas para tipo scuenta personal
                } else if(ls.getTipo_cuenta().equalsIgnoreCase("personal")){
                    CuentaPersonal per=(CuentaPersonal)ls;
                    if(que.equalsIgnoreCase("datos")){                    
                    mensa=" Cuenta: "+per.getNro_cuenta()+"\n Tipo: "+per.getTipo_cuenta()+
                    "\n Titular: "+per.getCliente().getNombre()+" "+per.getCliente().getApellidos()+"\n Comision Anual: "+
                    per.getComisionAnual()+"\n Saldo en Cuenta: "+per.getSaldo();
                    }else{mensa="Número de Cuenta: "+per.getNro_cuenta()+"\n Saldo en Cuenta: "+per.getSaldo();}
                         // y aqui lo mismo para el tipo de cuenta empresa 
                        }else if(ls.getTipo_cuenta().equalsIgnoreCase("empresa")){
                           CuentaEmpresa emp=(CuentaEmpresa)ls;
                           if(que.equalsIgnoreCase("datos")){                           
                           mensa=" Cuenta: "+emp.getNro_cuenta()+"\n Tipo: "+emp.getTipo_cuenta()+
                           "\n Titular: "+emp.getCliente().getNombre()+" "+emp.getCliente().getApellidos()+"\n Maximo Diferido permitido: "+
                           emp.getMaximoDescubierto()+"\n Interes sobre Descubierto: "+emp.getInteresDescubierto()+
                           "\n Comision opr cada Descubierto: "+emp.getComisionDescubierto()+"\n Saldo en Cuenta: "+emp.getSaldo();
                           }else{mensa="Número de Cuenta: "+emp.getNro_cuenta()+"\n Saldo en Cuenta: "+emp.getSaldo();}
                            
                                }
            // finalmente aqui se muestra por pantalla en un ventana los datos pedidos    
            JOptionPane.showMessageDialog(null, mensa," * Datos de la Cuenta * ",INFORMATION_MESSAGE);                
             break; // aqui se sale del for ya que no hace falta seguir buscando nada
            }
        } //fin del for
   
    } // fin del metodo datosDeUnaCuenta
    
// este fram en llamada al instanciarse el objeto app de la clase principal
// e inicia el programa mostrando una ventana con las opciones que ofrece
// imlementa la interface Actionlistener que captura las pulsacines que se hagan
// con el raton sobre los botones    
public class MenuPrincipal extends JFrame implements ActionListener{

    private JLabel labelTitulo, labelSeleccion; // se defienen la etiquetas informativas
    // en las siguentes sentencias se definen todos los botones asociados a las opciones
    private JButton botonAbrirCuenta,botonListado,botonDatosCuenta;
    private JButton botonIngresar,botonRetirar,botonConsultaSaldo,botonSalirApp;	
            
    	public MenuPrincipal() {
         /**
	 * constructor de la clase donde se inicializan todos los componentes
	 * de la ventana MenuPrincipal
	 */
         
         //este setDefault se coloca en el frame de menu principal, dado que es el unico
         // que debe estar habilitado para terminar la aplicación al salir de la ventana
         // por esa razón no debe existir en las ventanas que se abren desde aquí.
                setDefaultCloseOperation(EXIT_ON_CLOSE);

                // Opciones del Menu Principal
                botonAbrirCuenta = new JButton(); // se intancia el objeto boton
		botonAbrirCuenta.setBounds(65,70,350,25); // se ubica en un lugar de la ventana
                // eje x,eje y, longitud del botón, altura del botón 
		botonAbrirCuenta.setText("Abrir Cuenta Nueva");// se le pone un nombre al botón
                 // igual para los siguientes
                botonListado = new JButton();
		botonListado.setBounds(65,100,350,25);
		botonListado.setText("Listado de cuentas Disponibles");                
                
                botonDatosCuenta = new JButton();
                botonDatosCuenta.setBounds(65,130,350,25);
                botonDatosCuenta.setText("Obtener los datos de una cuenta concreta");
                
                botonIngresar = new JButton();
                botonIngresar.setBounds(65,160,350,25);
                botonIngresar.setText("Realizar un ingreso en una cuenta concreta");                
                
                botonRetirar = new JButton();
                botonRetirar.setBounds(65,190,350,25);
                botonRetirar.setText("Realizar un retiro en una cuenta concreta");                

                botonConsultaSaldo = new JButton();
                botonConsultaSaldo.setBounds(65,220,350,25);
                botonConsultaSaldo.setText("Consultar el saldo de una cuenta concreta"); 
                
		botonSalirApp = new JButton();
		botonSalirApp.setBounds(150, 280, 185, 25);
		botonSalirApp.setText("Salir de la Aplicación");
		
                // se coloca un etiqueta a modo de titulo
		labelTitulo = new JLabel(); // se instnacia la etiqueta
		labelTitulo.setText("Menú Principal"); // se escribe el contenido de la misma
		labelTitulo.setBounds(60, 20, 380, 30);// ubicacion en ventana (x,y,length,heigth)
                // aqui se le asigna una fuente de letra, un estilo, y un tamaño de letra
		labelTitulo.setFont(new java.awt.Font("Verdana", 1, 15));

                // lo mismo que lo anterior pero no se modifica la fuente dejandose por defecto
		labelSeleccion = new JLabel();
		labelSeleccion.setText("Pulse en el botón de la operacion que desea realizar");
		labelSeleccion.setBounds(50, 250, 380, 25);
                //se aññade el capturar del evento click mouse a cada botón de las opciones
                botonAbrirCuenta.addActionListener(this);
                botonListado.addActionListener(this);
                botonDatosCuenta.addActionListener(this);
                botonIngresar.addActionListener(this);
                botonRetirar.addActionListener(this);
                botonConsultaSaldo.addActionListener(this);                
                botonSalirApp.addActionListener(this);

               // se añaden los botones y etiquetas al fram de la ventana     
                add(botonAbrirCuenta);
                add(botonListado);
                add(botonDatosCuenta);
                add(botonIngresar);                
                add(botonRetirar);
                add(botonConsultaSaldo);
		add(botonSalirApp);
		add(labelSeleccion);
		add(labelTitulo);
	
                // Propiedades del Frame
		setSize(480, 350);  // tamaño ancho por alto
		setTitle(" * Aplicación Cuenta Bancaria * "); //titulo de la ventana
		setLocationRelativeTo(null); // posicion relativa igual null, para centrarlo
                // en la pantalla
		setResizable(false);// ventana no se puede redimensionar
		setLayout(null);  // a la ventana no se le aplica ningún layout
                setVisible(true);  // se hace visible el frame de la ventana

	} // fin del Constructor

        // aqui estan los metodos que definen las acciones a tomar por cada evento capturado
        // por el ActionListener 
        // ActionListener es una interface por lo cual hay que sobreescribir los metodos
	@Override
	public void actionPerformed(ActionEvent e) { // captura el evento click sobre el botonAbrirCuenta
            if (e.getSource()==botonAbrirCuenta){
                   Registro ac=new Registro();// define e inicializa la ventana Registro
                   // esa ventana captura los datos que tiene que escribir el user de la aplicacion
                   ac.setVisible(true); // se hace visible la ventana
                   this.dispose(); // se cierra y descarta la Ventana MenuPrinciapl
            }
            
            if (e.getSource()==botonListado){// este boton emite un listado por pantalla de las cuentas
                // contneidas en el ArrayList lista
                listarCuentas();
            }
            
            if(e.getSource()==botonDatosCuenta){ // este lanza el metodo para mostrar los datos de la cuenta
                datosDeUnaCuenta("datos"); // pasa el parametro datos el metodo procesa otro tipo de reporte
                                            // tambien
            }
            if(e.getSource()==botonIngresar){ // boton para ingresar dinero a la cuenta
                   Ingresar ig=new Ingresar(); //Define e instancia la ventana Ingresar
                   ig.setVisible(true); // igual que la anteries ventanas
                   this.dispose();
            }
            if(e.getSource()==botonRetirar){// boton para hacer retiros de la cuenta
                   Retirar rt=new Retirar();
                   rt.setVisible(true);
                   this.dispose();
            }              

            if(e.getSource()==botonConsultaSaldo){ //boton para consultar datos
                datosDeUnaCuenta("saldo"); // usa el mismo metodo que la de datosDeUnaCuenta
            }                          
            
            if (e.getSource()==botonSalirApp) { // boton para salir de la aplicacion
                    this.dispose(); // cierra el menu y termina el programa ya que esta es la
                    //unica que tiene la propiedad  setDefaultCloseOperation(EXIT_ON_CLOSE);
	     }

	 }
        
} //fin clase MenuPrincipal (JFrame)

// este frame muestra una ventana que muestra un formulario a ser rellenado por el usuario
// implementa la interfaces para controlar eventos de raton y de focus sobre campos
public class Registro extends JFrame implements ActionListener,FocusListener{
  
        //se definen uno para cada campo para mostrar si hay error en el mismo cada campo
        //tendrá asociado uno. Se definen 10 nuemrados del 1 al 9, pero solo se utilizaron
        // 9 que ya que el cero esta reservado para un contorl final que no fue necesario 
        
        private boolean hayerror[]={true,true,true,true,true,true,true,true,true,true};
        // creacion de etiquetas que identifican los campos          
	private JLabel labelTitulo;
        private JLabel cuenta,nombre,apellidos,fec_nac,saldo,error_cuenta,tipo_cuenta;
        private JLabel interesAhorro,comisionAnual,maximoDescubierto,interesDescubierto;
        private JLabel comisionDescubierto,errorInteresAhorro;
        // creacion de campos para cada dato necesario
	private JTextField textCuenta,textNombre,textApellidos,textFec_nac,textSaldo;
        private JTextField textInteresAhorro,textInteresPersonal,textMaximoDescubierto;
        private JTextField textInteresDescubierto,textComisionDescubierto,textComisionAnual;
        // creación de botones de Guardar para regsitrar la cuenta y cancelar para terminar sin 
        // hacer nada
	private JButton botonGuardar,botonCancelar;
        // se crea un BotonGroup que tendrá asociados botones del tipo Radio que son excluyentes
        // entre si, es deicr que solo uno de los que formen el grupo puede estar marcado
        // a los radiobotones se les pone un nombre y un valor de false, para que ninguno
        // este marcado al inicio de la ventana. 
        private JRadioButton radioAhorro=new JRadioButton("Ahorro",false);
        private JRadioButton radioPersonal=new JRadioButton("Corriente Personal",false);
        private JRadioButton radioEmpresa=new JRadioButton("Corriente Empresa",false);    
        private ButtonGroup grupo1 = new ButtonGroup();	

        ConexionAOracle baseDat= new ConexionAOracle();    
	/**
	 * constructor de la clase donde se inicializan todos los componentes
	 * de la ventana Registro
	 */
	public Registro(){
            // el significado de cada grupo de sentencias es el mismo que lo explicado
            // para el menu principal, aqui no se lleva un orden en especifico
            // sino se agrupan todas la propiedades de cada elemento
            
                interesAhorro=new JLabel();
                interesAhorro.setText("");
		interesAhorro.setBounds(40, 310, 150, 25);
   		add(interesAhorro);
                
                textInteresAhorro=new JTextField();
                textInteresAhorro.addFocusListener(this); 
                add(textInteresAhorro);
                
                comisionAnual=new JLabel();
                comisionAnual.setText("");
		comisionAnual.setBounds(230, 320, 220, 25);
   		add(comisionAnual);
                
                textComisionAnual=new JTextField();
                textComisionAnual.addFocusListener(this); 
                add(textComisionAnual);
                
                maximoDescubierto=new JLabel();
                maximoDescubierto.setText("");
		maximoDescubierto.setBounds(250, 320, 220, 25);
   		add(maximoDescubierto);
                
                textMaximoDescubierto=new JTextField();
                textMaximoDescubierto.addFocusListener(this); 
                add(textMaximoDescubierto);                

                interesDescubierto=new JLabel();
                interesDescubierto.setText("");
		interesDescubierto.setBounds(250, 360, 220, 25);
   		add(interesDescubierto);
                
                textInteresDescubierto=new JTextField();
                textInteresDescubierto.addFocusListener(this); 
                add(textInteresDescubierto);   
                
                comisionDescubierto=new JLabel();
                comisionDescubierto.setText("");
		comisionDescubierto.setBounds(250, 400, 220, 25);
   		add(comisionDescubierto);
                
                textComisionDescubierto=new JTextField();
                textComisionDescubierto.addFocusListener(this); 
                add(textComisionDescubierto);                   
                

            // Creación del Grupo de Botones tipo radio
                radioAhorro.setBounds(150,280,80,25);
                radioPersonal.setBounds(250,280,160,25);
                radioEmpresa.setBounds(250,300,160,25);
                radioAhorro.addFocusListener(this);
                radioPersonal.addFocusListener(this);
                radioEmpresa.addFocusListener(this);
                grupo1.add(radioAhorro);
                grupo1.add(radioPersonal);
                grupo1.add(radioEmpresa);

                add(radioAhorro);
                add(radioPersonal);
                add(radioEmpresa);            

		botonGuardar = new JButton();
		botonGuardar.setBounds(110, 460, 120, 25);
		botonGuardar.setText("Registrar");
		
		botonCancelar = new JButton();
		botonCancelar.setBounds(250, 460, 120, 25);
		botonCancelar.setText("Cancelar");

		labelTitulo = new JLabel();
		labelTitulo.setText("REGISTRO DE CUENTAS NUEVAS");
		labelTitulo.setBounds(80, 20, 380, 30);
		labelTitulo.setFont(new java.awt.Font("Verdana", 1, 18));
                
		cuenta=new JLabel();
		cuenta.setText("Codigo Cuenta CCC");
		cuenta.setBounds(20, 80, 160, 30);
		cuenta.setFont(new java.awt.Font("Verdana", 1, 13));                
		add(cuenta);
		
		nombre=new JLabel();
		nombre.setText("Nombre");
		nombre.setBounds(20, 120, 80, 25);
		add(nombre);

		apellidos=new JLabel();
		apellidos.setText("Apellidos");
		apellidos.setBounds(20, 160, 80, 25);
		add(apellidos);                  
                
		fec_nac=new JLabel();
		fec_nac.setText("Fecha Nacimiento");
		fec_nac.setBounds(20, 200, 130, 25);
		add(fec_nac);
                             
		saldo=new JLabel();
		saldo.setText("Saldo Inicial Cuenta");
		saldo.setBounds(20, 240, 140, 25);
		add(saldo);
                
                tipo_cuenta=new JLabel();
                tipo_cuenta.setText("Tipo de Cuenta");
                tipo_cuenta.setBounds(20,280,110,25);
                add(tipo_cuenta);
	
		error_cuenta=new JLabel();
		error_cuenta.setBounds(170, 50, 250, 30);
                error_cuenta.setForeground(Color.red);
                add(error_cuenta);
                
                errorInteresAhorro=new JLabel();
                errorInteresAhorro.setBounds(145,330,270,25);
                add(errorInteresAhorro);
                
		textCuenta=new JTextField();
		textCuenta.setBounds(170, 80, 210, 30);
		textCuenta.setFont(new java.awt.Font("Verdana", 1, 15));
                textCuenta.addFocusListener(this);
		add(textCuenta);
		
		textNombre=new JTextField();
		textNombre.setBounds(100, 120, 190, 25);
                textNombre.addFocusListener(this);                
		add(textNombre);

		textApellidos=new JTextField();
		textApellidos.setBounds(100, 160, 190, 25);
                textApellidos.addFocusListener(this);                
		add(textApellidos);
                
                textFec_nac=new JTextField();
		textFec_nac.setBounds(170, 200, 80, 25);
                textFec_nac.addFocusListener(this);                
		add(textFec_nac);                
                
		textSaldo=new JTextField();
		textSaldo.setBounds(170, 240, 80, 25);
                textSaldo.addFocusListener(this);                
		add(textSaldo);
		
		botonGuardar.addActionListener(this);
		botonCancelar.addActionListener(this);
		add(botonCancelar);
		add(botonGuardar);
		add(labelTitulo);

                limpiar();
		setSize(480, 500);
		setTitle("Apertura de Cuenta Bancaria nueva");
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(null);
                setVisible(true);
	}

        // se limpian los campos para segurar de que no tiene ningún contenido indeseable
	private void limpiar(){ // metodo que limpia los campos
		textCuenta.setText(""); textNombre.setText("");
                textApellidos.setText(""); textFec_nac.setText("");
		textSaldo.setText(""); textInteresAhorro.setText("");
                textComisionAnual.setText("");textComisionDescubierto.setText("");
                textInteresDescubierto.setText("");textMaximoDescubierto.setText("");
	}

        
	@Override
	public void actionPerformed(ActionEvent e){// evento sobre Guardar
		if (e.getSource()==botonGuardar){
                    //se reemplaza la coma por punto en el cmapo saldo
                    textSaldo.setText(textSaldo.getText().replace(",","."));
                    // se define ctabanca del la clase CuentaBancaria y se asgina null
                    CuentaBancaria ctabanca=null;
			try {
                            // se llama al metodo hayError, si este devuelve true sigue adelante
                            if(hayError()){ // OJO el valor devuelto contradice la pregunta pero es correcto el funcionamiento
            // una vez llenado el formulario y que todos los campos esten libre de errores y
            //  con contenido
                        //    if(baseDat.construirConexion("XE","josem","viviana1")!=null){
                              if(baseDat.construirConexion()!=null){                                  
                                    System.out.println("Estoy conectado y puedo grabar lo que sea");
                                       
                                    
                               }else {

                                        System.out.println("ERROR, no se conectó a la Base Datos no se ha grabado nada!!!!!!");}
            
            
                            //se le quita el coma al campo textSaldo y se le coloca un punto
                            // de lo contrario al hacer cast a Double lanza un error de exception
/* insert into clientes values( 
 persona_obj(1, '123456789', 'aitor', 'rigada', '01/01/1900'), 
 direccion_obj('Mimoses S/N', '08000', 'Sant Boi', 'Barcelona'), 
 sysdate);*/                            
                            String sqlq="insert into cuentasbancarias values(";
//"insert into cuentasbancarias values(PERSONA(cta.getNombre(),cta.getApellidos(),cta.getFec_nac()),cta.getNro_cuenta(),cta.getTipo_cuenta(),cta.getSaldo(),cta.getInteres_anual())"                            
                           textSaldo.setText(textSaldo.getText().replace(",","."));                            
                            String tipoCuenta=""; // se coloca vacio para luego al guardarse en el array tenga el valor apropiado 
                                if(radioAhorro.isSelected()){ // si el radioboton Ahorro esta marcado
                                  tipoCuenta="Ahorro"; // se pone tipoCuenta con el valor Ahorro
                                  // reemplazamos la coma del campo interesAhorro por el punto
                                  textInteresAhorro.setText(textInteresAhorro.getText().replace(",","."));
                                  //se crea un objeto ctabanca que será de la clase CuentaAhorro aunque
                                  // la clase de ctabanca en CuentaBancaria, al ser derivada CuentaAhorro
                                  // piede ser instanciada
                                    ctabanca=new CuentaAhorro();
                                 // ahora se defien una variable del tipo CuentaAhorro y se le asigna
                                 // la referencia de ctabanca haciendo un casting para que tome todos
                                 // los atributos de la clase ahorro, que seran guardados en el ArrayList
                                    CuentaAhorro cta=(CuentaAhorro)ctabanca;
                                    // Definimos e instanciamos un objeto cliente del tipo Persona
                                    //ya que unos de los atributos de la clase CuentaAhorro heredado de CuentaBancaria
                                    // es un objeto de este tipo Persona, se instancia con los valores escritos en los campos
                                    // correspondiente del formulario nombre apellidos y fecha de nacimiento
                                    Persona cliente=new Persona(textNombre.getText(),textApellidos.getText(),DstringAdate.main(textFec_nac.getText())) {};
                                    cta.setCliente(cliente); // se le asigna al campo cliente de CuentaAhorro el objeto Cliente
                                    // se guardan los demas atributos seugn su valor contenido en los campos del formulario
                                    cta.setNro_cuenta(textCuenta.getText());
                                    cta.setSaldo(Double.parseDouble(textSaldo.getText()));
                                    cta.setTipo_cuenta(tipoCuenta);
                                    cta.setInteres_anual(Double.parseDouble(textInteresAhorro.getText()));                                  
                                    // Se añade el objeto CuentaAhorro con todos sus datos al ArrayList lista
                                    lista.add(cta);
                                    
                                    sqlq=sqlq+"PERSONA(cta.getNombre(),cta.getApellidos(),cta.getFec_nac()),cta.getNro_cuenta(),cta.getTipo_cuenta(),cta.getSaldo(),cta.getInteres_anual())";
                                            
                                    PreparedStatement ps=baseDat.getConn().prepareStatement(sqlq);
                                    ps.executeUpdate();
                                    baseDat.getConn().close();

                                }else if(radioPersonal.isSelected()){
                                        tipoCuenta="Personal";
                                        textComisionAnual.setText(textComisionAnual.getText().replace(",","."));
                                        ctabanca=new CuentaPersonal();
                                        CuentaPersonal cta=(CuentaPersonal)ctabanca;
                                        Persona cliente=new Persona(textNombre.getText(),textApellidos.getText(),DstringAdate.main(textFec_nac.getText())) {};
                               
                                        cta.setNro_cuenta(textCuenta.getText());
                               
                                        cta.setCliente(cliente);
                                        cta.setSaldo(Double.parseDouble(textSaldo.getText()));
                                        cta.setTipo_cuenta(tipoCuenta);
                                        cta.setComisionAnual(Double.parseDouble(textComisionAnual.getText()));
                                        Double sald=Double.parseDouble(textSaldo.getText());
                                        cta.agregarOficina(textCuenta.getText().substring(4,8),sald);
                                        lista.add(cta);                                        
                                        
                                        }else if(radioEmpresa.isSelected()){
                                                tipoCuenta="Empresa";
                                                textMaximoDescubierto.setText(textMaximoDescubierto.getText().replace(",","."));
                                                textInteresDescubierto.setText(textInteresDescubierto.getText().replace(",","."));
                                                textComisionDescubierto.setText(textComisionDescubierto.getText().replace(",","."));
                                                ctabanca=new CuentaEmpresa();
                                                CuentaEmpresa cta=(CuentaEmpresa)ctabanca;
                                                Persona cliente=new Persona(textNombre.getText(),textApellidos.getText(),DstringAdate.main(textFec_nac.getText())) {};                                        
                                                cta.setNro_cuenta(textCuenta.getText());
                                                cta.setCliente(cliente);
                                                cta.setSaldo(Double.parseDouble(textSaldo.getText()));
                                                cta.setTipo_cuenta(tipoCuenta);
                                                cta.setMaximoDescubierto(Double.parseDouble(textMaximoDescubierto.getText()));
                                                cta.setInteresDescubierto(Double.parseDouble(textInteresDescubierto.getText()));
                                                cta.setComisionDescubierto(Double.parseDouble(textComisionDescubierto.getText()));
                                                Double sald=Double.parseDouble(textSaldo.getText());                                                
                                                cta.agregarOficina(textCuenta.getText().substring(4,8),sald);
                                                lista.add(cta);                                                  
                                                
                                                }//else{
                                               //         throw Error;
                                                //    }
                               //aqui se vuelve a instanciar el menu principal                     
                                MenuPrincipal mp= new MenuPrincipal();
                                mp.setVisible(true); // se coloca visible 
                                this.dispose();// se descarta el frame Registrar
                            }else{ 
                             // hay errores de tipo formato en los campos del formulario
                             // se envia el mensaje
                            JOptionPane.showMessageDialog(null,"Revise los campos, porque existe uno o más errores","Hay errores en el formulario", ERROR_MESSAGE);
                            
                            }
	
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null,"Error en el Ingreso de Datos"+ex,"Error",JOptionPane.ERROR_MESSAGE);
			}
		} //fin botonGuardar
                // este boton simplemente cancela la operacion de nos devuelve al menu principal
                if (e.getSource()==botonCancelar){
                        MenuPrincipal mp= new MenuPrincipal();
                        mp.setVisible(true);
			this.dispose();
		} //fin botonCancelar
                    
	}  // fin metodo actionPerformed

        
    // metodos cuando el campo gana el foco    
        
   
    @Override
    public void focusGained(FocusEvent e) {
        // el parametro FocusEvent e nos indica que campo tiene el foco
        // y se ejecutan la sentencias indicadas
                if(e.getSource()==textCuenta){
                 //   errorInteresAhorro.setText("");
                  //  errorInteresAhorro.setVisible(false);
                }
          // evidentemente al marcar los radiobotones se gana el foco      
         // estas acciones se ejecutan al marcar los radiobotones  
         // esta parte d elos radio botones tambien controla los campos
         // adicionales que se necesitan según el tipo de cuenta que se registra
         // es decir para cuenta ahorro solo habilita el campo InteresAhorro que
         // que se corresponde con ese tipo de cuenta.
         // Para tipo Personal será Comision mantenimiento
         //Para tipo Empresa se habilitaran MaximoDescubierto, InteresDescubierto y ComisionDescubierto
         // Solo se mostrara el campo asociado los demas estaran ocultos
         
                 if(e.getSource()==radioAhorro){
                    interesAhorro.setText("Tipo Interes Ahorro");
                    textInteresAhorro.setBounds(95, 330, 50, 25);
                    add(textInteresAhorro);
                    
                    interesAhorro.setVisible(true);                 
                    textInteresAhorro.setVisible(true);
                    errorInteresAhorro.setVisible(false);

                    textComisionAnual.setVisible(false);
                    comisionAnual.setVisible(false);
                    
                    maximoDescubierto.setVisible(false);
                    textMaximoDescubierto.setVisible(false);
                    interesDescubierto.setVisible(false);
                    textInteresDescubierto.setVisible(false);
                    comisionDescubierto.setVisible(false);
                    textComisionDescubierto.setVisible(false);
                 }

                if(e.getSource()==radioPersonal){
                    comisionAnual.setText("Comisión Mantemiento Anual");
                    textComisionAnual.setBounds(310, 340, 50, 25);
                    add(textComisionAnual);
                    comisionAnual.setVisible(true);
                    textComisionAnual.setVisible(true);
                    
                    textInteresAhorro.setVisible(false);
                    interesAhorro.setVisible(false);

                    maximoDescubierto.setVisible(false);
                    textMaximoDescubierto.setVisible(false);
                    interesDescubierto.setVisible(false);
                    textInteresDescubierto.setVisible(false);
                    comisionDescubierto.setVisible(false);
                    textComisionDescubierto.setVisible(false); 
                  }
                
                if(e.getSource()==radioEmpresa){
                    maximoDescubierto.setText("Máximo Descubierto");
                    textMaximoDescubierto.setBounds(310, 340, 50, 25);
                    add(textMaximoDescubierto);
                    
                    interesDescubierto.setText("Interés por Descubierto");
                    textInteresDescubierto.setBounds(310,380,50,25);
                    
                    comisionDescubierto.setText("Comisión por Descubierto");
                    textComisionDescubierto.setBounds(310,420,50,25);
                    
                    maximoDescubierto.setVisible(true);
                    textMaximoDescubierto.setVisible(true);
                    interesDescubierto.setVisible(true);
                    textInteresDescubierto.setVisible(true);
                    comisionDescubierto.setVisible(true);
                    textComisionDescubierto.setVisible(true);

                    textComisionAnual.setVisible(false);
                    comisionAnual.setVisible(false);
                    
                    textInteresAhorro.setVisible(false);
                    interesAhorro.setVisible(false);
                 }                  
    } //fin metodos cuando el campo gana el foco 
    
     // metodos cuando el campo pierde el foco
    @Override
    public void focusLost(FocusEvent e) {
        
    // los campos basicamente compara el contenido del campo text contra un patron
// que determina si el dato es valido, cada campo tiene la variable hayerror que inicialmente
// es pone true que significa que es valido y se coloca a false si hay error

         if(e.getSource()==textCuenta){
        
               Pattern numcta=Pattern.compile("([0-9]{20})"); 
               Matcher mat=null;
               mat= numcta.matcher(textCuenta.getText());
               
                hayerror[1]=true;               
            // si la longitud sin espacios en blanco es diferente de 20
            if(!mat.matches()){
                error_cuenta.setText("Error, escriba sólo 20 números");                  
                textCuenta.setFont(new java.awt.Font("Verdana", 2,15));
                textCuenta.setBackground(Color.RED); // se coloca el fonde en rojo en los campos con error
                hayerror[1]=false; // se coloca la variabel como falsa no es valido el campo
                  }else{ // aqui verifica si la cuenta es valida
                        textCuenta.setBackground(Color.WHITE); // se pone el fondn en blanco si es valido el campo
                        error_cuenta.setText(""); // desaparece el mensaje de error, puede aparecer en la misma ventana
                        // o en la consola
                // Aqui se obtiene el digito de control correcto
                int dc=Integer.parseInt(CalculoDigitoControl.calculoDigCtrl(textCuenta.getText()));
                    // verificacion de que el digito de control es correcto
                 if(dc!=Integer.parseInt(textCuenta.getText().substring(8,10))){
                        textCuenta.setFont(new java.awt.Font("Verdana", 2,15));
                        textCuenta.setBackground(Color.RED);
                        error_cuenta.setText("Error en el numero de Cuenta CCC");  
                    // fin if-else verificacion del Digito de Control       
                    hayerror[1]=false;
                  }else if(buscarCta(textCuenta.getText())){
                  
                      error_cuenta.setText("Error...  el numero de Cuenta YA EXISTE !!!");
                      hayerror[1]=false;
                  }
                 
                }
            } // fin del if de textCuenta
        
         // se sigue la misma lógica como se hizo en el campo anterior pero ajustado 
         // al tipo de campo por supuest. Igual para todo los campos
         // esta seccion evalua dos campos que utilizan el mismo patrón por ese 
         // se usa le operador logico "or" ||
        if(e.getSource()==textNombre || e.getSource()==textApellidos){
            
               Pattern nomape=Pattern.compile("([A-Za-z]?'?[A-Za-z]{1}[a-zñáéíóú]+[\\s]*)+"); 
               Matcher mat=null;
               mat=(e.getSource()==textNombre)? 
                    nomape.matcher(textNombre.getText()):
                    nomape.matcher(textApellidos.getText());
               hayerror[2]=true;
               if(!mat.matches()){
               
                    System.out.println("Solo letras");
                    hayerror[2]=false;
                    if(e.getSource()==textNombre){
                        textNombre.setBackground(Color.RED);}
                    else{textApellidos.setBackground(Color.RED);}
                    
               }else{
                     if(e.getSource()==textNombre){
                        textNombre.setBackground(Color.WHITE);}
                     else{textApellidos.setBackground(Color.WHITE);}    
                    }
        } //fin textNombre textApellidos
        
        if(e.getSource()==textFec_nac){
               Pattern fech=Pattern.compile("([0][1-9]|[12][0-9]|3[01])(\\/|-)([0][1-9]|[1][0-2])\\2(\\d{4})"); 
               Matcher mat=null;
               hayerror[3]=true;
               mat=fech.matcher(textFec_nac.getText());
               
               if(!mat.matches()){
               
                    System.out.println("Solo numeros y barras inclinadas DD/MM/AAAA");
                    hayerror[3]=false;
                    textFec_nac.setBackground(Color.RED);                   
               }else{textFec_nac.setBackground(Color.WHITE);}
        } // fin textFec_nac
        
        if(e.getSource()==textSaldo){
            Pattern sald=Pattern.compile("(\\d{1,10}),?(\\d{1,2})?");            
            Matcher mat=null;
            mat=sald.matcher(textSaldo.getText());
               hayerror[4]=true;
               if(!mat.matches()){
               
                    System.out.println("Solo numeros y si hay decimal coma ...,xx");
                    hayerror[4]=false;
                    textSaldo.setBackground(Color.RED);                    
                    
               }else{textSaldo.setBackground(Color.WHITE);}            
        } //fin del textSaldo
        
        if(e.getSource()==textInteresAhorro){
            hayerror[6]=true;               
            hayerror[7]=true;            
            hayerror[8]=true;            
            hayerror[9]=true;            
            
            Pattern sald=Pattern.compile("(\\d{1,2}),?(\\d{1,2})?");            
            Matcher mat=null;
            mat=sald.matcher(textInteresAhorro.getText());
             
               hayerror[5]=true;
               if(!mat.matches()){
                   hayerror[5]=false;
                   errorInteresAhorro.setVisible(true);
                   errorInteresAhorro.setText("Solo numeros y coma para decimales");
                   
                   textInteresAhorro.setBackground(Color.RED);
               }else{
                   errorInteresAhorro.setText("");
                   textInteresAhorro.setBackground(Color.WHITE);}        

        }
        
        if(e.getSource()==textComisionAnual){
            hayerror[5]=true;
            hayerror[7]=true;            
            hayerror[8]=true;            
            hayerror[9]=true;
            
            Pattern sald=Pattern.compile("(\\d{1,2}),?(\\d{1,2})?");            
            Matcher mat=null;
            mat=sald.matcher(textComisionAnual.getText());
               hayerror[6]=true;
               if(!mat.matches()){
                   System.out.println("Solo numeros y si hay decimal coma ...,xx");
                   hayerror[6]=false;
                   textComisionAnual.setBackground(Color.RED);
               }else{textComisionAnual.setBackground(Color.WHITE);}
         }

        if(e.getSource()==textMaximoDescubierto){
            hayerror[5]=true;
            hayerror[6]=true;            
            
            Pattern sald=Pattern.compile("(\\d{1,2}),?(\\d{1,2})?");            
            Matcher mat=null;
            mat=sald.matcher(textMaximoDescubierto.getText());
               hayerror[7]=true;
               if(!mat.matches()){
                   System.out.println("Solo numeros y si hay decimal coma ...,xx");
                   hayerror[7]=false;
                   textMaximoDescubierto.setBackground(Color.RED);
               }else{textMaximoDescubierto.setBackground(Color.WHITE);}
         }  

        if(e.getSource()==textInteresDescubierto){
            hayerror[5]=true;
            hayerror[6]=true;              
            
            Pattern sald=Pattern.compile("(\\d{1,2}),?(\\d{1,2})?");            
            Matcher mat=null;
            mat=sald.matcher(textInteresDescubierto.getText());
               hayerror[8]=true;
               if(!mat.matches()){
                   System.out.println("Solo numeros y si hay decimal coma ...,xx");
                   hayerror[8]=false;
                   textInteresDescubierto.setBackground(Color.RED);
               }else{textInteresDescubierto.setBackground(Color.WHITE);}
         }
        
        if(e.getSource()==textComisionDescubierto){
            hayerror[5]=true;
            hayerror[6]=true;              

            Pattern sald=Pattern.compile("(\\d{1,2}),?(\\d{1,2})?");            
            Matcher mat=null;
            mat=sald.matcher(textComisionDescubierto.getText());
               hayerror[9]=true;
               if(!mat.matches()){
                   System.out.println("Solo numeros y si hay decimal coma ...,xx");
                   hayerror[9]=false;
                   textComisionDescubierto.setBackground(Color.RED);
               }else{textComisionDescubierto.setBackground(Color.WHITE);}
         }        
       
    } //fin metodo focuslost

    // este metodo es para detectar si quedó algun error en los campos textfield
    private boolean hayError(){
       // cada campo tiene tiene su variable booleana
        for(boolean l: hayerror){ //recorro el array  aver si hay alguno false
        
            if(l==false){ //si hay un false, hay un error en el formulario
              // devuelve false poruqe hay errores
                return false;
                
            }
        
        }
    // sin no hay ningun false en el array es que todos los campos estan bien
    return true;
    } // fin del metod hayError
    
}  // fin de la clase Registro ventana JFrame

// si han explicaod antes y son comunes  la definiciones
public class Ingresar extends JFrame implements ActionListener,FocusListener{
    
        CuentaBancaria obj=null;
    
        private boolean todok=true; 
        
	private JLabel labelTitulo;
	private JLabel cuenta,nombre,montoIngreso,saldo,error_cuenta,tipo_cuenta;
	private JTextField textCuenta,textNombre,textIngreso,textSaldo,textTipoCuenta;
	private JButton botonGuardar,botonCancelar;
	

	/**
	 * constructor de la clase donde se inicializan todos los componentes
	 * de la ventana de registro
	 */
	public Ingresar(){
            
		botonGuardar = new JButton();
		botonGuardar.setBounds(110, 460, 120, 25);
		botonGuardar.setText("Registrar");
		
		botonCancelar = new JButton();
		botonCancelar.setBounds(250, 460, 120, 25);
		botonCancelar.setText("Cancelar");

		labelTitulo = new JLabel();
		labelTitulo.setText(" - REGISTRO DE INGRESO EN CUENTA - ");
		labelTitulo.setBounds(30, 20, 430, 30);
		labelTitulo.setFont(new java.awt.Font("Verdana", 1, 18));
                
		cuenta=new JLabel();
		cuenta.setText("Codigo Cuenta CCC");
		cuenta.setBounds(20, 80, 160, 30);
		cuenta.setFont(new java.awt.Font("Verdana", 1, 13));                
		add(cuenta);
                
                tipo_cuenta=new JLabel();
                tipo_cuenta.setText("Tipo de Cuenta");
                tipo_cuenta.setBounds(20,120,180,25);
                add(tipo_cuenta);                
		
		nombre=new JLabel();
		nombre.setText("Titular de la Cuenta");
		nombre.setBounds(20, 160,160, 25);
		add(nombre);
                             
		saldo=new JLabel();
		saldo.setText("Saldo Actual en Cuenta");
		saldo.setBounds(20, 210, 180, 25);
		add(saldo);
                
		montoIngreso=new JLabel();
		montoIngreso.setText("Monto a Ingresar");
		montoIngreso.setBounds(20, 260, 80, 25);
		add(montoIngreso); 
                
                error_cuenta=new JLabel();
		error_cuenta.setBounds(170, 50, 250, 30);
                error_cuenta.setForeground(Color.red);
                add(error_cuenta);
                
		textCuenta=new JTextField();
		textCuenta.setBounds(170,80, 210, 30);
		textCuenta.setFont(new java.awt.Font("Verdana", 1, 15));
                textCuenta.addFocusListener(this);
		add(textCuenta);
                
                textTipoCuenta=new JTextField();
                textTipoCuenta.setBounds(170,120,180,25);
                textTipoCuenta.setEditable(false);
                add(textTipoCuenta);
		
		textNombre=new JTextField();
		textNombre.setBounds(170, 160, 190, 25);
                textNombre.addFocusListener(this); 
                textNombre.setEditable(false);

		add(textNombre);
              
		textSaldo=new JTextField();
		textSaldo.setBounds(210, 210, 80, 25);
                textSaldo.addFocusListener(this);                
                textSaldo.setEditable(false);                
		add(textSaldo);
                
		textIngreso=new JTextField();
		textIngreso.setBounds(100, 260, 100, 25);
                textIngreso.addFocusListener(this);                
		add(textIngreso);                
		
		botonGuardar.addActionListener(this);
		botonCancelar.addActionListener(this);
		add(botonCancelar);
		add(botonGuardar);
		add(labelTitulo);
                
                limpiar();
		setSize(480, 500);
		setTitle(" * Ingreso en cuenta * ");
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(null);
                setVisible(true);
                

	}

	private void limpiar(){
		textCuenta.setText("");
                textTipoCuenta.setText("");
		textNombre.setText("");
		textSaldo.setText("");
                textIngreso.setText("");
	}

        @Override
        public void actionPerformed(ActionEvent e) {
		if (e.getSource()==botonGuardar){
                    textSaldo.setText(textSaldo.getText().replace(",","."));
                    textIngreso.setText(textIngreso.getText().replace(",","."));
                    double saldo=Double.parseDouble(textSaldo.getText())+Double.parseDouble(textIngreso.getText());
			try {
                                obj.setSaldo(saldo);
                                MenuPrincipal mp= new MenuPrincipal();
                                mp.setVisible(true);
                                this.dispose();
	
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null,"Error en la escritura de los Datos"+ex,"Error",JOptionPane.ERROR_MESSAGE);
			}
		} //fin botonGuardar
                
		if (e.getSource()==botonCancelar){
                        MenuPrincipal mp= new MenuPrincipal();
                        mp.setVisible(true);
			this.dispose();
		} //fin botonCancelar
                System.out.println(lista);
            
        }

        @Override
        public void focusGained(FocusEvent e) {
//            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void focusLost(FocusEvent e) {
        if(e.getSource()==textCuenta){
        
               Pattern numcta=Pattern.compile("([0-9]{20})"); 
               Matcher mat=null;
               mat= numcta.matcher(textCuenta.getText());
            // si la longitud sin espacios en blanco es diferente de 20
            if(!mat.matches()){
                if(todok){todok=false;}
                error_cuenta.setText("Error, la cuenta deebe tener sólo 20 números");                  
                textCuenta.setFont(new java.awt.Font("Verdana", 2,15));
                textCuenta.setBackground(Color.RED);
                  }else{
                        textCuenta.setBackground(Color.WHITE);
                        error_cuenta.setText("");
                // Aqui se obtiene el digito de control correcto
                int dc=Integer.parseInt(CalculoDigitoControl.calculoDigCtrl(textCuenta.getText()));
                    // verificacion de que el digito de control es correcto

                  if(dc!=Integer.parseInt(textCuenta.getText().substring(8,10))){
                        if(todok){todok=false;}
                        textCuenta.setFont(new java.awt.Font("Verdana", 2,15));
                        textCuenta.setBackground(Color.RED);
                        error_cuenta.setText("Error en el numero de Cuenta CCC");  
                  // fin if-else verificacion del Digito de Control          
                  } else if(buscarCta(textCuenta.getText())){
                        //   tipo_cuenta.setText("Tipo de Cuenta "+obj.getTipo_cuenta());
                           todok=true;
                           textTipoCuenta.setText(obj.getTipo_cuenta());
                           textNombre.setText(obj.getCliente().getNombre()+" "+obj.getCliente().getApellidos());
                           textSaldo.setText(String.valueOf(obj.getSaldo()));
                          }
                }
            } // fin del if de textCuenta
        
        } // fin metodo focuslost

public boolean buscarCta(String buscar){
        
        boolean ok=false;
        for(CuentaBancaria ls: lista){
            if(ls.getNro_cuenta().equalsIgnoreCase(buscar)){
                obj=ls;
                ok=true;
                break;
             }
         }
        return ok;
    } // fin metodo buscarCta        
        
        
} // fin de la clase Ingresar frame

public class Retirar extends JFrame implements ActionListener,FocusListener{
    
        CuentaBancaria obj=null;
    
	private JLabel labelTitulo;
	private JLabel cuenta,nombre,montoRetiro,saldo,error_cuenta,tipo_cuenta;        
	private JTextField textCuenta,textNombre,textRetiro,textSaldo,textTipoCuenta;
	private JButton botonGuardar,botonCancelar;

	/**
	 * constructor de la clase donde se inicializan todos los componentes
	 * de la ventana de registro
	 */
	public Retirar(){
            
		botonGuardar = new JButton();
		botonGuardar.setBounds(110, 460, 120, 25);
		botonGuardar.setText("Registrar");
		
		botonCancelar = new JButton();
		botonCancelar.setBounds(250, 460, 120, 25);
		botonCancelar.setText("Cancelar");

		labelTitulo = new JLabel();
		labelTitulo.setText(" - REGISTRO DE RETIRO DE CUENTA - ");
		labelTitulo.setBounds(30, 20, 430, 30);
		labelTitulo.setFont(new java.awt.Font("Verdana", 1, 18));
                
		cuenta=new JLabel();
		cuenta.setText("Codigo Cuenta CCC");
		cuenta.setBounds(20, 80, 160, 30);
		cuenta.setFont(new java.awt.Font("Verdana", 1, 13));                
		add(cuenta);
                
                tipo_cuenta=new JLabel();
                tipo_cuenta.setText("Tipo de Cuenta");
                tipo_cuenta.setBounds(20,120,180,25);
                add(tipo_cuenta);                
		
		nombre=new JLabel();
		nombre.setText("Titular de la Cuenta");
		nombre.setBounds(20, 160,160, 25);
		add(nombre);
                             
		saldo=new JLabel();
		saldo.setText("Saldo Actual en Cuenta");
		saldo.setBounds(20, 210, 180, 25);
		add(saldo);
                
		montoRetiro=new JLabel();
		montoRetiro.setText("Monto a Retirar");
		montoRetiro.setBounds(20, 260, 80, 25);
		add(montoRetiro); 
                
                error_cuenta=new JLabel();
		error_cuenta.setBounds(170, 50, 250, 30);
                error_cuenta.setForeground(Color.red);
                add(error_cuenta);
                
		textCuenta=new JTextField();
		textCuenta.setBounds(170,80, 210, 30);
		textCuenta.setFont(new java.awt.Font("Verdana", 1, 15));
                textCuenta.addFocusListener(this);
		add(textCuenta);
                
                textTipoCuenta=new JTextField();
                textTipoCuenta.setBounds(170,120,180,25);
                textTipoCuenta.setEditable(false);
                add(textTipoCuenta);
		
		textNombre=new JTextField();
		textNombre.setBounds(170, 160, 190, 25);
                textNombre.addFocusListener(this); 
                textNombre.setEditable(false);
		add(textNombre);
              
		textSaldo=new JTextField();
		textSaldo.setBounds(210, 210, 80, 25);
                textSaldo.addFocusListener(this);                
                textSaldo.setEditable(false);                
		add(textSaldo);
                
		textRetiro=new JTextField();
		textRetiro.setBounds(100, 260, 100, 25);
                textRetiro.addFocusListener(this);                
		add(textRetiro);                
		
		botonGuardar.addActionListener(this);
		botonCancelar.addActionListener(this);
		add(botonCancelar);
		add(botonGuardar);
		add(labelTitulo);
                
		limpiar();
		setSize(480, 500);
		setTitle(" * Retiro de la cuenta * ");
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(null);
                setVisible(true);

	}


	private void limpiar(){
		textCuenta.setText("");
		textNombre.setText("");
                textTipoCuenta.setText("");
		textSaldo.setText("");
                textRetiro.setText("");
	}

        @Override
        public void actionPerformed(ActionEvent e) {
		if (e.getSource()==botonGuardar){
                    boolean ok=false;
                    textSaldo.setText(textSaldo.getText().replace(",","."));
                    textRetiro.setText(textRetiro.getText().replace(",","."));
                    double saldo=Double.parseDouble(textSaldo.getText())-Double.parseDouble(textRetiro.getText());
                    // aqui se verifica si el tipo de cuenta admite descubiertos
                    if(saldo<0 && obj.getTipo_cuenta().equalsIgnoreCase("ahorro")){
                                JOptionPane.showMessageDialog(null,"No hay fondos suficientes, la operacion no se realizó", "Cuentas de Ahorros no admiten descubiertos",ERROR_MESSAGE);
                         }else{ok=true;}
			try {
                                if(ok){obj.setSaldo(saldo);}
                                MenuPrincipal mp= new MenuPrincipal();
                                mp.setVisible(true);
                                this.dispose();
	
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null,"Error en la escritura de los Datos"+ex,"Error",JOptionPane.ERROR_MESSAGE);
			}
		} //fin botonGuardar
                
		if (e.getSource()==botonCancelar){
                        MenuPrincipal mp= new MenuPrincipal();
                        mp.setVisible(true);
			this.dispose();
		} //fin botonCancelar
                System.out.println(lista);
            
        }

        @Override
        public void focusGained(FocusEvent e) {
//            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void focusLost(FocusEvent e) {
        if(e.getSource()==textCuenta){
        
               Pattern numcta=Pattern.compile("([0-9]{20})"); 
               Matcher mat=null;
               mat= numcta.matcher(textCuenta.getText());
            // si la longitud sin espacios en blanco es diferente de 20
            if(!mat.matches()){
                error_cuenta.setText("Error, escriba sólo 20 números");                  
                textCuenta.setFont(new java.awt.Font("Verdana", 2,15));
                textCuenta.setBackground(Color.RED);
                  }else{
                        textCuenta.setBackground(Color.WHITE);
                        error_cuenta.setText("");
                // Aqui se obtiene el digito de control correcto
                int dc=Integer.parseInt(CalculoDigitoControl.calculoDigCtrl(textCuenta.getText()));
                    // verificacion de que el digito de control es correcto

                  if(dc!=Integer.parseInt(textCuenta.getText().substring(8,10))){
                        textCuenta.setFont(new java.awt.Font("Verdana", 2,15));
                        textCuenta.setBackground(Color.RED);
                        error_cuenta.setText("Error en el numero de Cuenta CCC");  
                  // fin if-else verificacion del Digito de Control          
                  } else if(buscarCta(textCuenta.getText())){
                           textTipoCuenta.setText(obj.getTipo_cuenta());
                           textNombre.setText(obj.getCliente().getNombre()+" "+obj.getCliente().getApellidos());
                           textSaldo.setText(String.valueOf(obj.getSaldo()));
                          }
                }
            } // fin del if de textCuenta
        
            if(e.getSource()==textRetiro){
            
                textSaldo.setText(textSaldo.getText().replace(",","."));
                textRetiro.setText(textRetiro.getText().replace(",","."));
                double saldo=Double.parseDouble(textSaldo.getText())-Double.parseDouble(textRetiro.getText());
                if(saldo<0 && obj.getTipo_cuenta().equalsIgnoreCase("ahorro")){
            
                    JOptionPane.showMessageDialog(null,"No hay fondos suficientes, para hacer el retiro", "Cuentas de Ahorros no admiten descubiertos",ERROR_MESSAGE);
                    textRetiro.grabFocus();
                }
             }  
        
        } // fin metodo focuslost

public boolean buscarCta(String buscar){
        
        boolean ok=false;
        for(CuentaBancaria ls: lista){
            if(ls.getNro_cuenta().equalsIgnoreCase(buscar)){
                obj=ls;
                ok=true;
                break;
             }
         }
        return ok;
    } // fin metodo buscarCta        
        
        
} // fin de la clase Retirar frame


} // fin de la clase AplicacionBancaria
