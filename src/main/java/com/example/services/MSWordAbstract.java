package com.example.services;

import com.example.tools.HTTPLoaderImpl;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.IOException;

public abstract class MSWordAbstract implements MSWord {
    protected static final String DARK_GREEN_COLOR = "009933";
    protected static final String DARK_GRAY_COLOR = "00CC44";
    protected static final String DEFAULT_FONTFAMILY = "Courier";

    private XWPFParagraph createDefaultParagraph(XWPFDocument document){
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun paragraphRun = paragraph.createRun();
        paragraphRun.setColor(DARK_GRAY_COLOR);
        paragraphRun.setFontFamily(DEFAULT_FONTFAMILY);
        paragraphRun.setFontSize(14);
        paragraphRun.setTextPosition(20);
        paragraph.setSpacingAfter(0);
        paragraph.setSpacingAfterLines(0);
        paragraph.setSpacingBefore(0);
        paragraph.setSpacingBeforeLines(0);
        return paragraph;
    }

    private void setParagraphOptions(XWPFParagraph paragraph, ParagraphAlignment align, String color, int fontSize, String text, boolean bold){
        paragraph.setAlignment(align);
        XWPFRun paragraphRun = paragraph.createRun();
        paragraphRun.setText(text);
        paragraphRun.setColor(color);
        paragraphRun.setBold(bold);
        paragraphRun.setFontSize(fontSize);
    }

    protected byte[] close(XWPFDocument document) throws IOException {
        ByteOutputStream outputStream = new ByteOutputStream();
        document.write(outputStream);
        outputStream.flush();
        outputStream.close();
        document.close();
        return outputStream.getBytes();
    }

    protected MSWord addImage(XWPFDocument document, String url){
        XWPFParagraph image = createDefaultParagraph(document);
        image.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun imageRun = image.createRun();
        try (HTTPLoaderImpl httpLoader = new HTTPLoaderImpl(url)){
            imageRun.addPicture(httpLoader.HTTPGet(), XWPFDocument.PICTURE_TYPE_JPEG, url, Units.toEMU(300), Units.toEMU(500));
        } catch (InvalidFormatException | IOException e) {
            imageRun.setText("Poster lost");
        }
        return this;
    }

    protected MSWord addTitle(XWPFDocument document, String title){
        XWPFParagraph paragraph = createDefaultParagraph(document);
        setParagraphOptions(paragraph, ParagraphAlignment.CENTER, DARK_GREEN_COLOR, 20, title, true);
        return this;
    }

    protected MSWord addText(XWPFDocument document, String text){
        XWPFParagraph subTitle = document.createParagraph();
        subTitle.createRun().setText(text);
        return this;
    }

    protected MSWord breakPage(XWPFDocument document){
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setPageBreak(true);
        return this;
    }
}
