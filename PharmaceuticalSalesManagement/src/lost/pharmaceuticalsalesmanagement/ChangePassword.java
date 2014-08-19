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

public class ChangePassword extends Activity {
	private ProgressDialog pDialog;
	String  massage;
	Button b;
	EditText e1, e2, e3;
	TextView t1;
	String url = StaticData.s+"pharSalesMng/changePassword.php";
	// Creating JSON Parser object
	JSONparser jParser = new JSONparser();
	// products JSONArray
	JSONArray cpassword = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);
		
		b = (Button) findViewById(R.id.change);
		e1 = (EditText) findViewById(R.id.cPasswd);
		e2 = (EditText) findViewById(R.id.nPasswd);
		e3 = (EditText) findViewById(R.id.rnPasswd);
		t1 = (TextView) findViewById(R.id.mass);
		
		b.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (e1.getText().toString().equals(StaticData.pawd)) {
					if (e2.getText().toString().equals(e3.getText().toString())) {
						new cPasswordTask().execute();
					} else {
						t1.setText("new password & retype new password not matched");
					}
				} else {
					t1.setText("current password not matched");
				}
			}
		});
	}

	class cPasswordTask extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ChangePassword.this);
			pDialog.setMessage("Changing password. Please wait...");
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
			params.add(new BasicNameValuePair("password", e2.getText()
					.toString()));

			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(url, "POST", params);

			// Check your log cat for JSON reponse
			Log.d("Password: ", json.toString());

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

					t1.setText(massage);
					StaticData.pawd = e2.getText().toString();
					e1.setText("");
					e2.setText("");
					e3.setText("");

				}
			});
		}

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (StaticData.rank.equals("MPO")) {
			Intent in = new Intent(getApplicationContext(),
					MPOsMenuPage.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);;
			
			startActivity(in);
		} else if (StaticData.rank.equals("SMG")) {
			Intent in = new Intent(getApplicationContext(),
					SalesManagerMenuPage.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);;
			
			startActivity(in);
		} else if (StaticData.rank.equals("ADM")) {
			Intent in = new Intent(getApplicationContext(),
					AdminMenuPage.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);;
			
			startActivity(in);
		} else if (StaticData.rank.equals("PMG")) {

		}
		super.onBackPressed();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.change_password, menu);
		return true;
	}

}
