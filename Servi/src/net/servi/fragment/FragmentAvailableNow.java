package net.servi.fragment;

import net.servi.R;
import net.servi.model.ModelAvailableNow;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class FragmentAvailableNow extends Fragment {

	private ListView lstAvailableRooms;
	private TextView txtNoAvailable;
	private ModelAvailableNow modelAvailableNow;
	private View view;
	
	public FragmentAvailableNow() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_available_now, container, false);
		init();
        return view;
	}
	
	@Override
	public void onResume() {
		if (modelAvailableNow.getSizeList()>0) {
			lstAvailableRooms.setAdapter(modelAvailableNow.getAdapterList());
		} else {
			lstAvailableRooms.setVisibility(View.GONE);
			txtNoAvailable.setVisibility(View.VISIBLE);
		}
		super.onResume();
	}
	
	private void init(){
		modelAvailableNow = new ModelAvailableNow();
		lstAvailableRooms = (ListView)view.findViewById(R.id.lst_available_rooms);
		txtNoAvailable = (TextView)view.findViewById(R.id.txt_no_availables);
	}
}
