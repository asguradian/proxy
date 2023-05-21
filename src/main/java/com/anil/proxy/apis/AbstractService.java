package com.anil.proxy.apis;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class AbstractService {



    protected HttpHeaders cloneRequestHeader(HttpServletRequest request) {
        return  Collections.list(request.getHeaderNames())
                .stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        h -> Collections.list(request.getHeaders(h)),
                        (oldValue, newValue) -> newValue,
                        HttpHeaders::new
                ));
    }
    protected <T> ResponseEntity<T> proxy(HttpServletRequest request, Object payload, HttpMethod httpMethod, Class<T> responseType) throws Exception {
        HttpHeaders preservedHeader = cloneRequestHeader(request);
        HttpEntity req = new HttpEntity(payload,preservedHeader);
        String downStreamUrl = new StringBuilder(this.getDownStreamUrl()).append(request.getRequestURI()).toString();
        return exchange(downStreamUrl,req, httpMethod,responseType);
    }

    protected  <T >ResponseEntity<T> exchange(String url, HttpEntity httpEntity, HttpMethod method, Class<T> responseType) {
        ResponseEntity<T> result = getTemplate().exchange(url,method,httpEntity,responseType);
        return result;
    }
    protected abstract String getDownStreamUrl();
    protected abstract RestTemplate getTemplate();

}
