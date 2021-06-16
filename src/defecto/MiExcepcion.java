package defecto;

public class MiExcepcion extends Exception {

	private static final long serialVersionUID = -8312278303267218013L;
	
	
	/**
	 * Constructor
	 */
	public MiExcepcion() {
		super();
	}
	
	
	/**
	 * Constructor
	 * @param msn
	 */
	public MiExcepcion(String msn) {		
		super(msn);
	}
	

}
