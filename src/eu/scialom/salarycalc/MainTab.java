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
			this.value.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
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
	public Calculator calc;

	public MainTab(Context context) {
		super(context);

		this.calc = new Calculator();

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

		// Find input format
		for (final Format f : this.formats)
			if (f.submit == v) {
				position = this.formats.indexOf(f);
				value = Float.valueOf(f.value.getText().toString());
			}

		// Update Calculator base
		switch (position) {
		case 0:
			this.calc.setAnnualBT((int) value);
			break;
		case 1:
			this.calc.setAnnualAT((int) value);
			break;
		case 2:
			this.calc.setMonthlyBT((int) value);
			break;
		case 3:
			this.calc.setMonthlyAT((int) value);
			break;
		case 4:
			this.calc.setHourlyBT(value);
			break;
		case 5:
			this.calc.setHourlyAT(value);
			break;
		}

		// Update output formats
		this.formats.get(0).value.setText(Integer.toString(this.calc.getAnnualBT()));
		this.formats.get(1).value.setText(Integer.toString(this.calc.getAnnualAT()));
		this.formats.get(2).value.setText(Integer.toString(this.calc.getMonthlyBT()));
		this.formats.get(3).value.setText(Integer.toString(this.calc.getMonthlyAT()));
		this.formats.get(4).value.setText(Float.toString(this.calc.getHourlyBT()));
		this.formats.get(5).value.setText(Float.toString(this.calc.getHourlyAT()));
	}

	private void regenUI() {
		this.removeAllViews();
		for (final Format f : this.formats)
			this.addView(f);
	}

}
