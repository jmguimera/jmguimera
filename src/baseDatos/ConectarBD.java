/** @author José Miguel Guimerá Padrón. PROG 1º DAM */

package baseDatos;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.jdbc.pool.*;


public class ConectarBD {
    
        private Connection  conn = null;
        
        public ConectarBD(){
        
            hacerConexion("127.0.0.1",1521,"xe","josem","viviana1");
        
        }
        
        public ConectarBD(String ipserver,int puerto,String sid, String user,String clave){
        
            hacerConexion(ipserver,puerto,sid,user,clave);
        
        } 
        
        public void hacerConexion(String ipserver,int puerto,String sid, String user, String clave){
        
            try {
                OracleDataSource ods = new OracleDataSource();
                String url = "jdbc:oracle:thin:@//";
                url = url+ipserver+puerto+user+clave;
                            ods.setURL(url);
                ods.setUser(user);
                ods.setPassword(clave);
                this.conn = ods.getConnection();
                
            } catch (SQLException ex) {
                Logger.getLogger(ConectarBD.class.getName()).log(Level.SEVERE, null, ex);
                this.conn = null;
            }
        
        
        
        }

    public Connection getConn() {
        return conn;
    }
    
}

