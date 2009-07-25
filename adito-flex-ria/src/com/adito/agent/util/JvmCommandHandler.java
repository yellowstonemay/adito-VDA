package com.adito.agent.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import com.adito.boot.ContextHolder;
import com.adito.boot.PropertyClassManager;
import com.adito.boot.SystemProperties;

public class JvmCommandHandler {
	public static void doCommand(HttpServletRequest request, StringBuilder xml){
		String cmdstr = request.getParameter("command");
    	if(cmdstr == null){
    		CommandUtil.addInfo(xml, "invalid command");
    		return;
    	}
    	if(cmdstr.equals("GetSystemProperty")){
    		getSystemProperty(xml);
    	}else if(cmdstr.equals("GetSystemEnv")){
    		getSystemEnv(xml);
    	}else if(cmdstr.equals("GetWebserverContext")){
    		getWebserverContext(xml);
    	}else if(cmdstr.equals("displaySystemInfo")){
    		try{
    			displaySystemInfo(xml);
    		}catch(Exception e){
    			CommandUtil.addInfo(xml, e.getMessage());
    		}
    		
    	}
	}

    private static void getSystemProperty(StringBuilder xml){
    	
//    	addInfo(xml, System.getProperty("os.name"));
    	CommandUtil.addInfo(xml, "-------------------System.getProperty---------------------");
    	Enumeration em = System.getProperties().keys();
    	while(em.hasMoreElements()){
    		String key = (String)em.nextElement();
    		CommandUtil.addInfo(xml, key+" >>>"+System.getProperty(key));
    	}
    }

    private static void getSystemEnv(StringBuilder xml){
    	CommandUtil.addInfo(xml, "-------------------System.getenv---------------------");
    	Map<String, String> env = System.getenv();
    	for(String key: env.keySet()){
    		CommandUtil.addInfo(xml, key + ">>>"+env.get(key));
    	}
    }
    
    private static void getWebserverContext(StringBuilder xml){
    	CommandUtil.addInfo(xml, "-------------------getWebserverContext---------------------");
    	CommandUtil.addInfo(xml, "ConfDirectory" + ">>>"+
    			ContextHolder.getContext().getConfDirectory().getAbsolutePath());

//    	PropertyClassManager.getInstance().
    	Map<String, String> env = System.getenv();
    	for(String key: env.keySet()){
    		CommandUtil.addInfo(xml, key + ">>>"+env.get(key));
    	}
    }
    
        private static void displaySystemInfo(StringBuilder xml) throws SocketException {

        	CommandUtil.addInfo(xml, "-------------------displaySystemInfo---------------------");
        	CommandUtil.addInfo(xml, "Version is " + ContextHolder.getContext().getVersion());
            
            Enumeration e = NetworkInterface.getNetworkInterfaces();
            while (e.hasMoreElements()) {
                NetworkInterface netface = (NetworkInterface) e.nextElement();
                CommandUtil.addInfo(xml, "Net interface: " + netface.getName());

                Enumeration e2 = netface.getInetAddresses();
                while (e2.hasMoreElements()) {
                    InetAddress ip = (InetAddress) e2.nextElement();
                    CommandUtil.addInfo(xml, "IP address: " + ip.toString());
                }
            }
        }
}
