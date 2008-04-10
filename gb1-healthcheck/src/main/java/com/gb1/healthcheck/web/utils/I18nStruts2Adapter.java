package com.gb1.healthcheck.web.utils;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.displaytag.localization.I18nResourceProvider;
import org.displaytag.localization.LocaleResolver;

public class I18nStruts2Adapter implements LocaleResolver, I18nResourceProvider {
	public Locale resolveLocale(HttpServletRequest request) {
		return HttpRequestUtils.resolveRequestLocale();
	}

	public String getResource(String resourceKey, String defaultValue, Tag tag,
			PageContext pageContext) {
		return HttpRequestUtils.resolveResourceKey(resourceKey, defaultValue, pageContext);
	}
}
