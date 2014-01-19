package eu.scialom.salarycalc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class AboutTab extends ScrollView implements MyTab {

	public AboutTab(Context context) {
		super(context);

		final LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View rowView = inflater.inflate(R.layout.about_tab, this, false);
		this.addView(rowView);

		// Thanks To
		String[] externalCredits = {
			"ActionBarSherlock 4.4.0 --- actionbarsherlock.com",
			"NumberPicker --- github.com/michaelnovakjr/numberpicker"
		};
		LinearLayout le = (LinearLayout) rowView.findViewById(R.id.external_credits);
		for (String s : externalCredits) {
			TextView t = new TextView(context);
			t.setText(s);
			le.addView(t);
		}

		// Developpers
		String[] internalCredits = {
			"Yankel Scialom (YSC) <yankel.scialom@mail.com>"
		};
		LinearLayout li = (LinearLayout) rowView.findViewById(R.id.internal_credits);
		for (String s : internalCredits) {
			TextView t = new TextView(context);
			t.setText(s);
			li.addView(t);
		}
	}

	@Override
	public String getShortName() {
		return "About";
	}

	@Override
	public View getView() {
		return this;
	}

}
