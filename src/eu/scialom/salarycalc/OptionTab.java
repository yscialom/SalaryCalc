package eu.scialom.salarycalc;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class OptionTab extends RelativeLayout implements MyTab {

	public OptionTab(Context context) {
		super(context);
		final TextView t = new TextView(context);
		t.setText("Bye!\n" + this.getShortName());
		this.addView(t);
	}

	@Override
	public String getShortName() {
		return "Options";
	}

	@Override
	public View getView() {
		return this;
	}

}
