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

public class Medicine_entry extends Activity {
	private ProgressDialog pDialog;
	JSONparser jParser = new JSONparser();
	JSONArray medicine = null;
	Spinner sp;
	EditText e1;
	TextView t1;
	Button add, ok;
	String url = StaticData.s + "pharSalesMng/allMedicine2.php";
	String url1 = StaticData.s + "pharSalesMng/addMedicineOrderentry.php";
	List<String> areaList;
	List<Medicine> mm;
	int id;
	String proId, massage;
	int tot;
	ArrayAdapter<String> data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_medicine_entry);
		sp = (Spinner) findViewById(R.id.medicineList);
		e1 = (EditText) findViewById(R.id.mamount);
		t1 = (TextView) findViewById(R.id.tCost);
		add = (Button) findViewById(R.id.madd);
		ok = (Button) findViewById(R.id.ok);
		areaList = new ArrayList<String>();
		mm = new ArrayList<Medicine>();
		tot = 0;
		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String s = e1.getText().toString();
				if (s.length() != 0) {
					try {
						int mu = Integer.valueOf(s);
						tot += mu * mm.get(id).price;
						t1.setText(String.valueOf(tot));
						int t = (int) sp.getSelectedItemId();
						proId = mm.get(t).mId;
						new AddMedicine().execute();

					} catch (Exception e) {

					}
				}
			}

		});
		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new FinalEntry().execute();
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
			pDialog = new ProgressDialog(Medicine_entry.this);
			pDialog.setMessage("getting all medicine. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting All products from url
		 * */
		protected String doInBackground(String... args) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("area", StaticData.area));
			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(url, "GET", params);

			// Check your log cat for JSON reponse
			Log.d("Medicine: ", json.toString());

			try {

				// Checking for SUCCESS TAG
				int success = json.getInt("success");

				if (success == 1) {

					// products found
					// Getting Array of Products
					medicine = json.getJSONArray("medicine");

					// looping through All Products
					for (int i = 0; i < medicine.length(); i++) {
						JSONObject c = medicine.getJSONObject(i);
						areaList.add(c.getString("name"));
						mm.add(new Medicine(c.getString("m_id"), c
								.getString("stock"), Integer.valueOf(c
								.getString("price"))));

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
					set();
				}
			});
		}

	}
	
	class FinalEntry extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Medicine_entry.this);
			pDialog.setMessage("getting all medicine. Please wait...");
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
			params.add(new BasicNameValuePair("id", Mpo_entry_order.odrId));
			params.add(new BasicNameValuePair("amount", t1.getText().toString()));
			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(url, "POST", params);

			// Check your log cat for JSON reponse
			Log.d("Medicine: ", json.toString());

			try {

				// Checking for SUCCESS TAG
				success = json.getInt("success");

				if (success == 1) {

					

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
					if(success == 1){
						Intent in = new Intent(getApplicationContext(),
								MPOsMenuPage.class)
								.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

						startActivity(in);
					}
				}
			});
		}

	}
	
	class AddMedicine extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Medicine_entry.this);
			pDialog.setMessage("add medicine in order. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting All products from url
		 * */
		protected String doInBackground(String... args) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("odrId", Mpo_entry_order.odrId));
			params.add(new BasicNameValuePair("proId", proId));
			params.add(new BasicNameValuePair("amount", e1.getText().toString()));
			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(url1, "POST", params);

			// Check your log cat for JSON reponse
			Log.d("Medicine: ", json.toString());

			try {

				// Checking for SUCCESS TAG
				int success = json.getInt("success");

				if (success == 1) {

					massage = json.getString("massage");

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
					Toast.makeText(getApplicationContext(), massage,
							Toast.LENGTH_LONG).show();
				}
			});
		}

	}

	void set() {
		data = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, areaList);
		data.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp.setAdapter(data);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.medicine_entry, menu);
		return true;
	}

}
