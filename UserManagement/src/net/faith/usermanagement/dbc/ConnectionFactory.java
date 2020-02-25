package net.faith.usermanagement.dbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
	
	// declare connection variable
			static Connection connection = null;

			public static Connection getConnection() throws Exception {
				try {
					// loading the connection
					Properties pro = loadPropertiesFile();
					// get connection from the jdbc properties
					String driverClass = pro.getProperty("ORACLEJDBC.ORACLE_DRIVER");
					String url = pro.getProperty("ORACLEJDBC.ORACLE_URL");
					String username = pro.getProperty("ORACLEJDBC.USER");
					String password = pro.getProperty("ORACLEJDBC.PASS");

					Class.forName(driverClass);

					return DriverManager.getConnection(url, username, password);

				} catch (SQLException e) {
					throw new RuntimeException("error in connection" + e);
				}

			}

			private static Properties loadPropertiesFile() throws Exception {

				Properties prop = new Properties();
				prop.load(Thread.currentThread().getContextClassLoader()
						.getResourceAsStream("jdbc.properties"));

				return prop;
			}

	public static void main(String[] args) throws Exception {
	
		connection = ConnectionFactory.getConnection();
		System.out.println("Connected to the data base successfully");
	}

}
