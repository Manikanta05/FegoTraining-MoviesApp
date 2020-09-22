package com.cde.pcfsample.movie.loadbalancing;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
    public class RestTemplateComponentFix{



 @LoadBalanced
 public RestTemplate getRestTemplate() {
       // TODO set up your restTemplate
      RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory( new HttpComponentsClientHttpRequestFactory() );
        return restTemplate ;
  }

}