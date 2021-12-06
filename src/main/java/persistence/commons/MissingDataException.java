package persistence.commons;

public class MissingDataException extends RuntimeException {
	public MissingDataException(Exception e) {
		super(e);
	}
}
