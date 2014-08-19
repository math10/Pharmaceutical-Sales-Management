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

public class Medicine_list extends Activity {
	public static List<String> l, k;
	public static String id;
	private ProgressDialog pDialog;
	JSONparser jParser = new JSONparser();
	JSONArray employee = null;
	String url = StaticData.s + "pharSalesMng/orderMedicineList.php";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_medicine_list);
		Intent i = getIntent();
		id = i.getStringExtra("id");
		l = new ArrayList<String>();
		k = new ArrayList<String>();
		new GetOrder().execute();

	}
	class GetOrder extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Medicine_list.this);
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
					l.clear();
					k.clear();
					// products found
					// Getting Array of Products
					employee = json.getJSONArray("list");
					for(int i = 0;i<employee.length();i++){
						JSONObject c = employee.getJSONObject(i);
						l.add(c.getString("pro_id"));
						k.add(c.getString("amount"));
					}
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
					Intent in = new Intent(getApplicationContext(),
							Show.class)
							.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(in);

				}
			});
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.medicine_list, menu);
		return true;
	}

}
