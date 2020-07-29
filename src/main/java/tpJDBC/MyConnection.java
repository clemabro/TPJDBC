package tpJDBC;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbutils.DbUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class MyConnection {
	private static Connection connexionMysql = null;
	private final static Logger LOG = LogManager.getLogger(MyConnection.class);
	
	private static Connection createConnection()
	{
		try {
			LOG.info("Debut de la connexion a la BDD");
			InputStream input = new FileInputStream("src/main/resources/config.properties");
			Properties prop = new Properties();
			prop.load(input);
			
			connexionMysql = DriverManager.getConnection("jdbc:mysql://" + prop.getProperty("db.url") + "/" + prop.getProperty("db.schema") + "?user=" + prop.getProperty("db.user") + "&password=" + prop.getProperty("db.password") + "&useSSL=true&serverTimezone=UTC");
			LOG.info("Connexion à la BDD réussi");
			return connexionMysql;
		} catch (SQLException ex) {
		    LOG.error("SQLException: " + ex.getMessage());
		    LOG.error("SQLState: " + ex.getSQLState());
		    LOG.error("VendorError: " + ex.getErrorCode());
		} catch (FileNotFoundException e) {
			LOG.error(e.getMessage());
		} catch (IOException e) {
			LOG.error(e.getMessage());
		}
		return null;
	}

	public static Connection getInstance() {
		if(connexionMysql == null) {
			connexionMysql = createConnection();
		}
		return connexionMysql;
	}
	
	public static void closeConnection() {
		LOG.info("Fermeture de la connexion à la BDD");
	    try { DbUtils.close(connexionMysql); } catch (Exception e) {
	    	LOG.warn(e.getMessage()); 
	    }
	}
}
