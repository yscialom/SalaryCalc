package eu.scialom.salarycalc;

import java.util.Vector;

import android.content.Context;
import android.text.InputType;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainTab extends LinearLayout implements MyTab, OnClickListener {

	private class Format extends LinearLayout {
		public TextView name;
		public EditText value;
		public Button submit;
		private final int displayWidth;

		@SuppressWarnings("deprecation")
		public Format(Context context, String name, String defaultValue, OnClickListener cb) {
			super(context);

			final WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
			final Display display = wm.getDefaultDisplay();
			this.displayWidth = display.getWidth();

			this.setOrientation(LinearLayout.HORIZONTAL);
			this.name = new TextView(context);
			this.name.setText(name);
			this.name.setMinimumWidth(this.pixelWidthFromPerCent(20));
			this.addView(this.name);
			this.value = new EditText(context);
			this.value.setInputType(InputType.TYPE_CLASS_NUMBER
				| InputType.TYPE_NUMBER_FLAG_DECIMAL);
			this.value.setText(defaultValue);
			this.value.setMinimumWidth(this.pixelWidthFromPerCent(60));
			this.addView(this.value);
			this.submit = new Button(context);
			this.submit.setText("Compute");
			this.submit.setMinimumWidth(this.pixelWidthFromPerCent(20));
			this.submit.setOnClickListener(cb);
			this.addView(this.submit);
		}

		private int pixelWidthFromPerCent(int percentage) {
			return percentage * this.displayWidth / 100;
		}
	}

	private final Vector<Format> formats;
	private final Button reset;
	public static Calculator calc;

	public MainTab(Context context) {
		super(context);

		MainTab.calc = new Calculator();

		this.reset = new Button(this.getContext());
		this.reset.setText("Reset");
		this.reset.setOnClickListener(this);

		this.setOrientation(LinearLayout.VERTICAL);
		this.formats = new Vector<Format>();
		this.formats.add(new Format(this.getContext(), "Annual (BT):", "", this));
		this.formats.add(new Format(this.getContext(), "Annual (AT):", "", this));
		this.formats.add(new Format(this.getContext(), "Monthly (BT):", "", this));
		this.formats.add(new Format(this.getContext(), "Monthly (AT):", "", this));
		this.formats.add(new Format(this.getContext(), "Hourly (BT):", "", this));
		this.formats.add(new Format(this.getContext(), "Hourly (AT):", "", this));

		this.regenUI();
	}

	@Override
	public String getShortName() {
		return "Calculator";
	}

	@Override
	public View getView() {
		return this;
	}

	@Override
	public void onClick(View v) {
		int position = -1;
		float value = 0f;

		// Special case: clicked on reset?
		if (this.reset == v) {
			for (final Format f : this.formats)
				f.value.setText("");
			return;
		}

		// Find input format
		for (final Format f : this.formats)
			if (f.submit == v) {
				position = this.formats.indexOf(f);
				try {
					value = Float.valueOf(f.value.getText().toString());
				} catch (final NumberFormatException e) {
					value = 0f;
				}
			}

		// Update Calculator settings
		// this.get

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
		this.formats.get(0).value.setText(Integer.toString(MainTab.calc.getAnnualBT()));
		this.formats.get(1).value.setText(Integer.toString(MainTab.calc.getAnnualAT()));
		this.formats.get(2).value.setText(Integer.toString(MainTab.calc.getMonthlyBT()));
		this.formats.get(3).value.setText(Integer.toString(MainTab.calc.getMonthlyAT()));
		this.formats.get(4).value.setText(String.format("%.2f", MainTab.calc.getHourlyBT()));
		this.formats.get(5).value.setText(String.format("%.2f", MainTab.calc.getHourlyAT()));
	}

	private void regenUI() {
		this.removeAllViews();
		for (final Format f : this.formats)
			this.addView(f);
		this.addView(this.reset);
	}

}
