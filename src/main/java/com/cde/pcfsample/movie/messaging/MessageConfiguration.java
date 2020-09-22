//package com.cde.pcfsample.movie.messaging;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.amqp.core.*;
//
//@Configuration
//public class MessageConfiguration {
//
////    @Value("${remediation.templatebot.forward.engineering.input.queue.name}")
////    private String REMEDIATION_TEMPLATE_FE_INPUT_QUEUE;
////
////    @Value("${forward.engineering.exchange.name}")
////    private String EXCHANGE_NAME;
////
////    @Value("${remediation.templatebot.forward.engineering.input.routing.key}")
////    private String REMEDIATION_TEMPLATE_FE_INPUT_ROUTING_KEY;
//
//    @Bean
//    public Queue remediationTemplatesQueue(){
//        return new Queue("movies.app.queue");
//    }
//
//    @Bean
//    public Exchange forwardEngineeringExchange(){
//        return new DirectExchange("ForwardEngineering");
//    }
//
//    @Bean
//    public Binding remediationFeInputBinding(Queue remediationTemplatesQueue, DirectExchange directExchange){
//        return BindingBuilder.bind(remediationTemplatesQueue).to(directExchange).with("MOVIES_APP_ROUTING_KEY");
//    }
//
//    @Bean
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }
//
//}
