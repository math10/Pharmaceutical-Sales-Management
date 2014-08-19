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
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainPage extends Activity {
	private ProgressDialog pDialog;
	Button login;
	TextView nWork;
	EditText uid, passwd;

	String massage;
	String url = StaticData.s + "pharSalesMng/login.php";
	// Creating JSON Parser object
	JSONparser jParser = new JSONparser();
	// products JSONArray
	JSONArray info = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_page);
		login = (Button) findViewById(R.id.login);
		nWork = (TextView) findViewById(R.id.nWork);
		uid = (EditText) findViewById(R.id.userId);
		passwd = (EditText) findViewById(R.id.passwd);

		login.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (isNetworkAvailable()) {
					StaticData.id = uid.getText().toString();
					StaticData.pawd = passwd.getText().toString();
					int len = StaticData.id.length();
					if (len == 8) {
						boolean flag = false;
						if (StaticData.id.substring(0, 3).equals("MPO")) {
							StaticData.rank = "MPO";
							flag = true;
						} else if (StaticData.id.substring(0, 3).equals("SMG")) {
							StaticData.rank = "SMG";
							flag = true;
						} else if (StaticData.id.substring(0, 3).equals("ADM")) {
							StaticData.rank = "ADM";
							flag = true;
						} else if (StaticData.id.substring(0, 3).equals("PMG")) {
							StaticData.rank = "PMG";
							flag = true;
						}
						if (flag) {
							new LogingCheck().execute();
						} else {
							nWork.setText("Wrong id or password");
						}
					} else {
						nWork.setText("Wrong id or password");
					}

				} else {
					nWork.setText("Internet isn't enable");
				}
			}
		});

	}

	public boolean isNetworkAvailable() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		// if no network is available networkInfo will be null
		// otherwise check if we are connected
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		}
		return false;
	}

	class LogingCheck extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(MainPage.this);
			pDialog.setMessage("Cheacking id and password. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting All products from url
		 * */
		protected String doInBackground(String... args) {
			// Building Parameters

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("id", StaticData.id));
			params.add(new BasicNameValuePair("password", StaticData.pawd));
			params.add(new BasicNameValuePair("rank", StaticData.rank));
			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(url, "POST", params);

			// Check your log cat for JSON reponse
			Log.d("Login: ", json.toString());

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt("success");

				if (success == 1) {
					massage = "";
					if(!StaticData.rank.equals("ADM"))
						StaticData.area = json.getString("area");
					else StaticData.area = "";

				} else {
					massage = json.getString("message");
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
					if (massage.length() != 0) {
						nWork.setText(massage);
					} else {
						if (StaticData.rank.equals("MPO")) {
							Intent in = new Intent(getApplicationContext(),
									MPOsMenuPage.class)
									.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							;
							in.putExtra("id", StaticData.id);
							in.putExtra("pass", StaticData.pawd);
							in.putExtra("rank", StaticData.rank);
							startActivity(in);
						} else if (StaticData.rank.equals("SMG")) {
							Intent in = new Intent(getApplicationContext(),
									SalesManagerMenuPage.class)
									.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							;
							in.putExtra("id", StaticData.id);
							in.putExtra("pass", StaticData.pawd);
							in.putExtra("rank", StaticData.rank);
							startActivity(in);
						} else if (StaticData.rank.equals("ADM")) {
							Intent in = new Intent(getApplicationContext(),
									AdminMenuPage.class)
									.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							;
							in.putExtra("id", StaticData.id);
							in.putExtra("pass", StaticData.pawd);
							in.putExtra("rank", StaticData.rank);
							startActivity(in);
						} else if (StaticData.rank.equals("PMG")) {

						}
					}
				}
			});
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_page, menu);
		return true;
	}

}
