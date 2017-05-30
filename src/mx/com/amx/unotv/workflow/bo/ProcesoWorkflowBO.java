package mx.com.amx.unotv.workflow.bo;

import mx.com.amx.unotv.workflow.bo.exception.ProcesoWorkflowException;

import org.apache.log4j.Logger;

public class ProcesoWorkflowBO {

	
	//LOG
	private static Logger LOG = Logger.getLogger(ProcesoWorkflowBO.class);
	
	
	/**
	 * 
	 * */
	public void procesoWorkflow() throws ProcesoWorkflowException
	{		
		LOG.debug("Inicia procesoWorkflow");
		try {
			
			LOG.debug("Test");
			
		} catch (Exception e) {
			LOG.error("Exception en procesoWorkflow");
			throw new ProcesoWorkflowException(e.getMessage());
		}
		
	}
	
	
	
	
	
	
}// FIN CLASE

