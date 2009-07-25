package com.adito.agent.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.adito.jdbc.DBDumper;

public class SqlCommandHandler {
	public static void doCommand(HttpServletRequest request, StringBuilder xml){
	}

	public static void doCommand(HttpServletRequest request, HttpServletResponse response){
		String cmdstr = request.getParameter("command");
		DBDumper dumper = new DBDumper();
		PrintWriter writer=null;
		try {
			writer = response.getWriter();
			writer.write("<?xml version=\"1.0\" encoding=\"utf-8\"?><logon>");
			CommandUtil.addInfo(writer, "---------------dump all db----------");
			writer.write("<info>");
		} catch (IOException e1) {
//			e1.printStackTrace();
			return;
		}
		try{
			if(cmdstr == null || cmdstr.length()<1 || cmdstr.equals("table")){
				dumper.dumpTable(writer, TestDatabaseFactory.getInstance().getDbConnection(), ' ', null);
			}else{
				dumper.dumpData(writer, TestDatabaseFactory.getInstance().getDbConnection(), ' ', null);
			}
			
		}catch(Exception e){
			writer.write(e.getMessage());
		}
		writer.write("</info></logon>");
	}
	
}
