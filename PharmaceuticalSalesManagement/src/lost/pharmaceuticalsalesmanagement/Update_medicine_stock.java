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
import android.widget.Toast;

public class Update_medicine_stock extends Activity {
	private ProgressDialog pDialog;
	JSONparser jParser = new JSONparser();
	JSONArray medicine = null;
	EditText amount;
	TextView name, type, cur;
	Button add;
	String a, b, m_id, c;
	String url1 = StaticData.s + "pharSalesMng/singleMedicine2.php",
			url = StaticData.s + "pharSalesMng/updateStockMedicin.php";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_medicine_stock);
		name = (TextView) findViewById(R.id.name);
		type = (TextView) findViewById(R.id.type);
		cur = (TextView) findViewById(R.id.cur);
		amount = (EditText) findViewById(R.id.amount);
		add = (Button) findViewById(R.id.add);
		Intent i = getIntent();
		m_id = i.getStringExtra("m_id");
		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new UpStocktMedicine().execute();
			}

		});
		new GetMedicine().execute();
	}

	class GetMedicine extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Update_medicine_stock.this);
			pDialog.setMessage("getting medicine info. Please wait...");
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
			params.add(new BasicNameValuePair("area", StaticData.area));
			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(url1, "GET", params);

			// Check your log cat for JSON reponse
			Log.d("Medicine:: ", json.toString());

			try {

				// Checking for SUCCESS TAG
				int success = json.getInt("success");

				if (success == 1) {

					// products found
					// Getting Array of Products
					medicine = json.getJSONArray("medicine");

					// looping through All Products
					for (int i = 0; i < medicine.length(); i++) {
						JSONObject cc = medicine.getJSONObject(i);

						a = (cc.getString("name"));
						b = (cc.getString("type"));
						c = (cc.getString("stock"));

					}

				} else {

				}

			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		protected void onPostExecute(String file_url) {
			// dismiss the dialog after getting all products
			runOnUiThread(new Runnable() {
				public void run() {
					pDialog.dismiss();
					name.setText(a);
					type.setText(b);
					cur.setText(c);
				}
			});
		}

	}

	class UpStocktMedicine extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Update_medicine_stock.this);
			pDialog.setMessage("Update medicine stock. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		int success;

		/**
		 * getting All products from url
		 * */
		protected String doInBackground(String... args) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("id", m_id));
			params.add(new BasicNameValuePair("area", StaticData.area));
			params.add(new BasicNameValuePair("amount", amount.getText()
					.toString()));
			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(url, "GET", params);

			// Check your log cat for JSON reponse
			Log.d("Medicine:: ", json.toString());

			try {

				// Checking for SUCCESS TAG
				success = json.getInt("success");

			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		protected void onPostExecute(String file_url) {
			// dismiss the dialog after getting all products
			runOnUiThread(new Runnable() {
				public void run() {
					pDialog.dismiss();
					if (success == 1) {
						Toast.makeText(getApplicationContext(),
								"successfully updated", Toast.LENGTH_LONG)
								.show();
						new GetMedicine().execute();
					}
				}
			});
		}

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent in = new Intent(getApplicationContext(),
				Sm_manage_medicine.class)
				.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(in);
		super.onBackPressed();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.update_medicine_stock, menu);
		return true;
	}

}
