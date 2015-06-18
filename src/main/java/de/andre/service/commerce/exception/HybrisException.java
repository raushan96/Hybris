package de.andre.service.commerce.exception;

/**
 * Created by Andrei on 6/18/2015.
 */
public class HybrisException extends Exception {

	private static final long serialVersionUID = -6325480080103376912L;

	public HybrisException(final String pMessage) {
		super(pMessage);
	}

	public HybrisException(final String pMessage, final Throwable pCause) {
		super(pMessage, pCause);
	}

	public HybrisException(final Throwable pCause) {
		super(pCause);
	}
}
