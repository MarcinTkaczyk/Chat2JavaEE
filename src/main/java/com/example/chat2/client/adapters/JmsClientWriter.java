package com.example.chat2.client.adapters;


import com.example.chat2.client.ports.ClientOut;
import com.example.chat2.server.commons.ProxyFactory;
import com.example.chat2.server.ports.model.AbstractChatMessage;

import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.naming.NamingException;


public class JmsClientWriter implements ClientOut {
    private static final String JMS_CONNECTION_FACTORY_JNDI_NAME = "jms/RemoteConnectionFactory";
    private static final String MESSAGES_QUEUE_JNDI_NAME = "jms/queue/Bus";
    ConnectionFactory connectionFactory;
    Queue queue;

    public JmsClientWriter() throws NamingException {
        var proxyFactory = new ProxyFactory();

        connectionFactory = proxyFactory.createProxy(JMS_CONNECTION_FACTORY_JNDI_NAME);
        queue = proxyFactory.createProxy(MESSAGES_QUEUE_JNDI_NAME);
    }


    @Override
    public void write(AbstractChatMessage message) {

            try (
                JMSContext jmsContext = connectionFactory.createContext()) {
                jmsContext.createProducer().send(queue, message);
            }
        }

}
