package com.example.demo.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;


import java.io.FileOutputStream;
import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.URL;



public class MSWord {
    private XWPFDocument document = new XWPFDocument();

    public static void main(String[] args) throws IOException {
        String testJSON = "{\"responses\":[{\"query\":\"tt0372784\",\"type\":\"get\",\"response\":{\"Title\":\"Batman Begins\",\"Year\":\"2005\",\"Rated\":\"PG-13\",\"Released\":\"15 Jun 2005\",\"Runtime\":\"140 min\",\"Genre\":\"Action, Adventure\",\"Director\":\"Christopher Nolan\",\"Writer\":\"Bob Kane (characters), David S. Goyer (story), Christopher Nolan (screenplay), David S. Goyer (screenplay)\",\"Actors\":\"Christian Bale, Michael Caine, Liam Neeson, Katie Holmes\",\"Plot\":\"After training with his mentor, Batman begins his fight to free crime-ridden Gotham City from corruption.\",\"Language\":\"English, Mandarin\",\"Country\":\"USA, UK\",\"Awards\":\"Nominated for 1 Oscar. Another 14 wins & 77 nominations.\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BOTY4YjI2N2MtYmFlMC00ZjcyLTg3YjEtMDQyM2ZjYzQ5YWFkXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SX300.jpg\",\"Ratings\":[{\"Source\":\"Internet Movie Database\",\"Value\":\"8.2/10\"},{\"Source\":\"Rotten Tomatoes\",\"Value\":\"84%\"},{\"Source\":\"Metacritic\",\"Value\":\"70/100\"}],\"Metascore\":\"70\",\"imdbRating\":\"8.2\",\"imdbVotes\":\"1,313,328\",\"imdbID\":\"tt0372784\",\"Type\":\"movie\",\"DVD\":\"09 Sep 2009\",\"BoxOffice\":\"$206,852,432\",\"Production\":\"Warner Brothers, Di Bonaventura Pictures\",\"Website\":\"N/A\",\"Response\":\"True\"}},{\"query\":\"Batman\",\"type\":\"find\",\"response\":{\"Search\":[{\"Title\":\"Batman Begins\",\"Year\":\"2005\",\"imdbID\":\"tt0372784\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BOTY4YjI2N2MtYmFlMC00ZjcyLTg3YjEtMDQyM2ZjYzQ5YWFkXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SX300.jpg\"},{\"Title\":\"Batman v Superman: Dawn of Justice\",\"Year\":\"2016\",\"imdbID\":\"tt2975590\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BYThjYzcyYzItNTVjNy00NDk0LTgwMWQtYjMwNmNlNWJhMzMyXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SX300.jpg\"},{\"Title\":\"Batman\",\"Year\":\"1989\",\"imdbID\":\"tt0096895\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BMTYwNjAyODIyMF5BMl5BanBnXkFtZTYwNDMwMDk2._V1_SX300.jpg\"},{\"Title\":\"Batman Returns\",\"Year\":\"1992\",\"imdbID\":\"tt0103776\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BOGZmYzVkMmItM2NiOS00MDI3LWI4ZWQtMTg0YWZkODRkMmViXkEyXkFqcGdeQXVyODY0NzcxNw@@._V1_SX300.jpg\"},{\"Title\":\"Batman Forever\",\"Year\":\"1995\",\"imdbID\":\"tt0112462\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BNDdjYmFiYWEtYzBhZS00YTZkLWFlODgtY2I5MDE0NzZmMDljXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_SX300.jpg\"},{\"Title\":\"Batman & Robin\",\"Year\":\"1997\",\"imdbID\":\"tt0118688\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BMGQ5YTM1NmMtYmIxYy00N2VmLWJhZTYtN2EwYTY3MWFhOTczXkEyXkFqcGdeQXVyNTA2NTI0MTY@._V1_SX300.jpg\"},{\"Title\":\"The Lego Batman Movie\",\"Year\":\"2017\",\"imdbID\":\"tt4116284\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BMTcyNTEyOTY0M15BMl5BanBnXkFtZTgwOTAyNzU3MDI@._V1_SX300.jpg\"},{\"Title\":\"Batman: The Animated Series\",\"Year\":\"1992–1995\",\"imdbID\":\"tt0103359\",\"Type\":\"series\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BOTM3MTRkZjQtYjBkMy00YWE1LTkxOTQtNDQyNGY0YjYzNzAzXkEyXkFqcGdeQXVyOTgwMzk1MTA@._V1_SX300.jpg\"},{\"Title\":\"Batman: Under the Red Hood\",\"Year\":\"2010\",\"imdbID\":\"tt1569923\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BNmY4ZDZjY2UtOWFiYy00MjhjLThmMjctOTQ2NjYxZGRjYmNlL2ltYWdlL2ltYWdlXkEyXkFqcGdeQXVyNTAyODkwOQ@@._V1_SX300.jpg\"},{\"Title\":\"Batman: The Dark Knight Returns, Part 1\",\"Year\":\"2012\",\"imdbID\":\"tt2313197\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BMzIxMDkxNDM2M15BMl5BanBnXkFtZTcwMDA5ODY1OQ@@._V1_SX300.jpg\"}],\"totalResults\":\"410\",\"Response\":\"True\"}},{\"query\":\"tt0096895\",\"type\":\"get\",\"response\":{\"Title\":\"Batman\",\"Year\":\"1989\",\"Rated\":\"PG-13\",\"Released\":\"23 Jun 1989\",\"Runtime\":\"126 min\",\"Genre\":\"Action, Adventure\",\"Director\":\"Tim Burton\",\"Writer\":\"Bob Kane (Batman characters), Sam Hamm (story), Sam Hamm (screenplay), Warren Skaaren (screenplay)\",\"Actors\":\"Michael Keaton, Jack Nicholson, Kim Basinger, Robert Wuhl\",\"Plot\":\"The Dark Knight of Gotham City begins his war on crime with his first major enemy being Jack Napier, a criminal who becomes the clownishly homicidal Joker.\",\"Language\":\"English, French, Spanish\",\"Country\":\"USA, UK\",\"Awards\":\"Won 1 Oscar. Another 7 wins & 26 nominations.\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BMTYwNjAyODIyMF5BMl5BanBnXkFtZTYwNDMwMDk2._V1_SX300.jpg\",\"Ratings\":[{\"Source\":\"Internet Movie Database\",\"Value\":\"7.5/10\"},{\"Source\":\"Rotten Tomatoes\",\"Value\":\"71%\"},{\"Source\":\"Metacritic\",\"Value\":\"69/100\"}],\"Metascore\":\"69\",\"imdbRating\":\"7.5\",\"imdbVotes\":\"339,039\",\"imdbID\":\"tt0096895\",\"Type\":\"movie\",\"DVD\":\"24 Jul 2014\",\"BoxOffice\":\"$251,348,343\",\"Production\":\"Warner Brothers, Guber-Peters Company, PolyGram Filmed Entertainment\",\"Website\":\"N/A\",\"Response\":\"True\"}},{\"query\":\"Superman\",\"type\":\"find\",\"response\":{\"Search\":[{\"Title\":\"Batman v Superman: Dawn of Justice\",\"Year\":\"2016\",\"imdbID\":\"tt2975590\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BYThjYzcyYzItNTVjNy00NDk0LTgwMWQtYjMwNmNlNWJhMzMyXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SX300.jpg\"},{\"Title\":\"Superman Returns\",\"Year\":\"2006\",\"imdbID\":\"tt0348150\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BNzY2ZDQ2MTctYzlhOC00MWJhLTgxMmItMDgzNDQwMDdhOWI2XkEyXkFqcGdeQXVyNjc1NTYyMjg@._V1_SX300.jpg\"},{\"Title\":\"Superman\",\"Year\":\"1978\",\"imdbID\":\"tt0078346\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BMzA0YWMwMTUtMTVhNC00NjRkLWE2ZTgtOWEzNjJhYzNiMTlkXkEyXkFqcGdeQXVyNjc1NTYyMjg@._V1_SX300.jpg\"},{\"Title\":\"Superman II\",\"Year\":\"1980\",\"imdbID\":\"tt0081573\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BODk2NjgzNTEtYzZhZC00ZTBkLTllMGQtMmMxMzU1NDRkM2RlXkEyXkFqcGdeQXVyNjc1NTYyMjg@._V1_SX300.jpg\"},{\"Title\":\"Superman III\",\"Year\":\"1983\",\"imdbID\":\"tt0086393\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BMzI3ZDllMTctNmI2Mi00OGQ4LTk2ZTQtYTJhMjA5ZGI2YmRkXkEyXkFqcGdeQXVyNjUwNzk3NDc@._V1_SX300.jpg\"},{\"Title\":\"Superman IV: The Quest for Peace\",\"Year\":\"1987\",\"imdbID\":\"tt0094074\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BMmIwZWY1YTYtNDlhOS00NDRmLWI4MzItNjk2NDc1N2NhYzNlXkEyXkFqcGdeQXVyNTUyMzE4Mzg@._V1_SX300.jpg\"},{\"Title\":\"Superman/Batman: Apocalypse\",\"Year\":\"2010\",\"imdbID\":\"tt1673430\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BMjk3ODhmNjgtZjllOC00ZWZjLTkwYzQtNzc1Y2ZhMjY2ODE0XkEyXkFqcGdeQXVyNTAyODkwOQ@@._V1_SX300.jpg\"},{\"Title\":\"Superman/Batman: Public Enemies\",\"Year\":\"2009\",\"imdbID\":\"tt1398941\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BZDc5NTFiMzgtZWJiOS00N2M1LWJmOGYtZmNjMzFhMzcxZjRiXkEyXkFqcGdeQXVyNTAyODkwOQ@@._V1_SX300.jpg\"},{\"Title\":\"Lois & Clark: The New Adventures of Superman\",\"Year\":\"1993–1997\",\"imdbID\":\"tt0106057\",\"Type\":\"series\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BZTU1ZGFjNzEtZWYzZC00ZmI0LTg2NmMtN2YyNTY4YzhlODIyXkEyXkFqcGdeQXVyMjExMjk0ODk@._V1_SX300.jpg\"},{\"Title\":\"Superman: Doomsday\",\"Year\":\"2007\",\"imdbID\":\"tt0934706\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BZjU4MzIyZWUtNWQ4Yy00YTU4LTk5NjUtNDBiNDkxZTVlZDgwXkEyXkFqcGdeQXVyNjExODE1MDc@._V1_SX300.jpg\"}],\"totalResults\":\"253\",\"Response\":\"True\"}}]}";

        byte[] t = new MSWord(testJSON).save();
        try (FileOutputStream fos = new FileOutputStream("test.docx")) {
            fos.write(t);
        }
    }

