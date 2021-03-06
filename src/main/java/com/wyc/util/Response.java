package com.wyc.util;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Response {
	private InputStream inputStream;
	private String charsetName = "UTF-8";
	final static Logger logger = LoggerFactory.getLogger(Response.class);
	public Response(InputStream inputStream){
		this.inputStream = inputStream;
	}
	public Response(InputStream inputStream , String charsetName){
		this.inputStream = inputStream;
		this.charsetName = charsetName;
	}
	public String read()throws Exception{
	        int avail = this.inputStream.available();
	        if(avail==0){
	            avail=2480;
	        }
		byte[] jsonBytes = new byte[avail];
		inputStream.read(jsonBytes);
		String message = new String(jsonBytes,charsetName);
		logger.debug("readMessageFromWx:"+message);
		this.inputStream.close();
		return message;
	}
	
	public <T>T readObject(Class<T> t)throws Exception{
	    ObjectMapper objectMapper = new ObjectMapper();
	    return objectMapper.readValue(inputStream, t);
	}
}
