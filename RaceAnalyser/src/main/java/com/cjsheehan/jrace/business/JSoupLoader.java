package com.cjsheehan.jrace.business;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

@Component
public interface JSoupLoader {
	Document load(String url) throws IOException;
}
