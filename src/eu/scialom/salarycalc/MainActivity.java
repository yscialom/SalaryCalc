package eu.scialom.salarycalc;

import java.util.Vector;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;

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
		final RelativeLayout.LayoutParams lParams = new RelativeLayout.LayoutParams(
			LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		this.setContentView(this.lMain, lParams);

		// Create tabs
		this.tabs = new Vector<MyTab>();
		this.tabs.add(new MainTab(this));
		this.tabs.add(new OptionTab(this));

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
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction transaction) {

	}

	private void showTab(int position) {
		System.out.println("showTab(" + position + ")");
		for (final MyTab it : this.tabs)
			it.getView().setVisibility(View.GONE);
		this.tabs.get(position).getView().setVisibility(View.VISIBLE);
	}
}