    public MSWord(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(json);
        for (JsonNode jsonNode1 : jsonNode.get("responses")) {
            addText("Results on "+ jsonNode1.get("type").toString()+" by query "+jsonNode1.get("query"));
            if (jsonNode1.get("type").toString().equals("\"get\"")) {
                printNode(jsonNode1.get("response"));
                breakPage();
            } else for (JsonNode jsonNode2 : jsonNode1.get("response").get("Search")) {
                printNode(jsonNode2);
            }
        }
    }

    public byte[] save(){
        ByteOutputStream outputStream = new ByteOutputStream();
        try {
            document.write(outputStream);
            outputStream.flush();
            outputStream.close();
            document.close();
        } catch (IOException ignored){}
        return outputStream.getBytes();
    }

    void addImage(String url){
        try {
            XWPFParagraph image = document.createParagraph();
            image.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun imageRun = image.createRun();
            imageRun.setTextPosition(20);
            HttpURLConnection con = (HttpURLConnection) (new URL(url)).openConnection();
            con.setRequestMethod("GET");
            if (con.getResponseCode() <= 299)
                imageRun.addPicture(con.getInputStream(), XWPFDocument.PICTURE_TYPE_JPEG, url, Units.toEMU(300), Units.toEMU(500));
        } catch (IOException | InvalidFormatException e){
            addText("Poster lost");
        }
    }

