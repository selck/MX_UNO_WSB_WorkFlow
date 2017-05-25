package mx.com.amx.unotv.workflow.controller;

import java.util.Collections;
import java.util.List;

import mx.com.amx.unotv.workflow.bo.WorkFlowCol;
import mx.com.amx.unotv.workflow.dto.ContentDTO;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"workflow-controller-col"})
public class WorkFlowControllerCol {
	
	private static Logger logger=Logger.getLogger(WorkFlowControllerCol.class);
	
	private WorkFlowCol workFlowCol;
	
	@RequestMapping(value={"deleteNotaBD"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, headers={"Accept=application/json"})
	@ResponseBody
	public Boolean deleteNoticia(@RequestBody ContentDTO contentDTO){
		Boolean resultado=false;
		try{
			resultado = this.workFlowCol.deleteNotaBD(contentDTO);
		}
		catch (Exception e){
			logger.error(" Error WorkFlowController [deleteNotaBD ]:", e);
		}
		return resultado;
	}
	
	@RequestMapping(value={"deleteNotaHistoricoBD"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, headers={"Accept=application/json"})
	@ResponseBody
	public Boolean deleteNoticiaHistorico(@RequestBody ContentDTO contentDTO){
		Boolean resultado=false;
		try{
			resultado = this.workFlowCol.deleteNotaHistoricoBD(contentDTO);
		}
		catch (Exception e){
			logger.error(" Error WorkFlowController [deleteNotaHistoricoBD]:", e);
		}
		return resultado;
	}
	
	@RequestMapping(value={"deleteNotaTag"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, headers={"Accept=application/json"})
	@ResponseBody
	public Boolean deleteNotaTag(@RequestBody ContentDTO contentDTO){
		Boolean resultado=false;
		try{
			resultado = this.workFlowCol.deleteNotaTag(contentDTO);
		}
		catch (Exception e){
			logger.error(" Error WorkFlowController [deleteNotaTag ]:", e);
		}
		return resultado;
	}
	
	@RequestMapping(value={"getIdNotaByName"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, headers={"Accept=application/json"})
	@ResponseBody
	public String getIdNotaByName (@RequestBody String name){
		String idContenido="";
		try {
			idContenido=workFlowCol.getIdNotaByName(name);
		} catch (Exception e) {
			
			logger.error(" Error getIdNotaByName [Controller] ",e );
		}
		return idContenido;
	}
	
	@RequestMapping(value={"insertNotaTag"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, headers={"Accept=application/json"})
	@ResponseBody
	public Boolean insertNotaTag(@RequestParam("idContenido") String idContenido, @RequestParam("idTag") String idTag){
		Boolean resultado=false;
		try{
			resultado = this.workFlowCol.insertNotaTag(idContenido, idTag);
		}
		catch (Exception e){
			logger.error(" Error WorkFlowController [insertNotaTag]:", e);
		}
		return resultado;
	}
	
	@RequestMapping(value={"insertNotaTagContent"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, headers={"Accept=application/json"})
	@ResponseBody
	public Boolean insertNotaTagContent(@RequestBody ContentDTO content){
		Boolean resultado=false;
		try{
			resultado = this.workFlowCol.insertNotaTagContent(content);
		}
		catch (Exception e){
			logger.error(" Error WorkFlowController [insertNotaTagContent]:", e);
		}
		return resultado;
	}
	
	@RequestMapping(value={"getRelacionadasbyIdCategoria"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, headers={"Accept=application/json"})
	@ResponseBody
	public List<ContentDTO> getRelacionadasbyIdCategoria(@RequestBody ContentDTO content){
		try{
			return  (List<ContentDTO>) this.workFlowCol.getRelacionadasbyIdCategoria(content);
		}
		catch (Exception e){
			logger.error(" Error WorkFlowController [getRelacionadasbyIdCategoria]:", e);
			return Collections.emptyList();
		}
	}
			
	@RequestMapping(value={"existeNotaRegistrada"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, headers={"Accept=application/json"})
	@ResponseBody
	public Boolean existeNotaRegistrada(@RequestBody ContentDTO content){
		Boolean resultado=false;
		try{
			resultado = this.workFlowCol.existeNotaRegistrada(content);
		}
		catch (Exception e){
			logger.error(" Error WorkFlowController [existeNotaRegistrada]:", e);
		}
		return resultado;
	}
	
	@RequestMapping(value={"insertNotaBD"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, headers={"Accept=application/json"})
	@ResponseBody
	public Boolean insertNotaBD(@RequestBody ContentDTO contentDTO){
		Boolean resultado=false;
		try{
			resultado = this.workFlowCol.insertNotaBD(contentDTO);
		}
		catch (Exception e){
			logger.error(" Error WorkFlowController [insertNotaBD]:", e);
		}
		return resultado;
	}
	
	@RequestMapping(value={"insertNotaHistoricoBD"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, headers={"Accept=application/json"})
	@ResponseBody
	public Boolean insertNotaHistoricoBD(@RequestBody ContentDTO contentDTO){
		Boolean resultado=false;
		try{
			resultado = this.workFlowCol.insertNotaHistoricoBD(contentDTO);
		}
		catch (Exception e){
			logger.error(" Error WorkFlowController [insertNotaHistoricoBD]:", e);
		}
		return resultado;
	}
	
	@RequestMapping(value={"updateNotaBD"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, headers={"Accept=application/json"})
	@ResponseBody
	public Boolean updateNotaBD(@RequestBody ContentDTO contentDTO){
		Boolean resultado=false;
		try{
			resultado = this.workFlowCol.updateNotaBD(contentDTO);
		}
		catch (Exception e){
			logger.error(" Error WorkFlowController [updateNotaBD]:", e);
		}
		return resultado;
	}
	
	@RequestMapping(value={"updateNotaHistoricoBD"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, headers={"Accept=application/json"})
	@ResponseBody
	public Boolean updateHistoricoNoticiaBD(@RequestBody ContentDTO contentDTO){
		Boolean resultado=false;
		try{
			resultado = this.workFlowCol.updateNotaHistoricoBD(contentDTO);
		}
		catch (Exception e){
			logger.error(" Error WorkFlowController [updateNotaHistoricoBD]:", e);
		}
		return resultado;
	}
	

	@RequestMapping(value={"getSequenceImage"}, method={org.springframework.web.bind.annotation.RequestMethod.GET}, headers={"Accept=application/json"})
	@ResponseBody
	public Integer getSequenceImage(){
		int resultado=0;
		try{
			resultado = this.workFlowCol.getSequenceImage();
		}
		catch (Exception e){
			logger.error(" Error WorkFlowController [getSequenceImage]:", e);
		}
		return resultado;
	}
	
	@RequestMapping(value={"getSequenceImageTest"}, method={org.springframework.web.bind.annotation.RequestMethod.GET}, headers={"Accept=application/json"})
	@ResponseBody
	public Integer getSequenceImageTest(){
		int resultado=0;
		try{
			resultado = this.workFlowCol.getSequenceImageTest();
		}
		catch (Exception e){
			logger.error(" Error WorkFlowController [getSequenceImageTest]:", e);
		}
		return resultado;
	}
	/*Get and Set*/
	
	/**
	 * @return the workFlowCol
	 */
	public WorkFlowCol getWorkFlowCol() {
		return workFlowCol;
	}

	/**
	 * @param workFlowCol the workFlowCol to set
	 */
	@Autowired
	public void setWorkFlowCol(WorkFlowCol workFlowCol) {
		this.workFlowCol = workFlowCol;
	}

}
