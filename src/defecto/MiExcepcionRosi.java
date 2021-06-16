package defecto;
// Clase de manejo de excepciones

public class MiExcepcionRosi extends Exception {
	private static final long serialVersionUID = 1L;

	public MiExcepcionRosi() {
		super();
	}
	
	public MiExcepcionRosi(String msg) {
		super(msg);
	}
}
