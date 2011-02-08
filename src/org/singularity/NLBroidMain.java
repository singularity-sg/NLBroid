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

import java.util.List;

import org.singularity.bean.NLBBean;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class NLBroidMain extends Activity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        final Spinner filterSpinner = (Spinner)findViewById(R.id.search_filter);
        
        final Spinner spinner = (Spinner)findViewById(R.id.search_choice);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.search_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
        	
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				
				String itemSelected = parent.getItemAtPosition(pos).toString();
				ArrayAdapter<CharSequence> filterAdapter = null;
				
				
				if("Catalog".equals(itemSelected)) {
					Toast.makeText(getApplicationContext(), "Please select the filter type", Toast.LENGTH_SHORT);
							
					filterAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.filter_array, android.R.layout.simple_spinner_item);
				} else {
					filterAdapter = new ArrayAdapter<CharSequence>(getApplicationContext(), android.R.layout.simple_spinner_item, new String[]{"No item to select"});
				}
				
				filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				filterSpinner.setAdapter(filterAdapter);
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				Toast.makeText(getApplicationContext(), "Please select a legitimate dataset", Toast.LENGTH_SHORT).show();
			}
		});
        
        
        final EditText searchContents = (EditText)findViewById(R.id.search_contents);
        
        final Button searchButton = (Button)findViewById(R.id.search_button);
        searchButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				String selected = spinner.getSelectedItem().toString();
				String filterCriteria = searchContents.getText().toString();
				
				if("Not Selected".equals(selected)) {
					Toast.makeText(getApplicationContext(), "Please select a dataset", Toast.LENGTH_SHORT).show();
					return;
				}
				
				// Check if the searchCriteria is the same as the initial string
				// If it is, then prompt with a notification to enter a string	
				if("Catalog".equals(selected)) {
					
					// If we are searching for a Catalog, then we need to select the filter type and the filter criteria
					String filter = filterSpinner.getSelectedItem().toString();
					
					if("Not Selected".equals(filter)) {
						Toast.makeText(getApplicationContext(), "Please select a filter criteria", Toast.LENGTH_SHORT).show();
					} else
					if(getString(R.string.search_contents).equals(filterCriteria)) {
							Toast.makeText(getApplicationContext(), "Please enter a filter criteria!", Toast.LENGTH_SHORT).show();
					} else {
						new NLBroidTask().execute(selected, filter, filterCriteria);
					}
				
				} else {
					new NLBroidTask().execute(selected);
				}
				
			}
        });
        
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    
    /**
     * Private class for executing long running network jobss
     * @author han
     *
     */
    private class NLBroidTask extends AsyncTask<String, Integer, List<NLBBean>> {
    	
    	private ProgressDialog pDialog;
    	private NLBWebServicesUtil webService = new NLBWebServicesUtil();

    	@Override
    	protected List<NLBBean> doInBackground(String... params) {
    		
    		List<NLBBean> results = null;
    		
    		if(params.length == 3) {
    			results = webService.query(params[0], params[1], params[2]);
    		} else 
    		if(params.length == 1) {
    			results = webService.query(params[0]);
    		} else {
    			publishProgress(-1);
    		}
    		
    		return results;
    	}

    	@Override
    	protected void onPostExecute(List<NLBBean> result) {
    		if(result != null) {
    			Log.i("NLBroidMain", "No. of items created : " + result.size());
    			Toast.makeText(NLBroidMain.this.getApplicationContext(), "Listing results", Toast.LENGTH_SHORT);
    		}
    		
    		pDialog.dismiss();
    	}
    	

    	@Override
		protected void onPreExecute() {
			pDialog = ProgressDialog.show(NLBroidMain.this, "Status", "Querying", false, false);
		}

		@Override
    	protected void onProgressUpdate(Integer... values) {
    		
    		if(values[0] == -1) {
    			Toast.makeText(NLBroidMain.this.getApplicationContext(), "Unable to carry out task due to inadequate parameters", Toast.LENGTH_SHORT);
    		} else {
    			Toast.makeText(NLBroidMain.this.getApplicationContext(), "Please wait...", Toast.LENGTH_SHORT);
    		}
    		
    	}
    
    }
}