package eu.scialom.salarycalc;

import java.util.Vector;

import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainTab extends LinearLayout implements MyTab, OnClickListener {

	private class Format extends LinearLayout {
		public TextView name;
		public EditText value;
		public Button submit;

		public Format(Context context, String name, String defaultValue) {
			super(context);
			this.setOrientation(LinearLayout.HORIZONTAL);
			this.name = new TextView(context);
			this.name.setText(name);
			this.addView(this.name);
			this.value = new EditText(context);
			this.value.setInputType(InputType.TYPE_CLASS_NUMBER);
			this.value.setText(defaultValue);
			this.addView(this.value);
			this.submit = new Button(context);
			this.submit.setText("Compute");
			this.addView(this.submit);
		}
	}

	private final Vector<Format> formats;

	public MainTab(Context context) {
		super(context);

		this.setOrientation(LinearLayout.VERTICAL);
		this.formats = new Vector<Format>();
		this.formats.add(new Format(this.getContext(), "Annual (BT):", "14400"));
		this.formats.add(new Format(this.getContext(), "Annual (AT):", ""));
		this.formats.add(new Format(this.getContext(), "Monthly (BT):", ""));
		this.formats.add(new Format(this.getContext(), "Monthly (AT):", ""));
		this.formats.add(new Format(this.getContext(), "Hourly (BT):", ""));
		this.formats.add(new Format(this.getContext(), "Hourly (AT):", ""));

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
	public void onClick(View v) {/*
								 * Button sender = (Button) v; for (Form f :
								 * this.forms) { if (f.) }
								 */
		Toast.makeText(this.getContext(), " ~~ LOL ~~ ", Toast.LENGTH_LONG).show();
	}

	private void regenUI() {
		this.removeAllViews();
		for (final Format f : this.formats)
			this.addView(f);
	}

}
