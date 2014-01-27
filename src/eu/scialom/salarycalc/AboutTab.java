/*
 * SalaryCalc: android app to help users convert salary from/to many formats.
 * Copyright (C) 2014 Yankel Scialom.
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 * 
 * \author Yankel Scialom (YSC) <yankel.scialom@mail.com>
 * \file ./src/eu/scialom/salarycalc/AboutTab.java
 */
package eu.scialom.salarycalc;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
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

		final Resources res = this.getResources();

		// Description
		final TextView tTitle = (TextView) rowView.findViewById(R.id.desc_title);
		final TextView tDesc = (TextView) rowView.findViewById(R.id.desc_text);
		final String html = res.getString(R.string.desc_text);
		final String appName = res.getString(R.string.app_name);
		String appVersion;
		try {
			appVersion = Integer.toString(context.getPackageManager().getPackageInfo(
				context.getPackageName(), 0).versionCode);
			appVersion = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
		} catch (final NameNotFoundException e) {
			appVersion = "?";
		}
		final String appYear = res.getString(R.string.app_year);
		final String appAuthor = res.getString(R.string.app_author);
		final String title = String.format(res.getString(R.string.desc_title), appName);
		tTitle.setText(title);
		tDesc.setText(Html.fromHtml(String.format(html, appName, appVersion, appYear, appAuthor)));
		tDesc.setMovementMethod(LinkMovementMethod.getInstance());

		// Thanks To
		final String[] externalCredits = res.getStringArray(R.array.external_credits_list);
		final LinearLayout le = (LinearLayout) rowView.findViewById(R.id.external_credits);
		for (final String s : externalCredits) {
			final TextView t = new TextView(context);
			t.setText(Html.fromHtml(s));
			t.setMovementMethod(LinkMovementMethod.getInstance());
			le.addView(t);
		}

		// Developpers
		final String[] internalCredits = res.getStringArray(R.array.internal_credits_list);
		final LinearLayout li = (LinearLayout) rowView.findViewById(R.id.internal_credits);
		for (final String s : internalCredits) {
			final TextView t = new TextView(context);
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
