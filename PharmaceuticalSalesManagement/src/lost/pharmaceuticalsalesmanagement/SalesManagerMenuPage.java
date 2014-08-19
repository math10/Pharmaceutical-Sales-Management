package lost.pharmaceuticalsalesmanagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SalesManagerMenuPage extends Activity {
	Button odr, cPasswd, uStock;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sales_manager_menu_page);
		odr = (Button) findViewById(R.id.odr);
		cPasswd = (Button) findViewById(R.id.cPasswd);
		uStock = (Button) findViewById(R.id.uStock);

		cPasswd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent in = new Intent(getApplicationContext(),
						ChangePassword.class)
						.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(in);
			}
		});
		
		uStock.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent in = new Intent(getApplicationContext(),
						Sm_manage_medicine.class)
						.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(in);
			}
		});
		odr.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent in = new Intent(getApplicationContext(),
						Sm_order_manage_pre.class)
						.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(in);
			}
		});
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent in = new Intent(getApplicationContext(), MainPage.class)
				.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		startActivity(in);
		super.onBackPressed();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sales_manager_menu_page, menu);
		return true;
	}

}
