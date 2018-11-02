package com.merc.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
 
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.ko.KoreanAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.FSDirectory;

import com.merc.parser.DocFileParser;
import com.merc.parser.DocxFileParser;
import com.merc.parser.HwpFileParser;
import com.merc.parser.PdfFileParser;
 

public class CreateDoc {
 
    private final String indexFilePath = "./indexs";  
    private IndexWriter writer = null;
    private File indexDirectory = null;
    private String fileContent, keyword, category;
    int year;
 
    
    public CreateDoc(String keyword, String category, int year, String fullpath) throws FileNotFoundException, CorruptIndexException, IOException {
    	
    	this.year = year;
    	this.keyword = keyword;
    	this.category = category;
    	
        try {
            createIndexWriter(); // 인덱스 객체 오픈
            checkFileValidity(new File(fullpath)); // 문서 파싱 및 인덱스에 적재
            closeIndexWriter(); // 인덱스 객체 닫음
        } catch (Exception e) {
            System.out.println("Sorry task cannot be completed");
        }
    }
 
    private void createIndexWriter() {
        try {
            indexDirectory = new File(indexFilePath);
            if (!indexDirectory.exists()) {
                indexDirectory.mkdir();
            }
            
            FSDirectory dir = FSDirectory.open(indexDirectory.toPath());
            Analyzer analyzer = new KoreanAnalyzer();
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            config.setOpenMode(OpenMode.CREATE_OR_APPEND);
            writer = new IndexWriter(dir, config);    
        } catch (Exception ex) {
            System.out.println("Sorry cannot get the index writer");
        }
    }
 

    private void checkFileValidity(File file) throws CorruptIndexException, IOException {

    	if(!file.isHidden() && file.exists() && file.canRead() && file.length() > 0.0 && file.isFile()){
    		if(file.getName().endsWith(".txt") || file.getName().endsWith(".htm") || file.getName().endsWith(".html") || file.getName().endsWith(".xml")){
    			indexTextFiles(file); 
    			System.out.println("Indexed Txt : " + file.getAbsolutePath());
    			}
    		else if(file.getName().endsWith(".doc") || file.getName().endsWith(".ppt") || file.getName().endsWith(".xls") ||
    				file.getName().endsWith(".docx") || file.getName().endsWith(".pptx") || file.getName().endsWith(".xlsx") ||
    				file.getName().endsWith(".pdf") || file.getName().endsWith(".hwp") ){
    			StartIndex(file); 
    			}
    		}
      }
     
    
    // pdf, doc, docx, xls, xlsx, ppt, pptx 파일의 내용을 파싱및 추출
    private void StartIndex(File file) throws FileNotFoundException, CorruptIndexException, IOException {
    	
    	fileContent = null;
         
        try {
            Document doc = new Document();
            if (file.getName().endsWith(".pdf")) {
                //call the pdf file parser and get the content of pdf file in txt format
                fileContent = new PdfFileParser().PdfFileContentParser(file.getAbsolutePath());
            }
             
            // 확장자 doc, ppt, xls 에 대한 텍스트 추출 처리
            if (file.getName().endsWith(".doc") || file.getName().endsWith(".ppt") || file.getName().endsWith(".xls")) {
                //call the doc file parser and get the content of doc file in txt format
                fileContent = new DocFileParser().DocFileContentParser(file.getAbsolutePath());
            }
             
            // 확장자 docx, pptx, xlsx 에 대한 텍스트 추출 처리
            if (file.getName().endsWith(".docx") || file.getName().endsWith(".pptx") || file.getName().endsWith(".xlsx")) {
                fileContent = new DocxFileParser().docxFileContentParser(file.getAbsolutePath());
            }            
            
            if (file.getName().endsWith(".hwp")) {
                fileContent = new HwpFileParser().hwpFileContentParser(file.getAbsolutePath());
            }            
             

            doc.add(new TextField("contents", fileContent, Field.Store.YES));
            doc.add(new TextField("filename", removeExtention(file.getName()), Field.Store.YES));
            doc.add(new TextField("keyword", keyword, Field.Store.YES));
            doc.add(new TextField("category", category, Field.Store.YES));
            doc.add(new TextField("year", Integer.toString(year), Field.Store.YES));
            doc.add(new StringField("fullpath", file.getCanonicalPath(), Field.Store.YES)); 
             
            if (doc != null) {
                writer.addDocument(doc);
            }
            
            System.out.println("Indexed Doc : " + file.getAbsolutePath());
        }
        catch (Exception e) {
            System.out.println("error in indexing" + (file.getAbsolutePath()));
        }
    }
 
    
    
    private void indexTextFiles(File file) throws CorruptIndexException, IOException {
        Document doc = new Document();
        fileContent = null;

        
        BufferedReader buff = new BufferedReader(new InputStreamReader(new FileInputStream(file), "EUC-KR"));
         
        while(true)
        {
        	String s = buff.readLine();
        	if(s == null) break;
        	fileContent += s;
        }
        
        buff.close();
         
        
        if(!file.getName().endsWith(".txt")){ 
            fileContent = removeTag(fileContent);
        }
       
        
        doc.add(new TextField("contents", fileContent, Field.Store.YES));
        doc.add(new TextField("filename", removeExtention(file.getName()), Field.Store.YES));
        doc.add(new StringField("fullpath", file.getCanonicalPath(), Field.Store.YES)); 
        doc.add(new TextField("keyword", keyword, Field.Store.YES));
        doc.add(new TextField("category", category, Field.Store.YES));
        doc.add(new StringField("year", Integer.toString(year), Field.Store.YES));
        
        if (doc != null) {
            writer.addDocument(doc);
        }
    }
 

    
    private void closeIndexWriter() {
        try {
            writer.close();
        } catch (Exception e) {
            System.out.println("Indexer Cannot be closed");
        }
    }
 
    


    
    private static String removeTag(String s) {
        return s.replaceAll("<[^>]*>", " "); // <> 태그형식을 모두 공백으로 대체
    }
    
    private static String removeExtention(String s) {
    	
        return  s.substring(0, s.lastIndexOf('.')); // <> 태그형식을 모두 공백으로 대체
    }
 
}
