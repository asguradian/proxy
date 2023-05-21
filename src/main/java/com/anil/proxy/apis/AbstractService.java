package com.anil.proxy.apis;


import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.ArrayList;


public abstract class AbstractService {

    private MultivaluedHashMap<String, Object> cloneRequestHeader(HttpHeaders incomingHeaders) {
        MultivaluedHashMap<String, Object> downStreamHeaders = new MultivaluedHashMap<>();
        incomingHeaders.getRequestHeaders().entrySet().forEach(headerEntires -> downStreamHeaders.put(headerEntires.getKey(), new ArrayList<Object>(headerEntires.getValue())));
        return downStreamHeaders;
    }
    private boolean doesRequestHasBody(HttpServletRequest request) {
        return request.getContentLength() > 0;
    }

    protected Response proxy(HttpServletRequest request,InputStream inputStream, HttpHeaders headers) throws Exception {
        MultivaluedHashMap<String, Object> downStreamHeaders = cloneRequestHeader(headers);
        Entity body = doesRequestHasBody(request)? Entity.entity(inputStream,request.getContentType()): null;
        String downStreamUrl = new StringBuilder(this.getDownStreamUrl()).append(request.getRequestURI()).toString();
        return exchange(request,downStreamHeaders,downStreamUrl,body);
    }

    protected Response exchange(HttpServletRequest request,MultivaluedHashMap<String, Object> headers, String url, Entity body) {
        Client client = getHttpClient();
        switch (request.getMethod()) {
            case "GET":
            return client
                    .target(url)
                    .request()
                    .headers(headers)
                    .get();
            case "POST":
                return client
                        .target(url).request()
                        .headers(headers)
                        .post(body);
        }
        return Response.ok().build();

    }
    protected abstract String getDownStreamUrl();
    protected abstract Client getHttpClient();

}
