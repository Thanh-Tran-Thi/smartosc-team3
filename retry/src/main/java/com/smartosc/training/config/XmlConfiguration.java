package com.smartosc.training.config;

import com.smartosc.training.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.server.endpoint.interceptor.PayloadLoggingInterceptor;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.soap.security.wss4j2.callback.SpringSecurityPasswordValidationCallbackHandler;
import org.springframework.ws.soap.server.endpoint.interceptor.PayloadValidatingInterceptor;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import java.util.List;

@Configuration
@EnableWs
public class XmlConfiguration extends WsConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/ws/*");
    }

    @Bean(name = "calculation")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema calculationSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("CalculationPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://example.com");
        wsdl11Definition.setSchema(calculationSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema calculationSchema() {
        return new SimpleXsdSchema(new ClassPathResource("calculate.xsd"));
    }

    @Bean
    PayloadLoggingInterceptor payloadLoggingInterceptor() {
        return new PayloadLoggingInterceptor();
    }

    @Bean
    PayloadValidatingInterceptor payloadValidatingInterceptor() {
        PayloadValidatingInterceptor validatingInterceptor = new PayloadValidatingInterceptor();
        validatingInterceptor.setValidateRequest(true);
        validatingInterceptor.setValidateResponse(true);
        validatingInterceptor.setXsdSchema(calculationSchema());
        return validatingInterceptor;
    }

    @Override
    public void addInterceptors(List<EndpointInterceptor> interceptors) {
        interceptors.add(payloadLoggingInterceptor());
        interceptors.add(payloadValidatingInterceptor());
        //interceptors.add(xwsSecurityInterceptor());
        interceptors.add(securityInterceptor());
        super.addInterceptors(interceptors);
    }

    //region XWSS
    /*@Bean
    XwsSecurityInterceptor xwsSecurityInterceptor() {
        XwsSecurityInterceptor securityInterceptor = new XwsSecurityInterceptor();
        securityInterceptor.setCallbackHandler(callbackHandler());
        securityInterceptor.setPolicyConfiguration(new ClassPathResource("securityPolicy.xml"));
        return securityInterceptor;
    }

    @Bean
    SimplePasswordValidationCallbackHandler callbackHandler() {
        SimplePasswordValidationCallbackHandler callbackHandler = new SimplePasswordValidationCallbackHandler();
        callbackHandler.setUsersMap(Collections.singletonMap("admin", "1234"));
        return callbackHandler;
    }*/
    //endregion

    //region WSS4J
    /*@Bean
    Wss4jSecurityInterceptor securityInterceptor() {
        Wss4jSecurityInterceptor securityInterceptor = new Wss4jSecurityInterceptor();
        securityInterceptor.setValidationCallbackHandler(callbackHandler());
        securityInterceptor.setValidationActions("UsernameToken");
        return securityInterceptor;
    }

    @Bean
    SpringSecurityPasswordValidationCallbackHandler callbackHandler() {
        SpringSecurityPasswordValidationCallbackHandler callbackHandler = new SpringSecurityPasswordValidationCallbackHandler();
        callbackHandler.setUserDetailsService(userDetailsService);
        return callbackHandler;
    }*/
    //endregion

    //region Digested Password XWSS
    /*@Bean
    XwsSecurityInterceptor xwsSecurityInterceptor() {
        XwsSecurityInterceptor securityInterceptor = new XwsSecurityInterceptor();
        securityInterceptor.setCallbackHandler(callbackHandler());
        securityInterceptor.setPolicyConfiguration(new ClassPathResource("securityDigestPolicy.xml"));
        return securityInterceptor;
    }

    @Bean
    SpringDigestPasswordValidationCallbackHandler callbackHandler(){
        SpringDigestPasswordValidationCallbackHandler callbackHandler = new SpringDigestPasswordValidationCallbackHandler();
        callbackHandler.setUserDetailsService(userDetailsService);
        return callbackHandler;
    }*/
    //endregion

    //region Digested Password WSS4J
    @Bean
    Wss4jSecurityInterceptor securityInterceptor() {
        Wss4jSecurityInterceptor securityInterceptor = new Wss4jSecurityInterceptor();
        securityInterceptor.setValidationCallbackHandler(callbackHandler());
        securityInterceptor.setValidationActions("UsernameToken");
        return securityInterceptor;
    }
    @Bean
    SpringSecurityPasswordValidationCallbackHandler callbackHandler() {
        SpringSecurityPasswordValidationCallbackHandler callbackHandler = new SpringSecurityPasswordValidationCallbackHandler();
        callbackHandler.setUserDetailsService(userDetailsService);
        return callbackHandler;
    }
    //endregion
}
