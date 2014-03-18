package be.webfactor.openinghours.adapter;

import java.util.List;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import be.webfactor.openinghours.R;
import be.webfactor.openinghours.domain.Business;

public class BusinessAdapter extends ArrayAdapter<Business> {

	public BusinessAdapter(Context context, List<Business> values) {
		super(context, R.layout.result_row, values);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.result_row, parent, false);

		Business current = getItem(position);

		TextView openIcon = (TextView) rowView.findViewById(R.id.openIcon);
		TextView closedIcon = (TextView) rowView.findViewById(R.id.closedIcon);
		if (current.isOpen()) {
			closedIcon.setVisibility(View.GONE);
		} else {
			openIcon.setVisibility(View.GONE);
		}

		TextView name = (TextView) rowView.findViewById(R.id.name);
		name.setText(current.getName());

		TextView category = (TextView) rowView.findViewById(R.id.category);
		category.setText(current.getCategory());

		TextView city = (TextView) rowView.findViewById(R.id.city);
		city.setText(current.getCity());

		return rowView;
	}

	@TargetApi(11)
	public void addBusinesses(List<Business> businesses) {
		if (businesses != null) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
				addAll(businesses);
			} else {
				for (Business business : businesses) {
					add(business);
				}
			}
		}
	}

}
