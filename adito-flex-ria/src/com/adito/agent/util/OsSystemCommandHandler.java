package com.adito.agent.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

public class OsSystemCommandHandler {
	static void doCommand(HttpServletRequest request, StringBuilder xml){
		CommandUtil.addInfo(xml, "-----------OsSystemCommandHandler-------------");
        String cmdstr = request.getParameter("command");
    	if(cmdstr == null){
    		CommandUtil.addInfo(xml, "invalid command");
    		return;
    	}
    	doExecuteCommand(xml, cmdstr);
	}
	
	static void doExecuteCommand(StringBuilder xml, String cmdstr){
    	Process p;

    	try{
    		p = Runtime.getRuntime().exec(cmdstr);
    		InputStream fis=p.getInputStream();
    		InputStreamReader isr = new InputStreamReader(fis);
    		BufferedReader br=new BufferedReader(isr);

    		String str;
    		while((str = br.readLine())!=null)
    			CommandUtil.addInfo(xml,str);
    		
    	}catch (IOException e) {
    		CommandUtil.addInfo(xml,e.getMessage());
    	}

	}
}
