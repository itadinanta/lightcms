package it.itadinanta.web.db;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class ArticleDaoSqlMapClient extends SqlMapClientDaoSupport implements ArticleDao {
	private ArticleKind kind;
	public ArticleDaoSqlMapClient(ArticleKind kind) {
		this.kind = kind;
	}
	public Article findArticle(int id) {
		return (Article) getSqlMapClientTemplate().queryForObject("Article.getById", id);
	}
	@SuppressWarnings("unchecked")
	public List<Article> getHistoryMonth(Date firstOfMonth) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(firstOfMonth);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.MILLISECOND, -1);
		ArticleQueryArgs args = new ArticleQueryArgs(this.kind, firstOfMonth, calendar.getTime());
		return (List<Article>) getSqlMapClientTemplate().queryForList("Article.getByDateRange", args);
	}
	@SuppressWarnings("unchecked")
	public List<Article> getLatestArticles(Date limit) {
		ArticleQueryArgs args = new ArticleQueryArgs(this.kind, limit, new Date());
		return (List<Article>) getSqlMapClientTemplate().queryForList("Article.getByDateRange", args);
	}
	public Article getOldestArticle() {
		return (Article) getSqlMapClientTemplate().queryForObject("Article.getOldest", null);
	}
	public Article saveArticle(Article source) {
		if (source.getId() == 0)
			return (Article) getSqlMapClientTemplate().insert("Article.insert", source);
		else {
			getSqlMapClientTemplate().update("Article.update", source);
			return source;
		}
	}
	public boolean deleteArticle(int id) {
		return getSqlMapClientTemplate().update("Article.delete", id) > 0;
	}
}
