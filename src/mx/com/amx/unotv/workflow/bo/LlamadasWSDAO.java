package mx.com.amx.unotv.workflow.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mx.com.amx.unotv.workflow.bo.exception.LlamadasWSDAOException;
import mx.com.amx.unotv.workflow.dto.ContentDTO;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class LlamadasWSDAO {
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());
	private RestTemplate restTemplate;
	private String URL_WS_BASE="";
	private HttpHeaders headers = new HttpHeaders();
	
	public LlamadasWSDAO(String urlWS) {
		super();
		restTemplate = new RestTemplate();
		ClientHttpRequestFactory factory = restTemplate.getRequestFactory();

	        if ( factory instanceof SimpleClientHttpRequestFactory)
	        {
	            ((SimpleClientHttpRequestFactory) factory).setConnectTimeout( 50 * 1000 );
	            ((SimpleClientHttpRequestFactory) factory).setReadTimeout( 50 * 1000 );
	        }
	        else if ( factory instanceof HttpComponentsClientHttpRequestFactory)
	        {
	            ((HttpComponentsClientHttpRequestFactory) factory).setReadTimeout( 50 * 1000);
	            ((HttpComponentsClientHttpRequestFactory) factory).setConnectTimeout( 50 * 1000);
	            
	        }
	        restTemplate.setRequestFactory( factory );
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        
			URL_WS_BASE = urlWS;
	}
	
	public List<ContentDTO> getRelacionadasbyIdCategoria(ContentDTO contentDTO) throws LlamadasWSDAOException {
		ContentDTO[] arrayContentsRecibidos=null;
		ArrayList<ContentDTO> listRelacionadas=null;
		String metodo="getRelacionadasbyIdCategoria";
		String URL_WS=URL_WS_BASE+metodo;
		try {
			HttpEntity<ContentDTO> entity = new HttpEntity<ContentDTO>( contentDTO );
			arrayContentsRecibidos=restTemplate.postForObject(URL_WS, entity, ContentDTO[].class);
			listRelacionadas=new ArrayList<ContentDTO>(Arrays.asList(arrayContentsRecibidos));
			
		} catch(Exception e) {
			logger.error("Error getRelacionadasbyIdCategoria [BO]: "+e.getLocalizedMessage());
			throw new LlamadasWSDAOException(e.getMessage());
		}		
		return listRelacionadas;	
	}
	public List<ContentDTO> getNotasMagazine(String idMagazine, String idContenido) throws LlamadasWSDAOException {
		ContentDTO[] arrayContentsRecibidos=null;
		ArrayList<ContentDTO> listRelacionadas=null;
		String metodo="getNotasMagazine";
		String URL_WS=URL_WS_BASE+metodo;
		try {
			//HttpEntity<String> entity = new HttpEntity<String>( idMagazine );
			MultiValueMap<String, Object> parts;
			parts = new LinkedMultiValueMap<String, Object>();
			parts.add("idMagazine", idMagazine);
			parts.add("idContenido", idContenido);
			arrayContentsRecibidos=restTemplate.postForObject(URL_WS, parts, ContentDTO[].class);
			listRelacionadas=new ArrayList<ContentDTO>(Arrays.asList(arrayContentsRecibidos));
			
		} catch(Exception e) {
			logger.error("Error getNotasMagazine [BO]: "+e.getLocalizedMessage());
			throw new LlamadasWSDAOException(e.getMessage());
		}		
		return listRelacionadas;	
	}
	public List<ContentDTO> getNotasMagazine(String idMagazine) throws LlamadasWSDAOException {
		ContentDTO[] arrayContentsRecibidos=null;
		ArrayList<ContentDTO> listRelacionadas=null;
		String metodo="getNotasMagazine";
		String URL_WS=URL_WS_BASE+metodo;
		try {
			HttpEntity<String> entity = new HttpEntity<String>( idMagazine );
			arrayContentsRecibidos=restTemplate.postForObject(URL_WS, entity, ContentDTO[].class);
			listRelacionadas=new ArrayList<ContentDTO>(Arrays.asList(arrayContentsRecibidos));
			
		} catch(Exception e) {
			logger.error("Error getNotasMagazine [BO]: "+e.getLocalizedMessage());
			throw new LlamadasWSDAOException(e.getMessage());
		}		
		return listRelacionadas;	
	}
	
	public boolean deleteNotaBD(ContentDTO contentDTO) throws LlamadasWSDAOException {
		boolean success = false;
		String metodo="deleteNotaBD";
		String URL_WS=URL_WS_BASE+metodo;
		try {
			HttpEntity<ContentDTO> entity = new HttpEntity<ContentDTO>( contentDTO );
			success=restTemplate.postForObject(URL_WS, entity, Boolean.class);
		} catch(Exception e) {
			logger.error("Error deleteNotaBD [BO]: "+e.getLocalizedMessage());
			throw new LlamadasWSDAOException(e.getMessage());
		}		
		return success;		
	}

	
	public boolean deleteNotaHistoricoBD(ContentDTO contentDTO) throws LlamadasWSDAOException {
		boolean success = false;
		String metodo="deleteNotaHistoricoBD";
		String URL_WS=URL_WS_BASE+metodo;
		try {
			HttpEntity<ContentDTO> entity = new HttpEntity<ContentDTO>( contentDTO );
			success=restTemplate.postForObject(URL_WS, entity, Boolean.class);
		} catch(Exception e) {
			logger.error("Error deleteNotaHistoricoBD [BO]: "+e.getLocalizedMessage());
			throw new LlamadasWSDAOException(e.getMessage());
		}		
		return success;	
	}
	public String getIdNotaByName(String nombreContenido) throws LlamadasWSDAOException {
		String idContenido="";
		String metodo="getIdNotaByName";
		String URL_WS=URL_WS_BASE+metodo;
		try {
			HttpEntity<String> entity = new HttpEntity<String>( nombreContenido );
			idContenido=restTemplate.postForObject(URL_WS, entity, String.class);
		} catch(Exception e) {
			logger.error("Error getIdNotaByName [BO]: "+e.getLocalizedMessage());
			throw new LlamadasWSDAOException(e.getMessage());
		}		
		return idContenido;	
	}
	public boolean insertTagsApp(String idContenido, String[] TagsApp) throws LlamadasWSDAOException {
		boolean success = false;
		try {	
			ContentDTO contentDTO=new ContentDTO();
			contentDTO.setFcIdContenido(idContenido);
			deleteTagsApp(contentDTO);
				for (String idTag:TagsApp) {
					success=insertNotaTag(idContenido, idTag);
					//logger.info("Inserto Tag "+idTag+": "+success);
				}
		} catch(Exception e) {
			logger.error("Error insertTagsApp [BO]: "+e.getLocalizedMessage());
			throw new LlamadasWSDAOException(e.getMessage());
		}		
		return success;
	}
	public boolean insertTagsAppContent(ContentDTO contentDTO) throws LlamadasWSDAOException {
		boolean success = false;
		try {	
			deleteTagsApp(contentDTO);
			success=insertNotaTagContent(contentDTO);			
		} catch(Exception e) {
			logger.error("Error insertTagsAppContent [BO]: "+e.getLocalizedMessage());
			throw new LlamadasWSDAOException(e.getMessage());
		}		
		return success;
	}	
	public boolean deleteTagsApp(ContentDTO contentDTO) throws LlamadasWSDAOException {
		boolean success = false;
		String metodo="deleteNotaTag";
		String URL_WS=URL_WS_BASE+metodo;
		try {
			HttpEntity<ContentDTO> entity = new HttpEntity<ContentDTO>( contentDTO );
			success=restTemplate.postForObject(URL_WS, entity, Boolean.class);
		} catch(Exception e) {
			logger.error("Error deleteTagsApp [BO]: "+e.getLocalizedMessage());
			throw new LlamadasWSDAOException(e.getMessage());
		}		
		return success;	
	}
	public boolean insertNotaTag(String idContenido, String idTag) throws LlamadasWSDAOException {
		boolean success = false;
		try {	
			String URL_WS=URL_WS_BASE+"insertNotaTag";
			restTemplate=new RestTemplate();
			MultiValueMap<String, Object> parts;
			parts = new LinkedMultiValueMap<String, Object>();
			parts.add("idContenido", idContenido);
			parts.add("idTag", idTag);
			success=restTemplate.postForObject(URL_WS, parts, Boolean.class);
				
		} catch(Exception e) {
			logger.error("Error insertNotaTag [BO]: "+e.getLocalizedMessage());
			throw new LlamadasWSDAOException(e.getMessage());
		}		
		return success;
	}
	public boolean insertNotaTagContent(ContentDTO contentDTO) throws LlamadasWSDAOException {
		boolean success = false;
		try {	
			String URL_WS=URL_WS_BASE+"insertNotaTagContent";
			HttpEntity<ContentDTO> entity = new HttpEntity<ContentDTO>( contentDTO );
			success=restTemplate.postForObject(URL_WS, entity, Boolean.class);
				
		} catch(Exception e) {
			logger.error("Error insertNotaTagContent [BO]: "+e.getLocalizedMessage());
			throw new LlamadasWSDAOException(e.getMessage());
		}		
		return success;
	}
	
	public boolean setNotaBD(ContentDTO contentDTO) throws LlamadasWSDAOException {
		boolean success = false;
		try {	
			String URL_WS=URL_WS_BASE+"existeNotaRegistrada";
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
				
		} catch(Exception e) {
			logger.error("Error setNotaBD :( [BO]: "+e.getLocalizedMessage());
			throw new LlamadasWSDAOException(e.getMessage());
		}		
		return success;
	}
	
	public String getParameter(String idParameter) throws LlamadasWSDAOException {
		String resultado = "";
		String metodo="getParameter";
		String URL_WS=URL_WS_BASE+metodo;
		try {
			HttpEntity<String> entity = new HttpEntity<String>( idParameter );
			resultado=restTemplate.postForObject(URL_WS, entity, String.class);
		} catch(Exception e) {
			logger.error("Error getParameter [BO]: "+e.getLocalizedMessage());
			throw new LlamadasWSDAOException(e.getMessage());
		}		
		return resultado;		
	}
	
}
