package com.merc.core;

import java.io.File;
import java.io.IOException;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.ko.KoreanAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.FSDirectory;

public class DeleteDoc {

    private final String indexFilePath = "./indexs";  
    private IndexWriter writer = null;
    private File indexDirectory = null;
    
    
    private void createIndexWriter() {
        try {
        	
            indexDirectory = new File(indexFilePath);
            FSDirectory dir = FSDirectory.open(indexDirectory.toPath());
            Analyzer analyzer = new KoreanAnalyzer();
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            config.setOpenMode(OpenMode.CREATE_OR_APPEND);
            writer = new IndexWriter(dir, config);    
        } catch (Exception ex) {
            System.out.println("Sorry cannot get the index writer");
        }
    }
    
    
    private void closeIndexWriter() {
        try {
            writer.close();
        } catch (Exception e) {
            System.out.println("Indexer Cannot be closed");
        }
    }
    
    
    public boolean deleteDocument(String fullpath) throws IOException, ParseException {
    	boolean isdeleted = true;
    	createIndexWriter();
    	FSDirectory dir = FSDirectory.open(indexDirectory.toPath());
        IndexReader reader = DirectoryReader.open(dir); 
        IndexSearcher is = new IndexSearcher(reader); 
    	Query query = new TermQuery(new Term("fullpath", fullpath));
        TopDocs hits = is.search(query, 10); 
    	if(hits.totalHits != 0){
    		writer.deleteDocuments(new Term("fullpath", fullpath));
    		writer.commit(); 
    		isdeleted = true;
    	}
    	else {
    		isdeleted = false;
    	}
		reader.close(); 
		closeIndexWriter();
		
		return isdeleted;
		
    }  

    
}
