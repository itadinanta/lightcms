package it.itadinanta.web.db;

import java.util.Date;

public class ArticleQueryArgs {
	private int id;
	private int kind;
	private Date fromDate;
	private Date toDate;
	public ArticleQueryArgs(ArticleKind kind, Date fromDate, Date toDate) {
		this.kind = kind.ordinal();
		this.fromDate = fromDate;
		this.toDate = toDate;
	}
	public ArticleQueryArgs(ArticleKind kind) {
		this.kind = kind.ordinal();
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date from) {
		this.fromDate = from;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date to) {
		this.toDate = to;
	}
	public int getKind() {
		return kind;
	}
}
