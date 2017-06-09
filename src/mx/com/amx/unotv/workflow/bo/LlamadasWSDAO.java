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
	
	/**
	 * Contructor de la clase LlamadasWSDAO, seteamos el ContentType para que sea del tipo
	 * APPLICATION_JSON
	 * @param String urlWS
	 * @author jesus
	 * */
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
	
	/**
	 * Método que es utilizado para hacer una llamada al servicio que se conecta a la
	 * base de datos para verificar la existencia del contenido, si este no existe es tratado como
	 * un insert, por otro lado si el contenido ya existe, se hace un actualización del mismo.
	 * @param ContentDTO
	 * @return boolean
	 * @throws LlamadasWSDAOException
	 * @author jesus
	 * */
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
	
	/**
	 * Método que es utilizado para hacer una llamada al servicio que se conecta a la
	 * base de datos para obtener el id de una nota previamente guardada en la base 
	 * de datos mediante el campo fc_nombre
	 * @param String nameContent
	 * @return String
	 * @throws LlamadasWSDAOException
	 * @author jesus
	 * */
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
	/**
	 * Método que es utilizado para hacer una llamada al servicio que se conecta a la
	 * base de datos para hacer un insert en una tabla intermedia que lleva el registro 
	 * del id_contenido y su relación con con el id_tag_app 
	 * @param String id_contenido
	 * @param String[] tags_app_id
	 * @return boolean
	 * @throws LlamadasWSDAOException
	 * @author jesus
	 * */
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
	
	/**
	 * Método que es utilizado para hacer una llamada al servicio que se conecta a la
	 * base de datos para hacer un insert en una tabla intermedia que lleva el registro 
	 * del id_contenido y su relación con con el id_tag_app 
	 * @param  id_contenido
     *          Id del contenido para insertar en la tabla intermedia
   	 * @param  id_tag
     *          Id del Tag para insertar en la tabla intermedia
	 * @return boolean
	 * @throws LlamadasWSDAOException
	 * @author jesus
	 * */
	private boolean insertNotaTag(String id_contenido, String id_tag) throws LlamadasWSDAOException {
		boolean success = false;
		try {	
			String URL_WS=URL_WS_BASE+"insertNotaTag";
			restTemplate=new RestTemplate();
			MultiValueMap<String, Object> parts;
			parts = new LinkedMultiValueMap<String, Object>();
			parts.add("idContenido", id_contenido);
			parts.add("idTag", id_tag);
			success=restTemplate.postForObject(URL_WS, parts, Boolean.class);
				
		} catch(Exception e) {
			logger.error("Error insertNotaTag [BO]: "+e.getLocalizedMessage());
			throw new LlamadasWSDAOException(e.getMessage());
		}		
		return success;
	}
	
	
	
	
	
	/**
	 * Método que es utilizado para hacer una llamada al servicio que se conecta a la
	 * base de datos para llevar a cabo un delete en la tabla intermedia que lleva a
	 * cabo el control de la relación entre el id del contenido y el id tag de la app V1.0
	 * @param  ContentDTO
	 * @return boolean
	 * @throws LlamadasWSDAOException
	 * @author jesus
	 * */
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
	/**
	 * Método que es utilizado para hacer una llamada al servicio que se conecta a la
	 * base de datos para llevar a cabo un delete en la tabla de negocio
	 * @param  ContentDTO
	 * @return boolean
	 * @throws LlamadasWSDAOException
	 * @author jesus
	 * */
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

	/**
	 * Método que es utilizado para hacer una llamada al servicio que se conecta a la
	 * base de datos para llevar a cabo un delete en la tabla de historicos
	 * @param  ContentDTO
	 * @return boolean
	 * @throws LlamadasWSDAOException
	 * @author jesus
	 * */
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
	/**
	 * Método que es utilizado para hacer una llamada al servicio que se conecta a la
	 * base de datos para obtener las notas del magazine
	 * @param  String
	 * 		   idMagazine es el identificador del magazine para obtener sus notas
	 * @param  String
	 * 		   idContenido es el parametro que se utiliza para no repetir notas
	 * @return List<ContentDTO>
	 * 		   lista de notas del magazine
	 * @throws LlamadasWSDAOException
	 * @author jesus
	 * */
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
	/**
	 * Método que es utilizado para hacer una llamada al servicio que se conecta a la
	 * base de datos para obtener una lista de notas relacionadas por id_categoria
	 * @param  ContentDTO
	 * @return List<ContentDTO>
	 * 		   lista de notas de una misma categoria
	 * @throws LlamadasWSDAOException
	 * @author jesus
	 * */
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
