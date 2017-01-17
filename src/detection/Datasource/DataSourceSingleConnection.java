
package detection.Datasource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class DataSourceSingleConnection implements DataSource {
	
	
	// Champs

	private String			pathJndi;
	private String			driver;
	private String			url;
	
	private String			user;
	private String			password;
	
	private DataSource		dataSourceInitial;
	
	private IProxyConnection connection;
	
	private boolean			flagDebug = false;

	
	// Getters é Setters
	
	public DataSource getDataSourceInitial() {
		return dataSourceInitial;
	}
	
	public void setDataSourceInitial(DataSource dataSourceInitial) {
		if ( connection != null ) {
			throw new IllegalStateException( "La connecxion est ouverte." );
		}
		if ( dataSourceInitial != null ) {
			setPathJndi(null);
			setDriver(null);
			setUrl(null);
		}
		this.dataSourceInitial = dataSourceInitial;
	}
	
	public String getPathJndi() {
		return pathJndi;
	}
	
	public void setPathJndi(String pathJndi) {
		if ( connection != null ) {
			throw new IllegalStateException( "La connecxion est ouverte." );
		}
		if ( pathJndi != null ) {
			setDataSourceInitial(null);
			setDriver(null);
			setUrl(null);
		}
		this.pathJndi = pathJndi;
	}
	
	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		if ( connection != null ) {
			throw new IllegalStateException( "La connecxion est ouverte." );
		}
		if( driver != null ) {
			setDataSourceInitial(null);
			setPathJndi(null);
		}
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		if( url != null ) {
			setDataSourceInitial(null);
			setPathJndi(null);
		}
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	// Constructeurs
	
	public DataSourceSingleConnection() {
		super();
	}

	public DataSourceSingleConnection( DataSource dataSource ) {
		super();
		this.dataSourceInitial = dataSource;
	}

	public DataSourceSingleConnection( Properties props ) {
		super();
		extractProperties(props);
	}
	
	public DataSourceSingleConnection( InputStream in ) throws IOException {
		Properties props = new Properties();
		props.load(in);
		extractProperties(props);
	}
	
	public DataSourceSingleConnection( String path ) throws IOException {
		this( new FileInputStream(path) );
	}
	
	public DataSourceSingleConnection( URL url ) throws IOException {
		this( url.openStream() );
	}
	
	
	// Actions

	@Override
	public Connection getConnection(String user, String password) throws SQLException {

		this.user = user;
		this.password = password;
		
		if ( connection == null ) {
			if ( pathJndi != null && dataSourceInitial == null ) {
				try {
					Context nc = new InitialContext();
					dataSourceInitial = (DataSource) nc.lookup( pathJndi );
				} catch ( Exception e) {
					throw new RuntimeException(e);
				}
			}
			if ( dataSourceInitial != null ) {
				connection = FactoryProxyConnection.createProxy( dataSourceInitial.getConnection(user, password) );
			} else {
				if ( driver != null ) {
					try {
						Class.forName( driver );
					} catch (ClassNotFoundException e) {
						throw new RuntimeException(e);
					}
				}
				connection = FactoryProxyConnection.createProxy( DriverManager.getConnection(url, user, password) );
			}
		}
		return connection;
	}

	@Override
	public Connection getConnection() throws SQLException {
		return getConnection(user, password);
	}
	
	public void closeConnection() {
		if ( flagDebug ) {
			System.out.println( this.getClass().getName() + " closeConnection()" );
		}
		if ( connection != null ) {
			connection.closeConnection();
			connection = null;
		}
	}
	
	
	// Méthode auxiliaires
	
	private void extractProperties( Properties props ) {
		setUser(		props.getProperty( "jdbc.user" ) );
		setPassword(	props.getProperty( "jdbc.password" ) );
		setDriver(		props.getProperty( "jdbc.driver" ) );
		setUrl( 		props.getProperty( "jdbc.url" ) );
		setPathJndi(	props.getProperty( "jdbc.jndi" ) );
	}
	
	
	// Actions non implémentées
	
	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return null;
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return 0;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return null;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return false;
	}
	
	

}
