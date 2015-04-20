package com.tetragon.desto.test;

import com.tetragon.desto.R;

import android.os.Bundle;
import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.TextView;
import android.widget.Toast;

public class TestFragment extends Fragment {
	
	private SlidingDrawer drawer;
	ImageView handle;
	private Button clickMe;
	private TextView text1;
	private Context context;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view= inflater.inflate(R.layout.test_activity, container,false);
		
		context = this.getActivity();
		
		handle = (ImageView) view.findViewById(R.id.handle);
		text1 = (TextView) view.findViewById(R.id.text1);
		clickMe = (Button) view.findViewById(R.id.click);
		drawer=(SlidingDrawer) view.findViewById(R.id.slidingDrawer);
		
		drawer.setOnDrawerOpenListener(new OnDrawerOpenListener() {
			@Override
			public void onDrawerOpened() {
//				handle.setText("-");
				handle.setImageResource(R.drawable.ic_action_expand);
				text1.setText("Already dragged...");
			}
		});
		
		drawer.setOnDrawerCloseListener(new OnDrawerCloseListener() {
			
			@Override
			public void onDrawerClosed() {
//				handle.setText("+");
				handle.setImageResource(R.drawable.ic_action_collapse);
				text1.setText("For more info drag the button...");
			}
		});
		
		clickMe.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(context, "The button is clicked", Toast.LENGTH_LONG).show();
			}
		});
		return view;
	}	

}