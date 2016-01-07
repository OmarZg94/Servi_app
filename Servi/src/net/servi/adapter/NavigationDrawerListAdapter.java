package net.servi.adapter;

import java.util.ArrayList;

import net.servi.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NavigationDrawerListAdapter extends BaseAdapter {

	private Context context;
    private ArrayList<String> navDrawerTitles;
	
	public NavigationDrawerListAdapter(Context context, ArrayList<String> navTitles) {
		this.context = context;
		navDrawerTitles = navTitles;
	}

	@Override
	public int getCount() {
		return navDrawerTitles.size();
	}

	@Override
	public Object getItem(int position) {
		return navDrawerTitles.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.drawer_list_item, null);
        }
          
//        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
//        TextView txtCount = (TextView) convertView.findViewById(R.id.counter);
          
//        imgIcon.setImageResource(navDrawerItems.get(position).getIcon());        
        txtTitle.setText(navDrawerTitles.get(position));
         
        // displaying count
        // check whether it set visible or not
//        if(navDrawerItems.get(position).getCounterVisibility()){
//            txtCount.setText(navDrawerItems.get(position).getCount());
//        }else{
            // hide the counter view
//            txtCount.setVisibility(View.GONE);
//        }
         
        return convertView;
	}

}
