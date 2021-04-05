package com.example.tools;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class HTTPLoaderImpl implements Closeable, HTTPLoader {
    private String url;
    private Map<String, String> params = new HashMap<>();
    private InputStream inputStream;

    public HTTPLoaderImpl(String url){
        this.url = url;
    }

    @Override
    public HTTPLoader addParam(String name, String value){
        params.put(name, value);
        return this;
    }

    @Override
    public InputStream HTTPGet() throws HTTPLoaderException {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) (new URL(url+"?"+buildParamString())).openConnection();
            httpURLConnection.setRequestMethod("GET");
            if (httpURLConnection.getResponseCode() <= 299) return inputStream = httpURLConnection.getInputStream();
            else throw new HTTPLoaderException("Request code: " + httpURLConnection.getResponseCode());
        } catch (IOException e){
            throw new HTTPLoaderException("Request fail", e);
        }
    }

    private String buildParamString(){
        StringBuilder stringBuilder = new StringBuilder();
        for(Map.Entry<String,String> entry : params.entrySet()){
            stringBuilder.append(entry.getKey());
            if(entry.getValue() != null)
                stringBuilder.append("=").append(entry.getValue());
            stringBuilder.append("&");
        }
        stringBuilder.setLength(stringBuilder.length()-1);
        return stringBuilder.toString();
    }

    @Override
    public void close(){
        try {
            inputStream.close();
        } catch (IOException ignored) {} // inputStream already closed
    }

    class HTTPLoaderException extends IOException{
        public HTTPLoaderException() {
            super();
        }

        public HTTPLoaderException(String message) {
            super(message);
        }

        public HTTPLoaderException(String message, Throwable cause) {
            super(message, cause);
        }

        public HTTPLoaderException(Throwable cause) {
            super(cause);
        }
    }
}
