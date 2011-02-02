/**
 * Copyright 2011 Singularity.Sg

This file is part of NLBroid.

NLBroid is free software: you can redistribute it and/or modify it under the terms of the 
GNU General Public License as published by the Free Software Foundation, either version 3 
of the License, or (at your option) any later version.
NLBroid is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with NLBroid.
If not, see http://www.gnu.org/licenses/.
 **/


package org.singularity;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.singularity.util.NLBUtil;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.os.Environment;
import android.util.Log;

public class NLBWebServicesUtil implements IWebServicesUtil {

	private static String NLB_URL = "https://nlb.projectnimbus.org/nlbodataservice.svc/";
	
	public Map<String, Object> query(String queryString) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpsURLConnection conn = null;
		URL url = null;
		
		try {
			
			url = new URL(new StringBuffer(NLB_URL).append(URLEncoder.encode(queryString, "UTF-8")).toString());
			
			conn = (HttpsURLConnection) url.openConnection();
			//conn.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml");
			//conn.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.2.13) Gecko/20101206 Ubuntu/10.10 (maverick) Firefox/3.6.13 GTB7.0");
			//conn.setRequestProperty("Accept-Language", "en-us,en");
			//conn.setRequestProperty("Accept-Charset", "ISO-8859-1,UTF-8");
			conn.setRequestProperty("Connection", "keep-alive");
			conn.setRequestProperty("Accept", "*/*");
			conn.setRequestProperty("AccountKey", "CbRVBj3dG034Yf4KzxaS6CdjZNT=");
			conn.setRequestProperty("UniqueUserID", "00000000000000000000000000000001");
			
			writeNLBResponseToFile(conn.getInputStream());
			
		} catch (FileNotFoundException fnfe) {
			StringBuffer debug = new StringBuffer();
			
			try {
				debug.append("Response Code: ").append(conn.getResponseCode()).append(", ResponseMessage: ").append(conn.getResponseMessage());
				Log.e("NLBroidView", debug.toString(), fnfe);
			}catch(IOException ioe) {
				Log.e("NLBroidView", "Unable to generate debug string", fnfe);
			}	
	    } catch (Exception e) {
	    	Log.e("NLBroidView", "There is a problem connecting to the NLB webservice", e);
	    } finally {
			if(conn != null) {
				conn.disconnect();
			}
		}
	    
	    return null;
		
	}
	
	
    private void parseNLBResponse(InputStream is) throws Exception {
    	
    	SAXParserFactory spf = SAXParserFactory.newInstance();
    	SAXParser parser = spf.newSAXParser();
    	XMLReader reader = parser.getXMLReader();
    	reader.setContentHandler(new NLBXMLHandler());
    	reader.parse(new InputSource(is));
    
    	Log.i("NLBroidView", "Parsing NLB Response");

    }
    
    private void writeNLBResponseToFile(InputStream is) throws Exception {
    	
    	BufferedInputStream bis = new BufferedInputStream(is, 8000);
    	FileWriter writer = null;
    	
    	Log.i("NLBRoidView","Starting write into the file...");
    	
    	if(NLBUtil.isSDCardPresent()) {
    		
    		Log.i("NLBRoidView","SDCard is found!");
    		
    		try {
	    		File root = Environment.getExternalStorageDirectory();
	    		Log.i("NLBRoidView", "SDCard directory : " + root.getAbsolutePath());
	    		File respFile = new File(root, "NLBroid.xml");
	    		Log.i("NLBRoidView", "File location : " + respFile.getAbsolutePath());
	    		writer = new FileWriter(respFile);
	    		
	    		int read;
	    		while((read = bis.read()) != -1) {
	    			writer.write(read);
	    		}
    		} finally {
    			if(writer != null) {
	    			writer.flush();
	    			writer.close();
	    		}
    		}
    		
    		Log.i("NLBDroid","Finished writing to sdcard");
    	} else {
    		Log.e("NLBDroid","Unable to find SDCard!!!");
    	}
    }
	
}
