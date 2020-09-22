//package com.cde.pcfsample.movie.messaging;
//
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
//@Component
//public class MoviesAppListener {
//
//    @RabbitListener(queues="movies.app.queue")
//    public void messageListen(String jsonPayload) {
//        System.out.println("MoviesAppListener.messageListen() entered");
//    }
//}
