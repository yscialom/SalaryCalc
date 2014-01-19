package eu.scialom.salarycalc;

import android.content.Context;
import android.content.res.Resources;
import android.text.Html;
import android.text.method.LinkMovementMethod;
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
		
		Resources res = getResources();
		
		// Description
		TextView tTitle= (TextView)rowView.findViewById(R.id.desc_title);
		TextView tDesc = (TextView)rowView.findViewById(R.id.desc_text);
		String html = res.getString(R.string.desc_text);
		String appName = res.getString(R.string.app_name);
		String appVersion = res.getString(R.string.app_version);
		String appYear = res.getString(R.string.app_year);
		String appAuthor = res.getString(R.string.app_author);
		String title = String.format(res.getString(R.string.desc_title), appName);
		tTitle.setText(title);
		tDesc.setText(Html.fromHtml(String.format(html,appName, appVersion, appYear, appAuthor)));
		tDesc.setMovementMethod(LinkMovementMethod.getInstance());

		// Thanks To
		String[] externalCredits = res.getStringArray(R.array.external_credits_list);
		LinearLayout le = (LinearLayout) rowView.findViewById(R.id.external_credits);
		for (String s : externalCredits) {
			TextView t = new TextView(context);
			t.setText(Html.fromHtml(s));
			t.setMovementMethod(LinkMovementMethod.getInstance());
			le.addView(t);
		}

		// Developpers
		String[] internalCredits = res.getStringArray(R.array.internal_credits_list);
		LinearLayout li = (LinearLayout) rowView.findViewById(R.id.internal_credits);
		for (String s : internalCredits) {
			TextView t = new TextView(context);
			t.setText(s);
			li.addView(t);
		}
	}

	@Override
	public String getShortName() {
		return this.getResources().getString(R.string.about_tab);
	}

	@Override
	public View getView() {
		return this;
	}

}
