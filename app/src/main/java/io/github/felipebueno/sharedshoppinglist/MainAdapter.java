package io.github.felipebueno.sharedshoppinglist;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import sneer.android.PartnerSession;


public class MainAdapter extends ArrayAdapter<Item> {

	public MainAdapter(Context context, ArrayList<Item> items) {
		super(context, R.layout.row_layout, items);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(getContext());
		View v = inflater.inflate(R.layout.row_layout, parent, false);

		final Item item = getItem(position);
		final TextView tv = (TextView) v.findViewById(R.id.tv_item);
		final CheckBox cb = (CheckBox) v.findViewById(R.id.chk_done);
		final ImageButton btnDeleteItem = (ImageButton) v.findViewById(R.id.btn_delete_item);

		tv.setText(item.name);
		cb.setChecked(item.isDone);
		strikeThrough(item, tv);

		cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				ArrayList<String> payload = new ArrayList<String>();
				payload.add(isChecked ? "check" : "uncheck");
				payload.add(item.name);
				((MainActivity)getContext()).sendToSession(payload);
			}
		});

		btnDeleteItem.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ArrayList<String> payload = new ArrayList<String>();
				payload.add("remove");
				payload.add(item.name);
				((MainActivity)getContext()).sendToSession(payload);
			}
		});
		return v;
	}

	private void strikeThrough(Item item, TextView tv) {
		if (item.isDone) {
			tv.setPaintFlags(tv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
			tv.setTextColor(Color.LTGRAY);
		} else {
			tv.setPaintFlags(0);
			tv.setTextColor(Color.DKGRAY);
		}
	}
}
