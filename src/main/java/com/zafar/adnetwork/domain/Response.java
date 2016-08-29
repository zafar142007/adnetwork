package com.zafar.adnetwork.domain;

import java.util.ArrayList;
import java.util.List;

import com.zafar.adnetwork.util.Constants;

public class Response<A> {
	
	private String status=Constants.STATUS_OK;
	
	private List<Data<A>> ads=new ArrayList<>();
		
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	public Response(Data<A> t, String st){
		ads.add(t);
		status=st;
	}
	
	public Response(){
		
	}
	public Response(String status){
		this.status=status;
	}
	
	public Response(A imageUrL, A adText, A destinationUrl){
		ads.add(new Data<A>(imageUrL, adText, destinationUrl));
	}

	public void add(A adImageUrl, A adText, A destinationUrl) {
		ads.add(new Data<A>(adImageUrl, adText, destinationUrl));		
	}

}
class Data<A>{
	A imageUrl;
	
	A destinationURL;

	A adText;
	public A getAdText() {
		return adText;
	}

	public void setAdText(A adText) {
		this.adText = adText;
	}

	public A getDestinationURL() {
		return destinationURL;
	}

	public void setDestinationURL(A destinationURL) {
		this.destinationURL = destinationURL;
	}



	public A getData() {
		return imageUrl;
	}

	public void setData(A data) {
		this.imageUrl = data;
	}
	Data(){
		
	}
	Data(A url, A text, A dest){
		imageUrl=url;
		adText=text;
		destinationURL=dest;
	}
}

