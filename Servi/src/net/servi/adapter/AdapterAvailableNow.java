package net.servi.adapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import net.servi.R;
import net.servi.context.ContextApp;
import net.servi.dto.DtoHorario;
import net.servi.util.Config;
import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

@SuppressLint("SimpleDateFormat")
public class AdapterAvailableNow extends BaseAdapter /*
													 * implements
													 * FilterableCustom
													 */{

	private List<DtoHorario> list;
	// private List<DtoHorario> mOriginalValues;
	private LayoutInflater layoutInflater;
	private Calendar now;
	private DateFormat dateFormat;

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0).getId();
	}

	public AdapterAvailableNow(List<DtoHorario> list) {
		layoutInflater = LayoutInflater.from(ContextApp.context);
		this.list = list;
		now = Calendar.getInstance(TimeZone
				.getTimeZone("America/Mexico_City"));
		dateFormat = new SimpleDateFormat("hh:mm a");
		dateFormat.setTimeZone(TimeZone.getTimeZone("America/Mexico_City"));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		TextView txtRoom;
		TextView txtUntil;
		if (v == null) {
			v = layoutInflater.inflate(R.layout.row_available_now, parent,
					false);
			txtRoom = (TextView) v.findViewById(R.id.txt_room_row);
			txtUntil = (TextView) v.findViewById(R.id.txt_until_row);

			v.setTag(new ViewHolderAvailableNow(txtRoom, txtUntil));

		} else {
			ViewHolderAvailableNow vh = (ViewHolderAvailableNow) v.getTag();
			txtRoom = vh.txtRoom;
			txtUntil = vh.txtUntil;
		}
		DtoHorario dto = (DtoHorario) getItem(position);
		txtRoom.setTag(dto);
		txtUntil.setTag(dto);

		txtRoom.setText(dto.getSalon() + "");
		// Calendar now = Calendar.getInstance(Locale.getDefault());
		int actualHour = now.get(Calendar.HOUR);
		int actualMinute = now.get(Calendar.MINUTE);
		Log.e(Config.TAG, "@dto.getDay(): " + dto.getDay());
		int hourClass = Integer.parseInt(dto.getDay().substring(0, 2));
		int minuteClass = Integer.parseInt(dto.getDay().substring(3, 5));
		DateFormat inputFormat = new SimpleDateFormat("HH:mm:ss");
		String hour="";
		try {
			hour = dateFormat.format(inputFormat.parse(dto.getDay()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.e(Config.TAG, "@actualHour: " + actualHour + " actualMinute: "
				+ actualMinute + "\nhourClass: " + hourClass + " minuteClass: "
				+ minuteClass);
		if (dto.getDay() != null) {
			if (actualHour < hourClass) {
				txtUntil.setText(hour);
			} else if (actualHour == hourClass & actualMinute < minuteClass) {
				txtUntil.setText(hour);
			} else {
				txtUntil.setText(R.string.tomorrow);
			}
		} else {
			txtUntil.setText(R.string.tomorrow);
		}
		return v;
	}

	private class ViewHolderAvailableNow {

		TextView txtRoom, txtUntil;

		public ViewHolderAvailableNow(TextView txtRoom, TextView txtUntil) {
			super();
			this.txtRoom = txtRoom;
			this.txtUntil = txtUntil;
		}
	}

	// @Override
	// public FilterCustom getFilter() {
	// FilterCustom filter = new FilterCustom() {
	//
	// @Override
	// protected void publishResults(CharSequence constraint,FilterResults
	// results) {
	// list= (List<DtoCaducidad>) results.values; // has the filtered values
	// notifyDataSetChanged(); // notifies the data with new filtered values
	// }
	//
	// @Override
	// protected FilterResults performFiltering(CharSequence constraint,Object
	// obj) {
	// String filtcategory=(String)obj;
	// FilterResults results = new FilterResults(); // Holds the results of a
	// filtering operation in values
	// List<DtoCaducidad> FilteredArrList = new ArrayList<DtoCaducidad>();
	// if (mOriginalValues == null) {
	// mOriginalValues = new ArrayList<DtoCaducidad>(list); // saves the
	// original data in mOriginalValues
	// }
	//
	// /********
	// *
	// * If constraint(CharSequence that is received) is null returns the
	// mOriginalValues(Original) values
	// * else does the Filtering and returns FilteredArrList(Filtered)
	// *
	// ********/
	// if(constraint==null) constraint="";
	// if(filtcategory==null||filtcategory.equals("NA")) filtcategory="";
	//
	// if (filtcategory.equals("")&&constraint.equals("")) {
	// // set the Original result to return
	// results.count = mOriginalValues.size();
	// results.values = mOriginalValues;
	// } else {
	// constraint = constraint.toString().toLowerCase().trim();
	// filtcategory= filtcategory.toString().toLowerCase().trim();
	// for (int i = 0; i < mOriginalValues.size(); i++) {
	// String data = mOriginalValues.get(i).getSku();
	// String category=mOriginalValues.get(i).getCategory();
	//
	// if(filtcategory.isEmpty()){
	// if (data.toLowerCase().contains(constraint)) {
	// FilteredArrList.add(mOriginalValues.get(i));
	// }
	// }else{
	// if (data.toLowerCase().contains(constraint)
	// &&category.toLowerCase().equals(filtcategory)) {
	// FilteredArrList.add(mOriginalValues.get(i));
	// }
	// }
	// }
	// // set the Filtered result to return
	// results.count = FilteredArrList.size();
	// results.values = FilteredArrList;
	//
	// }
	// return results;
	// }
	//
	//
	// };
	// return filter;
	// }
}
