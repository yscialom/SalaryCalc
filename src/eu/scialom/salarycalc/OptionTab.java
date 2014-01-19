package eu.scialom.salarycalc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.michaelnovakjr.numberpicker.NumberPickerDialog;

import eu.scialom.salarycalc.Calculator.Settings;

public class OptionTab extends ListView implements MyTab, OnItemClickListener {

	public class Adapter extends ArrayAdapter<Option> {
		private final Context context;

		private final Option[] values;

		public Adapter(Context context, Option[] values) {
			super(context, R.layout.option_tab_item, values);
			this.context = context;
			this.values = values;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final LayoutInflater inflater = (LayoutInflater) this.context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final Option opt = this.values[position];
			final View rowView = inflater.inflate(R.layout.option_tab_item, parent, false);
			final TextView name = (TextView) rowView.findViewById(R.id.name);
			final TextView desc = (TextView) rowView.findViewById(R.id.description);
			final TextView value = (TextView) rowView.findViewById(R.id.value);
			name.setText(opt.name);
			desc.setText(opt.description);
			switch (opt.type) {
			case Option.TYPE_TEXT:
				value.setText((String)opt.value);
				break;
			case Option.TYPE_INTEGER:
				value.setText(Integer.toString((Integer)opt.value));
				break;
			case Option.TYPE_FLOAT:
				Float v = (Float)opt.value;
				value.setText(String.format("%.0f%%", 100*v));
				break;
			}

			return rowView;
		}

		@Override
		public void notifyDataSetChanged() {
			super.notifyDataSetChanged();
			final Calculator c = MainTab.calc;
			Settings s = c.getSettings();
			s.taxRate = 1.0f - ((Float) this.getItem(0).value);
			s.hourPerWeek = (Integer) this.getItem(1).value;
			s.monthsPerYear = (Integer) this.getItem(2).value;
			c.setSettings(s);
		}
	}

	public class Option {
		public static final int TYPE_TEXT = 0x1;
		public static final int TYPE_INTEGER = 0x2;
		public static final int TYPE_FLOAT = 0x4;

		public String name;
		public String description;
		public int type;
		public Object value;

		public Option(String name, String description, int type, Object value) {
			this.name = name;
			this.description = description;
			this.type = type;
			this.value = value;
		}

		public void input(Context context, Adapter adapter) {
			switch (this.type) {
			case TYPE_TEXT:
				break;
			case TYPE_INTEGER:
				this.inputInteger(context, adapter);
				break;
			case TYPE_FLOAT:
				this.inputRate(context, adapter);
				break;
			}
		}

		private void inputInteger(Context c, final Adapter adapter) {
			final Option self = this;
			final NumberPickerDialog.OnNumberSetListener listener = new NumberPickerDialog.OnNumberSetListener() {
				@Override
				public void onNumberSet(int selectedNumber) {
					self.value = selectedNumber;
					adapter.notifyDataSetChanged();
				}
			};
			final NumberPickerDialog dialog = new NumberPickerDialog(c, -1, (Integer) this.value,
				"Update " + this.name + " value", "OK", "Cancel (back to " + this.value + ")");
			dialog.getNumberPicker().setRange(1, 24 * 7);
			dialog.setOnNumberSetListener(listener);
			dialog.show();
		}

		private void inputRate(Context c, final Adapter adapter) {
			final Option self = this;
			final NumberPickerDialog.OnNumberSetListener listener = new NumberPickerDialog.OnNumberSetListener() {
				@Override
				public void onNumberSet(int selectedNumber) {
					self.value = selectedNumber / 100.0f;
					adapter.notifyDataSetChanged();
				}
			};
			final int rate = (int) ((Float) this.value * 100.0f);
			final NumberPickerDialog dialog = new NumberPickerDialog(c, -1, rate, "Update "
				+ this.name + " value", "OK", "Cancel (back to " + rate + "%)");
			dialog.getNumberPicker().setRange(0, 100);
			dialog.setOnNumberSetListener(listener);
			dialog.show();
		}
	}

	public OptionTab(Context context) {
		super(context);

		Settings s = MainTab.calc.getSettings();
		final Option opt[] = new Option[] {
			new Option("Tax rate", "The part of the salary the employee have to pay, in percent.",
				Option.TYPE_FLOAT, 1.0f - s.taxRate),
			new Option("Hours per week", "The number of hour worked in a typical week.",
				Option.TYPE_INTEGER, s.hourPerWeek),
			new Option("Months per year",
				"Number of administrative months the year is split into.", Option.TYPE_INTEGER, s.monthsPerYear) };

		final Adapter data = new Adapter(context, opt);
		this.setAdapter(data);
		this.setOnItemClickListener(this);
	}

	@Override
	public String getShortName() {
		return "Options";
	}

	@Override
	public View getView() {
		return this;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View sender, int position, long lposition) {
		final Option o = (Option) this.getAdapter().getItem(position);
		final Context c = this.getContext();
		final Adapter a = (Adapter) this.getAdapter();
		o.input(c, a);
	}
}
