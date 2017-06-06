package mx.com.amx.unotv.workflow.bo;

import mx.com.amx.unotv.workflow.bo.exception.LlamadasWSBOException;
import mx.com.amx.unotv.workflow.bo.exception.LlamadasWSDAOException;
import mx.com.amx.unotv.workflow.bo.exception.ProcesoWorkflowException;
import mx.com.amx.unotv.workflow.dto.ContentDTO;
import mx.com.amx.unotv.workflow.dto.ParametrosDTO;
import mx.com.amx.unotv.workflow.dto.PushAmpDTO;
import mx.com.amx.unotv.workflow.dto.VideoOoyalaDTO;
import mx.com.amx.unotv.workflow.dto.RespuestaWSAmpDTO;
import mx.com.amx.unotv.workflow.utileria.UtilWorkFlow;

import org.apache.log4j.Logger;

public class ProcesoWorkflowBO {

	
	//LOG
	private static Logger logger = Logger.getLogger(ProcesoWorkflowBO.class);
	
	
	/**
	 * desde
	 * @param
	 * @param
	 * @return
	 * @throws
	 * @author jesus
	 * */
	public void procesoWorkflow() throws ProcesoWorkflowException
	{		
		logger.debug("Inicia procesoWorkflow");
		try {
			
			logger.debug("Test");
			
		} catch (Exception e) {
			logger.error("Exception en procesoWorkflow");
			throw new ProcesoWorkflowException(e.getMessage());
		}
		
	}
	
	/**
	 * Método que es utilizado para el proceso de caducación de una nota del portal de UNOTV,
	 * se elimina nota de la base de datos y de Facebook Instant Articles
	 * @param ContentDTO contentDTO
	 * @return Boolean
	 * @throws LlamadasWSDAOException, LlamadasWSBOException, ProcesoWorkflowException
	 * @author jesus
	 * */
	public Boolean caducarNota(ContentDTO contentDTO) throws ProcesoWorkflowException{
		
		boolean success=false;
		try {
			ParametrosDTO parametrosDTO=UtilWorkFlow.obtenerPropiedades("ambiente.resources.properties");
			LlamadasWSDAO LlamadasWSDAO=new LlamadasWSDAO(parametrosDTO.getURL_WS_DATOS());
			LlamadasWSBO llamadasWSBO=new LlamadasWSBO(parametrosDTO.getURL_WS_FB()); //kaka

			if(contentDTO.getFcTagsApp() != null && contentDTO.getFcTagsApp().length>0){
				success = LlamadasWSDAO.deleteTagsApp(contentDTO);
			}
			success = LlamadasWSDAO.deleteNotaBD(contentDTO);
			success = LlamadasWSDAO.deleteNotaHistoricoBD(contentDTO);
			
			if(!contentDTO.getFcFBArticleId().equals("")){
		 		logger.info(llamadasWSBO.deleteArticleFB(contentDTO.getFcFBArticleId()));
			}else
		 		logger.info("No se contaba con el articleFBId");
			
		}catch (LlamadasWSDAOException daoException){
			logger.error("Exception LlamadasWSDAOException: "+daoException.getMessage());
			throw new ProcesoWorkflowException(daoException.getMessage());
		}catch (LlamadasWSBOException boException){
			logger.error("Exception LlamadasWSBOException: "+boException.getMessage());
			throw new ProcesoWorkflowException(boException.getMessage());
		}catch (Exception e) {
			logger.error("Exception CaducarNota: ",e);
			throw new ProcesoWorkflowException(e.getMessage());
		}
		return success;
	}
	/**
	 * Método que es utilizado para el proceso de revisión de una nota del portal de UNOTV,
	 * se genera html para poder visualizarlo antes de publicar la nota.
	 * @param ContentDTO contentDTO
	 * @return String
	 * @throws ProcesoWorkflowException
	 * @author jesus
	 * */
	public String revisarNota(ContentDTO contentDTO) throws ProcesoWorkflowException{
		
		String url_revision_nota="";
		try {
			ParametrosDTO parametrosDTO=UtilWorkFlow.obtenerPropiedades("ambiente.resources.properties");
			String carpetaContenido=parametrosDTO.getPathFilesTest()+UtilWorkFlow.getRutaContenido(contentDTO, parametrosDTO);
			logger.debug("carpetaContenido: "+carpetaContenido);
			UtilWorkFlow.createFolders(carpetaContenido);
			parametrosDTO.setBaseURL(parametrosDTO.getBaseURLTest());
			UtilWorkFlow.createPlantilla(parametrosDTO, contentDTO, "unotv-wfs-revision");
			url_revision_nota=parametrosDTO.getAmbiente().equalsIgnoreCase("desarrollo")?parametrosDTO.getDominio()+"/portal/test-unotv/"+UtilWorkFlow.getRutaContenido(contentDTO, parametrosDTO):
 				"http://pruebas-unotv.tmx-internacional.net"+"/"+UtilWorkFlow.getRutaContenido(contentDTO, parametrosDTO);
		} catch (Exception e) {
			logger.error("Error en revisarNota: ",e);
			throw new ProcesoWorkflowException(e.getMessage());
		}
		return url_revision_nota;
	}
	
