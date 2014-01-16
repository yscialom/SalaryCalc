package eu.scialom.salarycalc;

import java.util.Vector;

import android.content.Context;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Form extends LinearLayout {

	private String title;
	private final Vector<EditText> edits;

	public Form(Context context) {
		super(context);
		this.edits = new Vector<EditText>();
	}

	public void addTextField(String defaultValue) {
		final EditText e = new EditText(this.getContext());
		e.setText(defaultValue);
		this.edits.add(e);
		this.regenUI();
	}

	public String getTitle() {
		return this.title;
	}

	private void regenUI() {
		this.removeAllViews();
		switch (this.edits.size()) {
		case 0:
			return;
		case 1:
			this.setOrientation(LinearLayout.HORIZONTAL);
			break;
		default:
			this.setOrientation(LinearLayout.VERTICAL);
			break;
		}
		final TextView t = new TextView(this.getContext());
		t.setText(this.title);
		this.addView(t);
		for (final EditText e : this.edits)
			this.addView(e);
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
