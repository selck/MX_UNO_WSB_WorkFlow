package mx.com.amx.unotv.workflow.bo;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import mx.com.amx.unotv.workflow.dto.ContentDTO;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Component
@Qualifier("workFlowCol")
public class WorkFlowCol {
	
	private static Logger logger = Logger.getLogger(WorkFlowCol.class);
	private final Properties props = new Properties();
	
	private String ambiente="";
	private String URL_WS_BASE = "";
	
	private RestTemplate restTemplate;
	HttpHeaders headers = new HttpHeaders();
	
	public WorkFlowCol() {
		super();
		restTemplate = new RestTemplate();
		ClientHttpRequestFactory factory = restTemplate.getRequestFactory();
		
		if ( factory instanceof SimpleClientHttpRequestFactory) {
			((SimpleClientHttpRequestFactory) factory).setConnectTimeout( 50 * 1000 );
			((SimpleClientHttpRequestFactory) factory).setReadTimeout( 50 * 1000 );
		} else if ( factory instanceof HttpComponentsClientHttpRequestFactory) {
			((HttpComponentsClientHttpRequestFactory) factory).setReadTimeout( 50 * 1000);
			((HttpComponentsClientHttpRequestFactory) factory).setConnectTimeout( 50 * 1000);
		}
		
		restTemplate.setRequestFactory( factory );
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			props.load( this.getClass().getResourceAsStream( "/general.properties" ) );						
		} catch(Exception e) {
			logger.error("[ConsumeWS:init]Error al iniciar y cargar arhivo de propiedades." + e.getMessage());
		}		
		URL_WS_BASE = props.getProperty(props.getProperty( "ambiente" )+".URL_WS_COL");
		ambiente = props.getProperty("ambiente");
		logger.info("ambiente--->: "+ambiente);
	}
	
	public List<ContentDTO> getRelacionadasbyIdCategoria(ContentDTO contentDTO) {
		ContentDTO[] arrayContentsRecibidos=null;
		ArrayList<ContentDTO> listRelacionadas=null;
		String metodo="getRelacionadasbyIdCategoria";
		String URL_WS=URL_WS_BASE+metodo;
		HttpHeaders headers=new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		if(ambiente.equalsIgnoreCase("produccion"))
			headers.add("X-Target", "jb-apps-utv.tmx-internacional.net");

		try {
			logger.info("URL_WS: "+URL_WS);
			HttpEntity<ContentDTO> entity = new HttpEntity<ContentDTO>( contentDTO, headers );
			arrayContentsRecibidos=restTemplate.postForObject(URL_WS, entity, ContentDTO[].class);
			listRelacionadas=new ArrayList<ContentDTO>(Arrays.asList(arrayContentsRecibidos));
			logger.info("listRelacionadas: "+listRelacionadas.size());
		} catch(Exception e) {
			logger.error("Error getRelacionadasbyIdCategoria [BO]: ",e);
		}		
		return listRelacionadas;	
	}
	
	public boolean deleteNotaBD(ContentDTO contentDTO) {
		boolean success = false;
		String metodo="deleteNotaBD";
		String URL_WS=URL_WS_BASE+metodo;
		HttpHeaders headers=new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		if(ambiente.equalsIgnoreCase("produccion"))
			headers.add("X-Target", "jb-apps-utv.tmx-internacional.net");
		
		try {
			logger.info("URL_WS: "+URL_WS);
			HttpEntity<ContentDTO> entity = new HttpEntity<ContentDTO>( contentDTO, headers );
			success=restTemplate.postForObject(URL_WS, entity, Boolean.class);
			logger.info("Respuesta: "+success);
		} catch(Exception e) {
			logger.error("Error deleteNotaBD [BO]: ",e);
		}		
		return success;		
	}
	
	public int getSequenceImage() {
		int secuencia=0;
		String metodo="getSequenceImage";
		String URL_WS=URL_WS_BASE+metodo;
		HttpHeaders headers=new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		if(ambiente.equalsIgnoreCase("produccion")){
			logger.info("Ambiente prodcutivo, seteamos el X-Target[jb-apps-utv.tmx-internacional.net]");
			headers.add("X-Target", "jb-apps-utv.tmx-internacional.net");
		}
			
		try {
			logger.info("URL_WS: "+URL_WS);
			secuencia = restTemplate.getForObject(URL_WS, Integer.class, headers);
			logger.info("Secuencia: "+secuencia);
		} catch(Exception e) {
			logger.error("Error getSequenceImage [BO]: ",e);
		}		
		return secuencia;		
	}
	public int getSequenceImageEntity() {
		int secuencia=0;
		String metodo="getSequenceImage";
		String URL_WS=URL_WS_BASE+metodo;
		HttpHeaders headers=new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		if(ambiente.equalsIgnoreCase("produccion")){
			logger.info("Ambiente prodcutivo, seteamos el X-Target[jb-apps-utv.tmx-internacional.net]");
			headers.add("X-Target", "jb-apps-utv.tmx-internacional.net");
		}
			
		try {
			logger.info("URL_WS: "+URL_WS);
			secuencia = restTemplate.getForObject(URL_WS, Integer.class, headers);
			logger.info("Secuencia: "+secuencia);
		} catch(Exception e) {
			logger.error("Error getSequenceImage [BO]: ",e);
		}		
		return secuencia;		
	}
	public int getSequenceImageTest() {
		int secuencia=0;
		String metodo="getSequenceImage";
		String URL_WS=URL_WS_BASE+metodo;
		URL url;
		HttpURLConnection connection = null;
		String respuesta = "";
		try {
			Properties propsTmp = new Properties();
		    propsTmp.load(this.getClass().getResourceAsStream( "/general.properties" ));
			ambiente = propsTmp.getProperty("ambiente");
			
			logger.info("Conectandose a: "+URL_WS);
			//Create connection
			url = new URL(URL_WS);
			connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");		
			//connection.setRequestProperty("Content-Length", "" + Integer.toString(parametros.getBytes().length));						
			connection.setRequestProperty("Accept-Charset", "UTF-8");
			
			if(ambiente.equalsIgnoreCase("produccion")){
				logger.info("Ambiente prodcutivo, seteamos el X-Target[jb-apps-utv.tmx-internacional.net]");
				connection.setRequestProperty("X-Target", "jb-apps-utv.tmx-internacional.net");
			}	
			
			connection.setConnectTimeout(60000);
			connection.setUseCaches (false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			//Send request
			DataOutputStream wr = new DataOutputStream (connection.getOutputStream ());
			//wr.writeBytes (parametros);
			wr.flush ();
			wr.close ();
			//Get Response	
			InputStream is = null;			
			if (connection.getResponseCode() >= 400) {
			    is = connection.getErrorStream();
			} else {
			    is = connection.getInputStream();
			}
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuffer response = new StringBuffer(); 
			while((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			respuesta =  response.toString();	
			logger.info("Respuesta del WS: "+respuesta);
			secuencia=Integer.parseInt(respuesta);
			//obj = new JSONObject(respuesta);
			
		}  catch (java.net.SocketTimeoutException e) {
			logger.info("Se ha tardado demasiado tiempo en esta solicitud, favor de contactar con su administrador");
			logger.error(e);
		} catch (Exception e) {
			logger.error("Ocurrio error al abrir la Url: " , e);
		} finally {
			if(connection != null) {
				connection.disconnect(); 
			}
		}
		return secuencia;	
	}
	
	public boolean deleteNotaHistoricoBD(ContentDTO contentDTO) {
		boolean success = false;
		String metodo="deleteNotaHistoricoBD";
		String URL_WS=URL_WS_BASE+metodo;
		HttpHeaders headers=new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		if(ambiente.equalsIgnoreCase("produccion"))
			headers.add("X-Target", "jb-apps-utv.tmx-internacional.net");
		try {
			logger.info("URL_WS: "+URL_WS);
			HttpEntity<ContentDTO> entity = new HttpEntity<ContentDTO>( contentDTO, headers );
			success=restTemplate.postForObject(URL_WS, entity, Boolean.class);
			logger.info("Respuesta: "+success);
		} catch(Exception e) {
			logger.error("Error deleteNotaHistoricoBD [BO]: ",e);
		}		
		return success;	
	}
	public String getIdNotaByName(String nombreContenido) {
		String idContenido="";
		String metodo="getIdNotaByName";
		String URL_WS=URL_WS_BASE+metodo;
		HttpHeaders headers=new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		if(ambiente.equalsIgnoreCase("produccion"))
			headers.add("X-Target", "jb-apps-utv.tmx-internacional.net");
		try {
			logger.info("URL_WS: "+URL_WS);
			HttpEntity<String> entity = new HttpEntity<String>( nombreContenido, headers );
			idContenido=restTemplate.postForObject(URL_WS, entity, String.class);
			logger.info("Respuesta: "+idContenido);
		} catch(Exception e) {
			logger.error("Error getIdNotaByName [BO]: ",e);
		}		
		return idContenido;	
	}
	public boolean insertTagsApp(String idContenido, String[] TagsApp) {
		boolean success = false;
		try {	
			ContentDTO contentDTO=new ContentDTO();
			contentDTO.setFcIdContenido(idContenido);
			deleteNotaTag(contentDTO);
			//if(deleteTagsApp(contentDTO)){
				for (String idTag:TagsApp) {
					success=insertNotaTag(idContenido, idTag);
					logger.debug("Inserto Tag "+idTag+": "+success);
				}
			//}
			
		} catch(Exception e) {
			logger.error("Error insertTagsApp [BO]: ",e);
		}		
		return success;
	}
		
	public boolean deleteNotaTag(ContentDTO contentDTO) {
		boolean success = false;
		String metodo="deleteNotaTag";
		String URL_WS=URL_WS_BASE+metodo;
		HttpHeaders headers=new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		if(ambiente.equalsIgnoreCase("produccion"))
			headers.add("X-Target", "jb-apps-utv.tmx-internacional.net");
		try {
			logger.info("URL_WS: "+URL_WS);
			HttpEntity<ContentDTO> entity = new HttpEntity<ContentDTO>( contentDTO, headers );
			success=restTemplate.postForObject(URL_WS, entity, Boolean.class);
			logger.info("Respuesta: "+success);
		} catch(Exception e) {
			logger.error("Error deleteNotaTag [BO]: ",e);
		}		
		return success;	
	}
	
	public boolean existeNotaRegistrada(ContentDTO contentDTO) {
		boolean success = false;
		String metodo="existeNotaRegistrada";
		String URL_WS=URL_WS_BASE+metodo;
		logger.info("Creando nuevo objeto de headers...");
		HttpHeaders headers=new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		if(ambiente.equalsIgnoreCase("produccion")){
			headers.add("X-Target", "jb-apps-utv.tmx-internacional.net");
			logger.info("Ambiente productivo, seteando X-Target:jb-apps-utv.tmx-internacional.net");
		}
		try {
			logger.info("URL_WS: "+URL_WS);
			HttpEntity<ContentDTO> entity = new HttpEntity<ContentDTO>( contentDTO, headers );
			success=restTemplate.postForObject(URL_WS, entity, Boolean.class);
			logger.info("Respuesta: "+success);
		} catch(Exception e) {
			logger.error("Error existeNotaRegistrada [BO]: ",e);
		}		
		return success;	
	}
	
	public boolean insertNotaBD(ContentDTO contentDTO) {
		boolean success = false;
		String metodo="insertNotaBD";
		String URL_WS=URL_WS_BASE+metodo;
		HttpHeaders headers=new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		if(ambiente.equalsIgnoreCase("produccion"))
			headers.add("X-Target", "jb-apps-utv.tmx-internacional.net");
		try {
			logger.info("URL_WS: "+URL_WS);
			HttpEntity<ContentDTO> entity = new HttpEntity<ContentDTO>( contentDTO, headers );
			success=restTemplate.postForObject(URL_WS, entity, Boolean.class);
			logger.info("Respuesta: "+success);
		} catch(Exception e) {
			logger.error("Error insertNotaBD [BO]: ",e);
		}		
		return success;	
	}
	
	public boolean insertNotaHistoricoBD(ContentDTO contentDTO) {
		boolean success = false;
		String metodo="insertNotaHistoricoBD";
		String URL_WS=URL_WS_BASE+metodo;
		HttpHeaders headers=new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		if(ambiente.equalsIgnoreCase("produccion"))
			headers.add("X-Target", "jb-apps-utv.tmx-internacional.net");
		try {
			logger.info("URL_WS: "+URL_WS);
			HttpEntity<ContentDTO> entity = new HttpEntity<ContentDTO>( contentDTO, headers );
			success=restTemplate.postForObject(URL_WS, entity, Boolean.class);
			logger.info("Respuesta: "+success);
		} catch(Exception e) {
			logger.error("Error insertNotaHistoricoBD [BO]: ",e);
		}		
		return success;	
	}
	
	public boolean insertNotaTag(String idContenido, String idTag) {
		boolean success = false;
		try {	
			String URL_WS=URL_WS_BASE+"insertNotaTag";
			logger.info("URL_WS: "+URL_WS);
			HttpHeaders headers=new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			if(ambiente.equalsIgnoreCase("produccion"))
			headers.add("X-Target", "jb-apps-utv.tmx-internacional.net");
			
			MultiValueMap<String, Object> parts;
			parts = new LinkedMultiValueMap<String, Object>();
			parts.add("idContenido", idContenido);
			parts.add("idTag", idTag);
			HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(parts, headers);
			success=restTemplate.postForObject(URL_WS, request, Boolean.class);
			
			//success=restTemplate.postForObject(URL_WS, parts, Boolean.class);
			logger.info("Respuesta: "+success);	
		} catch(Exception e) {
			logger.error("Error insertNotaTag :( [BO]: ",e);
		}		
		return success;
	}
	
	public boolean insertNotaTagContent(ContentDTO contentDTO) {	
		boolean success = false;
		String metodo="insertNotaTagContent";
		String URL_WS=URL_WS_BASE+metodo;
		HttpHeaders headers=new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		if(ambiente.equalsIgnoreCase("produccion"))
			headers.add("X-Target", "jb-apps-utv.tmx-internacional.net");
		try {
			logger.info("URL_WS: "+URL_WS);
			HttpEntity<ContentDTO> entity = new HttpEntity<ContentDTO>( contentDTO, headers );
			success=restTemplate.postForObject(URL_WS, entity, Boolean.class);
			logger.info("Respuesta: "+success);
		} catch(Exception e) {
			logger.error("Error insertNotaTagContent [BO]: ",e);
		}		
		return success;	
	}
	public boolean updateNotaHistoricoBD(ContentDTO contentDTO) {
		boolean success = false;
		String metodo="updateNotaHistoricoBD";
		String URL_WS=URL_WS_BASE+metodo;
		HttpHeaders headers=new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		if(ambiente.equalsIgnoreCase("produccion"))
			headers.add("X-Target", "jb-apps-utv.tmx-internacional.net");
		try {
			logger.info("URL_WS: "+URL_WS);
			HttpEntity<ContentDTO> entity = new HttpEntity<ContentDTO>( contentDTO, headers );
			success=restTemplate.postForObject(URL_WS, entity, Boolean.class);
			logger.info("Respuesta: "+success);
		} catch(Exception e) {
			logger.error("Error updateNotaHistoricoBD [BO]: ",e);
		}		
		return success;	
	}
	
	public boolean updateNotaBD(ContentDTO contentDTO) {
		boolean success = false;
		String metodo="updateNotaBD";
		String URL_WS=URL_WS_BASE+metodo;
		HttpHeaders headers=new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		if(ambiente.equalsIgnoreCase("produccion"))
			headers.add("X-Target", "jb-apps-utv.tmx-internacional.net");
		try {
			logger.info("URL_WS: "+URL_WS);
			HttpEntity<ContentDTO> entity = new HttpEntity<ContentDTO>( contentDTO, headers );
			success=restTemplate.postForObject(URL_WS, entity, Boolean.class);
			logger.info("Respuesta: "+success);
		} catch(Exception e) {
			logger.error("Error updateNotaBD [BO]: ",e);
		}		
		return success;	
	}
}