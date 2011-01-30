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

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NLBroidView extends Activity {
	
	private static String NLB_URL = "https://api.projectnimbus.org/nlbodataservice.svc/CatalogSet?keyword=";
	
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
				HttpURLConnection nlbConnection = null;
				
				if(getString(R.string.search_contents).equals(searchCriteria)) {
					Toast toast = Toast.makeText(getApplicationContext(), "Please enter a search query!", Toast.LENGTH_SHORT);
					toast.show();
				} else {
					try {
						URL url = new URL(new StringBuffer(NLB_URL).append(searchCriteria).toString());
						nlbConnection = (HttpURLConnection) url.openConnection();
						InputStream input = nlbConnection.getInputStream();
						parseNLBResponse(input);
					} catch (Exception mfue) {
						Log.e("NLBroidView", "There is a problem connecting to the NLB webservice");
					} finally {
						if(nlbConnection != null) {
							nlbConnection.disconnect();
						}
					}
				}
				
			}
        	
        	
        });
        
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    
    private void parseNLBResponse(InputStream is) throws Exception {
    	
    	SAXParserFactory spf = SAXParserFactory.newInstance();
    	SAXParser parser = spf.newSAXParser();
    	XMLReader reader = parser.getXMLReader();
    	reader.setContentHandler(new NLBXMLHandler());
    	reader.parse(new InputSource(is));
    }
   
    
}