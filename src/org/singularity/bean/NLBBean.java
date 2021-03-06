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

package org.singularity.bean;

import java.io.Serializable;

public class NLBBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5358328534980060417L;

	private String id;
	private int idx;
	private String name;
	private String address;
	private Float latitude;
	private Float longitude;
	
	private String title;
	private String description;
	private String author;
	private String category;
	private String publishDate;
	private String url;
	
	private String subject;
	private String room;
	private String library;
	private String date;
	private String summary;
	
	private Type type;
	
	public NLBBean() {
		name = "N/A";
		address = "N/A";
		title = "N/A";
		description = "N/A";
		author = "N/A";
		category = "N/A";
		publishDate = "N/A";
		url = "N/A";
		subject = "N/A";
		room = "N/A";
		library = "N/A";
		date = "N/A";
		summary = "N/A";
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Float getLatitude() {
		return latitude;
	}
	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}
	public Float getLongitude() {
		return longitude;
	}
	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public String getLibrary() {
		return library;
	}
	public void setLibrary(String library) {
		this.library = library;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	
	public String getListTitle() {
		switch(this.getType()) {
			case LIBRARY : 	return this.getName();
			case EVENT   :	return this.getDescription();
			case CATALOG :  return this.getTitle();
			case LATEST_ARTICLE : return this.getTitle();
			default : return "Unknown item";
		}
	}
	
	public String getDetails() {
		switch(this.getType()) {
			case LIBRARY : 	return this.getAddress();
			case EVENT   :	return this.getSummary();
			case CATALOG :  return this.getTitle();
			case LATEST_ARTICLE : return this.getDescription();
			default : return "Unknown item";
		}
	}

	public enum Type { CATALOG, LATEST_ARTICLE, LIBRARY, EVENT }
	
}
