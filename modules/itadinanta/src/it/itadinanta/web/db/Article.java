package it.itadinanta.web.db;

import java.util.Date;

public interface Article {
	public int getAuthor();
	public String getBody();
	public Date getCreated();
	public int getId();
	public short getKind();
	public String getLanguage();
	public Date getLastModified();
	public String getTitle();
	public String getText();
}