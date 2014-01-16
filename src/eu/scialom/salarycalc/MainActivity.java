package eu.scialom.salarycalc;

import java.util.Vector;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockActivity;

public class MainActivity extends SherlockActivity implements ActionBar.TabListener {
	private RelativeLayout lMain;
	private Vector<MyTab> tabs;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Create layout
		this.lMain = new RelativeLayout(this);
		@SuppressWarnings("deprecation")
		RelativeLayout.LayoutParams lParams = new RelativeLayout.LayoutParams(
			RelativeLayout.LayoutParams.FILL_PARENT,
			RelativeLayout.LayoutParams.FILL_PARENT
		);
		setContentView(lMain, lParams);

		// Create tabs
		tabs = new Vector<MyTab>();
		tabs.add(new MainTab(this));
		tabs.add(new OptionTab(this));

		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		for (MyTab it : tabs) {
			ActionBar.Tab t = getSupportActionBar().newTab();
			t.setText(it.getShortName());
			t.setTabListener(this);
			getSupportActionBar().addTab(t);

			this.lMain.addView(it.getView());
		}

		showTab(0);
	}

	private void showTab(int position) {
		System.out.println("showTab(" + position + ")");
		for (MyTab it : tabs)
			it.getView().setVisibility(View.GONE);
		tabs.get(position).getView().setVisibility(View.VISIBLE);
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction transaction) {

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction transaction) {
		showTab(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction transaction) {

	}
}