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

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class NLBBeanParcelableArray implements Parcelable {

	private List<NLBBean> array;
	
	public NLBBeanParcelableArray(List<NLBBean> array) {
		this.array = array;
	}
	
	private NLBBeanParcelableArray(Parcel in) {
		array = new ArrayList<NLBBean>();
		in.readList(array, NLBBean.class.getClassLoader());
	}
	
	public static final Parcelable.Creator<NLBBeanParcelableArray> CREATOR = new Parcelable.Creator<NLBBeanParcelableArray>() {
		public NLBBeanParcelableArray createFromParcel(Parcel in) {
			return new NLBBeanParcelableArray(in);
		}

		public NLBBeanParcelableArray[] newArray(int size) {
			return new NLBBeanParcelableArray[size];
		}
	};
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeList(array);
	}

	public List<NLBBean> getArray() {
		return array;
	}

	public void setArray(List<NLBBean> array) {
		this.array = array;
	}

}
