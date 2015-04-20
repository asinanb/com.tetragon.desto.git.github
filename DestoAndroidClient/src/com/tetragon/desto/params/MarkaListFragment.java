package com.tetragon.desto.params;

import java.io.IOException;

import com.google.cloud.backend.core.CloudCallbackHandler;
import com.google.cloud.backend.core.CloudEntity;
import com.google.cloud.backend.core.DbObjects;
import com.tetragon.desto.ListActivity;
import com.tetragon.desto.R;
import com.tetragon.desto.eventHandler.DataChangedEvent;
import com.tetragon.desto.eventHandler.DataListener;
import com.tetragon.desto.model.Marka;
import com.tetragon.desto.model.MarkaItem;
import com.tetragon.desto.model.MarkaItemList;
import com.tetragon.desto.util.DestoConstants;

import android.app.Dialog;
import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class MarkaListFragment extends Fragment implements OnItemClickListener,
		DataListener {

	private ListView markaListView;
	private MarkaItemList markaItemList = DbObjects.getMarkaItemList();
	
	private MarkaItemList suggestionList= new MarkaItemList();
	private boolean suggestion=false;
	
	private ImageView addImg;

//	private CloudBackendMessaging backendMessaging;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = inflater.inflate(R.layout.desto_list, container, false);
		setHasOptionsMenu(true);
		getActivity().setTitle("Marka Listesi");
		
		// Marka deðiþikliklerini dinle
		ListActivity activity = (ListActivity) getActivity();
		activity.getMarkaEventHandler().addListener(this);

		markaListView = (ListView) view.findViewById(R.id.listView);
		markaListView.setOnItemClickListener(this);
		updateMarkaList();
		addImg = (ImageView) view.findViewById(R.id.ic_add);
		addImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				addMarka();
			}

		});
		ProgressBar progressBar = (ProgressBar) view
				.findViewById(R.id.progressBar);
		progressBar.setVisibility(View.GONE);
		return view;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view,
			final int position, long id) {
		final Dialog editMarkaDialog = new Dialog(getActivity());

		final MarkaItem marka ;
		if (suggestion)
			 marka = suggestionList.get(position);
		else marka = markaItemList.get(position);

		editMarkaDialog.setContentView(R.layout.marka_save);
		editMarkaDialog.setTitle(Marka.LABEL_MARKA + " Kayýtlarý");
		final ProgressBar progressBar = (ProgressBar) editMarkaDialog
				.findViewById(R.id.progressBar);
		progressBar.setVisibility(View.GONE);
		editMarkaDialog.show();

		final EditText markaEditText = (EditText) editMarkaDialog
				.findViewById(R.id.markaEditText);
		markaEditText
				.setText(marka.getMarka());
		final EditText firmaEditText = (EditText) editMarkaDialog
				.findViewById(R.id.firmaEditText);
		firmaEditText
				.setText(marka.getFirma());
		final Button vazgecButton = (Button) editMarkaDialog
				.findViewById(R.id.vazgecButton);
		vazgecButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				editMarkaDialog.dismiss();
			}
		});
		final Button saveMarkaButton = (Button) editMarkaDialog
				.findViewById(R.id.saveMarkaButton);
		if ((markaEditText.getText().toString().trim().isEmpty())
				&& (firmaEditText.getText().toString().trim().isEmpty()))
			saveMarkaButton.setEnabled(false);
		else
			saveMarkaButton.setEnabled(true);
		markaEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if ((markaEditText.getText().toString().trim().isEmpty())
						|| (firmaEditText.getText().toString().trim().isEmpty()))
					saveMarkaButton.setEnabled(false);
				else
					saveMarkaButton.setEnabled(true);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
		firmaEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if ((markaEditText.getText().toString().trim().isEmpty())
						|| (firmaEditText.getText().toString().trim().isEmpty()))
					saveMarkaButton.setEnabled(false);
				else
					saveMarkaButton.setEnabled(true);

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
		saveMarkaButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				saveMarkaButton.setEnabled(false);
				vazgecButton.setEnabled(false);
				marka.setMarka(markaEditText.getText().toString());
				marka.setFirma(firmaEditText.getText().toString());
				updateMarka(editMarkaDialog, marka, markaEditText,
						firmaEditText);
			}
		});
	}

	private void addMarka() {
		final DbObjects dbObjects = new DbObjects(getActivity());
		final Dialog addMarkaDialog = new Dialog(getActivity());
		addMarkaDialog.setContentView(R.layout.marka_save);
		addMarkaDialog.setTitle(Marka.LABEL_MARKA + " Giriþi");
		final ProgressBar progressBar = (ProgressBar) addMarkaDialog
				.findViewById(R.id.progressBar);
		progressBar.setVisibility(View.GONE);

		final EditText markaEditText = (EditText) addMarkaDialog
				.findViewById(R.id.markaEditText);
		final EditText firmaEditText = (EditText) addMarkaDialog
				.findViewById(R.id.firmaEditText);
		final Button vazgecButton = (Button) addMarkaDialog
				.findViewById(R.id.vazgecButton);

		vazgecButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				addMarkaDialog.dismiss();
			}
		});
		
		final Button saveMarkaButton = (Button) addMarkaDialog
				.findViewById(R.id.saveMarkaButton);

		if ((markaEditText.getText().toString().trim().isEmpty())
				&& (firmaEditText.getText().toString().trim().isEmpty()))
			saveMarkaButton.setEnabled(false);
		else
			saveMarkaButton.setEnabled(true);
		markaEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if ((markaEditText.getText().toString().trim().isEmpty())
						|| (firmaEditText.getText().toString().trim().isEmpty()))
					saveMarkaButton.setEnabled(false);
				else
					saveMarkaButton.setEnabled(true);

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
		firmaEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if ((markaEditText.getText().toString().trim().isEmpty())
						|| (firmaEditText.getText().toString().trim().isEmpty()))
					saveMarkaButton.setEnabled(false);
				else
					saveMarkaButton.setEnabled(true);

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
		saveMarkaButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final MarkaItem markaItem = new MarkaItem();
				if (markaEditText != null) { 
					
					String markastr= markaEditText.getText()
							.toString();
					markaItem.setMarka(markastr);
					
					String firmastr=firmaEditText.getText()
							.toString();
					markaItem.setFirma(firmastr);
				}

				CloudCallbackHandler<CloudEntity> mhandler = new CloudCallbackHandler<CloudEntity>() {
					public void onComplete(CloudEntity result) {

						LinearLayout linearLayout = (LinearLayout) addMarkaDialog
								.findViewById(R.id.markaEditLayout);
						linearLayout.setBackgroundResource(R.color.blue);
						Toast toast = Toast.makeText(getActivity(),
								result.get(Marka.PROP_MARKA).toString()
										+ " kaydedildi",
								DestoConstants.TOAST_MESSAGE_SHOWTIME);
						toast.show();
						
						markaItem.assign(new MarkaItem(result));
						DbObjects.getMarkaItemList().addOrUpdate(markaItem);
						((ListActivity) getActivity()).getMarkaEventHandler().fireMarkaChanged();
						
						addMarkaDialog.dismiss();
					}

					@Override
					public void onError(final IOException exception) {
						Log.i(Marka.KIND_MARKA + " kayýt hatasý! :",
								exception.toString());
						Toast toast = Toast.makeText(getActivity(),
								Marka.KIND_MARKA + " kayýt hatasý! ",
								DestoConstants.TOAST_MESSAGE_SHOWTIME);
						toast.show();
						saveMarkaButton.setEnabled(true);
						vazgecButton.setEnabled(true);
						markaEditText.setEnabled(true);
						firmaEditText.setEnabled(true);
						progressBar.setVisibility(View.GONE);
					}
				};
				saveMarkaButton.setEnabled(false);
				vazgecButton.setEnabled(false);
				markaEditText.setEnabled(false);
				firmaEditText.setEnabled(false);
				progressBar.setVisibility(View.VISIBLE);
				dbObjects.insert(markaItem.getMarkaCE(), mhandler);
			}

		});
		addMarkaDialog.show();
	}

	private void updateMarka(final Dialog editMarkaDialog, final MarkaItem markaItem,
			final TextView markaEditText, final TextView firmaEditText) {
		
		final DbObjects dbObjects = new DbObjects(getActivity());
		
		final ProgressBar progressBar = (ProgressBar) editMarkaDialog
				.findViewById(R.id.progressBar);
		CloudCallbackHandler<CloudEntity> mhandler = new CloudCallbackHandler<CloudEntity>() {

			public void onComplete(final CloudEntity result) {
				LinearLayout linearLayout = (LinearLayout) editMarkaDialog
						.findViewById(R.id.markaEditLayout);
				linearLayout.setBackgroundResource(R.color.blue);
				Toast toast = Toast
						.makeText(getActivity(), result.get(Marka.PROP_MARKA)
								.toString() + " kaydedildi",
								DestoConstants.TOAST_MESSAGE_SHOWTIME);
				toast.show();
				
				String idkey=(String) result.get(Marka.PROP_IDKEY);
				MarkaItem markaToSave = DbObjects.getMarkaItemList().findMarkaByIdkey(idkey);//
				
				MarkaItem newMarkaItem= new MarkaItem(result);
				markaToSave.assign(newMarkaItem);
				DbObjects.getMarkaItemList().addOrUpdate(markaToSave);
				((ListActivity) getActivity()).getMarkaEventHandler().fireMarkaChanged();
				editMarkaDialog.dismiss();
			}

			@Override
			public void onError(final IOException exception) {
				Log.i(Marka.KIND_MARKA + " kayýt hatasý! :",
						exception.toString());
				Toast toast = Toast.makeText(getActivity(), Marka.KIND_MARKA
						+ " kayýt hatasý! ",
						DestoConstants.TOAST_MESSAGE_SHOWTIME);
				toast.show();
				markaEditText.setEnabled(true);
				firmaEditText.setEnabled(true);
				progressBar.setVisibility(View.GONE);
			}

		};
		markaEditText.setEnabled(false);
		firmaEditText.setEnabled(false);
		progressBar.setVisibility(View.VISIBLE);
		dbObjects.update(markaItem.getMarkaCE(), mhandler);
	}

	private void updateMarkaList(MarkaItemList list) {
		if (!list.isEmpty()) {
			suggestion=true;
			markaListView.setVisibility(View.VISIBLE);
			markaListView.setAdapter(new MarkaPostAdapter(getActivity(),
					android.R.layout.simple_list_item_1, list));
		} else {
			markaListView.setVisibility(View.GONE);
		}
	}

	private void updateMarkaList() {
		suggestion=false;
		markaItemList = DbObjects.getMarkaItemList();
		if (!markaItemList.isEmpty()) {
			markaListView.setVisibility(View.VISIBLE);
			markaListView.setAdapter(new MarkaPostAdapter(getActivity(),
					android.R.layout.simple_list_item_1, markaItemList));
		} else {
			markaListView.setVisibility(View.GONE);
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.activity_main_actions, menu);
		super.onCreateOptionsMenu(menu, inflater);

		// Associate searchable configuration with the SearchView
		SearchManager searchManager = (SearchManager) getActivity()
				.getSystemService(Context.SEARCH_SERVICE);
		final SearchView searchView = (SearchView) menu.findItem(
				R.id.action_search).getActionView();
		searchView.setSearchableInfo(searchManager
				.getSearchableInfo(getActivity().getComponentName()));

		searchView.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextChange(String query) {
				loadSuggestions(query, searchView);
				updateMarkaList(suggestionList);
				return true;
			}

			@Override
			public boolean onQueryTextSubmit(String query) {
				return false;

			}

		});

	}

	private void loadSuggestions(String query, SearchView searchView) {
		suggestionList= new MarkaItemList();
		for (MarkaItem item : markaItemList) {
			if (item.getMarka().contains(query)) {
				suggestionList.add(item);
			}

		}

	}

	@Override
	public <T> void dataChanged(DataChangedEvent<T> e) {
		if (suggestion)
			updateMarkaList(suggestionList);
		else updateMarkaList();
	}

}
