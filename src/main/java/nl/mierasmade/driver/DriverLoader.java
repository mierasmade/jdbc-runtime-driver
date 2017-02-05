package nl.mierasmade.driver;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.stereotype.Component;

@Component
public class DriverLoader {
	
	private static final String URL = "jdbc:mysql://localhost:3306/sakila?useSSL=false";
	private static final String USER = "root";
	private static final String PASSWORD = "admin";
	private static final String DRIVER_JAR = "C:\\Users\\Sander\\Downloads\\mysql-connector-java-5.1.40.jar";
	
	@PostConstruct
	private void loadDriver() throws Exception {
		File file = new File(DRIVER_JAR);
		
		if (file != null) {			
			URLClassLoader ucl = new URLClassLoader(new URL[] { file.toURI().toURL() });
			String driverName = DatabaseDriver.fromJdbcUrl(URL).getDriverClassName();	
			Driver d = (Driver)Class.forName(driverName, true, ucl).newInstance();
			DriverManager.registerDriver(new DynamicDriver(d));			
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			if (conn != null) {
				conn.isValid(1);
			}			
		}		
		
		if (file != null) {
			URLClassLoader URLClassLoader = new URLClassLoader(new URL[] {file.toURI().toURL()}, this.getClass().getClassLoader());
			String driverName = DatabaseDriver.fromJdbcUrl(URL).getDriverClassName();			
			Class.forName(driverName, true, URLClassLoader).newInstance();			
			DataSource dataSource = createNewDataSource(URL, USER, PASSWORD);
			
			if (dataSource != null) {
				// lets try to connect
				// Throws: Caused by: java.lang.ClassNotFoundException: com.mysql.jdbc.Driver
				dataSource.getConnection().isValid(1);
			}			
			URLClassLoader.close();
		}		
	}
	
	public DataSource createNewDataSource(String url, String username, String password) {
		return DataSourceBuilder
				.create()
				.url(url)		
				.username(username)
				.password(password)
				.build();
	}

}
