package com.example.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlRootElement
public class PojoOmdb {
    @XmlElement @JsonProperty("Title")     String title;
    @XmlElement @JsonProperty("Year")      String year;
    @XmlElement @JsonProperty("Rated")     String rated;
    @XmlElement @JsonProperty("Released")  String released;
    @XmlElement @JsonProperty("Runtime")   String runtime;
    @XmlElement @JsonProperty("Genre")     String genre;
    @XmlElement @JsonProperty("Director")  String director;
    @XmlElement @JsonProperty("Writer")    String writer;
    @XmlElement @JsonProperty("Actors")    String actors;
    @XmlElement @JsonProperty("Plot")      String plot;
    @XmlElement @JsonProperty("Language")  String language;
    @XmlElement @JsonProperty("Country")   String country;
    @XmlElement @JsonProperty("Awards")    String awards;
    @XmlElement @JsonProperty("Poster")    String poster;
    @XmlElement @JsonProperty("Metascore") String metascore;
    @XmlElement @JsonProperty("imdbRating")String imdbRating;
    @XmlElement @JsonProperty("imdbVotes") String imdbVotes;
    @XmlElement @JsonProperty("imdbID")    String imdbID;
    @XmlElement @JsonProperty("Type")      String type;
    @XmlElement @JsonProperty("DVD")       String DVD;
    @XmlElement @JsonProperty("BoxOffice") String boxOffice;
    @XmlElement @JsonProperty("Production")String production;
    @XmlElement @JsonProperty("Website")   String website;
    @XmlElement @JsonProperty("Ratings")   List<Raiting> ratings;
    @XmlElement @JsonProperty("Search")    List<PojoOmdb> movie;
    @XmlElement @JsonProperty("totalResults") String totalResults;
    @XmlElement @JsonProperty("Response")  String response;
    @XmlElement @JsonProperty("Error")     String error;
    @XmlElement @JsonProperty("Query")     String query;

    SearchType searchType;

    public PojoOmdb setQuery(String query) {
        this.query = query;
        return this;
    }

    @JsonIgnore
    public PojoOmdb setSearchType(SearchType searchType) {
        this.searchType = searchType;
        return this;
    }

    @XmlRootElement
    public static class Raiting {
        @XmlElement @JsonProperty("Source") String Source;
        @XmlElement @JsonProperty("Value")  String Value;

        public String getSource() {
            return Source;
        }

        public String getValue() {
            return Value;
        }
    }

    public enum SearchType{
        SEARCH_BY_ID_TYPE("i"),
        SEARCH_BY_TITLE_TYPE("s");

        public final String flag;

        SearchType(String flag) {
            this.flag = flag;
        }
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getRated() {
        return rated;
    }

    public String getReleased() {
        return released;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getGenre() {
        return genre;
    }

    public String getDirector() {
        return director;
    }

    public String getWriter() {
        return writer;
    }

    public String getActors() {
        return actors;
    }

    public String getPlot() {
        return plot;
    }

    public String getLanguage() {
        return language;
    }

    public String getCountry() {
        return country;
    }

    public String getAwards() {
        return awards;
    }

    public String getPoster() {
        return poster;
    }

    public String getMetascore() {
        return metascore;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public String getImdbVotes() {
        return imdbVotes;
    }

    public String getImdbID() {
        return imdbID;
    }

    public String getType() {
        return type;
    }

    public String getDVD() {
        return DVD;
    }

    public String getBoxOffice() {
        return boxOffice;
    }

    public String getProduction() {
        return production;
    }

    public String getWebsite() {
        return website;
    }

    public List<Raiting> getRatings() {
        return ratings;
    }

    public List<PojoOmdb> getMovie() {
        return movie;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public String getResponse() {
        return response;
    }

    public String getError() {
        return error;
    }

    public String getQuery() {
        return query;
    }

    public SearchType getSearchType() {
        return searchType;
    }
}
