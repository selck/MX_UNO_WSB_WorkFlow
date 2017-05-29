package mx.com.amx.unotv.workflow.bo.exception;

public class ProcesoWorkflowException extends Exception {

private static final long serialVersionUID = 1L;
	
	public ProcesoWorkflowException(String mensaje) {
        super(mensaje);
    }

	public ProcesoWorkflowException(Throwable exception) {
        super(exception);
    }
	
    public ProcesoWorkflowException(String mensaje, Throwable exception) {
        super(mensaje, exception);
    }	
	
}
