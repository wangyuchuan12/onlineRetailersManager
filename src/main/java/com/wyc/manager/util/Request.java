package com.wyc.manager.util;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.util.Map;
import java.util.Map.Entry;

public class Request {
	private HttpURLConnection urlConnection;
	private String charsetName = "UTF-8";
	
	public Request(HttpURLConnection urlConnection){
		this.urlConnection = urlConnection;
		urlConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
		urlConnection.setRequestProperty("User-Agent","apidebuger/1.0 (Java; token:1c6bf5ce2facf248f3a14dfe27f4afe8; );");
		urlConnection.setDoOutput(true);
		urlConnection.setDoInput(true);
	}
	
	public Request(HttpURLConnection urlConnection , String charsetName){
		this.urlConnection = urlConnection;
		this.charsetName = charsetName;
		urlConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		urlConnection.setRequestProperty("User-Agent","apidebuger/1.0 (Java; token:1c6bf5ce2facf248f3a14dfe27f4afe8; );");
		urlConnection.setDoOutput(true);
		urlConnection.setDoInput(true);
	}
	
	public Response get(Map<String, String> params) throws IOException{
		urlConnection.setRequestMethod("GET");
		urlConnection.connect();
		if(params!=null&&params.size()>0){
			for(Entry<String, String> param:params.entrySet()){
				urlConnection.setRequestProperty(param.getKey(), param.getValue());
			}
		}
		urlConnection.setRequestProperty("token", "6822e9547ef834672085367c247fb5f4");
		urlConnection.setRequestProperty("User-Agent", "test");
		OutputStream outputStream = urlConnection.getOutputStream();
		outputStream.flush();
		outputStream.close();
		return new Response(urlConnection.getInputStream(),charsetName);
	}
	
	public Response post(String data) throws IOException{
		urlConnection.setRequestMethod("POST");
		urlConnection.connect();
		OutputStream outputStream = urlConnection.getOutputStream();
		outputStream.write(data.getBytes(charsetName));
		outputStream.flush();
		outputStream.close();
		return new Response(urlConnection.getInputStream(),charsetName);
		
	}
	
	public Response post(String data , String params)throws IOException{
	    urlConnection.setRequestMethod("POST");
            urlConnection.connect();
            OutputStream outputStream = urlConnection.getOutputStream();
            if(data!=null){
                outputStream.write(data.getBytes(charsetName));
                outputStream.flush();
            }
            if(params!=null){
                PrintWriter printWriter = new PrintWriter(outputStream);
                printWriter.print(params);
                printWriter.flush();
                
                printWriter.close();
            }
            outputStream.close();
            return new Response(urlConnection.getInputStream(),charsetName);
	}
}
