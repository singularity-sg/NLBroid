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
	boolean isInsideEntry = false;
	boolean isInsideContent = false;
	String textVal = "";
	int idx = 1;

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		
		//if(Log.isLoggable("NLBXMLHandler", Log.DEBUG)) {
		Log.d("NLBXMLHandler", "-----------------------");
		//} 		
		
		/** We are still at the root so we can be assured that this item is still at the root. The title is a data type **/
		if("title".equalsIgnoreCase(localName)) { 
			if(currentEntry == null) {
				Log.i("NLBXMLHandler","Query type : " + textVal);
				this.type = textVal;
			} 
		}
		
		if("entry".equalsIgnoreCase(localName)) {
			results.add(currentEntry);
			this.idx++;
			isInsideEntry = false;
		}
		
		if("content".equalsIgnoreCase(localName)) {
			isInsideContent = false;
		}
		
		if(isInsideContent) {
			if("LibrarySet".equals(this.type)) {
				if("LibraryID".equalsIgnoreCase(localName)) {
					currentEntry.setId(textVal);
				} else
				if("Name".equalsIgnoreCase(localName)) {
					currentEntry.setName(textVal);
				} else
				if("Address".equalsIgnoreCase(localName)) {
					currentEntry.setAddress(textVal);
				} else 
				if("Latitude".equalsIgnoreCase(localName)) {
					Float latitude = null;
					try {
						latitude = Float.parseFloat(textVal);
					} catch (NumberFormatException nfe) {
						nfe.printStackTrace();
					}
					currentEntry.setLatitude(latitude);
				} else
				if("Longitude".equalsIgnoreCase(localName)) {
					Float longitude = null;
					try {
						longitude = Float.parseFloat(textVal);
					} catch (NumberFormatException nfe) {
						nfe.printStackTrace();
					}
					currentEntry.setLongtitude(longitude);
				}
			} else 
			
			if("LatestArticleSet".equals(this.type)) {
				
				if("LatestArticleID".equalsIgnoreCase(localName)) {
					currentEntry.setId(textVal);
				} else
				if("Title".equalsIgnoreCase(localName)) {
					currentEntry.setTitle(textVal);
				} else 
				if("Description".equalsIgnoreCase(localName)) {
					currentEntry.setDescription(textVal);
				} else 
				if("Author".equalsIgnoreCase(localName)) {
					currentEntry.setAuthor(textVal);
				} else 
				if("Category".equalsIgnoreCase(localName)) {
					currentEntry.setCategory(textVal);
				} else 
				if("PublishDate".equalsIgnoreCase(localName)) {
					currentEntry.setPublishDate(textVal);
				} else
				if("URL".equalsIgnoreCase(localName)) {
					currentEntry.setUrl(textVal);
				} 		
			} else 
				
			if("EventSet".equals(this.type)) {
				if("Description".equalsIgnoreCase(localName)) {
					currentEntry.setDescription(textVal);
				} else
				if("EventID".equalsIgnoreCase(localName)) {
					currentEntry.setId(textVal);
				} else
				if("URL".equalsIgnoreCase(localName)) {
					currentEntry.setUrl(textVal);
				} else
				if("Subject".equalsIgnoreCase(localName)) {
					currentEntry.setSubject(textVal);
				} else
				if("Room".equalsIgnoreCase(localName)) {
					currentEntry.setRoom(textVal);
				} else
				if("Library".equalsIgnoreCase(localName)) {
					currentEntry.setLibrary(textVal);
				} else
				if("Summary".equalsIgnoreCase(localName)) {
					currentEntry.setSummary(textVal);
				} else
				if("Latitude".equalsIgnoreCase(localName)) {				
					Float latitude = null;				
					try {
						latitude = Float.parseFloat(textVal);
					} catch (NumberFormatException nfe) {
						nfe.printStackTrace();
					}
					currentEntry.setLatitude(latitude);
				} else
				if("Longitude".equalsIgnoreCase(localName)) {	
					Float longitude = null;
					try {
						longitude = Float.parseFloat(textVal);
					} catch (NumberFormatException nfe) {
						nfe.printStackTrace();
					}
					currentEntry.setLongtitude(longitude);
				}
			}
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		
		Log.d("NLBXMLHandler", "-----------------------");
		Log.d("NLBXMLHandler", "Element uri : " + uri);
		Log.d("NLBXMLHandler", "Element localname : " + localName);
		Log.d("NLBXMLHandler", "Element qName : " + qName);
	
		
		isInsideEntry = true;
		
		if("entry".equalsIgnoreCase(localName)) {
			if(this.type != null) {			
				currentEntry = new NLBBean();
				currentEntry.setIdx(this.idx);	
				
				if("LibrarySet".equals(this.type)) {
					currentEntry.setType(NLBBean.Type.LIBRARY);
				} else 
				if("EventSet".equals(this.type)) {
					currentEntry.setType(NLBBean.Type.EVENT);
				} else 
				if("LatestArticleSet".equals(this.type)) {
					currentEntry.setType(NLBBean.Type.LATEST_ARTICLE);
				} else
				if("CatalogSet".equals(this.type)) {
					currentEntry.setType(NLBBean.Type.CATALOG);
				}
			}
		}
		
		if("content".equalsIgnoreCase(localName)) {
			isInsideContent = true;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if(isInsideEntry) {
			textVal = new String(ch, start, length);
		}
	}
	
	
	
}
