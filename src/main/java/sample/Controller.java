package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;

import javax.jms.*;
import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Controller {
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    @FXML
    private TextArea textArea;

    public void initialize() {

//        System.out.println("metoda działa");
        executorService.execute(() -> {
                    try {
                        BrokerService broker = BrokerFactory.createBroker(new URI(
                                "broker:(tcp://localhost:61618)"));
                        broker.start();
                        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                                "tcp://localhost:61618");
                        Connection connection = connectionFactory.createConnection();
                        Session session = connection.createSession(false,
                                Session.AUTO_ACKNOWLEDGE);
                        Topic topic = session.createTopic("EXAMPLE_TOPIC");

                        // Consumer1 subscribes to customerTopic
                        MessageConsumer consumer1 = session.createConsumer(topic);
                        consumer1.setMessageListener(new SubscribentMessageListener(textArea));

                        connection.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }


        );


    }

}