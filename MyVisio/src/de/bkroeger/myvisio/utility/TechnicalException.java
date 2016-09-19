package de.bkroeger.myvisio.utility;

public class TechnicalException extends Exception {

	private static final long serialVersionUID = -4080376276505592395L;

	public TechnicalException(String msg) {
		super(msg);
	}

	public TechnicalException(String msg, Throwable t) {
		super(msg, t);
	}
}
