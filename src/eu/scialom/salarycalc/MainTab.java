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
 * \file ./src/eu/scialom/salarycalc/MainTab.java
 */
package eu.scialom.salarycalc;

import java.util.Vector;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class MainTab extends ScrollView implements MyTab, OnClickListener {

	private class Line implements OnEditorActionListener {
		Button b;
		EditText e;

		Line(View parent, int b, int e) {
			this.b = (Button) parent.findViewById(b);
			this.e = (EditText) parent.findViewById(e);
			if (null == this.b)
				throw new NotFoundException("Cant find resource #" + b + " in view '"
					+ parent.toString() + "'.");
			if (null == this.e)
				throw new NotFoundException("Cant find resource #" + e + " in view '"
					+ parent.toString() + "'.");
			this.e.setOnEditorActionListener(this);
		}

		@Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			boolean handled = false;
			switch (actionId) {
			case EditorInfo.IME_ACTION_DONE:
				handled = this.b.performClick();
				break;
			}
			return handled;
		}
	}

	private final Vector<Line> lines;
	private final Button reset;
	public static Calculator calc;

	public MainTab(Context context) {
		super(context);

		final LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View rowView = inflater.inflate(R.layout.main_tab, this, false);
		this.addView(rowView);

		MainTab.calc = new Calculator();

		this.lines = new Vector<Line>();
		this.lines.add(new Line(rowView, R.id.annual_bt, R.id.annual_bt_value));
		this.lines.add(new Line(rowView, R.id.annual_at, R.id.annual_at_value));
		this.lines.add(new Line(rowView, R.id.monthly_bt, R.id.monthly_bt_value));
		this.lines.add(new Line(rowView, R.id.monthly_at, R.id.monthly_at_value));
		this.lines.add(new Line(rowView, R.id.hourly_bt, R.id.hourly_bt_value));
		this.lines.add(new Line(rowView, R.id.hourly_at, R.id.hourly_at_value));

		for (final Line l : this.lines)
			l.b.setOnClickListener(this);

		this.reset = (Button) rowView.findViewById(R.id.reset);
		this.reset.setOnClickListener(this);
	}

	@Override
	public String getShortName() {
		return this.getResources().getString(R.string.main_tab);
	}

	@Override
	public View getView() {
		return this;
	}

	private void hideKeyboard(View v) {
		if (null == v)
			return;
		final InputMethodManager imm = (InputMethodManager) MainActivity.activity
			.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
	}

	@Override
	public void onClick(View v) {
		int position = -1;
		float value = 0f;
		this.hideKeyboard(v);

		// Special case: clicked on reset?
		if (this.reset == v) {
			this.onReset();
			return;
		}

		// Find input format
		for (final Line l : this.lines)
			if (l.b == v) {
				position = this.lines.indexOf(l);
				try {
					value = Float.valueOf(l.e.getText().toString());
				} catch (final NumberFormatException e) {
					value = 0f;
				}
			}

		// Update Calculator base
		switch (position) {
		case 0:
			MainTab.calc.setAnnualBT((int) value);
			break;
		case 1:
			MainTab.calc.setAnnualAT((int) value);
			break;
		case 2:
			MainTab.calc.setMonthlyBT((int) value);
			break;
		case 3:
			MainTab.calc.setMonthlyAT((int) value);
			break;
		case 4:
			MainTab.calc.setHourlyBT(value);
			break;
		case 5:
			MainTab.calc.setHourlyAT(value);
			break;
		}

		// Update output formats
		this.lines.get(0).e.setText(Integer.toString(MainTab.calc.getAnnualBT()));
		this.lines.get(1).e.setText(Integer.toString(MainTab.calc.getAnnualAT()));
		this.lines.get(2).e.setText(Integer.toString(MainTab.calc.getMonthlyBT()));
		this.lines.get(3).e.setText(Integer.toString(MainTab.calc.getMonthlyAT()));
		this.lines.get(4).e.setText(String.format("%.2f", MainTab.calc.getHourlyBT()));
		this.lines.get(5).e.setText(String.format("%.2f", MainTab.calc.getHourlyAT()));
	}

	private void onReset() {
		for (final Line l : this.lines)
			l.e.setText("");
	}

}
