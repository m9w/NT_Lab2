package com.example.controller;

import com.example.pojo.PojoOmdb;
import com.example.services.ExecutorsService;
import com.example.services.MSWord;
import com.example.services.OmdbService;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.LinkedList;
import java.util.List;

import java.util.concurrent.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping
public class OmdbController {
    private final OmdbService omdb;
    private final MSWord msWord;
    private final ExecutorsService executors;

    public OmdbController(OmdbService omdb, MSWord msWord, ExecutorsService executors) {
        this.omdb = omdb;
        this.msWord = msWord;
        this.executors = executors;
    }

    @RequestMapping(path = "/omdb", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> omdb(@RequestBody Request request, @RequestParam(name = "word", required = false) String word, HttpServletRequest httpRequest ){
        if(request.get == null && request.find == null) return ResponseEntity.badRequest().build();

        List<CompletableFuture<PojoOmdb>> futures = new LinkedList<>();

        if(request.get != null) request.get.forEach(s -> futures.add(executors.async(() -> omdb.getById(s))));
        if(request.find != null) request.find.forEach(s -> futures.add(executors.async(() -> omdb.getByTitle(s))));

        List<PojoOmdb> omdbResult = joinAllFuture(futures);

        if(word!=null)
            return ResponseEntity
                    .ok()
                    .header("Content-Disposition", "attachment; filename=result.docx")
                    .contentType(MediaType.valueOf("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
                    .body(msWord.generate(omdbResult));

        return ResponseEntity.ok().contentType(httpRequest.getContentType()
                .contains("xml")? MediaType.APPLICATION_XML : MediaType.APPLICATION_JSON)
                .body(new ResponseAdapter().setUnit(omdbResult));
    }

    private static <T> List<T> joinAllFuture(List<CompletableFuture<T>> futures) {
        CompletableFuture[] cfs = futures.toArray(new CompletableFuture[futures.size()]);
        return  CompletableFuture.allOf(cfs).thenApply(ignored -> futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList())
        ).join();
    }

    @XmlRootElement(name = "films")
    private static class Request {
        @JsonValue @XmlElement(name = "get")  List<String> get;
        @JsonValue @XmlElement(name = "find") List<String> find;

        void setGet(List<String> get) {
            this.get = get;
        }

        void setFind(List<String> find) {
            this.find = find;
        }
    }

    @XmlRootElement(name = "responses")
    private static class ResponseAdapter {
        @XmlElement @JsonProperty("response") List<PojoOmdb> response;

        ResponseAdapter setUnit(List<PojoOmdb> response) {
            this.response = response;
            return this;
        }
    }
}
