package lost.pharmaceuticalsalesmanagement;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class Show extends Activity {
	RelativeLayout r;
	String id;
	List<String> l, k;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_medicine_list);
		r = (RelativeLayout) findViewById(R.id.rrr);
		Intent ii = getIntent();
		id = ii.getStringExtra("id");
		l = Medicine_list.l;
		k = Medicine_list.k;
		TextView t;
		RelativeLayout.LayoutParams ll;
		for(int i = 0;i<l.size();i++){
			t = new TextView(this);
			t.setId(i+1);
			t.setText(l.get(i) + " " + k.get(i));
			ll = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);
			if(i!=0){
				ll.addRule(RelativeLayout.BELOW, i);
			}
			r.addView(t, ll);
		}
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent in = new Intent(getApplicationContext(),
				Order_info.class)
				.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(in);
		super.onBackPressed();
	}
}
