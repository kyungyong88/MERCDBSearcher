package com.merc.core;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import org.apache.lucene.analysis.ko.KoreanAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class SearchDoc { 

	 private static String indexDir = "./indexs";

/*
    public ArrayList<String> searchcontent(String name, String keyword, String category, String year) throws IOException, ParseException, Exception { 

        Directory dir = FSDirectory.open(new File(indexDir).toPath()); 
        IndexReader reader = DirectoryReader.open(dir); 
        IndexSearcher is = new IndexSearcher(reader); 
        QueryParser parse = new QueryParser("contents", new KoreanAnalyzer()); 
        Query query = parse.parse(name); 
        long start = System.currentTimeMillis(); 
        TopDocs hits = is.search(query, 10); 
        long end = System.currentTimeMillis(); 
        
        System.err.println("Found "+ hits.totalHits + " document(s) ( in " + (end - start) + " milliseconds)  that mached query '" + name+ "':" ); 
        
        ArrayList<String> listA = new ArrayList<String>();
       
        for(ScoreDoc scoreDoc : hits.scoreDocs){ 
        	Document doc = is.doc(scoreDoc.doc); 
        	listA.add(doc.get("fullpath"));
        	System.out.println(doc.get("fullpath")); 
        } 
        
        
        reader.close(); 
        
        return listA;
        
    } */
    
    public ArrayList<String> booleancontent(String name, String keyword, String category, String year) throws IOException, ParseException, Exception { 

        Directory dir = FSDirectory.open(new File(indexDir).toPath()); 
        IndexReader reader = DirectoryReader.open(dir); 
        IndexSearcher is = new IndexSearcher(reader); 

        BooleanQuery fieldTextSubQuery = new BooleanQuery.Builder()
        	    .add(new QueryParser("contents", new KoreanAnalyzer()).parse(name), Occur.SHOULD)
        	    .add(new QueryParser("filename", new KoreanAnalyzer()).parse(name), Occur.SHOULD)
        	    .build();
        
        BooleanQuery finalQuery;
        
        if(category != "" && year != "")
        {
        finalQuery = new BooleanQuery.Builder()
        		.add(new TermQuery(new Term("category", category)), Occur.MUST)
        	    .add(new TermQuery(new Term("year", year)), Occur.MUST)
        	    .add(new FuzzyQuery(new Term("keyword", keyword), 0), Occur.SHOULD)
        	    .add(fieldTextSubQuery, Occur.MUST)
        	    .build();
        }
        else if(category != "" && year == "")
        {
        finalQuery = new BooleanQuery.Builder()
        		.add(new TermQuery(new Term("category", category)), Occur.MUST)
        	    .add(new FuzzyQuery(new Term("keyword", keyword), 0), Occur.SHOULD)
        	    .add(fieldTextSubQuery, Occur.MUST)
        	    .build();
        }
        
        else if(category == "" && year != "")
        {
        finalQuery = new BooleanQuery.Builder()
        	    .add(new TermQuery(new Term("year", year)), Occur.MUST)
        	    .add(new FuzzyQuery(new Term("keyword", keyword), 0), Occur.SHOULD)
        	    .add(fieldTextSubQuery, Occur.MUST)
        	    .build();
        }
        
        else
        {
        finalQuery = new BooleanQuery.Builder()
        	    .add(new FuzzyQuery(new Term("keyword", keyword), 0), Occur.SHOULD)
        	    .add(fieldTextSubQuery, Occur.MUST)
        	    .build();
        }
        
        long start = System.currentTimeMillis(); 
        TopDocs hits = is.search(finalQuery, 30); 
        long end = System.currentTimeMillis(); 
        
        System.err.println("Found "+ hits.totalHits + " document(s) ( in " + (end - start) + " milliseconds)  that mached query '" + name+ "':" ); 
        
        ArrayList<String> listA = new ArrayList<String>();
       
        for(ScoreDoc scoreDoc : hits.scoreDocs){ 
        	Document doc = is.doc(scoreDoc.doc); 
        	listA.add(doc.get("fullpath"));
        	System.out.println(doc.get("fullpath")); 
        } 
        
        
        reader.close(); 
        
        return listA;
        
    } 
    
   public boolean isEmptyDirectory() {
	   File file = new File(indexDir);
        if (!file.exists()) {
        	file.mkdir();
        }
		
	   if(file.list().length>0){
		   return false;
		   }
	   else
		   return true;
	}
   

   public boolean isDuplicated(String fullpath) {
	   File file = new File(fullpath);
	   
	   if(file.exists()){
		   return true;
		   }
	   else {
		   return false;
	   }
	}
   
}
