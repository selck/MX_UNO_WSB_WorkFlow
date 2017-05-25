package mx.com.amx.unotv.workflow.controller;

import mx.com.amx.unotv.workflow.bo.WorkFlowMex;
import mx.com.amx.unotv.workflow.dto.ContentDTO;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"workflow-controller-mex"})
public class WorkFlowControllerMex {
	
	private static Logger logger=Logger.getLogger(WorkFlowControllerMex.class);
	
	private WorkFlowMex workFlowMex; 
	
	@RequestMapping(value={"setNotaBD"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, headers={"Accept=application/json"})
	@ResponseBody
	public Boolean setNotaBD(@RequestBody ContentDTO contentDTO){
		Boolean resultado=false;
		try{
			resultado = this.workFlowMex.setNotaBD(contentDTO);
		}
		catch (Exception e){
			logger.error(" Error WorkFlowController [setNotaBD]:", e);
		}
		return resultado;
	}
		/**************************/

	/**
	 * @return the workFlowMex
	 */
	public WorkFlowMex getWorkFlowMex() {
		return workFlowMex;
	}

	/**
	 * @param workFlowMex the workFlowMex to set
	 */
	@Autowired
	public void setWorkFlowMex(WorkFlowMex workFlowMex) {
		this.workFlowMex = workFlowMex;
	}

	
	
	
	
	
}
