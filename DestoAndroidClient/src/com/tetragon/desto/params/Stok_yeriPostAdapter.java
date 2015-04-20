package com.tetragon.desto.params;

import com.google.cloud.backend.core.CloudCallbackHandler;
import com.google.cloud.backend.core.DbObjects;
import com.tetragon.desto.ListActivity;
import com.tetragon.desto.R;
import com.tetragon.desto.model.Stok_yeri;
import com.tetragon.desto.util.DestoConstants;

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
 * the Stok_yeri. Layout uses row.xml.
 *
 */
public class Stok_yeriPostAdapter extends ArrayAdapter<Stok_yeri> {

	private LayoutInflater mInflater;
	private ListActivity listActivity;

	/**
	 * Creates a new instance of this adapter.
	 *
	 * @param context
	 * @param textViewResourceId
	 * @param objects
	 */
	public Stok_yeriPostAdapter(Context context, int textViewResourceId,
			List<Stok_yeri> objects) {
		super(context, textViewResourceId, objects);
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		setListActivity((ListActivity) context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView != null ? convertView : mInflater.inflate(
				R.layout.stok_yeri_post, parent, false);

		final Stok_yeri stok_yeri = getItem(position);
		if (stok_yeri != null) {
			TextView stok_yeriTextView = (TextView) view
					.findViewById(R.id.stok_yeriTextView);
			if (stok_yeriTextView != null) {
				stok_yeriTextView.setText(stok_yeri.getStok_yeri());
			}
			ImageView deleteImg = (ImageView) view.findViewById(R.id.ic_delete);
			deleteImg.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					deleteStok_yeri(stok_yeri);
				}

			});
		}

		return view;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return this.getView(position, convertView, parent);
	}
	
	private void deleteStok_yeri(final Stok_yeri stok_yeri) {
		final DbObjects dbObjects = new DbObjects(getContext());
		final CloudCallbackHandler<Void> mhandler = new CloudCallbackHandler<Void>() {

			@Override
			public void onComplete(Void results) {
				Toast toast = Toast.makeText(getContext(),
						stok_yeri.getStok_yeri()
								+ " Silindi",
						DestoConstants.TOAST_MESSAGE_SHOWTIME);
				toast.show();
				DbObjects.getStok_yeriList().remove(stok_yeri);
				getListActivity().getStok_yeriEventHandler().fireStok_yeriChanged();

			}
			
			@Override
			public void onError(final IOException exception) {
				Log.i(Stok_yeri.KIND_STOK_YERI + " Silme hatasý! :",
						exception.toString());
				Toast toast = Toast.makeText(getContext(),
						stok_yeri.getStok_yeri()+ " Silinemedi! ",
						DestoConstants.TOAST_MESSAGE_SHOWTIME);
				toast.show();
				
			}
		};

		AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
		dialog.setTitle(Stok_yeri.LABEL_STOK_YERI + " Silinecek");
		dialog.setMessage(stok_yeri.getStok_yeri()
				+ " Silinsin mi?");
		dialog.setCancelable(false);
		dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Sil",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int buttonId) {

						dbObjects.delete(stok_yeri, mhandler);
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
