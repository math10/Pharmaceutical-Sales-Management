package lost.pharmaceuticalsalesmanagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AdminMenuPage extends Activity {

	Button cPawd, mMedicin, mMpo, mSm , mArea;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_menu_page);

		cPawd = (Button) findViewById(R.id.cPasswd);
		mMedicin = (Button) findViewById(R.id.mProduct);
		mMpo = (Button) findViewById(R.id.mMPO);
		mSm = (Button) findViewById(R.id.mSM);
		mArea = (Button)findViewById(R.id.mArea);
		cPawd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent in = new Intent(getApplicationContext(),
						ChangePassword.class)
						.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				;

				startActivity(in);
			}
		});
		mMpo.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent in = new Intent(getApplicationContext(),
						Admin_manage_mpo.class)
						.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				;

				startActivity(in);
			}
		});
		mSm.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent in = new Intent(getApplicationContext(),
						Admin_manage_sm.class)
						.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				;

				startActivity(in);
			}
		});
		mMedicin.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent in = new Intent(getApplicationContext(),
						Admin_manage_medicin.class)
						.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				;

				startActivity(in);
			}
		});
		mArea.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent in = new Intent(getApplicationContext(),
						Admin_manage_area.class)
						.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				;

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
		getMenuInflater().inflate(R.menu.admin_menu_page, menu);
		return true;
	}

}
