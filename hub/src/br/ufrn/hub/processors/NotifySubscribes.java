package br.ufrn.hub.processors;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.ufrn.model.SubscribeBean;


public class NotifySubscribes {

    private Iterator<SubscribeBean> subscribes;
    private String notification;
    private String topic;

    /*classe respons�vel por enviar as atualiza��es para os susbcritos em algum t�pico
    , recebe um iterator contendo todos os subscribers q recebr�o as atualiza��es
    o valor da atualiza��o e o t�pico*/
    
    public NotifySubscribes(Iterator<SubscribeBean> subscribes, String notification, String topic) {
        this.subscribes = subscribes;
        this.notification = notification;
        this.topic = topic;
    }
    
   /* envia a atualiza��o para todos os subscritos em no t�pico */
    public void notifySubscribers() {

 
        while (subscribes.hasNext()) {


            SubscribeBean subscribe = subscribes.next();

            if (subscribe.getTopic().equals(topic)) {
                try {
                    Socket socket = new Socket(subscribe.getAddress(), subscribe.getPort());
                    ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                  
                    output.writeObject(notification);
                    
                } catch (UnknownHostException ex) {
                    Logger.getLogger(NotifySubscribes.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(NotifySubscribes.class.getName()).log(Level.SEVERE, null, ex);
                }


            }
        }
    }
}
