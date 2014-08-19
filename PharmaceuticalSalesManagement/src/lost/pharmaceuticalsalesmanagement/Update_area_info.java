package lost.pharmaceuticalsalesmanagement;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Update_area_info extends Activity {
	private ProgressDialog pDialog;
	JSONparser jParser = new JSONparser();
	JSONArray medicine = null;
	String m_id, massage;
	EditText name, area;
	TextView msg;
	Button up;;
	String url = StaticData.s + "pharSalesMng/update_area.php";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update_area_info);
		Intent i = getIntent();

		m_id = i.getStringExtra("m_id");
		name = (EditText) findViewById(R.id.name);
		area = (EditText) findViewById(R.id.area);
		
		up = (Button) findViewById(R.id.update);
		

		
		name.setText(i.getStringExtra("name"));
		area.setText(i.getStringExtra("add"));
		

		up.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new UpdateArea().execute();
			}

		});
	}
	
	class UpdateArea extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Update_area_info.this);
			pDialog.setMessage("Updateing area info. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting All products from url
		 * */
		protected String doInBackground(String... args) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("id", m_id));
			params.add(new BasicNameValuePair("name", name.getText().toString()));
			params.add(new BasicNameValuePair("add", area.getText().toString()));
			
			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(url, "POST", params);

			// Check your log cat for JSON reponse
			Log.d("Area:: ", json.toString());

			try {

				massage = json.getString("message");

			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog after getting all products
			runOnUiThread(new Runnable() {
				public void run() {
					pDialog.dismiss();
					
				}
			});
		}

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent in = new Intent(getApplicationContext(), Area_info.class)
				.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		in.putExtra("m_id", m_id);
		startActivity(in);
		super.onBackPressed();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.update_area_info, menu);
		return true;
	}

}
