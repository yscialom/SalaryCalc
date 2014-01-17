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
			final View rowView = inflater.inflate(R.layout.option_tab_item, parent, false);
			final TextView name = (TextView) rowView.findViewById(R.id.name);
			final TextView desc = (TextView) rowView.findViewById(R.id.description);
			name.setText(this.values[position].name);
			desc.setText(this.values[position].description);
			return rowView;
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

	}

	public OptionTab(Context context) {
		super(context);

		final Option opt[] = new Option[] {
			new Option("TEXT", "This is a string", Option.TYPE_TEXT, "value"),
			new Option("INTEGER", "This is an integer", Option.TYPE_INTEGER, 42),
			new Option("FLOAT", "This is SPARTAAAAA!", Option.TYPE_FLOAT, -42.0f), };

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
	}
}
