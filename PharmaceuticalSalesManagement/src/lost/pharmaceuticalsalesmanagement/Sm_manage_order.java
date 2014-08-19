package lost.pharmaceuticalsalesmanagement;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Sm_manage_order extends Activity {
	RelativeLayout r;
	List<String> l, k;
	TextView text[] = new TextView[100010];
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sm_manage_order);
		r = (RelativeLayout) findViewById(R.id.RR);
		TextView t;
		l = Sm_order_manage_pre.l;
		k = Sm_order_manage_pre.k;
		RelativeLayout.LayoutParams ll;
		if (l.size() != 0) {
			t = new TextView(this);
			t.setId(1);
			t.setText("Order ID :: " + l.get(0));
			if (k.get(0).equals("true"))
				t.setBackgroundColor(Color.GREEN);
			ll = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);
			r.addView(t, ll);
			text[0] = t;
			text[0].setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent in = new Intent(getApplicationContext(),
							Order_info.class)
							.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					in.putExtra("flag",k.get(0));
					in.putExtra("id", l.get(0));
					startActivity(in);
				}
			});
			for (int i = 1; i < l.size(); i++) {
				t = new TextView(this);
				t.setId(i + 1);
				t.setText("Order ID :: " + l.get(i));
				if (k.get(i).toString().equals("true"))
					t.setBackgroundColor(Color.GREEN);
				else
					t.setBackgroundColor(Color.RED);
				ll = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.WRAP_CONTENT);
				ll.addRule(RelativeLayout.BELOW, i);
				r.addView(t, ll);
				text[i] = t;
				text[i].setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						int id = v.getId();
						Intent in = new Intent(getApplicationContext(),
								Order_info.class)
								.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						in.putExtra("flag",k.get(id-1));
						in.putExtra("id", l.get(id-1));
						startActivity(in);
					}
				});

			}
		}
		
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent in = new Intent(getApplicationContext(),
				SalesManagerMenuPage.class)
				.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(in);
		super.onBackPressed();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sm_manage_order, menu);
		return true;
	}

}
