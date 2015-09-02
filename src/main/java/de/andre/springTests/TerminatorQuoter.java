package de.andre.springTests;

/**
 * Created by andreika on 9/1/2015.
 */
public class TerminatorQuoter implements Quoter {
	private String message;

	@InjectRandom(min = 3, max = 7)
	private int repeat;

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public void sayQuote() {
		for (int i = 0; i < repeat; i++) {
			System.out.println("message" + message);
		}
	}
}
