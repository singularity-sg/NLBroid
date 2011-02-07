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

import java.util.ArrayList;
import java.util.List;

import org.singularity.bean.NLBBean;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

public class NLBXMLHandler extends DefaultHandler {
	
	List<NLBBean> results = new ArrayList<NLBBean>();
	String type = null;
	
	NLBBean currentEntry = null;
	boolean isInsideElement = false;
	String textVal = "";
	int idx = 1;

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		
		//if(Log.isLoggable("NLBXMLHandler", Log.DEBUG)) {
		Log.d("NLBXMLHandler", "-----------------------");
		//} 
		
		isInsideElement = false;
		
		/** We are still at the root **/
		if("title".equalsIgnoreCase(localName)) { 
			if(currentEntry == null) {
				Log.i("NLBXMLHandler","Query type : " + textVal);
				this.type = textVal;
			} 
		}
		
		if("Name".equalsIgnoreCase(localName)) {
			currentEntry.setName(textVal);
		}
		
		if("Address".equalsIgnoreCase(localName)) {
			currentEntry.setAddress(textVal);
		}
		
		if("Latitude".equalsIgnoreCase(localName)) {
			
			Float latitude = null;
			
			try {
				latitude = Float.parseFloat(textVal);
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
			}
			
			
			currentEntry.setLatitude(latitude);
		} 
		
		if("Longitude".equalsIgnoreCase(localName)) {
			
			Float longitude = null;
			
			try {
				longitude = Float.parseFloat(textVal);
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
			}
			
			currentEntry.setLongtitude(longitude);
			
		}
		
		if("entry".equalsIgnoreCase(localName)) {
			results.add(currentEntry);
			this.idx++;
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		
		Log.d("NLBXMLHandler", "-----------------------");
		Log.d("NLBXMLHandler", "Element uri : " + uri);
		Log.d("NLBXMLHandler", "Element localname : " + localName);
		Log.d("NLBXMLHandler", "Element qName : " + qName);
	
		
		isInsideElement = true;
		
		if("entry".equalsIgnoreCase(localName)) {
			if(this.type != null) {
				if("LibrarySet".equals(this.type)) {
					currentEntry = new NLBBean();
					currentEntry.setIdx(this.idx);
					currentEntry.setId(this.idx);
				}
			}
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if(isInsideElement) {
			textVal = new String(ch, start, length);
		}
	}
	
	
	
}
