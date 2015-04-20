package com.tetragon.desto;

import com.tetragon.desto.model.ModelItem;
import com.tetragon.desto.model.StokItem;
import com.tetragon.desto.model.Stok_yeri;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * This ArrayAdapter uses CloudEntities as items and displays them as a post in
 * the model. Layout uses row.xml.
 *
 */
public class StokPostAdapter extends ArrayAdapter<StokItem> {

	private LayoutInflater mInflater;
	private ListActivity listActivity;

	private int adet = 0;

	/**
	 * Creates a new instance of this adapter.
	 *
	 * @param context
	 * @param textViewResourceId
	 * @param listCE
	 */
	public StokPostAdapter(Context context, int textViewResourceId,
			List<StokItem> listCE) {
		super(context, textViewResourceId, listCE);
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		setListActivity((ListActivity) context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView != null ? convertView : mInflater.inflate(
				R.layout.stok_post, parent, false);

		final StokItem stokItem = getItem(position);
		if (stokItem != null) {
			// ilk degerler
			setAdet(stokItem);

			TextView markaTextView = (TextView) view
					.findViewById(R.id.markaTextView);

			TextView urunTextView = (TextView) view
					.findViewById(R.id.urunTipTextView);

			TextView modelTextView = (TextView) view
					.findViewById(R.id.modelTextView);

			TextView adetTextView = (TextView) view
					.findViewById(R.id.adetTextView);

			TextView stokYeriTextView = (TextView) view
					.findViewById(R.id.stokYeriTextView);
			
			ModelItem model = stokItem.getModelItem();

			if ((modelTextView != null) && (model != null)) {
				modelTextView.setText(model.getModel());
			}

			if ((markaTextView != null) && (model.getMarkaItem() != null)) {
				markaTextView.setText(model.getMarkaItem().getMarka());
			}
			if ((urunTextView != null) && (model.getUrun_tipItem() != null)) {
				urunTextView.setText(" " + model.getUrun_tipItem().getUrun_tip());
			}
			if (adetTextView != null){
				adetTextView.setText(String.valueOf(getAdet()));
			}
			
			Stok_yeri stok_yeri = stokItem.getStok_yeri();
			
			if (stokYeriTextView != null)
				stokYeriTextView.setText(stok_yeri.getStok_yeri());
			}
		
		return view;
	}
	
	public int getAdet() {
		return adet;
	}

	public void setAdet(int adet) {
		this.adet = adet;
	}

	public void setAdet(StokItem stok) {
		this.adet = stok.getAdet();
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return this.getView(position, convertView, parent);
	}

	public ListActivity getListActivity() {
		return listActivity;
	}

	public void setListActivity(ListActivity listActivity) {
		this.listActivity = listActivity;
	}
}
