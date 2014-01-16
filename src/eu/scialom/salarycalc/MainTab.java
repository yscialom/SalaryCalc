package eu.scialom.salarycalc;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainTab extends RelativeLayout implements MyTab {

	public MainTab(Context context) {
		super(context);
		TextView t = new TextView(context);
		t.setText("Hello, World!\n" + getShortName());
		this.addView(t);
	}

	@Override
	public String getShortName() {
		return "Calculator";
	}

	@Override
	public View getView() {
		return this;
	}

}
