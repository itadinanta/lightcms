package it.itadinanta.lightcms.toolkit;

import it.itadinanta.lightcms.toolkit.dictionary.Dictionary;
import it.itadinanta.lightcms.toolkit.sitemap.Sitemap;

public class Toolkit {
	public Sitemap sitemap;
	public Dictionary dictionary;
	public String module;

	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public Sitemap getSitemap() {
		return sitemap;
	}
	public void setSitemap(Sitemap sitemap) {
		this.sitemap = sitemap;
	}
	public Dictionary getDictionary() {
		return dictionary;
	}
	public void setDictionary(Dictionary dictionary) {
		this.dictionary = dictionary;
	}
}
