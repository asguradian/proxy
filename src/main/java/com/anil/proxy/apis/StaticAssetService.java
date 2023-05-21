package com.anil.proxy.apis;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@RestController
@OpenAPIDefinition(info = @Info(title = "Test service API", version = "1.0", description = "Test service"))
public class StaticAssetService extends  AbstractService {
    @Autowired
    private RestTemplate restTemplate;
    private ObjectMapper mapper = new ObjectMapper();

    @Value("${enginxUrl}")
    private String downStreamUrl;


    @GetMapping(value = "/*")
    public ResponseEntity staticAssets(HttpServletRequest request) throws  Exception {
        return proxy(request,null, HttpMethod.GET, String.class );

    }

    @Override
    protected String getDownStreamUrl() {
        return downStreamUrl;
    }

    @Override
    protected RestTemplate getTemplate() {
        return this.restTemplate;
    }

}
