package br.ufrn.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Topics {
	
	
	private String[] topics;

	public Topics() {
		
	}

	public String[] getTopics() {
		return topics;
	}

	public void setTopics(String[] topics) {
		this.topics = topics;
	}
	
	

}
