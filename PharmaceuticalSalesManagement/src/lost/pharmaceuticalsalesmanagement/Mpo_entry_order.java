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

public class Mpo_entry_order extends Activity {
	private ProgressDialog pDialog;
	JSONparser jParser = new JSONparser();
	JSONArray Area = null;
	String url = StaticData.s + "pharSalesMng/addEntry.php";
	public static String odrId, cusId;
	TextView t1;
	EditText e1;
	Button ok;
	String massage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mpo_entry_order);
		t1 = (TextView) findViewById(R.id.odrIdp);
		e1 = (EditText) findViewById(R.id.cusIdin);

		ok = (Button) findViewById(R.id.ok);

		ok.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				cusId = e1.getText().toString();
				new AddEntry().execute();
			}
		});
		new AllEntry().execute();

	}

	class AddEntry extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Mpo_entry_order.this);
			pDialog.setMessage("Add entry. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting All products from url
		 * */
		protected String doInBackground(String... args) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("odrId", odrId));
			params.add(new BasicNameValuePair("cusId", cusId));
			params.add(new BasicNameValuePair("mpoId", StaticData.id));
			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(url, "POST", params);

			// Check your log cat for JSON reponse
			Log.d("MPO :: ", json.toString());

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
					Intent in = new Intent(getApplicationContext(),
							Medicine_entry.class)
							.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(in);
				}
			});
		}

	}

	class AllEntry extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Mpo_entry_order.this);
			pDialog.setMessage("All entry. Please wait...");
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
					+ "pharSalesMng/allOrder.php", "POST", params);

			// Check your log cat for JSON reponse
			Log.d("MPO :: ", json.toString());

			try {
				int m = json.getInt("success");
				if (m == 1)
					massage = json.getString("message");
				else
					massage = "";
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
					if (massage.length() == 0)
						odrId = "1";
					else {
						odrId = String.valueOf((Integer.valueOf(massage) + 1));

					}
					t1.setText(odrId);

				}
			});
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mpo_entry_order, menu);
		return true;
	}

}
