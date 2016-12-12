package detection.Datasource;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;


public class FactoryProxyConnection {
	
	
	public static IProxyConnection createProxy( Connection connection ) {

		return (IProxyConnection) Proxy.newProxyInstance(
				IProxyConnection.class.getClassLoader(),
				new Class[] {IProxyConnection.class},
				new Handler( connection )
				);		
	}
	
	
	
	private static class Handler implements InvocationHandler {
	
		
		// Champs
		
		private final Connection connection;
		
		private boolean		flagDebug = false;
		
		
		// Constructeur
		
		public Handler( Connection connection ) {
			super();

			if ( connection == null ) {
				throw new NullPointerException();
			}
			this.connection = connection;
		}

		
		// Actions
		
	    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
	    	
	    	switch ( method.getName() ) {
	    		
	    	case "closeConnection" :
	    		if ( flagDebug ) {
		    		System.out.println( this.getClass().getName() + " " + method.getName() + "()" );
	    		}
	    		connection.close();
	    		return null ;
	    		
	    	case "close" :
	    		return null;
	    		
	    	default :
		    	return method.invoke( connection, args);
	    	}
	    	
		}
	}
	

}
