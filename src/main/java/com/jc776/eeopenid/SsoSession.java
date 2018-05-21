package com.jc776.eeopenid;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

@SessionScoped
public class SsoSession implements Serializable {
	private static final long serialVersionUID = 1L;
	private String subject;
	public void setSubject(final String subject) {
		this.subject = subject;
	}
	public String getSubject() {
		return subject;
	}

}
