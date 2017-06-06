package mx.com.amx.unotv.workflow.bo;

import mx.com.amx.unotv.workflow.bo.exception.LlamadasWSBOException;
import mx.com.amx.unotv.workflow.dto.ContentDTO;
import mx.com.amx.unotv.workflow.dto.ParametrosDTO;
import mx.com.amx.unotv.workflow.dto.PushAmpDTO;
import mx.com.amx.unotv.workflow.dto.RespuestaWSAmpDTO;
import mx.com.amx.unotv.workflow.dto.VideoOoyalaDTO;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class LlamadasWSBO {
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());
	private RestTemplate restTemplate;
	private String URL_WS_BASE="";
	private HttpHeaders headers = new HttpHeaders();
	
	
	
	public LlamadasWSBO(String urlWS) {
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
	
	public String insertUpdateArticleFB (ContentDTO contentDTO) throws LlamadasWSBOException{
		
		String respuesta="";
		String metodo="insertUpdateArticle2";
		String URL_WS=URL_WS_BASE+metodo;
		try {
			HttpEntity<ContentDTO> entity = new HttpEntity<ContentDTO>( contentDTO );
			respuesta=restTemplate.postForObject(URL_WS, entity, String.class);
		} catch(Exception e) {
			logger.error("Error insertUpdateArticle - FB [BO]: ",e);
			throw new LlamadasWSBOException(e.getMessage());
		}
		return respuesta;
	}
	public String deleteArticleFB (String articleId) throws LlamadasWSBOException{
		
		String respuesta="";
		String metodo="deleteArticle";
		String URL_WS=URL_WS_BASE+metodo;
		try {
			HttpEntity<String> entity = new HttpEntity<String>( articleId );
			respuesta=restTemplate.postForObject(URL_WS, entity, String.class);
		} catch(Exception e) {
			logger.error("Error deleteArticle - FB [BO]: "+e.getLocalizedMessage());
			throw new LlamadasWSBOException(e.getMessage());
		}
		return respuesta;
	}
	
	public RespuestaWSAmpDTO sendPushAMP(PushAmpDTO pushAMP ) throws LlamadasWSBOException {
		RespuestaWSAmpDTO respuestaWSAMP=new RespuestaWSAmpDTO();
		String URL_WS=URL_WS_BASE+"sendPushAMP";
		try {
			HttpEntity<PushAmpDTO> entity = new HttpEntity<PushAmpDTO>( pushAMP );
			respuestaWSAMP=restTemplate.postForObject(URL_WS, entity, RespuestaWSAmpDTO.class);
		} catch(Exception e) {
			logger.error("Error sendPushAMP [BO]: "+e.getLocalizedMessage());
			throw new LlamadasWSBOException(e.getMessage());
		}		
		return respuestaWSAMP;	
	}
	
	public VideoOoyalaDTO getInfoVideo(String content_id, ParametrosDTO parametrosDTO) throws LlamadasWSBOException {
		VideoOoyalaDTO respuesta = new VideoOoyalaDTO();
		String metodo="getInfoVideo";
		String URL_WS=parametrosDTO.getURL_WS_VIDEO()+metodo;
		try {
			HttpEntity<String> entity = new HttpEntity<String>( content_id );
			respuesta=restTemplate.postForObject(URL_WS, entity, VideoOoyalaDTO.class);
		} catch(Exception e) {
			logger.error("Error getInfoVideo [BO]: "+e.getLocalizedMessage());
			throw new LlamadasWSBOException(e.getMessage());
		}		
		return respuesta;	
	}
	
}
