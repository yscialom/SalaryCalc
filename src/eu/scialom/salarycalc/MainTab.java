package eu.scialom.salarycalc;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainTab extends RelativeLayout implements MyTab, OnClickListener {

	public MainTab(Context context) {
		super(context);

		final Form f = new Form(this.getContext());
		f.addTextField("Annual:", "14400", "Compute", this);
		this.addView(f);
	}

	@Override
	public String getShortName() {
		return "Calculator";
	}

	@Override
	public View getView() {
		return this;
	}

	@Override
	public void onClick(View arg0) {
		Toast.makeText(this.getContext(), " ~~ LOL ~~ ", Toast.LENGTH_LONG).show();
	}

}
