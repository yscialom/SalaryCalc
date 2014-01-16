package eu.scialom.salarycalc;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockActivity;


public class MainActivity extends SherlockActivity implements ActionBar.TabListener {
	private RelativeLayout lMain;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create layout
		RelativeLayout lMain = new RelativeLayout(this);
		@SuppressWarnings("deprecation")
		RelativeLayout.LayoutParams lParams = new RelativeLayout.LayoutParams(
			RelativeLayout.LayoutParams.FILL_PARENT,
			RelativeLayout.LayoutParams.FILL_PARENT
		);
        setContentView(lMain, lParams);

        // Create tabs
        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
    	ActionBar.Tab mainTab = getSupportActionBar().newTab();
    	ActionBar.Tab optionTab = getSupportActionBar().newTab();
    	mainTab.setText("Calculator");
    	optionTab.setText("Options");
    	mainTab.setTabListener(this);
    	optionTab.setTabListener(this);
    	getSupportActionBar().addTab(mainTab);
    	getSupportActionBar().addTab(optionTab);
    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction transaction) {

    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction transaction) {

    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction transaction) {

    }
}