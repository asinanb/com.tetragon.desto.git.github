package com.tetragon.desto.params;

import com.google.cloud.backend.core.CloudCallbackHandler;
import com.google.cloud.backend.core.DbObjects;
import com.tetragon.desto.ListActivity;
import com.tetragon.desto.R;
import com.tetragon.desto.model.Marka;
import com.tetragon.desto.model.MarkaItem;
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
 * the marka. Layout uses row.xml.
 *
 */
public class MarkaPostAdapter extends ArrayAdapter<MarkaItem> {

	private LayoutInflater mInflater;
	private ListActivity listActivity;

	/**
	 * Creates a new instance of this adapter.
	 *
	 * @param context
	 * @param textViewResourceId
	 * @param objects
	 */
	public MarkaPostAdapter(Context context, int textViewResourceId,
			List<MarkaItem> objects) {
		super(context, textViewResourceId, objects);
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		setListActivity((ListActivity) context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView != null ? convertView : mInflater.inflate(
				R.layout.marka_row_post, parent, false);

		final MarkaItem markaItem = getItem(position);
		if (markaItem != null) {
			TextView markaTextView = (TextView) view
					.findViewById(R.id.markaTextView);
			TextView firmaTextView = (TextView) view.findViewById(R.id.firmaTextView);
			if (markaTextView != null) {
				markaTextView.setText(markaItem.getMarka());
			}
			if (firmaTextView != null) {
				firmaTextView.setText(markaItem.getFirma());
			}
			ImageView image = (ImageView) view.findViewById(R.id.ic_marka);
			
			DestoUtil.changeMarkaImage(image, markaItem.getMarka());
			
			ImageView deleteImg = (ImageView) view.findViewById(R.id.ic_delete);
			deleteImg.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					deleteMarka(markaItem);
				}

			});
		}

		return view;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return this.getView(position, convertView, parent);
	}
	
	private void deleteMarka(final MarkaItem markaItem) {
		final DbObjects dbObjects = new DbObjects(getContext());
		final CloudCallbackHandler<Void> mhandler = new CloudCallbackHandler<Void>() {

			@Override
			public void onComplete(Void results) {
				Toast toast = Toast.makeText(getContext(),
						markaItem.getMarka()
								+ " Silindi",
						DestoConstants.TOAST_MESSAGE_SHOWTIME);
				toast.show();
				
				DbObjects.getMarkaItemList().remove(markaItem);
				getListActivity().getMarkaEventHandler().fireMarkaChanged();

			}
			
			@Override
			public void onError(final IOException exception) {
				Log.i(Marka.KIND_MARKA + " Silme hatasý! :",
						exception.toString());
				Toast toast = Toast.makeText(getContext(),
						markaItem.getMarka()+ " Silinemedi! ",
						DestoConstants.TOAST_MESSAGE_SHOWTIME);
				toast.show();
				
			}
		};

		AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
		dialog.setTitle(Marka.LABEL_MARKA + " Silinecek");
		dialog.setMessage(markaItem.getMarka()
				+ " Silinsin mi?");
		dialog.setCancelable(false);
		dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Sil",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int buttonId) {

						dbObjects.delete(markaItem.getMarkaCE(), mhandler);
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
}
