package net.servi.fragment;

import net.servi.R;
import android.app.DialogFragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;

public class DialogFragmentInfo extends DialogFragment implements
		OnClickListener {

	private View view;
	private ImageButton btnClose;

	public DialogFragmentInfo() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
		view = inflater.inflate(R.layout.dialog_fragment_info, container);
		btnClose = (ImageButton) view.findViewById(R.id.btn_close_dialog_info);
		btnClose.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View arg0) {
		this.dismiss();
	}
}
