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
import org.singularity.bean.NLBBeanParcelable;
import org.singularity.bean.NLBBeanParcelableArray;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class NLBroidResults extends ListActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		NLBBeanParcelableArray array = getIntent().getParcelableExtra(NLBroidMain.ACTION_SHOW_RESULTS);
		final List<NLBBean> results = array.getArray();
		String[] titles = NLBroidUtil.convertToStringTitleArray(results);
	
		setListAdapter(new ArrayAdapter<String>(this, R.layout.results, titles));

		ListView lv = getListView();
		lv.setTextFilterEnabled(true);

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		      
			  NLBBean b = (NLBBean) results.get(position);
			 
			  NLBBeanParcelable parcelableObj = new NLBBeanParcelable(b);
      		  Intent detailsIntent = new Intent(NLBroidResults.this, NLBroidMap.class);    	 	
      		  detailsIntent.putExtra(NLBroidMain.ACTION_SHOW_MAP, parcelableObj);
			  
      		  
      		  NLBroidResults.this.startActivity(detailsIntent);
      		  
			  /** Deprecated : show the item address on selecting **/
			  /*
      		  Dialog dialog = new Dialog(NLBroidResults.this);

			  dialog.setContentView(R.layout.result_details);
			  
			  TextView text = (TextView) dialog.findViewById(R.id.dialog_text);
			  text.setText(b.getDetails());
			  
			  dialog.setTitle(b.getListTitle());
			  dialog.setCancelable(true);
			  dialog.setCanceledOnTouchOutside(true);

			  dialog.show();
			  */
      		  
		    }
		});
		
	}

	
	
}
