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

package org.singularity.bean;

import java.io.Serializable;

public class NLBBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5358328534980060417L;
	
	private long id;
	private int idx;
	private String name;
	private String address;
	private Float latitude;
	private Float longtitude;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
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
	public Float getLongtitude() {
		return longtitude;
	}
	public void setLongtitude(Float longtitude) {
		this.longtitude = longtitude;
	}
	
	
}
