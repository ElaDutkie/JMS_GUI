package sample;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class SubscribentMessageListener implements MessageListener {
    private TextArea textArea;

    public SubscribentMessageListener(TextArea textArea) {
        this.textArea = textArea;
    }

    public void onMessage(Message message) {

        TextMessage textMessage = (TextMessage) message;


        try {

            String msg = textMessage.getText();
            Platform.runLater(() -> textArea.appendText(msg + "\n"));
            System.out.println(msg);
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
