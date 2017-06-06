package mx.com.amx.unotv.workflow.utileria;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


import mx.com.amx.unotv.workflow.dto.ParametrosDTO;

import org.apache.log4j.Logger;

public class ReadHTMLWebServer {
	
	static Logger logger=Logger.getLogger(ReadHTMLWebServer.class);
	
	public String getResourceWebServer(String url_a_conectar){
		URL url;
		StringBuffer HTML=new StringBuffer();
		try {
			//String conectar="http://QROPC2WEB07.tmx-internacional.net/portal/unotv/utils/plantilla_amp.html";
			logger.debug("Conectandose a: "+url_a_conectar);
			url = new URL(url_a_conectar);
			URLConnection conn = url.openConnection();
			BufferedReader br = new BufferedReader(
                               new InputStreamReader(conn.getInputStream()));
			String inputLine;
			
			while ((inputLine = br.readLine()) != null) {
				HTML.append(inputLine+"\n");
			}
			br.close();
		} catch (MalformedURLException e) {
			logger.error("Error getHTML_DetailAMP MalformedURLException: ",e);
		} catch (IOException e) {
			logger.error("Error getHTML_DetailAMP IOException: ",e);
		}
		return HTML.toString();
	}
}