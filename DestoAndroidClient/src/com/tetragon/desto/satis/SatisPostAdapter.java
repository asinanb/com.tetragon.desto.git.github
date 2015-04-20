package com.tetragon.desto.satis;

import com.google.cloud.backend.core.DbObjects;
import com.tetragon.desto.ListActivity;
import com.tetragon.desto.R;
import com.tetragon.desto.model.ModelItem;
import com.tetragon.desto.model.StokItem;
import com.tetragon.desto.model.StokItemList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * This ArrayAdapter uses CloudEntities as items and displays them as a post in
 * the model. Layout uses row.xml.
 *
 */
public class SatisPostAdapter extends ArrayAdapter<StokItem> {

	private LayoutInflater mInflater;
	private ListActivity listActivity;

	/**
	 * Creates a new instance of this adapter.
	 *
	 * @param context
	 * @param textViewResourceId
	 * @param listCE
	 */
	public SatisPostAdapter(Context context, int textViewResourceId,
			StokItemList pStokList) {
		super(context, textViewResourceId, pStokList);
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		setListActivity((ListActivity) context);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// View view = convertView != null ? convertView : mInflater.inflate(
		// R.layout.satis_post, parent, false);
		View rowView = convertView;
		final ViewHolder viewHolder;
		final StokItem stokItem = getItem(position);
		if (stokItem != null) {
			if (stokItem.getAdet() < 1)
				remove(stokItem);
			// reuse views
			if (rowView == null) {
				rowView = mInflater.inflate(R.layout.satis_post, parent, false);
				viewHolder = new ViewHolder();

				viewHolder.markaTextView = (TextView) rowView
						.findViewById(R.id.markaTextView);

				viewHolder.urunTipTextView = (TextView) rowView
						.findViewById(R.id.urunTipTextView);

				viewHolder.modelTextView = (TextView) rowView
						.findViewById(R.id.modelTextView);

				viewHolder.stokAdetTextView = (TextView) rowView
						.findViewById(R.id.stokAdetTextView);

				viewHolder.fiyatTextView = (TextView) rowView
						.findViewById(R.id.fiyatTextView);
				viewHolder.checkBox = (CheckBox) rowView
						.findViewById(R.id.checkBox);
				if (viewHolder.checkBox != null)
					viewHolder.checkBox.setChecked(stokItem.isSelected());
				rowView.setTag(viewHolder);
			} else
				viewHolder = (ViewHolder) convertView.getTag();

			viewHolder.checkBox.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (viewHolder.checkBox.isChecked()) {
						DbObjects.getSelectedStokList().add(stokItem);
						stokItem.setSelected(true);
					} else {
						DbObjects.getSelectedStokList().remove(stokItem);
						stokItem.setSelected(false);
					}
					getListActivity().getStokEventHandler().fireStokChanged();
				}

			});

			if ((viewHolder.modelTextView != null)
					&& (stokItem.getModelItem() != null)) {
				viewHolder.modelTextView
						.setText(stokItem.getModelItem().getModel());
			}

			ModelItem model = stokItem.getModelItem();

			if ((viewHolder.markaTextView != null)
					&& (model.getMarkaItem() != null)) {
				viewHolder.markaTextView.setText(model.getMarkaItem().getMarka());
			}
			if ((viewHolder.urunTipTextView != null)
					&& (model.getUrun_tipItem() != null)) {
				viewHolder.urunTipTextView.setText(" "
						+ model.getUrun_tipItem().getUrun_tip());
			}
			if (viewHolder.stokAdetTextView != null){
				viewHolder.stokAdetTextView
						.setText(stokItem.getAdet() + " Ad.");
			}
			if ((viewHolder.fiyatTextView != null)
					&& (model.getListeFiyati() != null)) {
				if (model.getListeFiyati().isEmpty())
					viewHolder.fiyatTextView.setText(" ");
				else
					viewHolder.fiyatTextView.setText(model.getListeFiyati() + " TL");
			}
			if (viewHolder.checkBox != null)
				viewHolder.checkBox.setChecked(stokItem.isSelected());
		}

		return rowView;
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

	static class ViewHolder {
		public TextView markaTextView;
		public TextView urunTipTextView;
		public TextView modelTextView;
		public TextView stokAdetTextView;
		public TextView fiyatTextView;
		public CheckBox checkBox;
	}
}
