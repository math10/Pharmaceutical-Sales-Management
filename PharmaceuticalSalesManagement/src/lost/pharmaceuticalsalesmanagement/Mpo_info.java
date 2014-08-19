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
import android.widget.TextView;

public class Mpo_info extends Activity {
	private ProgressDialog pDialog;
	JSONparser jParser = new JSONparser();
	JSONArray employee = null;
	String m_id, a, b,c;
	TextView t1, t2;
	Button up, del;
	String url1 = StaticData.s + "pharSalesMng/singleMpo.php",
			url2 = StaticData.s + "pharSalesMng/deleteMpo.php";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mpo_info);
		Intent i = getIntent();

		m_id = i.getStringExtra("m_id");
		t1 = (TextView) findViewById(R.id.name);
		t2 = (TextView) findViewById(R.id.area);
		up = (Button) findViewById(R.id.update);
		del = (Button) findViewById(R.id.delete);
		up.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in = new Intent(getApplicationContext(),
						Update_mpo_info.class)
						.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

				in.putExtra("m_id", m_id);
				in.putExtra("name", a);
				in.putExtra("area", c);
				startActivity(in);
			}

		});
		del.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new DeleteMpo().execute();
			}

		});
		new GetMpo().execute();
	}

	class GetMpo extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Mpo_info.this);
			pDialog.setMessage("getting employee info. Please wait...");
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
			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(url1, "GET", params);

			// Check your log cat for JSON reponse
			Log.d("employee:: ", json.toString());

			try {

				// Checking for SUCCESS TAG
				int success = json.getInt("success");

				if (success == 1) {

					// products found
					// Getting Array of Products
					employee = json.getJSONArray("employee");

					// looping through All Products
					for (int i = 0; i < employee.length(); i++) {
						JSONObject cc = employee.getJSONObject(i);

						a = (cc.getString("name"));
						b = (cc.getString("area_id"));
						c = (cc.getString("area_id"));
					}

				} else {

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
					pDialog.dismiss();
					t1.setText(a);
					t2.setText(b);

				}
			});
		}

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent in = new Intent(getApplicationContext(),
				Admin_manage_mpo.class)
				.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(in);
		super.onBackPressed();
	}

	class DeleteMpo extends AsyncTask<String, String, String> {
		boolean flag;

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Mpo_info.this);
			pDialog.setMessage("Delete employee. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
			flag = false;
		}

		/**
		 * getting All products from url
		 * */
		protected String doInBackground(String... args) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("id", m_id));
			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(url2, "POST", params);

			// Check your log cat for JSON reponse
			Log.d("employee:: ", json.toString());

			try {

				// Checking for SUCCESS TAG
				int success = json.getInt("success");

				if (success == 1) {

					flag = true;

				} else {

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
					pDialog.dismiss();
					if (flag) {
						onBackPressed();
					}
				}
			});
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mpo_info, menu);
		return true;
	}

}
