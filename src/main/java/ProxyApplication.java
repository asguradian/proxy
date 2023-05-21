import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Arrays;

@ComponentScan(basePackages = "com.anil")
@SpringBootApplication
public class ProxyApplication  extends SpringBootServletInitializer {
	@Value("${key-store}")
	private Resource keyStore;

	@Value("${key-store-password}")
	private String keyStorePassword;

	@Value("${trust-store}")
	private Resource trustStore;

	@Value("${trust-store-password}")
	private String trustStorePassword;


	public static void main(String[] args) {

		new ProxyApplication().configure(new SpringApplicationBuilder(ProxyApplication.class)).run(args);
	}


	private SSLContext getSslContext () throws Exception{
			SSLContext sslContext = new SSLContextBuilder()
			.loadKeyMaterial(keyStore.getURL(), keyStorePassword.toCharArray(), keyStorePassword.toCharArray(), (aliases, socket) -> "client")
			.loadTrustMaterial(trustStore.getURL(), trustStorePassword.toCharArray())
			.build();
			return sslContext;
	}


	@Bean
	public Client getJaxRSClient() throws  Exception {
		Client client = ClientBuilder.newBuilder()
				 .sslContext(getSslContext()).build();
		return client;
	}
}


