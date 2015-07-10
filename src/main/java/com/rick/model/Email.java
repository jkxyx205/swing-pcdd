package com.rick.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Email {
    public String[] getTo() {
		return to;
	}
	public void setTo(String ... to) {
		this.to = to;
	}
	public String[] getCc() {
		return cc;
	}
	public void setCc(String... cc) {
		this.cc = cc;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public List<File> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<File> attachments) {
		this.attachments = attachments;
	}

    private  String from;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    /**收件人**/
    private String[] to;    
     /**抄送给**/   
    private String[] cc;    
     /**邮件主题**/  
    private String subject;   
      /**邮件内容**/  
    private String text;    
     /**附件**/   
    private List<File> attachments = new ArrayList<File>();
}
