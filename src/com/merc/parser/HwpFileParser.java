package com.merc.parser;


  import rcc.h2tlib.parser.H2TParser; 
  import rcc.h2tlib.parser.HWPMeta; 


public class HwpFileParser { 
 
	public String hwpFileContentParser(String fileName) {
		HWPMeta meta = new HWPMeta(); 
		H2TParser parser = new H2TParser();	 
		StringBuilder sb = new StringBuilder();
		boolean flg = parser.GetText(fileName, meta, sb);
		return sb.toString();
        
	}
  
  } 
