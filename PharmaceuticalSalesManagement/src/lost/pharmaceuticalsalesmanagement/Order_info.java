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

public class Order_info extends Activity {
	private ProgressDialog pDialog;
	JSONparser jParser = new JSONparser();
	JSONArray employee = null;
	String a, b, c, id, flag;
	TextView t1, t2, t3, t4;
	Button medicine, ok;
	String url = StaticData.s + "pharSalesMng/orderInfo.php",url1 = StaticData.s + "pharSalesMng/changeStatus.php";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_info);
		Intent i = getIntent();
		id = i.getStringExtra("id");
		flag = i.getStringExtra("flag");
		t1 = (TextView) findViewById(R.id.odrId);
		t2 = (TextView) findViewById(R.id.cusName);
		t3 = (TextView) findViewById(R.id.cusAdd);
		t4 = (TextView) findViewById(R.id.tot);
		medicine = (Button) findViewById(R.id.mList);
		ok = (Button) findViewById(R.id.btn);
		if (flag.equals("true")) {
			ok.setText("Complet");
			ok.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					new Change().execute();
				}

			});
		} else
			ok.setText("not Complet");
		medicine.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in = new Intent(getApplicationContext(),
						Sm_order_manage_pre.class)
						.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				in.putExtra("id", id);
				startActivity(in);
			}

		});
		t1.setText(id);
		new GetOrder().execute();
	}

	class GetOrder extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Order_info.this);
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
			params.add(new BasicNameValuePair("id", id));
			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(url, "GET", params);

			// Check your log cat for JSON reponse
			Log.d("employee:: ", json.toString());

			try {

				// Checking for SUCCESS TAG
				int success = json.getInt("success");

				if (success == 1) {

					// products found
					// Getting Array of Products

					// looping through All Products
					a = json.getString("name");
					b = json.getString("add");
					c = json.getString("tot");

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
					t2.setText(a);
					t3.setText(b);
					t4.setText(c);

				}
			});
		}

	}
	
	class Change extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Order_info.this);
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
			params.add(new BasicNameValuePair("id", id));
			params.add(new BasicNameValuePair("status", "1"));
			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(url1, "POST", params);

			// Check your log cat for JSON reponse
			Log.d("employee:: ", json.toString());

			try {

				// Checking for SUCCESS TAG
				int success = json.getInt("success");

				if (success == 1) {

					// products found
					// Getting Array of Products

					// looping through All Products
					

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
					onBackPressed();

				}
			});
		}

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent in = new Intent(getApplicationContext(),
				Sm_order_manage_pre.class)
				.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(in);
		super.onBackPressed();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.order_info, menu);
		return true;
	}

}
