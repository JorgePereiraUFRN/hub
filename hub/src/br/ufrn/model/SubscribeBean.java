/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.model;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class SubscribeBean {

    private String address;
    private String topic;
    private int port;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    
}
