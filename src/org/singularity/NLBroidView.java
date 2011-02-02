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

import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NLBroidView extends Activity {

	private NLBWebServicesUtil webService = new NLBWebServicesUtil();
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        final EditText searchContents = (EditText)findViewById(R.id.search_contents);
        final Button searchButton = (Button)findViewById(R.id.search_button);
        
        searchButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				String searchCriteria = searchContents.getText().toString();
				
				// Check if the searchCriteria is the same as the initial string
				// If it is, then prompt with a notification to enter a string
				if(getString(R.string.search_contents).equals(searchCriteria)) {
					Toast toast = Toast.makeText(getApplicationContext(), "Please enter a search query!", Toast.LENGTH_SHORT);
					toast.show();
				} else {
					
					Map<String, Object> results = webService.query("CatalogSet?keyword=");
				
				}
				
			}
        });
        
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }    
}