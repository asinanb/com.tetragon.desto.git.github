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

public class DbMenuFragment extends Fragment implements OnClickListener {

	 public static final String TAG = "DBMENU";
	/*
     * UI components
     */
	private ImageView markalarLink;
	private ImageView urunGrupLink;
	private ImageView urunlerLink;
	private ImageView modellerLink;
	private ImageView stok_yeriLink;
	private ImageView musteriLink;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view= inflater.inflate(R.layout.db_menu, container,false);
		getActivity().setTitle(R.string.title_db);
		markalarLink = (ImageView) view.findViewById(R.id.markalarView);
		markalarLink.setOnClickListener(this);
		musteriLink = (ImageView) view.findViewById(R.id.musteriView);
		musteriLink.setOnClickListener(this);
		urunGrupLink = (ImageView) view.findViewById(R.id.urungrubuView);
		urunGrupLink.setOnClickListener(this);
		urunlerLink = (ImageView) view.findViewById(R.id.urunlerView);
		urunlerLink.setOnClickListener(this);
		modellerLink = (ImageView) view.findViewById(R.id.modellerView);
		modellerLink.setOnClickListener(this);
		stok_yeriLink = (ImageView) view.findViewById(R.id.stok_yeriView);
		stok_yeriLink.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(getActivity(), ListActivity.class);
		if (v.getId()==R.id.markalarView)			
			intent.putExtra("TAG", DestoConstants.MARKA);
		if (v.getId()==R.id.urungrubuView)
			intent.putExtra("TAG", DestoConstants.URUNGRUBU);
		if (v.getId()==R.id.urunlerView)
			intent.putExtra("TAG", DestoConstants.URUN_TIP);	
		if (v.getId()==R.id.modellerView)
			intent.putExtra("TAG", DestoConstants.MODEL);
		if (v.getId()==R.id.stok_yeriView)
			intent.putExtra("TAG", DestoConstants.STOK_YERI);
		if (v.getId()==R.id.musteriView)
			intent.putExtra("TAG", DestoConstants.MUSTERI);
		
		startActivity(intent);   
	}

}