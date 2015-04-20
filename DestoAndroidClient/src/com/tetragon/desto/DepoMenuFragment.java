package com.tetragon.desto; 

import com.tetragon.desto.util.DestoConstants;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

public class DepoMenuFragment extends Fragment implements OnClickListener {

	 public static final String TAG = "DBMENU";
	/*
     * UI components
     */
	private ImageView depoUrunGirLink;
	private ImageView depourunCikLink;
	private ImageView depoRaporLink;
	private ImageView depUrunListLink;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view= inflater.inflate(R.layout.depo_menu, container,false);
		getActivity().setTitle(R.string.title_depomenu);
		depoUrunGirLink = (ImageView) view.findViewById(R.id.depoUrunGirisView);
		depoUrunGirLink.setOnClickListener(this);
		depourunCikLink = (ImageView) view.findViewById(R.id.depoUrunCikisView);
		depourunCikLink.setOnClickListener(this);
		depoRaporLink = (ImageView) view.findViewById(R.id.depoRaporView);
		depoRaporLink.setOnClickListener(this);
		depUrunListLink = (ImageView) view.findViewById(R.id.depoUrunListView);
		depUrunListLink.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(getActivity(), ListActivity.class);
		if (v.getId()==R.id.depoUrunGirisView)			
			intent.putExtra("TAG", DestoConstants.DEPOURUNGIR);
		if (v.getId()==R.id.depoUrunCikisView)
			intent.putExtra("TAG", DestoConstants.DEPOURUNCIK);
		if (v.getId()==R.id.depoRaporView)
			intent.putExtra("TAG", DestoConstants.DEPORAPOR);	
		if (v.getId()==R.id.depoUrunListView)
			intent.putExtra("TAG", DestoConstants.DEPOURUNLIST);	
		
		startActivity(intent);   
	}

}