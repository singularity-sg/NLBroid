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

import java.util.List;

import org.singularity.bean.NLBBean;

public interface IWebServicesUtil {
	
	/**
	 * 
	 * @param option - The selected library dataset
	 * @param filter - The parameters for adding search criteria
	 * @param keyword - The keyword related to the search criteria
	 * @return Map<String, Object>
	 */
	public List<NLBBean> query(String option, String filter, String keyword);
	
	/**
	 * 
	 * @param option - The selected library dataset
	 * @return
	 */
	public List<NLBBean> query(String option);
	
}
