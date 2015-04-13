package de.andre.entity.core.utils;

/**
 * Created by andreika on 4/13/2015.
 */
public class ForgotPasswordForm {
	private String enteredPassword;
	private String confirmedPassword;

	public String getEnteredPassword() {
		return enteredPassword;
	}

	public void setEnteredPassword(String enteredPassword) {
		this.enteredPassword = enteredPassword;
	}

	public String getConfirmedPassword() {
		return confirmedPassword;
	}

	public void setConfirmedPassword(String confirmedPassword) {
		this.confirmedPassword = confirmedPassword;
	}
}
