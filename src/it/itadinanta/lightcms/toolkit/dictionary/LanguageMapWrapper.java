package it.itadinanta.lightcms.toolkit.dictionary;

import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateScalarModel;

public class LanguageMapWrapper implements TemplateHashModel {
	private Language language;

	private class StringWrapperModel implements TemplateScalarModel {
		private String value;

		public StringWrapperModel(String value) {
			this.value = value;
		}
		public String getAsString() throws TemplateModelException {
			return value;
		}
	}

	public LanguageMapWrapper(Language language) {
		this.language = language;
	}
	public TemplateModel get(String key) throws TemplateModelException {
		return new StringWrapperModel(language.translate(key));
	}
	public boolean isEmpty() throws TemplateModelException {
		return false;
	}
}