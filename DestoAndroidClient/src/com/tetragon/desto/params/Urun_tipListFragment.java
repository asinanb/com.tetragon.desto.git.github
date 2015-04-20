package com.tetragon.desto.params;

import java.io.IOException;

import com.google.cloud.backend.core.CloudCallbackHandler;
import com.google.cloud.backend.core.CloudEntity;
import com.google.cloud.backend.core.DbObjects;
import com.tetragon.desto.ListActivity;
import com.tetragon.desto.R;
import com.tetragon.desto.eventHandler.DataChangedEvent;
import com.tetragon.desto.eventHandler.DataListener;
import com.tetragon.desto.model.Urun_tip;
import com.tetragon.desto.model.Urun_tipItem;
import com.tetragon.desto.model.Urun_tipItemList;
import com.tetragon.desto.model.UrungrubuItem;
import com.tetragon.desto.model.UrungrubuItemList;
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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Urun_tipListFragment extends Fragment implements OnItemClickListener,DataListener {

	private ListView urunTipListView;
	private Urun_tipItemList urunTipList = DbObjects.getUrunTipItemList();
	private UrungrubuItemList urungrubuList = DbObjects.getUrungrubuItemList();
	
	private Urun_tipItemList suggestionList= new Urun_tipItemList();
	private boolean suggestion=false;
	
	private ImageView addImg;

	private Spinner urungrubuspinner;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = inflater.inflate(R.layout.desto_list, container, false);
		setHasOptionsMenu(true);
		getActivity().setTitle("Ürün Listesi");
		
		// Urun deðiþikliklerini dinle
		ListActivity activity = (ListActivity) getActivity();
		activity.getUrunEventHandler().addListener(this);

		urunTipListView = (ListView) view.findViewById(R.id.listView);
		urunTipListView.setOnItemClickListener(this);
		updateUrunList();
		addImg = (ImageView) view.findViewById(R.id.ic_add);
		addImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				addUrun();
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
		final Dialog editUrunDialog = new Dialog(getActivity());

		final Urun_tipItem urunTipItem; 
		if (suggestion)
			urunTipItem = suggestionList.get(position);
		else urunTipItem = urunTipList.get(position);
		
		editUrunDialog.setContentView(R.layout.urun_tip_save);
		editUrunDialog.setTitle(Urun_tip.LABEL_URUN_TIP + " Kayýtlarý");
		final ProgressBar progressBar = (ProgressBar) editUrunDialog
				.findViewById(R.id.progressBar);
		progressBar.setVisibility(View.GONE);
		editUrunDialog.show();

		final EditText urunEditText = (EditText) editUrunDialog
				.findViewById(R.id.urunEditText);
		urunEditText.setText(urunTipItem.getUrun_tip());

		urungrubuspinner = (Spinner) editUrunDialog
				.findViewById(R.id.urungrubuspinner);

		ArrayAdapter<UrungrubuItem> adapter = new ArrayAdapter<UrungrubuItem>(getActivity(),
				android.R.layout.simple_spinner_item, urungrubuList);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		urungrubuspinner.setAdapter(adapter);
		urungrubuspinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						Log.i("uyarý", String.valueOf(arg2));
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});
		urungrubuspinner.setSelection(adapter.getPosition(urunTipItem.getUrungrubuItem()));

		final Button vazgecButton = (Button) editUrunDialog
				.findViewById(R.id.vazgecButton);
		vazgecButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				editUrunDialog.dismiss();
			}
		});
		final Button saveUrunButton = (Button) editUrunDialog
				.findViewById(R.id.saveUrunButton);
		if (urunEditText.getText().toString().trim().isEmpty())
			saveUrunButton.setEnabled(false);
		else
			saveUrunButton.setEnabled(true);
		urunEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (urunEditText.getText().toString().trim().isEmpty())
					saveUrunButton.setEnabled(false);
				else
					saveUrunButton.setEnabled(true);
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

		saveUrunButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				saveUrunButton.setEnabled(false);
				vazgecButton.setEnabled(false);
				int pos = urungrubuspinner.getSelectedItemPosition();
				urunTipItem.setUrungrubuItem((UrungrubuItem) urungrubuspinner.getAdapter().getItem(pos));
				urunTipItem.setUrun_tip(urunEditText.getText().toString());
				updateUrun(editUrunDialog, urunTipItem, urunEditText, position);
			}
		});
		
	}

	private void addUrun() {
		final DbObjects dbObjects = new DbObjects(getActivity());
		final Dialog addUrunDialog = new Dialog(getActivity());
		addUrunDialog.setContentView(R.layout.urun_tip_save);
		addUrunDialog.setTitle(Urun_tip.LABEL_URUN_TIP + " Giriþi");
		final ProgressBar progressBar = (ProgressBar) addUrunDialog
				.findViewById(R.id.progressBar);
		progressBar.setVisibility(View.GONE);
		addUrunDialog.show();

		final EditText urunEditText = (EditText) addUrunDialog
				.findViewById(R.id.urunEditText);
		final Button vazgecButton = (Button) addUrunDialog
				.findViewById(R.id.vazgecButton);

		vazgecButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				addUrunDialog.dismiss();
			}
		});
		urungrubuspinner = (Spinner) addUrunDialog
				.findViewById(R.id.urungrubuspinner);

		
		ArrayAdapter<UrungrubuItem> adapter = new ArrayAdapter<UrungrubuItem>(getActivity(),
				android.R.layout.simple_spinner_item, urungrubuList);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		urungrubuspinner.setAdapter(adapter);
		urungrubuspinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						Log.i("uyarý", String.valueOf(arg2));
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});

		final Button saveUrunButton = (Button) addUrunDialog
				.findViewById(R.id.saveUrunButton);

		if (urunEditText.getText().toString().trim().isEmpty())
			saveUrunButton.setEnabled(false);
		else
			saveUrunButton.setEnabled(true);
		urunEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (urunEditText.getText().toString().trim().isEmpty())
					saveUrunButton.setEnabled(false);
				else
					saveUrunButton.setEnabled(true);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (suggestion)
					updateUrunList(suggestionList);
				else updateUrunList();

			}
		});
		saveUrunButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final Urun_tipItem urun_tipItem= new Urun_tipItem();
				
				
				if (urunEditText != null) {
					urun_tipItem.setUrun_tip(urunEditText.getText().toString());
					int pos = urungrubuspinner.getSelectedItemPosition();
					urun_tipItem.setUrungrubuItem((UrungrubuItem) urungrubuspinner.getAdapter().getItem(pos));
					
				}

				CloudCallbackHandler<CloudEntity> mhandler = new CloudCallbackHandler<CloudEntity>() {
					public void onComplete(final CloudEntity result) {

						LinearLayout linearLayout = (LinearLayout) addUrunDialog
								.findViewById(R.id.urunEditLayout);
						linearLayout.setBackgroundResource(R.color.blue);
						Toast toast = Toast.makeText(getActivity(),
								result.get(Urun_tip.PROP_URUN_TIP).toString()
										+ " kaydedildi",
								DestoConstants.TOAST_MESSAGE_SHOWTIME);
						toast.show();
					
						urun_tipItem.assign(new Urun_tipItem(result));
						DbObjects.getUrunTipItemList().addOrUpdate(urun_tipItem);
						((ListActivity) getActivity()).getUrunEventHandler().fireUrunChanged();
						
						addUrunDialog.dismiss();
					}

					@Override
					public void onError(final IOException exception) {
						Log.i(Urun_tip.KIND_URUN_TIP + " kayýt hatasý! :",
								exception.toString());
						Toast toast = Toast.makeText(getActivity(),
								Urun_tip.KIND_URUN_TIP + " kayýt hatasý! ",
								DestoConstants.TOAST_MESSAGE_SHOWTIME);
						toast.show();
						saveUrunButton.setEnabled(true);
						vazgecButton.setEnabled(true);
						urunEditText.setEnabled(true);
						urungrubuspinner.setEnabled(true);
						progressBar.setVisibility(View.GONE);
					}
				};
				saveUrunButton.setEnabled(false);
				vazgecButton.setEnabled(false);
				urunEditText.setEnabled(false);
				urungrubuspinner.setEnabled(false);
				progressBar.setVisibility(View.VISIBLE);
				dbObjects.insert(urun_tipItem.getUrun_tipCE(), mhandler);
			}
		});

	}

	private void updateUrun(final Dialog editUrunDialog,final Urun_tipItem urunTipItem,
			final TextView urunEditText, final int position) {
		final DbObjects dbObjects = new DbObjects(getActivity());
		final ProgressBar progressBar = (ProgressBar) editUrunDialog
				.findViewById(R.id.progressBar);
		CloudCallbackHandler<CloudEntity> mhandler = new CloudCallbackHandler<CloudEntity>() {

			public void onComplete(final CloudEntity result) {
				LinearLayout linearLayout = (LinearLayout) editUrunDialog
						.findViewById(R.id.urunEditLayout);
				linearLayout.setBackgroundResource(R.color.blue);
				Toast toast = Toast.makeText(getActivity(),
						result.get(Urun_tip.PROP_URUN_TIP).toString() + " kaydedildi",
						DestoConstants.TOAST_MESSAGE_SHOWTIME);
				toast.show();
				
				String idkey=(String) result.get(Urun_tip.PROP_IDKEY);
				Urun_tipItem item2save = DbObjects.getUrunTipItemList().findUrun_tipByIdkey(idkey);//
				Urun_tipItem newUrun_tipItem= new Urun_tipItem(result);
				
				item2save.assign(newUrun_tipItem);
				DbObjects.getUrunTipItemList().addOrUpdate(item2save);
				((ListActivity) getActivity()).getUrunEventHandler()
						.fireUrunChanged();
				
				editUrunDialog.dismiss();
			}

			@Override
			public void onError(final IOException exception) {
				Log.i(Urun_tip.KIND_URUN_TIP + " kayýt hatasý! :", exception.toString());
				Toast toast = Toast.makeText(getActivity(), Urun_tip.KIND_URUN_TIP
						+ " kayýt hatasý! ",
						DestoConstants.TOAST_MESSAGE_SHOWTIME);
				toast.show();
				urunEditText.setEnabled(true);
				urungrubuspinner.setEnabled(true);
				progressBar.setVisibility(View.GONE);
			}

		};
		urunEditText.setEnabled(false);
		urungrubuspinner.setEnabled(false);
		progressBar.setVisibility(View.VISIBLE);
		dbObjects.update(urunTipItem.getUrun_tipCE(), mhandler);
	}

	

	private void updateUrunList(Urun_tipItemList suggestionList) {
		if (!suggestionList.isEmpty()) {
			suggestion=true;
			urunTipListView.setVisibility(View.VISIBLE);
			urunTipListView.setAdapter(new Urun_tipPostAdapter(getActivity(),
					android.R.layout.simple_list_item_1, suggestionList));
		} else {
			urunTipListView.setVisibility(View.GONE);
		}
	}
	
	private void updateUrunList() {
		suggestion=false;
		urunTipList= DbObjects.getUrunTipItemList();
		if (!urunTipList.isEmpty()) {
			urunTipListView.setVisibility(View.VISIBLE);
			urunTipListView.setAdapter(new Urun_tipPostAdapter(getActivity(),
					android.R.layout.simple_list_item_1, urunTipList));
		} else {
			urunTipListView.setVisibility(View.GONE);
		}
	}
	

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.activity_main_actions, menu);
		super.onCreateOptionsMenu(menu, inflater);

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
				updateUrunList(suggestionList);
				return true;
			}

			@Override
			public boolean onQueryTextSubmit(String query) {
				return false;
			}
		});
	}

	private void loadSuggestions(String query, SearchView searchView) {
		suggestionList= new Urun_tipItemList();
		for (Urun_tipItem item : urunTipList) {
			if (item.getUrun_tip().contains(query)) {
				suggestionList.add(item);
			}
		}
	}

	@Override
	public <T> void dataChanged(DataChangedEvent<T> e) {
		if (suggestion)
			updateUrunList(suggestionList);
		else updateUrunList();
	}
}
