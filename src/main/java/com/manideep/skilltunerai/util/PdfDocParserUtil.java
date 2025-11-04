package com.manideep.skilltunerai.util;

import java.io.InputStream;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.web.multipart.MultipartFile;

import com.manideep.skilltunerai.exception.FileLoadingException;

// Extracts texts from resume
public class PdfDocParserUtil {

    // Extracts from PDF
    public static String extractTextFromPdf(MultipartFile file) throws FileLoadingException {
        
        try (PDDocument pdDocument = Loader.loadPDF(file.getBytes())) {

            // Extracting text from a PDF
            PDFTextStripper textStripper = new PDFTextStripper();
            String text = textStripper.getText(pdDocument).trim();

            // If no text, then likely an scanned image PDF or empty PDF
            // Also, way too less text resume will not be accepted
            if (text.isEmpty() || text.length() < 50) {
                throw new FileLoadingException("The uploaded PDF seems empty, way less text or image-based!");
            }
            // Returns the extracted raw text;
            return text;
            
        } catch (Exception e) {
            throw new FileLoadingException("The PDF seems to be either corrupted, truncated, or encrypted!");
        }
        
    }
    
    // Extracts from DOCX
    public static String extractTextFromDocx(MultipartFile file) throws FileLoadingException {

        try (
            InputStream inputStream = file.getInputStream();
            XWPFDocument document = new XWPFDocument(inputStream);
            XWPFWordExtractor extractor = new XWPFWordExtractor(document);
        ) {
            return extractor.getText();
            
        } catch (Exception e) {
            throw new FileLoadingException("Can't extract text from this DOCX file! File seems corrupted");
        }
        
    }
    
    // Extracts from DOC
    public static String extractTextFromDoc(MultipartFile file) throws FileLoadingException {

        try (
            InputStream inputStream = file.getInputStream();
            HWPFDocument document = new HWPFDocument(inputStream);
            WordExtractor extractor = new WordExtractor(document);
        ) {
            return extractor.getText();
            
        } catch (Exception e) {
            throw new FileLoadingException("Can't extract text from this DOC file! File seems corrupted");
        }

    }

}
