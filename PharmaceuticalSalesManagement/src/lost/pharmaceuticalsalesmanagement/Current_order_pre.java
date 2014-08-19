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

public class Current_order_pre extends Activity {
	private ProgressDialog pDialog;
	JSONparser jParser = new JSONparser();
	JSONArray medicine = null;

	String url = StaticData.s + "pharSalesMng/currentOrder.php";
	String massage;
	public static List<String> l, k, m, n, id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_current_order);
		l = new ArrayList<String>();
		k = new ArrayList<String>();
		new GetOders().execute();

	}

	class GetOders extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Current_order_pre.this);
			pDialog.setMessage("Get Orders. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting All products from url
		 * */
		protected String doInBackground(String... args) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("id", StaticData.id));
			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(url, "GET", params);

			// Check your log cat for JSON reponse
			Log.d("Orders :: ", json.toString());

			try {
				l.clear();
				k.clear();
				m.clear();
				n.clear();
				id.clear();

				int s = json.getInt("success");
				if (s == 1) {
					l.clear();
					medicine = json.getJSONArray("order");
					for (int i = 0; i < medicine.length(); i++) {
						JSONObject c = medicine.getJSONObject(i);
						l.add(c.getString("name"));
						k.add(c.getString("add"));
						m.add(c.getString("tot"));
						n.add(c.getString("status"));
						id.add(c.getString("id"));
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
					pDialog.dismiss();
					Intent in = new Intent(getApplicationContext(),
							Current_order.class)
							.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(in);
				}
			});
		}

	}
}
