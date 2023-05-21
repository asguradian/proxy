package com.anil.proxy.apis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;

@Path("{seg:.*}")
@Component
public class StaticAssetService extends  AbstractService {
    @Autowired
    private Client client;
    @Value("${enginxUrl}")
    private String downStreamUrl;


    @GET
    public Response staticAssets(@Context  HttpServletRequest request, @Context HttpHeaders headers) throws  Exception {
          return proxy(request,new ByteArrayInputStream(new byte[0]),headers);
    }

    @Override
    protected String getDownStreamUrl() {
        return downStreamUrl;
    }

    @Override
    protected Client getHttpClient() {
        return this.client;
    }

}
