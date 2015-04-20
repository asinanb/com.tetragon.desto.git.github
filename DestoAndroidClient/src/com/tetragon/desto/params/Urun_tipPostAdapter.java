package com.tetragon.desto.params;

import com.google.cloud.backend.core.CloudCallbackHandler;
import com.google.cloud.backend.core.DbObjects;
import com.tetragon.desto.ListActivity;
import com.tetragon.desto.R;
import com.tetragon.desto.model.Urun_tip;
import com.tetragon.desto.model.Urun_tipItem;
import com.tetragon.desto.util.DestoConstants;
import com.tetragon.desto.util.DestoUtil;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

/**
 * This ArrayAdapter uses CloudEntities as items and displays them as a post in
 * the urun. Layout uses row.xml.
 *
 */
public class Urun_tipPostAdapter extends ArrayAdapter<Urun_tipItem> {

	private LayoutInflater mInflater;
	private ListActivity listActivity;
	
	/**
	 * Creates a new instance of this adapter.
	 *
	 * @param context
	 * @param textViewResourceId
	 * @param urunTipList
	 */
	public Urun_tipPostAdapter(Context context, int textViewResourceId,
			List<Urun_tipItem> urunTipList) {
		super(context, textViewResourceId, urunTipList);
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		setListActivity((ListActivity) context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View rowView = convertView;
		ViewHolder viewHolder;
		
		final Urun_tipItem urun_tipItem = getItem(position);
		if (urun_tipItem != null) {
			
			if (rowView == null) {
				rowView = mInflater.inflate(R.layout.urun_tip_row_post, parent, false);
				// configure view holder
				viewHolder = new ViewHolder();
				viewHolder.urunTipTextView = (TextView) rowView
						.findViewById(R.id.urunTipTextView);
				viewHolder.urungrubuTextView = (TextView) rowView
						.findViewById(R.id.urungrubuTextView);
				
				viewHolder.urunImage = (ImageView) rowView.findViewById(R.id.ic_urunTip);
				viewHolder.deleteImg = (ImageView) rowView.findViewById(R.id.ic_delete);
				
				rowView.setTag(viewHolder);
			} else
				viewHolder = (ViewHolder) convertView.getTag();
			
			
			
			if (viewHolder.urunTipTextView != null) {
				viewHolder.urunTipTextView.setText(urun_tipItem.getUrun_tip());
			}
			if (viewHolder.urungrubuTextView != null) {
				viewHolder.urungrubuTextView.setText(urun_tipItem.getUrungrubuItem().getUrungrubu());
			}
			if (viewHolder.deleteImg!=null){
				viewHolder.deleteImg.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						deleteUrunTip(urun_tipItem);
					}
				});
			}
			if (viewHolder.urunImage!=null){
				DestoUtil.changeUrunImage(viewHolder.urunImage, urun_tipItem.getUrun_tip());
			}
		}

		return rowView;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return this.getView(position, convertView, parent);
	}
	
	private void deleteUrunTip(final Urun_tipItem urunTip) {
		final DbObjects dbObjects = new DbObjects(getContext());
		final CloudCallbackHandler<Void> mhandler = new CloudCallbackHandler<Void>() {

			@Override
			public void onComplete(Void results) {
				Toast toast = Toast.makeText(getContext(),
						urunTip.getUrun_tip()
								+ " Silindi",
						DestoConstants.TOAST_MESSAGE_SHOWTIME);
				toast.show();
				DbObjects.getUrunTipItemList().remove(urunTip);
				getListActivity().getUrunEventHandler().fireUrun_tipChanged(DbObjects.getUrunTipItemList());
			}
			@Override
			public void onError(final IOException exception) {
				Log.i(Urun_tip.KIND_URUN_TIP + " Silme hatasý! :",
						exception.toString());
				Toast toast = Toast.makeText(getContext(),
						urunTip.getUrun_tip() + " Silinemedi! ",
						DestoConstants.TOAST_MESSAGE_SHOWTIME);
				toast.show();
				
			}

		};

		AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
		dialog.setTitle(Urun_tip.LABEL_URUN_TIP + " Silinecek");
		dialog.setMessage(urunTip.getUrun_tip()
				+ " Silinsin mi?");
		dialog.setCancelable(false);
		dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Sil",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int buttonId) {
						dbObjects.delete(urunTip.getUrun_tipCE(), mhandler);
						dialog.dismiss();
					}
				});
		dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Silme",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int buttonId) {
						dialog.dismiss();
					}
				});
		dialog.setIcon(android.R.drawable.ic_delete);
		dialog.show();
	}

	public ListActivity getListActivity() {
		return listActivity;
	}

	public void setListActivity(ListActivity listActivity) {
		this.listActivity = listActivity;
	}
	
	static class ViewHolder {
		public TextView urunTipTextView;
		public TextView urungrubuTextView;
		public ImageView urunImage;
		public ImageView deleteImg;
	}

	
}
