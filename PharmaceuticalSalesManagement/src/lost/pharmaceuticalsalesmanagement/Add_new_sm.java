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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Add_new_sm extends Activity {
	private ProgressDialog pDialog;
	JSONparser jParser = new JSONparser();
	JSONArray Area = null;
	String url = StaticData.s + "pharSalesMng/addSm.php";
	List<String> areaList, areaID;
	ArrayAdapter<String> data;
	EditText name;
	TextView t;
	Spinner area;
	Button add;
	String massage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new_sm);
		name = (EditText) findViewById(R.id.name);
		area = (Spinner) findViewById(R.id.area);
		add = (Button) findViewById(R.id.add);
		t = (TextView) findViewById(R.id.mid);
		areaList = new ArrayList<String>();
		areaID = new ArrayList<String>();
		Intent i = getIntent();
		t.setText(i.getStringExtra("m_id"));
		new AllArea().execute();
		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AddSm().execute();
			}

		});
	}

	class AddSm extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Add_new_sm.this);
			pDialog.setMessage("Add new Sales Manager. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting All products from url
		 * */
		protected String doInBackground(String... args) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("id", t.getText().toString()));
			params.add(new BasicNameValuePair("name", name.getText().toString()));
			params.add(new BasicNameValuePair("area", areaID.get((int) area
					.getSelectedItemId())));
			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(url, "POST", params);

			// Check your log cat for JSON reponse
			Log.d("SMG :: ", json.toString());

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
					Toast.makeText(getApplicationContext(), massage,
							Toast.LENGTH_LONG).show();
					name.setText("");
					onBackPressed();
				}
			});
		}

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent in = new Intent(getApplicationContext(), Admin_manage_sm.class)
				.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(in);
		super.onBackPressed();
	}

	class AllArea extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Add_new_sm.this);
			pDialog.setMessage("Getting all area. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting All products from url
		 * */
		protected String doInBackground(String... args) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();

			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(StaticData.s
					+ "pharSalesMng/allArea.php", "POST", params);

			// Check your log cat for JSON reponse
			Log.d("SMG :: ", json.toString());

			try {

				// Checking for SUCCESS TAG
				int success = json.getInt("success");
				if (success == 1) {
					try {

						Area = json.getJSONArray("area");
						for (int i = 0; i < Area.length(); i++) {
							JSONObject c = Area.getJSONObject(i);
							areaList.add(c.getString("name"));
							areaID.add(c.getString("area_id"));

						}

					} catch (JSONException e) {
						e.printStackTrace();
					}
				}

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

					set();
				}
			});
		}

	}

	void set() {
		data = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, areaList);
		data.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		area.setAdapter(data);
		pDialog.dismiss();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_new_sm, menu);
		return true;
	}

}
