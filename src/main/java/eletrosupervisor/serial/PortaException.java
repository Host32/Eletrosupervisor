package eletrosupervisor.serial;

public class PortaException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	private Exception originalException;
	
	public PortaException(Exception e){
		this.originalException = e;
	}

	public Exception getOriginalException() {
		return originalException;
	}
}
