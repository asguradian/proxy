package com.anil.proxy.apis;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import javax.servlet.http.HttpServletRequest;

@RestController
@OpenAPIDefinition(info = @Info(title = "Test service API", version = "1.0", description = "Test service"))
public class ProxyService extends  AbstractService {
    @Autowired
    private RestTemplate restTemplate;
    private ObjectMapper mapper = new ObjectMapper();

    @Value("${enginxUrl}")
    private String downStreamUrl;


    @GetMapping(value = "/service/*")
    public ResponseEntity service(String payload,HttpServletRequest request) throws  Exception {
        return proxy(request, payload,HttpMethod.GET, String.class);

    }
    @RequestMapping(value = "/service/*", method = RequestMethod.POST)
    public ResponseEntity postService(@RequestBody String payload, HttpServletRequest request) throws Exception {
        return proxy(request, payload,HttpMethod.POST, String.class);
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
