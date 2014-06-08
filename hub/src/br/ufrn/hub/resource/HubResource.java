/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.hub.resource;

import br.ufrn.hub.processors.NotifySubscribes;
import br.ufrn.model.SubscribeBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("hub")
public class HubResource {

   

   
    public HubResource() {
    }

    private static  final Map<String, List<SubscribeBean>> subscribers = Collections.synchronizedMap(new HashMap<String, List<SubscribeBean>>());
    
    
   /* metodo usado para publicar atualizações, para isto p publish deve passar o
    identificador do topico e a atualização que será enviada para os
    subscriber
    */
    
    @PUT
    @Path("publish/{idTopic}")
    public void publish(@PathParam("idTopic") String idTopic, String valueTopic) {

        if(subscribers.get(idTopic) != null){
        	new NotifySubscribes(subscribers.get(idTopic).iterator(), valueTopic, idTopic).notifySubscribers();
        }
        
    }
    
   /* método usado para registrar um tópico no hub, para isto o publish deve 
    enviar o identificador do topico. Caso o topico não exista ele será´cadadtrado
    e retornara a string sucess, caso o topico ja esteja cadastrado ele retornará
    "the topic "+idTopic+" already exists"*/

    @PUT
    @Path("register")
    @Produces(MediaType.TEXT_PLAIN)
    public String register(String idTopic){
    	
    	if(subscribers.get(idTopic) == null){
    		subscribers.put(idTopic, new ArrayList<SubscribeBean>());
    		return "sucess";
    	}else{
    		return "the topic "+idTopic+" already exists";
    	}
    	
    }
    
   /* metodo usado pelo subscriber para registrar interesse em receber atualizações 
    acerca de um topico, para isso ele deve enviar um objeto do tipo subscriberbean
    que contem seu endereço ip, a porta na quala aguardara pelas atualizações
     e o topico de interesse*/
   
    @PUT
    @Path("subscribe")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String subscribe(SubscribeBean subscribe) throws InterruptedException {

        if(subscribers.get(subscribe.getTopic()) != null){
        	subscribers.get(subscribe.getTopic()).add(subscribe);        
        	return "success";
        }else{
            return "topic "+subscribe.getTopic()+" not exists";
        }         

    }

    /*
    metodo usado pelo subscribe para cancelar o interesse em receber atualizações a cerca 
    de um tópico, para isso ele deve enviar um objeto do tipo subscriberbean
    que contem seu endereço ip, a porta na quala aguardar pelas atualizações
    e o topico de interesse*/
    
    @DELETE
    @Path("/unsubscribe")
    @Consumes(MediaType.APPLICATION_JSON)
    public void unsubscribe(@PathParam("address") String address, @PathParam("idTopic") String topic, @PathParam("port") int port) {

        if(subscribers.get(topic)  != null){
            
            for(int i = 0; i  < subscribers.get(topic).size(); i++){
                
                SubscribeBean s = subscribers.get(topic).get(i);
               
                if(address.equals(s.getAddress()) 
                        && port == s.getPort()
                        && topic.equals(s.getTopic())){
                    
                    subscribers.get(topic).remove(i);
                   
                }
                
            }
        }

    }
}
