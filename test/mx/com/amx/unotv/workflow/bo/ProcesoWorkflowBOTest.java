package mx.com.amx.unotv.workflow.bo;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Clase test para ProcesoWorkflowBO
 * */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContextBO.xml"})
public class ProcesoWorkflowBOTest {

	private static Logger LOG = Logger.getLogger(ProcesoWorkflowBOTest.class);
		
	@Autowired
	private ProcesoWorkflowBO procesoWorkflowBO;

	
	/**
	 * 
	 * 
	 * */
	@Test
	public void test_procesoWorkflow()
	{
		LOG.debug("Inicia test_procesoWorkflow");
		try {			
			procesoWorkflowBO.procesoWorkflow();
			
		} catch (Exception e) {
			LOG.error("Exception "+e.getMessage());
		}		
		
	}
	
}//FIN CLASE
