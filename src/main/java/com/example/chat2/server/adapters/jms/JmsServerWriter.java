package com.example.chat2.server.adapters.jms;



import com.example.chat2.server.commons.ProxyFactory;
import com.example.chat2.server.ports.model.AbstractChatMessage;
import com.example.chat2.server.ports.out.ServerOut;

import javax.enterprise.context.ApplicationScoped;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Topic;
import javax.naming.NamingException;

@ApplicationScoped
public class JmsServerWriter implements ServerOut {
    private static final String JMS_CONNECTION_FACTORY_JNDI_NAME = "jms/RemoteConnectionFactory";
    private static final String MESSAGES_TOPIC_JNDI_NAME = "jms/topic/Messages";
    ConnectionFactory connectionFactory;
//    @Resource(mappedName = "java:jboss/exported/jms/topic/Messages")
    private Topic topic;
//
//    @Inject
//            @JMSConnectionFactory("jms/RemoteConnectionFactory")
//   JMSContext jmsContext;

    public JmsServerWriter() throws NamingException {
        var proxyFactory = new ProxyFactory();

        connectionFactory = proxyFactory.createProxy(JMS_CONNECTION_FACTORY_JNDI_NAME);

        topic = proxyFactory.createProxy(MESSAGES_TOPIC_JNDI_NAME);
    }


    @Override
    public void write(AbstractChatMessage message) {

            try(JMSContext jmsContext = connectionFactory.createContext()){
                jmsContext.createProducer().send(topic, message);
            }
            catch (Exception e){
                e.printStackTrace();
            }
  }

}
