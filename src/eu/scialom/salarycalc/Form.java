package eu.scialom.salarycalc;

import java.util.Vector;

import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Form extends LinearLayout {

	private class Setting {
		public TextView name;
		public EditText value;
		public Button submit;

		@SuppressWarnings("unused")
		public Setting(Context context, String name, String defaultValue) {
			this.setup(context, name, defaultValue, "", null);
		}

		public Setting(Context context, String name, String defaultValue, String submit,
			OnClickListener cb) {
			this.setup(context, name, defaultValue, submit, cb);

		}

		private void setup(Context context, String name, String defaultValue, String submit,
			OnClickListener cb) {
			this.name = new TextView(context);
			this.name.setText(name);
			this.value = new EditText(context);
			this.value.setText(defaultValue);
			this.submit = new Button(context);
			this.submit.setText(submit);
			this.submit.setOnClickListener(cb);
		}
	}

	private String title;
	private final Vector<Setting> settings;

	public Form(Context context) {
		super(context);
		this.settings = new Vector<Setting>();
	}

	public void addTextField(String name, String defaultValue, String submit, OnClickListener cb) {
		this.settings.add(new Setting(this.getContext(), name, defaultValue, submit, cb));
		this.regenUI();
	}

	public String getTitle() {
		return this.title;
	}

	private void regenUI() {
		this.removeAllViews();
		switch (this.settings.size()) {
		case 0:
			return;
		case 1:
			this.setOrientation(LinearLayout.HORIZONTAL);
			break;
		default:
			this.setOrientation(LinearLayout.VERTICAL);
			final TextView t = new TextView(this.getContext());
			t.setText(this.title);
			this.addView(t);
			break;
		}
		for (final Setting s : this.settings) {
			this.addView(s.name);
			this.addView(s.value);
			if (null != s.submit)
				this.addView(s.submit);
		}
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
