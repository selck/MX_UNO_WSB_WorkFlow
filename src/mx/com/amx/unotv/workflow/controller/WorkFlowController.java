package mx.com.amx.unotv.workflow.controller;

import mx.com.amx.unotv.workflow.bo.ProcesoWorkflowBO;
import mx.com.amx.unotv.workflow.dto.ContentDTO;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"workflow-controller"})
public class WorkFlowController {
	
	//LOG
	private static Logger LOG=Logger.getLogger(WorkFlowController.class);
	
	@Autowired
	private ProcesoWorkflowBO procesoWorkflowBO;
	
	
	/**
	 * 
	 * 
	 * 
	 * */
	@RequestMapping(value={"publicarNota"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, headers={"Accept=application/json"})
	@ResponseBody
	public String publicarNota(@RequestBody ContentDTO contentDTO){
		LOG.debug("Inicia publicarNota..");
		
		String resultado="";
		try{
		
			resultado = this.procesoWorkflowBO.publicarNota(contentDTO);
		}
		catch (Exception e){
			LOG.error(" Error WorkFlowController [publicarNota]:", e);
		}
		return resultado;
	}
	
	@RequestMapping(value={"caducarNota"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, headers={"Accept=application/json"})
	@ResponseBody
	public Boolean caducarNota(@RequestBody ContentDTO contentDTO){
		LOG.debug("Inicia caducarNota..");
		
		Boolean resultado=false;
		try{
		
			resultado = this.procesoWorkflowBO.caducarNota(contentDTO);
		}
		catch (Exception e){
			LOG.error(" Error WorkFlowController [caducarNota]:"+e.getMessage());
		}
		return resultado;
	}
	
	@RequestMapping(value={"revisarNota"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, headers={"Accept=application/json"})
	@ResponseBody
	public String revisarNota(@RequestBody ContentDTO contentDTO){
		LOG.debug("Inicia revisarNota..");
		
		String resultado="";
		try{
			resultado = this.procesoWorkflowBO.revisarNota(contentDTO);
		}
		catch (Exception e){
			LOG.error(" Error WorkFlowController [revisarNota]:"+e.getMessage());
		}
		return resultado;
	}

}//FIN CLASE
