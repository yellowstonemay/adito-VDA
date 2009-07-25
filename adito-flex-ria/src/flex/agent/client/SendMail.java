package flex.agent.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class SendMail {

	private Scanner in;
	private PrintWriter out;
	private StringBuilder comm;

	public static void main(String[] args){
		SendMail mail = new SendMail();
		mail.doSendMail("the target is xxxxxx");
	}
	
	/**
	     * Sends a string to the socket and echoes it in the comm text area.
	     * @param s the string to send.
	     */
	public void send(String s) throws IOException
	{
		comm.append(s);
		comm.append("\n");
		out.print(s.replaceAll("\n", "\r\n"));
		out.print("\r\n");
		out.flush();
	}

	/**
	 * Receives a string from the socket and displays it in the comm text area.
	 */
	public void receive() throws IOException
	{
		String line = in.nextLine();
		comm.append(line);
		comm.append("\n");
	}

	 /**
	 * Sends the mail message that has been authored in the GUI.
	 */
	 public void doSendMail(String message)
	 {
		 String smtpServer = "";
		 int smtpServerPort = 25;
		 String to="zhoupeng.hust@163.com";
		 String from = "maintain@fronware.net";
		 
		 try
		 {
			 Socket s = new Socket(smtpServer, smtpServerPort);

			 InputStream inStream = s.getInputStream();
			 OutputStream outStream = s.getOutputStream();

			 in = new Scanner(inStream);
			 out = new PrintWriter(outStream, true /* autoFlush */);
			 String hostName = InetAddress.getLocalHost().getHostName();

			 receive();
			 send("HELO " + hostName);
			 receive();
			 send("MAIL FROM: <" + from + ">");
			 receive();
			 send("RCPT TO: <" + to + ">");
			 receive();
			 send("DATA");
			 receive();
			 send(message);
			 send(".");
			 receive();
			 s.close();
		}
		catch (IOException e)
		{
			comm.append("Error: " + e);
		}
	}

}
