package com.tetragon.desto.satis;

import com.tetragon.desto.R;
import com.tetragon.desto.SubMenuActivity;
import com.tetragon.desto.model.ModelItem;
import com.tetragon.desto.model.SatisItem;
import com.tetragon.desto.model.StokItem;
import com.tetragon.desto.model.StokItemList;
import com.tetragon.desto.util.DestoConstants;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * This ArrayAdapter uses CloudEntities as items and displays them as a post in
 * the model. Layout uses row.xml.
 *
 */
public class SatisIslemiPostAdapter extends ArrayAdapter<StokItem> {

	private LayoutInflater mInflater;
	private SubMenuActivity subMenuActivity;

	private SatisItem satisItem;
	private String sender=DestoConstants.SATISISLEMI;

	/**
	 * Creates a new instance of this adapter.
	 *
	 * @param context
	 * @param textViewResourceId
	 * @param listCE
	 */
	public SatisIslemiPostAdapter(Context context, int textViewResourceId,
			StokItemList psStokList, SatisItem satisItem,String sender) {
		super(context, textViewResourceId, psStokList);
		setSender(sender);
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		setSubMenuActivity((SubMenuActivity) context);
		setSatisItem(satisItem);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// View view = convertView != null ? convertView : mInflater.inflate(
		// R.layout.satis_post, parent, false);

		View rowView = convertView;
		ViewHolder viewHolder;
		final StokItem stokItem = getItem(position);
		if (stokItem != null) {
			// reuse views
			if (rowView == null) {
				rowView = mInflater.inflate(R.layout.satis_post, parent, false);
				// configure view holder
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
				viewHolder.markaTextView = (TextView) rowView
						.findViewById(R.id.markaTextView);
				viewHolder.checkBox = (CheckBox) rowView
						.findViewById(R.id.checkBox);
				
				if (viewHolder.checkBox != null)
					viewHolder.checkBox.setChecked(stokItem.isSelected());
				rowView.setTag(viewHolder);
			} else
				viewHolder = (ViewHolder) convertView.getTag();

			if (getSender().equals(DestoConstants.SATISISLEMI))
				viewHolder.checkBox.setVisibility(View.VISIBLE);
			else 
				viewHolder.checkBox.setVisibility(View.INVISIBLE);
			final ModelItem model = stokItem.getModelItem();

			viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					if (((CheckBox) v).isChecked()) {
						stokItem.setSelected(true);
					} else {
						stokItem.setSelected(false);
					}
					getSubMenuActivity().getSatisEventHandler()
							.fireSatisChanged(satisItem);
				}

			});

			if (viewHolder.checkBox != null)
				viewHolder.checkBox.setChecked(stokItem.isSelected());

			if ((viewHolder.modelTextView != null)
					&& (stokItem.getModelItem() != null)) {
				viewHolder.modelTextView.setText(stokItem.getModelItem()
						.getModel());
			}

			if ((viewHolder.markaTextView != null)
					&& (model.getMarkaItem() != null)) {
				viewHolder.markaTextView.setText(model.getMarkaItem().getMarka());
			}
			if ((viewHolder.urunTipTextView != null)
					&& (model.getUrun_tipItem() != null)) {
				viewHolder.urunTipTextView.setText(" "
						+ model.getUrun_tipItem().getUrun_tip());
			}
			if (viewHolder.stokAdetTextView != null) {
				viewHolder.stokAdetTextView.setText("1");

			}
			if ((viewHolder.fiyatTextView != null)
					&& (model.getListeFiyati() != null)) {
				if (!model.getListeFiyati().isEmpty()) {
					viewHolder.fiyatTextView.setText(model.getListeFiyati());
				}
			}

		}
		return rowView;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return this.getView(position, convertView, parent);
	}

	public SubMenuActivity getSubMenuActivity() {
		return subMenuActivity;
	}

	public void setSubMenuActivity(SubMenuActivity subMenuActivity) {
		this.subMenuActivity = subMenuActivity;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public SatisItem getSatisItem() {
		return satisItem;
	}

	public void setSatisItem(SatisItem satisItem) {
		this.satisItem = satisItem;
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
