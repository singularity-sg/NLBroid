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

import android.os.Parcel;
import android.os.Parcelable;

public class NLBBeanParcelable implements Parcelable {

	private NLBBean bean;
	
	public NLBBeanParcelable(NLBBean bean) {
		this.bean = bean;
	}
	
	public NLBBeanParcelable(Parcel in) {
		bean = (NLBBean) in.readValue(NLBBean.class.getClassLoader());
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeValue(bean);
	}

	public static final Parcelable.Creator<NLBBeanParcelable> CREATOR = new Parcelable.Creator<NLBBeanParcelable>() {
		public NLBBeanParcelable createFromParcel(Parcel in) {
			return new NLBBeanParcelable(in);
		}

		public NLBBeanParcelable[] newArray(int size) {
			return new NLBBeanParcelable[size];
		}
	};
	
}
