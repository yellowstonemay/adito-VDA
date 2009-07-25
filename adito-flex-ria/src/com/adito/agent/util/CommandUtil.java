package com.adito.agent.util;

import java.io.PrintWriter;

public class CommandUtil {

	static void addInfo(StringBuilder xml, String str){
    	xml.append("<info>"+str+"</info>");
    }
	
	static void addInfo(PrintWriter writer, String str){
    	writer.write("<info>"+str+"</info>");
    }
}
