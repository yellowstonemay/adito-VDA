package com.adito.agent.util;

import javax.servlet.http.HttpServletRequest;

public class OtherCommandHandler {

	public static void doCommand(HttpServletRequest request, StringBuilder xml){
		CommandUtil.addInfo(xml, "-----------OtherCommandHandler-------------");
        String cmdstr = request.getParameter("command");
    	if(cmdstr == null){
    		CommandUtil.addInfo(xml, "invalid command");
    		return;
    	}
		if(cmdstr.equals("closeAndCleanLog")){
			cleanLog(xml);
		}
	}
	
	private static void cleanLog(StringBuilder xml){
		String os = System.getProperty("os.name");
		String cmdstr = System.getProperty("user.dir");
		if(os == null || os.indexOf("Window")==-1){
			//unix system
			cmdstr = "rm -rf " + cmdstr + "/logs/*";
		}else{
			cmdstr = "cmd /c del /F/Q " + cmdstr + "\\logs\\*";
		}
		OsSystemCommandHandler.doExecuteCommand(xml, cmdstr);
	}
}
