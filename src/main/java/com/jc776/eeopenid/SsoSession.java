package com.jc776.eeopenid;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

@SessionScoped
public class SsoSession implements Serializable {
	private static final long serialVersionUID = 1L;
	private String subject;
	private boolean tried;
	public void setSubject(final String subject) {
		this.subject = subject;
	}
	public void setTried(final boolean tried) {
		this.tried = tried;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public boolean getTried() {
		return tried;
	}

}
