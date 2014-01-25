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

		this.showTab(0);
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
		for (final MyTab it : this.tabs)
			it.getView().setVisibility(View.GONE);
		this.tabs.get(position).getView().setVisibility(View.VISIBLE);
	}
}