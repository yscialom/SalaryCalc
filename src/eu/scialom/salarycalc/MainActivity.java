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
 * \file ./src/eu/scialom/salarycalc/MainActivity.java
 */
package eu.scialom.salarycalc;

import java.util.Vector;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockActivity;

public class MainActivity extends SherlockActivity implements ActionBar.TabListener {
	private RelativeLayout lMain;
	private Vector<MyTab> tabs;
	private int curTab;
	public static Activity activity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = this;

		// Create layout
		this.lMain = new RelativeLayout(this);
		@SuppressWarnings("deprecation")
		final RelativeLayout.LayoutParams lParams = new RelativeLayout.LayoutParams(
			LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		this.setContentView(this.lMain, lParams);

		// Create tabs
		this.tabs = new Vector<MyTab>();
		this.tabs.add(new MainTab(this));
		this.tabs.add(new OptionTab(this));
		this.tabs.add(new AboutTab(this));

		this.getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		for (final MyTab it : this.tabs) {
			final ActionBar.Tab t = this.getSupportActionBar().newTab();
			t.setText(it.getShortName());
			t.setTabListener(this);
			this.getSupportActionBar().addTab(t);

			this.lMain.addView(it.getView());
		}

		if (null != savedInstanceState)
			this.curTab = savedInstanceState.getInt("tab", 0);
		this.getSupportActionBar().setSelectedNavigationItem(this.curTab);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("tab", this.curTab);
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction transaction) {

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction transaction) {
		this.showTab(tab.getPosition());
		final View v = this.getWindow().getDecorView();
		if (null == v)
			return;
		final InputMethodManager imm = (InputMethodManager) this
			.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction transaction) {

	}

	private void showTab(int position) {
		this.curTab = position;
		for (final MyTab it : this.tabs)
			it.getView().setVisibility(View.GONE);
		this.tabs.get(position).getView().setVisibility(View.VISIBLE);
	}
}
