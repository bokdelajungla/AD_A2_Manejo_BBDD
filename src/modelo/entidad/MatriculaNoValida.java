package modelo.entidad;

/**
 * Clase que implementa la excepción que se lanza en caso de que la matrícula introducida no 
 * cumpla con la expresión regular
 *
 */
public class MatriculaNoValida extends Exception{
	
	private static final long serialVersionUID = 1L;

	public MatriculaNoValida(String errorMessage) {
        super(errorMessage);
    }
}