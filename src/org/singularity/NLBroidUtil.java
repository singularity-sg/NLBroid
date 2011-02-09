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

import java.util.Iterator;
import java.util.List;

import org.singularity.bean.NLBBean;

public class NLBroidUtil {
	
	public static String[] convertToStringTitleArray(List<NLBBean> input) {
		
		String[] titles = new String[input.size()]; //List item title;

		Iterator<NLBBean> it = input.iterator();
		int idx = 0;
		
		while(it.hasNext()) {
			NLBBean b = it.next();
			titles[idx++] = b.getListTitle();
		}
		
		return titles;
	}

}
