package nl.mierasmade.driver;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

public class DynamicDriver implements Driver {
	
	private Driver driver;
	
	public DynamicDriver(Driver driver) {
		this.driver = driver;
	}

	@Override
	public boolean acceptsURL(String candidate) throws SQLException {
		return this.driver.acceptsURL(candidate);		
	}

	@Override
	public Connection connect(String url, Properties info) throws SQLException {
		return this.driver.connect(url, info);		
	}

	@Override
	public int getMajorVersion() {
		return this.driver.getMajorVersion();		
	}

	@Override
	public int getMinorVersion() {
		return this.driver.getMinorVersion();
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return this.driver.getParentLogger();
	}

	@Override
	public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
		return this.driver.getPropertyInfo(url, info);
	}

	@Override
	public boolean jdbcCompliant() {
		return this.driver.jdbcCompliant();
	}
}
