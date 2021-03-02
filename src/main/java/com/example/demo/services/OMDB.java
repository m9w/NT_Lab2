package com.example.demo.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

@Service
public class OMDB {
    private @Value("${app.apiKey}") String apiKey;

    private String query(String key, String val, boolean isXML){
        try {
            HttpURLConnection con = (HttpURLConnection) (new URL("http://omdbapi.com/?apikey="+apiKey+"&"+key+"="+val+(isXML?"&r=xml":"")).openConnection());
            con.setRequestMethod("GET");

            if (con.getResponseCode() <= 299)
                return cutXMLtags(new BufferedReader(new InputStreamReader(con.getInputStream())).lines().collect(Collectors.joining()), isXML);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return isXML?"<error>Internal error</error>":"{\"Error\":\"Internal error\"}";
    }

    public String get(String id, boolean isXML){
        return query("i", id, isXML);
    }

    public String find(String name, boolean isXML){
        return query("s", name, isXML);
    }

    private String cutXMLtags(String xml, boolean isXML){
        if(!isXML) return xml;
        return xml.replaceAll(".*<root[^<]*>|</root>$","");
    }
}
