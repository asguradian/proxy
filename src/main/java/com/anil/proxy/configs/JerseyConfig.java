package com.anil.proxy.configs;

import com.anil.proxy.apis.ProxyService;
import com.anil.proxy.apis.StaticAssetService;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig()
    {
        registerClasses(StaticAssetService.class, ProxyService.class);
    }
}
