package lost.pharmaceuticalsalesmanagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class Admin_manage_medicin extends ListActivity {
	private ProgressDialog pDialog;
	JSONparser jParser = new JSONparser();
	JSONArray medicine = null;
	EditText e1;
	Button add;
	String s, massage;
	ArrayList<HashMap<String, String>> medicineList, tmp;
	String url = StaticData.s + "pharSalesMng/allMedicin.php";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_manage_medicin);

		e1 = (EditText) findViewById(R.id.search);
		add = (Button) findViewById(R.id.addMedicin);
		medicineList = new ArrayList<HashMap<String, String>>();
		tmp = new ArrayList<HashMap<String, String>>();
		new GetMedicine().execute();
		ListView lv = getListView();
		add.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent in = new Intent(getApplicationContext(),
						Add_new_medicine.class)
						.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(in);
			}
		});

		e1.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
				// TODO Auto-generated method stub
				s = e1.getText().toString();
				tmp.clear();
				for (int i = 0; i < medicineList.size(); i++) {
					HashMap<String, String> map = medicineList.get(i);
					String n = map.get("name");
					if (n.length() >= s.length()
							&& n.substring(0, s.length()).equals(s)) {
						tmp.add(map);
					}
				}
				ListAdapter adapter = new SimpleAdapter(
						Admin_manage_medicin.this, tmp, R.layout.list_item,
						new String[] { "m_id", "name" }, new int[] { R.id.pid,
								R.id.name });
				// updating listview
				setListAdapter(adapter);
				return false;
			}

		});

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String pid = ((TextView) view.findViewById(R.id.pid)).getText()
						.toString();
				Intent in = new Intent(getApplicationContext(),
						Medicine_info.class)
						.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

				in.putExtra("m_id", pid);
				startActivity(in);
			}
		});

	}

	class GetMedicine extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Admin_manage_medicin.this);
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

						// Storing each json item in variable
						String id = c.getString("m_id");
						String name = c.getString("name");
						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						map.put("m_id", id);
						map.put("name", name);

						// adding HashList to ArrayList
						medicineList.add(map);
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
					ListAdapter adapter = new SimpleAdapter(
							Admin_manage_medicin.this, medicineList,
							R.layout.list_item,
							new String[] { "m_id", "name" }, new int[] {
									R.id.pid, R.id.name });
					// updating listview
					setListAdapter(adapter);
				}
			});
		}

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent in = new Intent(getApplicationContext(), AdminMenuPage.class)
				.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		startActivity(in);
		super.onBackPressed();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.admin_manage_medicin, menu);
		return true;
	}

}
