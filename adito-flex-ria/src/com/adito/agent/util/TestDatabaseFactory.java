package com.adito.agent.util;

public class TestDatabaseFactory {
		
	    static JDBCTestDatabase instance;
	    static Class adminLogDatabaseImpl = JDBCTestDatabase.class;
	    private static boolean locked = false;

	    /**
	     * @return An instance of the system database factory.
	     */
	    public static JDBCTestDatabase getInstance() {
	        try {
	            return instance == null ? instance = (JDBCTestDatabase) adminLogDatabaseImpl.newInstance()
	                            : instance;
	        } catch (Exception e) {
//	            log.error("Could not create instance of class " + adminLogDatabaseImpl.getCanonicalName(), e);
	            return instance == null ? instance = new JDBCTestDatabase() : instance;
	        }
	    }

	    /**
	     * @param applicationShortcutDatabaseImpl the class of the application
	     *        shortcut database
	     * @param lock weather to lock the system database after setting it.
	     * @throws IllegalStateException
	     */
	    public static void setFactoryImpl(Class adminLogDatabaseImpl, boolean lock) throws IllegalStateException {
	        if (locked) {
	            throw new IllegalStateException("System database factory has been locked by another plugin.");
	        }
	        TestDatabaseFactory.adminLogDatabaseImpl = adminLogDatabaseImpl;
	        locked = lock;
	    }

	}

