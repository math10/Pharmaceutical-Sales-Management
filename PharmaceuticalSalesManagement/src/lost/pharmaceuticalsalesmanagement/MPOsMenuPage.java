package lost.pharmaceuticalsalesmanagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MPOsMenuPage extends Activity {
	Button cPasswd, eOdr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mpos_menu_page);

		cPasswd = (Button) findViewById(R.id.cPasswd);
		eOdr = (Button) findViewById(R.id.eOdr);
		cPasswd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent in = new Intent(getApplicationContext(),
						ChangePassword.class)
						.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(in);
			}
		});
		eOdr.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent in = new Intent(getApplicationContext(),
						Mpo_entry_order.class)
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
		getMenuInflater().inflate(R.menu.mpos_menu_page, menu);
		return true;
	}

}
