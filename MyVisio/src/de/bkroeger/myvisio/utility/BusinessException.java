package de.bkroeger.myvisio.utility;

public class BusinessException extends Exception {

	private static final long serialVersionUID = -4080376276505592395L;

	public BusinessException(String msg) {
		super(msg);
	}

	public BusinessException(String msg, Throwable t) {
		super(msg, t);
	}
}