    void addTitle(String title){
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titleRun = paragraph.createRun();
        titleRun.setText(title);
        titleRun.setColor("009933");
        titleRun.setBold(true);
        titleRun.setFontFamily("Courier");
        titleRun.setFontSize(20);
    }

    void addText(String text){
        XWPFParagraph subTitle = document.createParagraph();
        subTitle.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun subTitleRun = subTitle.createRun();
        subTitleRun.setText(text);
        subTitleRun.setColor("00CC44");
        subTitleRun.setFontFamily("Courier");
        subTitleRun.setFontSize(14);
        subTitleRun.setTextPosition(20);
        subTitle.setSpacingAfter(0);
        subTitle.setSpacingAfterLines(0);
        subTitle.setSpacingBefore(0);
        subTitle.setSpacingBeforeLines(0);
    }

    void breakPage(){
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setPageBreak(true);
    }


    void printNode(JsonNode jsonNode){
        addImage(jsonNode.get("Poster").toString().replaceAll("\"",""));
        addTitle(jsonNode.get("Title").toString());
        jsonNode.fields().forEachRemaining(nodeEntry -> {
            if(!nodeEntry.getKey().equals("Title")&&!nodeEntry.getKey().equals("Poster")&&!nodeEntry.getKey().equals("Ratings")){
                addText(nodeEntry.getKey()+" - "+nodeEntry.getValue());
            }
        });
        if(jsonNode.has("Ratings"))
            jsonNode.get("Ratings").forEach(jsonNode2 -> addText("Rating " + jsonNode2.get("Source").toString() + " - "+ jsonNode2.get("Value").toString()));

    }
}