	/**
	 * Método que es utilizado para el proceso de publicación de una nota del portal de UNOTV,
	 * se inserta nota en la base de datos, se hace el llamado para el Push AMP y el insert a Facebook Instant Articles
	 * @param ContentDTO
	 * @return Boolean
	 * @throws LlamadasWSDAOException, LlamadasWSBOException
	 * @author jesus
	 * */
	public String publicarNota(ContentDTO contentDTO) throws ProcesoWorkflowException{
		String id_facebook="";
		try {
			ParametrosDTO parametrosDTO=UtilWorkFlow.obtenerPropiedades("ambiente.resources.properties");
			LlamadasWSDAO llamadasWSDAO=new LlamadasWSDAO(parametrosDTO.getURL_WS_DATOS());
			LlamadasWSBO llamadasWSBO=new LlamadasWSBO(parametrosDTO.getURL_WS_FB());
			if(contentDTO.getFcIdTipoNota().equals("video") || contentDTO.getFcIdTipoNota().equals("multimedia")
					&& !contentDTO.getFcIdVideoOoyala().equals("")){
				
				VideoOoyalaDTO ooyalaDTO=llamadasWSBO.getInfoVideo(contentDTO.getFcIdVideoOoyala(), parametrosDTO);
				contentDTO.setFcSourceVideo(ooyalaDTO.getSource() ==  null?"":ooyalaDTO.getSource());
				contentDTO.setFcAlternateTextVideo(ooyalaDTO.getAlternate_text()==null?"":ooyalaDTO.getAlternate_text());
				contentDTO.setFcDurationVideo(ooyalaDTO.getDuration()==null?"":ooyalaDTO.getDuration());
				contentDTO.setFcFileSizeVideo(ooyalaDTO.getFileSize()==null?"":ooyalaDTO.getFileSize());
			}
			
			llamadasWSDAO.setNotaBD(contentDTO);
			String carpetaContenido=parametrosDTO.getPathFiles()+UtilWorkFlow.getRutaContenido(contentDTO, parametrosDTO);
			logger.debug("carpetaContenido: "+carpetaContenido);
			UtilWorkFlow.createFolders(carpetaContenido);
			UtilWorkFlow.createPlantilla(parametrosDTO, contentDTO, "unotv-wfs-publicar");
			
			String html_amp=UtilWorkFlow.createPlantillaAMP(parametrosDTO, contentDTO);
			
			if(!html_amp.equals("")){ 
				logger.info("Enviamos PUSH al AMP");
				PushAmpDTO pushAMP=new PushAmpDTO();
				pushAMP.setFcIdCategoria(contentDTO.getFcIdCategoria());
				pushAMP.setFcIdContenido(contentDTO.getFcIdContenido());
				pushAMP.setFcNombre(contentDTO.getFcNombre());
				pushAMP.setFcSeccion(contentDTO.getFcSeccion());
				pushAMP.setFcTipoSeccion(contentDTO.getFcTipoSeccion());
				pushAMP.setFcTitulo(contentDTO.getFcTitulo());
				pushAMP.setFdFechaPublicacion(contentDTO.getFdFechaPublicacion());
				pushAMP.setHtmlAMP(html_amp);
				llamadasWSBO = new LlamadasWSBO(parametrosDTO.getURL_WS_AMP());
				RespuestaWSAmpDTO  respuestaWSAMP=llamadasWSBO.sendPushAMP(pushAMP);
				logger.info("Respuesta AMP: "+respuestaWSAMP.getRespuesta());
			}
			
				llamadasWSBO = new LlamadasWSBO(parametrosDTO.getURL_WS_FB());
				id_facebook=llamadasWSBO.insertUpdateArticleFB(contentDTO);
				
			 }catch (LlamadasWSDAOException daoException){
				logger.error("Exception LlamadasWSDAOException: "+daoException.getMessage());
				throw new ProcesoWorkflowException(daoException.getMessage());
			}catch (LlamadasWSBOException boException){
				logger.error("Exception LlamadasWSBOException: "+boException.getMessage());
				throw new ProcesoWorkflowException(boException.getMessage());
			}catch (Exception e) {
				logger.error("Exception publicarNota: ",e);
				throw new ProcesoWorkflowException(e.getMessage());
			}
		return id_facebook;
	}
	
}// FIN CLASE

