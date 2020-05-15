package org.springframework.boot.autoconfigure.http;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.oracle.svm.core.annotate.Substitute;
import com.oracle.svm.core.annotate.TargetClass;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.internal.svm.MappingJackson2XmlHttpMessageConverterIsAround;
import org.springframework.internal.svm.OnlyPresent;
import org.springframework.internal.svm.RemoveXmlSupport;

@TargetClass(className = "org.springframework.boot.autoconfigure.http.HttpMessageConverters", onlyWith = { OnlyPresent.class, MappingJackson2XmlHttpMessageConverterIsAround.class, RemoveXmlSupport.class })
final class Target_HttpMessageConverters {

	@Substitute
	private void reorderXmlConvertersToEnd(List<HttpMessageConverter<?>> converters) {
		List<HttpMessageConverter<?>> xml = new ArrayList<>();
		for (Iterator<HttpMessageConverter<?>> iterator = converters.iterator(); iterator.hasNext();) {
			HttpMessageConverter<?> converter = iterator.next();
			if (converter instanceof MappingJackson2XmlHttpMessageConverter) {
				xml.add(converter);
				iterator.remove();
			}
		}
		converters.addAll(xml);
	}
}
