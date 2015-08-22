package it.itadinanta.web.db;

import java.util.Date;
import java.util.List;

public interface ArticleDao {
	public Article findArticle(int id);
	public List<Article> getHistoryMonth(Date firstDayOfMonth);
	public List<Article> getLatestArticles(Date limit);
	public Article getOldestArticle();
	public Article saveArticle(Article source);
	public boolean deleteArticle(int id);
}
