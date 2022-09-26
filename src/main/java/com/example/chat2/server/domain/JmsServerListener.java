package com.example.chat2.server.domain;


import com.example.chat2.server.commons.ProxyFactory;
import lombok.SneakyThrows;
import lombok.extern.java.Log;


import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.naming.NamingException;


@Log
public class JmsServerListener implements Runnable {

    private static final String JMS_CONNECTION_FACTORY_JNDI_NAME = "jms/RemoteConnectionFactory";
    private static final String MESSAGES_QUEUE_JNDI_NAME = "jms/queue/Bus";
    ConnectionFactory connectionFactory;
    Queue queue;
    IncomingMessageService incomingMessageService;


    public JmsServerListener(IncomingMessageService incomingMessageService) throws NamingException {
        var proxyFactory = new ProxyFactory();
        connectionFactory = proxyFactory.createProxy(JMS_CONNECTION_FACTORY_JNDI_NAME);
        queue = proxyFactory.createProxy(MESSAGES_QUEUE_JNDI_NAME);
        this.incomingMessageService = incomingMessageService;

    }

    @SneakyThrows
    @Override
    public void run() {

            try (JMSContext jmsContext = connectionFactory.createContext();
                 JMSConsumer jmsConsumer = jmsContext.createConsumer(queue)) {
                log.info("-----starting jms listener----");
                jmsConsumer.setMessageListener(new ServerMessageListener(incomingMessageService));
                jmsContext.start();
                while(true){

                }
                //log.info("post jms consumer");

        }
    }
}
