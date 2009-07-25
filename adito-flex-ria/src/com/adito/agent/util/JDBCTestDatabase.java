package com.adito.agent.util;



import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.adito.boot.ContextHolder;
import com.adito.boot.SystemProperties;
import com.adito.core.CoreEvent;
import com.adito.core.CoreListener;
import com.adito.core.CoreServlet;
import com.adito.extensions.types.PluginDefinition;
import com.adito.jdbc.JDBCConnectionImpl;
import com.adito.jdbc.JDBCDatabaseEngine;


public class JDBCTestDatabase implements CoreListener {
    private static final Log log = LogFactory.getLog(JDBCTestDatabase.class);

    private JDBCDatabaseEngine db;

	public void open(CoreServlet controllingServlet, PluginDefinition def)
			throws Exception {
        String dbName = SystemProperties.get("adito.systemDatabase.jdbc.dbName", "explorer_configuration");
        controllingServlet.addDatabase(dbName, ContextHolder.getContext().getDBDirectory());
        String jdbcUser = SystemProperties.get("adito.jdbc.username", "sa");
        String jdbcPassword = SystemProperties.get("adito.jdbc.password", "");
        String vendorDB = SystemProperties.get("adito.jdbc.vendorClass", "com.adito.jdbc.hsqldb.HSQLDBDatabaseEngine");

        if (log.isInfoEnabled()) {
            log.info("System database is being opened...");
            log.info("JDBC vendor class implementation is " + vendorDB);
        }

        db = (JDBCDatabaseEngine) Class.forName(vendorDB).newInstance();
        db.init("applicationShortcutsDatabase", dbName, jdbcUser, jdbcPassword, null);

        CoreServlet.getServlet().addCoreListener(this);
	}

	public void cleanup() throws Exception {

	}

	public void close() throws Exception {

	}

	public void open(CoreServlet controllingServlet) throws Exception {

	}

	public void coreEvent(CoreEvent evt) {

	}

	JDBCConnectionImpl getDbConnection(){
		try {
			return db.aquireConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		return null;
	}
}
