package it.itadinanta.web.commands;

import java.util.Date;

public class BlogCommand extends BaseCommand {
	private String content;
	private String subject;
	private Date date;
	private String keywords;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public void doBlog() throws Exception {
	}
	public void doEditPost() throws Exception {
//		useBody("/blog/edit");
	}
	public void doNewPost() throws Exception {
//		useBody("/blog/edit");
	}
	public void doArchive() throws Exception {
	}
}
