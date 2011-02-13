/**
 * Copyleft 2011 Singularity.Sg

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
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import junit.framework.Assert;

import org.singularity.bean.NLBBean;
import org.singularity.util.NLBUtil;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.os.Environment;
import android.util.Log;

public class NLBWebServicesUtil implements IWebServicesUtil {
	
	private static final String NOT_SELECTED = "Not Selected";
	private static String NLB_URL = "https://nlb.projectnimbus.org/nlbodataservice.svc/";
	private HttpsURLConnection conn = null;
	
	public List<NLBBean> query(String option, String filter, String keyword) {
		
		URL url = null;
		List<NLBBean> results = null;
		
		try {
			
			if(keyword != null && !NOT_SELECTED.equals(keyword) && filter != null && !NOT_SELECTED.equals(filter) ) {
				String urlEncoded = new StringBuffer("?$filter=indexof(").append(getFilterString(filter)).append(",'").append(keyword).append("')").append("%20ne%20-1").toString();
				url = new URL(new StringBuffer(NLB_URL).append(getQueryString(option)).append(urlEncoded).toString());
			} else {
				url = new URL(new StringBuffer(NLB_URL).append(getQueryString(option)).toString());
			}
			
			Log.i("NLBWebServicesUtil", new StringBuffer("URL: ").append(url.toString()).toString());
			
			conn = (HttpsURLConnection) url.openConnection();
			//conn.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml");
			//conn.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.2.13) Gecko/20101206 Ubuntu/10.10 (maverick) Firefox/3.6.13 GTB7.0");
			//conn.setRequestProperty("Accept-Language", "en-us,en");
			//conn.setRequestProperty("Accept-Charset", "ISO-8859-1,UTF-8");
			conn.setRequestProperty("Connection", "keep-alive");
			conn.setRequestProperty("Accept", "application/atom+xml,application/xml");
			conn.setRequestProperty("AccountKey", "CbRVBj3dG034Yf4KzxaS6CdjZNT=");
			conn.setRequestProperty("UniqueUserID", "00000000000000000000000000000001");
			
			File root = Environment.getExternalStorageDirectory();
    		Log.i("NLBWebServicesUtil", "SDCard directory : " + root.getAbsolutePath());
    		File respFile = new File(root, "NLBroid.xml");
    		Log.i("NLBWebServicesUtil", "File location : " + respFile.getAbsolutePath());
			
    		//Create a file in SDCard
			writeNLBResponseToFile(respFile);
    		
			results = parseNLBResponse(respFile);
			
    		Log.i("NLBWebServicesUtil", "Finished parsing XML File");
    		
		} catch (FileNotFoundException fnfe) {
			StringBuffer debug = new StringBuffer();
			
			try {
				debug.append("Response Code: ").append(conn.getResponseCode()).append(", ResponseMessage: ").append(conn.getResponseMessage());
				Log.e("NLBWebServicesUtil", debug.toString(), fnfe);
			}catch(IOException ioe) {
				Log.e("NLBWebServicesUtil", "Unable to generate debug string", fnfe);
			}
			
	    } catch (Exception e) {
	    	Log.e("NLBWebServicesUtil", "There is a problem connecting to the NLB webservice", e);
	    } finally {
			if(conn != null) {
				conn.disconnect();
			}
		}
	    
	    return results;
		
	}
	
	private String getQueryString(String selection) {
		
		Assert.assertTrue(selection != null);
		
		String returnVal = null;
		
		if("Catalog".equals(selection)) {
			returnVal = "CatalogSet";
		} else  
		if("Latest Article".equals(selection)) {
			returnVal = "LatestArticleSet";
		} else 
		if("New Arrival".equals(selection)) {
			returnVal = "NewArrivalSet";
		} else 
		if("Library".equals(selection)) {
			returnVal = "LibrarySet";
		} else 
		if("Event".equals(selection)) {
			returnVal = "EventSet";
		}
		
		Assert.assertTrue(returnVal != null);
		
		return returnVal;
		
	}
	
	
	private String getFilterString(String filter) {
		
		Assert.assertTrue(filter != null);
		
		String returnVal = null;
		
		if("Author".equals(filter)) {
			returnVal = "Author";
		} else
		if("Title".equals(filter)) {
			returnVal = "TitleName";
		} else 
		if("Description".equals(filter)) {
			returnVal = "MediaDesc";
		}
		
		Assert.assertTrue(returnVal != null);
		
		return returnVal;
	}
	
	
    private List<NLBBean> parseNLBResponse(File respFile) throws Exception {
    	
    	Assert.assertTrue(respFile != null && respFile.exists());
    	
    	BufferedInputStream bis = null;
    	
    	Log.i("NLBWebServicesUtil","Parsing xml from file...");
    	
		if(respFile != null && respFile.exists() && respFile.isFile()) {
			bis = new BufferedInputStream(respFile.toURL().openStream(), 8000);
		}
    	
    	SAXParserFactory spf = SAXParserFactory.newInstance();
    	SAXParser parser = spf.newSAXParser();
    	XMLReader reader = parser.getXMLReader();
    	
    	NLBXMLHandler handler = new NLBXMLHandler();
    	reader.setContentHandler(handler);
    	reader.parse(new InputSource(bis));
    
    	
    	return handler.results;
    }
    
    private void writeNLBResponseToFile(File file) throws Exception {
    
    	BufferedInputStream bis = new BufferedInputStream(conn.getInputStream(), 8000);
    	FileWriter writer = null;
    	Log.i("NLBWebServicesUtil","Starting write into the file...");
    	
    	if(NLBUtil.isSDCardPresent()) {
    		
    		Log.i("NLBWebServicesUtil","SDCard is found!");
    		
    		try {
    			
	    		writer = new FileWriter(file);
	    		
	    		int read;
	    		while((read = bis.read()) != -1) {
	    			writer.write(read);
	    		}
	    	
    		} finally {
    			if(writer != null) {
	    			writer.flush();
	    			writer.close();
	    		}
    			
    			if(bis != null) {
    				bis.close();
    			}
    		}
    		
    		Log.i("NLBWebServicesUtil","Finished writing to sdcard");
    		
    	} else {
    		Log.e("NLBWebServicesUtil","Unable to find SDCard!!!");
    	}
    }

	@Override
	public List<NLBBean> query(String option) {
		// TODO Auto-generated method stub
		return query(option, null, null);
	}
	
	
}
