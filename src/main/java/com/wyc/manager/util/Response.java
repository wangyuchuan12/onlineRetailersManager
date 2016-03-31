package com.wyc.manager.util;

import java.io.InputStream;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.BaseEncoding;

public class Response {
	private InputStream inputStream;
	private String charsetName = "UTF-8";
	public Response(InputStream inputStream){
		this.inputStream = inputStream;
	}
	public Response(InputStream inputStream , String charsetName){
		this.inputStream = inputStream;
		this.charsetName = charsetName;
	}
	public String read()throws Exception{
		byte[] jsonBytes = new byte[this.inputStream.available()];
		inputStream.read(jsonBytes);
		String message = new String(jsonBytes,charsetName);
		return message;
	}
	
	public String readDecoded()throws Exception{
	    String content = read();
	    ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(content);
	    byte[] encryptedByte = BaseEncoding.base64().decode(node.get("data").asText());
	    IvParameterSpec iv = new IvParameterSpec("nanosicsecretiv1".getBytes());
            byte[] secretKeyByte = BaseEncoding.base16().lowerCase().decode("f4d045bde437e03fe6ee604614336d06");
            Key key = new SecretKeySpec(secretKeyByte, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            

            byte[] decryptedData = cipher.doFinal(encryptedByte);

            String jsonData = new String(decryptedData, Charsets.UTF_8);
            return jsonData;
	    
	}
	
	public <T>T readObject(Class<T> t)throws Exception{
	    String message = read();
	    
	    ObjectMapper objectMapper = new ObjectMapper();
	    return objectMapper.readValue(message.getBytes(charsetName), t);
	}
}
