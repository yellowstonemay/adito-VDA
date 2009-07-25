package com.adito.agent.util;


//import com.adito.core.CoreListener;
import com.adito.core.CoreServlet;
//import com.adito.core.CoreUtil;
import com.adito.extensions.ExtensionException;
import com.adito.extensions.types.DefaultPlugin;

public class AgentUtilPlugin extends DefaultPlugin{

	public AgentUtilPlugin() {
		super("/WEB-INF/adito-agent-util-tiles-defs.xml", false);
		System.setProperty("fan.agent.url", "www.fronware.net:80:false");
		System.setProperty("fan.agent.inteval", "5184000");//3600*60*24
	}

	public void activatePlugin() throws ExtensionException {
		super.activatePlugin();
		try {
			TestDatabaseFactory.getInstance().open(CoreServlet.getServlet(), this.getPluginDefinition());
//            CoreUtil.updateEventsTable(AgentUtilPlugin.MESSAGE_RESOURCES_KEY, ApplicationShortcutEventConstants.class);
//			CoreServlet.getServlet().addCoreListener(this);
		} catch (Exception e) {
            e.printStackTrace();
			throw new ExtensionException(ExtensionException.INTERNAL_ERROR, e);
		}
	}
}
