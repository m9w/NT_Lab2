package com.example.demo.controller;

import com.example.demo.services.MSWord;
import com.example.demo.services.OMDB;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.*;


@RestController
@RequestMapping
public class OMDBController {

    Logger logger = LoggerFactory.getLogger(OMDBController.class);

    private @Value("${app.threads}") int countThread;

    private final OMDB omdb;

    public OMDBController(OMDB omdb) {
        this.omdb = omdb;
    }

    @Cacheable(value = "RequestBody", key = "#request")
    @RequestMapping(path = "/omdb", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> omdb(@RequestBody Request request, @RequestParam(name = "word", required = false) String word, HttpServletRequest httpRequest ){
        boolean usageXML;
        if(httpRequest.getContentType().contains("json") || word != null) usageXML = false;
        else if(httpRequest.getContentType().contains("xml")) usageXML = true;
        else return ResponseEntity.badRequest().build();

        ExecutorService executorService = Executors.newFixedThreadPool(countThread);
        ResponseBuilder builder = usageXML ? new XMLResponseBuilder() : new JSONResponseBuilder();

        List<Callable<Void>> tasks = new ArrayList<>();
        if(request.get != null) tasks.add(() -> {request.get.forEach(s -> builder.append(s, "get", omdb.get(s, usageXML))); return null;});
        if(request.find != null) tasks.add(() -> {request.find.forEach(s -> builder.append(s, "find", omdb.find(s, usageXML))); return null;});

        try {
            executorService.invokeAll(tasks);
            executorService.shutdown();
        } catch (InterruptedException e) {
            logger.warn("Drop to wait threads", e);
            e.printStackTrace();
        }

        try {
            if(word!=null)
                return ResponseEntity
                        .ok()
                        .header("Content-Disposition", "attachment; filename=result.docx")
                        .contentType(MediaType.valueOf("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
                        .body(new MSWord(builder.toString()).save());

        } catch (JsonProcessingException e) {
            logger.warn("Json parsing error", e);
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().contentType(usageXML?MediaType.APPLICATION_XML:MediaType.APPLICATION_JSON).body(builder.toString());
    }

    @XmlRootElement(name = "films")
    private static class Request {
        @JsonValue @XmlElement(name = "get")
        private List<String> get;
        @JsonValue @XmlElement(name = "find")
        private List<String> find;

        public void setGet(List<String> get) {
            this.get = get;
        }

        public void setFind(List<String> find) {
            this.find = find;
        }
    }

    private abstract class ResponseBuilder {
        StringBuilder builder = new StringBuilder();
        ResponseBuilder(){
            builder.append(getPrefix());
        }

        abstract String getPrefix();
        abstract String getSuffix();
        abstract String getPattern();

        synchronized void append(String query, String type, String response){
            builder.append(String.format(getPattern(), query, type, response));
        }

        @Override
        public String toString() {
            builder.append(getSuffix());
            return builder.toString();
        }
    }

    private class JSONResponseBuilder extends ResponseBuilder {
        @Override
        String getPrefix() {
            return "{\"responses\":[";
        }

        @Override
        String getSuffix() {
            return "]}";
        }

        @Override
        String getPattern() {
            return "{\"query\":\"%s\",\"type\":\"%s\",\"response\":%s},";
        }

        @Override
        public String toString() {
            builder.setLength(builder.length()-1);
            return super.toString();
        }
    }

    private class XMLResponseBuilder extends ResponseBuilder {
        @Override
        String getPrefix() {
            return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><responses>";
        }

        @Override
        String getSuffix() {
            return "</responses>";
        }

        @Override
        String getPattern() {
            return "<response query=\"%s\" type=\'%s\'>%s</response>";
        }
    }

}
