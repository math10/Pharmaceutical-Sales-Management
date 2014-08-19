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

public class Update_medicine_info extends Activity {
	private ProgressDialog pDialog;
	JSONparser jParser = new JSONparser();
	JSONArray medicine = null;
	String m_id, massage;
	EditText name, des, type, price;
	TextView msg;
	Button up;;
	String url = StaticData.s + "pharSalesMng/update_medicine.php";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_medicine_info);
		Intent i = getIntent();

		m_id = i.getStringExtra("m_id");
		name = (EditText) findViewById(R.id.name);
		des = (EditText) findViewById(R.id.description);
		type = (EditText) findViewById(R.id.type);
		up = (Button) findViewById(R.id.update);
		msg = (TextView) findViewById(R.id.msg);

		price = (EditText) findViewById(R.id.price);
		name.setText(i.getStringExtra("name"));
		des.setText(i.getStringExtra("description"));
		type.setText(i.getStringExtra("type"));
		price.setText(i.getStringExtra("price"));

		up.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new UpdateMedicine().execute();
			}

		});

	}

	class UpdateMedicine extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Update_medicine_info.this);
			pDialog.setMessage("Updateing medicine info. Please wait...");
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
			params.add(new BasicNameValuePair("name", name.getText().toString()));
			params.add(new BasicNameValuePair("des", des.getText().toString()));
			params.add(new BasicNameValuePair("type", type.getText().toString()));
			params.add(new BasicNameValuePair("price", price.getText()
					.toString()));
			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(url, "POST", params);

			// Check your log cat for JSON reponse
			Log.d("Medicine:: ", json.toString());

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
					msg.setText(massage);
				}
			});
		}

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent in = new Intent(getApplicationContext(), Medicine_info.class)
				.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		in.putExtra("m_id", m_id);
		startActivity(in);
		super.onBackPressed();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.update_medicine_info, menu);
		return true;
	}

}
