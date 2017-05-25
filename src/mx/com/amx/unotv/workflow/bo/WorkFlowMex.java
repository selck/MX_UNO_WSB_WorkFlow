package mx.com.amx.unotv.workflow.bo;

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
@Qualifier("workFlowMex")
public class WorkFlowMex {
	
	private static Logger logger = Logger.getLogger(WorkFlowMex.class);
	private final Properties props = new Properties();
	
	String URL_WS_BASE = "";

	private RestTemplate restTemplate;
	HttpHeaders headers = new HttpHeaders();
	
	public WorkFlowMex() {
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
		URL_WS_BASE = props.getProperty(props.getProperty( "ambiente" )+".URL_WS_MEX");
	}
	
		
	public boolean setNotaBD(ContentDTO contentDTO) {
		boolean success = false;
		boolean successTagsApp = false;
		try {	
			String URL_WS=URL_WS_BASE+"existeNotaRegistrada";
			logger.debug("Conectandose a: "+URL_WS);
			restTemplate=new RestTemplate();
			HttpEntity<ContentDTO> entity = new HttpEntity<ContentDTO>( contentDTO );
			success=restTemplate.postForObject(URL_WS, entity, Boolean.class);
			if(success){
				logger.info("Se actualiza la nota");
				success=restTemplate.postForObject(URL_WS_BASE+"updateNotaBD", entity, Boolean.class);
				success=restTemplate.postForObject(URL_WS_BASE+"updateNotaHistoricoBD", entity, Boolean.class);
			}else{
				logger.info("Se inserta nueva nota");
				success=restTemplate.postForObject(URL_WS_BASE+"insertNotaBD", entity, Boolean.class);
				success=restTemplate.postForObject(URL_WS_BASE+"insertNotaHistoricoBD", entity, Boolean.class);
			}
			
			String id_contenido_colombia=contentDTO.getFcIdContenido();
			if(!id_contenido_colombia.equals("")){
				try {
					successTagsApp=insertTagsApp(id_contenido_colombia, contentDTO.getFcTagsApp());
				} catch (Exception e) {
					logger.error("Error insertTagsAppContent[Colombia]: "+e.getLocalizedMessage());
				}
				logger.info("insertoTagsApp_colombia " + contentDTO.getFcNombre() + ": " + successTagsApp);
			}
				
		} catch(Exception e) {
			logger.error("Error setNotaBD :( [BO]: ",e);
		}		
		return success;
	}
	
	private boolean insertTagsApp(String idContenido, String[] TagsApp) {
		boolean success = false;
		try {	
			ContentDTO contentDTO=new ContentDTO();
			contentDTO.setFcIdContenido(idContenido);
			deleteTagsApp(contentDTO);
			//if(deleteTagsApp(contentDTO)){
				for (String idTag:TagsApp) {
					success=insertNotaTag(idContenido, idTag);
					logger.info("Inserto Tag "+idTag+": "+success);
				}
			//}
			
		} catch(Exception e) {
			logger.error("Error insertTagsApp [BO]: ",e);
		}		
		return success;
	}
	
	private boolean insertNotaTag(String idContenido, String idTag) {
		boolean success = false;
		try {	
			String URL_WS=URL_WS_BASE+"insertNotaTag";
			logger.info("URL_WS: "+URL_WS);
			restTemplate=new RestTemplate();
			MultiValueMap<String, Object> parts;
			parts = new LinkedMultiValueMap<String, Object>();
			parts.add("idContenido", idContenido);
			parts.add("idTag", idTag);
			success=restTemplate.postForObject(URL_WS, parts, Boolean.class);
				
		} catch(Exception e) {
			logger.error("Error insertNotaTag [BO]: ",e);
		}		
		return success;
	}
	
	private boolean deleteTagsApp(ContentDTO contentDTO) {
		boolean success = false;
		String metodo="deleteNotaTag";
		String URL_WS=URL_WS_BASE+metodo;
		try {
			logger.info("URL_WS: "+URL_WS);
			HttpEntity<ContentDTO> entity = new HttpEntity<ContentDTO>( contentDTO );
			success=restTemplate.postForObject(URL_WS, entity, Boolean.class);
		} catch(Exception e) {
			logger.error("Error deleteTagsApp [BO]: ",e);
		}		
		return success;	
	}
}
