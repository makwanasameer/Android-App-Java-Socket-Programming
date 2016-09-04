
/*
Roll no:09bce026
Author: Sameer Makwana
Licence: GPLv3

 */
 package p.my.location;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.app.Activity;
import android.os.Bundle;


import android.content.Context;

import android.location.Location;

import android.location.LocationListener;

import android.location.LocationManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;




public class LocationActivity extends Activity {
    /** Called when the activity is first created. */
	Thread t1=null;
	EditText e1;
	Button b1;
	TextView v1;
	Socket socket = null;  
    DataOutputStream dataOutputStream = null;  
    DataInputStream dataInputStream = null;  
    InetAddress serverip;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        b1=(Button)findViewById(R.id.button1);
        b1.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				e1=(EditText)findViewById(R.id.editText1);
				
					
					try {
						serverip=InetAddress.getByName(e1.getText().toString().trim());
						socket = new Socket(serverip,8889);
						v1.setText("Connected");
						dataOutputStream = new DataOutputStream(socket.getOutputStream());  
	                    dataInputStream = new DataInputStream(socket.getInputStream());
	                    
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
				      

			
				
				
				LocationManager  mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
				LocationListener mlocListener = new MyLocationListener();
				mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, mlocListener);
				
				
			}
		});
        
        
        				
        	
    }
	
    
    public class MyLocationListener implements LocationListener
    {
    	public void onLocationChanged(Location loc) 
    	{
    		loc.getLatitude();
    		loc.getLongitude();   		 
            String Text1 = "Lat" + loc.getLatitude();
            String Text2 =  "Long" + loc.getLongitude();
            String finals = Text1+Text2;
            
            try {
				dataOutputStream.writeUTF(finals);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
            
            Toast.makeText( getApplicationContext(),finals,Toast.LENGTH_SHORT).show();    		
    	}
    	
    	public void onStatusChanged(String provider, int status, Bundle extras)

    	{

    	}

		public void onProviderDisabled(String arg0) {
			// TODO Auto-generated method stub
			Toast.makeText( getApplicationContext(),"gps disabled",Toast.LENGTH_SHORT).show();
			
		}

		public void onProviderEnabled(String arg0) {
			// TODO Auto-generated method stub
			
			Toast.makeText( getApplicationContext(),"gps enabled",Toast.LENGTH_SHORT).show();
		}
    	
    }
    @Override
	protected void onPause() {
		// TODO Auto-generated method stub
    	
    	super.onPause();
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finish();
		
	}
    
    
}
