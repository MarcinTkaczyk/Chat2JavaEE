package com.example.chat2.client.adapters;


import com.example.chat2.client.ports.ClientIn;
import com.example.chat2.server.commons.ProxyFactory;

import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Topic;
import javax.naming.NamingException;

public class JmsListener implements Runnable{

    private static final String JMS_CONNECTION_FACTORY_JNDI_NAME = "jms/RemoteConnectionFactory";
    private static final String MESSAGES_TOPIC_JNDI_NAME = "jms/topic/Messages";
    ConnectionFactory connectionFactory;
    Topic topic;
    ClientIn clientIn;

    public JmsListener(ClientIn clientIn) throws NamingException {
        var proxyFactory = new ProxyFactory();
        connectionFactory = proxyFactory.createProxy(JMS_CONNECTION_FACTORY_JNDI_NAME);
        topic = proxyFactory.createProxy(MESSAGES_TOPIC_JNDI_NAME);
        this.clientIn = clientIn;
    }

    @Override
    public void run() {
        try (JMSContext jmsContext = connectionFactory.createContext();
             JMSConsumer jmsConsumer = jmsContext.createConsumer(topic)) {
            jmsConsumer.setMessageListener(new ClientMessageListener(clientIn));
            while(true){

            }
        }
    }
}
