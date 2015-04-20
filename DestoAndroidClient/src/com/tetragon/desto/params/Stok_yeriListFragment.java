package com.tetragon.desto.params;

import java.io.IOException;

import com.google.cloud.backend.core.CloudCallbackHandler;
import com.google.cloud.backend.core.CloudEntity;
import com.google.cloud.backend.core.DbObjects;
import com.tetragon.desto.ListActivity;
import com.tetragon.desto.R;
import com.tetragon.desto.eventHandler.DataChangedEvent;
import com.tetragon.desto.eventHandler.DataListener;
import com.tetragon.desto.model.Stok_yeri;
import com.tetragon.desto.model.Stok_yeriList;
import com.tetragon.desto.util.DestoConstants;
import com.tetragon.desto.util.DestoUtil;

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

public class Stok_yeriListFragment extends Fragment implements OnItemClickListener,
		DataListener {

	private ListView stok_yeriListView;
	private Stok_yeriList stok_yeriList = DbObjects.getStok_yeriList();
	
	private Stok_yeriList suggestionList= new Stok_yeriList();
	private boolean suggestion=false;
	
	private ImageView addImg;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = inflater.inflate(R.layout.desto_list, container, false);
		setHasOptionsMenu(true);
		getActivity().setTitle("Stok_yeri Listesi");
		
		// Stok_yeri deðiþikliklerini dinle
		ListActivity activity = (ListActivity) getActivity();
		activity.getStok_yeriEventHandler().addListener(this);

		stok_yeriListView = (ListView) view.findViewById(R.id.listView);
		stok_yeriListView.setOnItemClickListener(this);
		updateStok_yeriList();
		addImg = (ImageView) view.findViewById(R.id.ic_add);
		addImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				addStok_yeri();
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
		final Dialog editStok_yeriDialog = new Dialog(getActivity());

		final Stok_yeri stok_yeri ;
		if (suggestion)
			 stok_yeri = suggestionList.get(position);
		else stok_yeri = stok_yeriList.get(position);

		editStok_yeriDialog.setContentView(R.layout.stok_yeri_save);
		editStok_yeriDialog.setTitle(stok_yeri.getKindName() + " Kayýtlarý");
		final ProgressBar progressBar = (ProgressBar) editStok_yeriDialog
				.findViewById(R.id.progressBar);
		progressBar.setVisibility(View.GONE);
		editStok_yeriDialog.show();

		final EditText stok_yeriEditText = (EditText) editStok_yeriDialog
				.findViewById(R.id.stok_yeriEditText);
		stok_yeriEditText
				.setText(stok_yeri.getStok_yeri());
		
		final Button vazgecButton = (Button) editStok_yeriDialog
				.findViewById(R.id.vazgecButton);
		vazgecButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				editStok_yeriDialog.dismiss();
			}
		});
		final Button saveStok_yeriButton = (Button) editStok_yeriDialog
				.findViewById(R.id.saveStok_yeriButton);
		if (stok_yeriEditText.getText().toString().trim().isEmpty())
			saveStok_yeriButton.setEnabled(false);
		else
			saveStok_yeriButton.setEnabled(true);
		
		stok_yeriEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (stok_yeriEditText.getText().toString().trim().isEmpty())
					saveStok_yeriButton.setEnabled(false);
				else
					saveStok_yeriButton.setEnabled(true);
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
		
		saveStok_yeriButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				saveStok_yeriButton.setEnabled(false);
				vazgecButton.setEnabled(false);
				updateStok_yeri(editStok_yeriDialog, stok_yeri, stok_yeriEditText);
			}
		});
	}

	private void addStok_yeri() {
		final DbObjects dbObjects = new DbObjects(getActivity());
		final Dialog addStok_yeriDialog = new Dialog(getActivity());
		addStok_yeriDialog.setContentView(R.layout.stok_yeri_save);
		addStok_yeriDialog.setTitle(Stok_yeri.LABEL_STOK_YERI + " Giriþi");
		final ProgressBar progressBar = (ProgressBar) addStok_yeriDialog
				.findViewById(R.id.progressBar);
		progressBar.setVisibility(View.GONE);

		final EditText stok_yeriEditText = (EditText) addStok_yeriDialog
				.findViewById(R.id.stok_yeriEditText);
		
		final Button vazgecButton = (Button) addStok_yeriDialog
				.findViewById(R.id.vazgecButton);

		vazgecButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				addStok_yeriDialog.dismiss();
			}
		});
		
		final Button saveStok_yeriButton = (Button) addStok_yeriDialog
				.findViewById(R.id.saveStok_yeriButton);

		if (stok_yeriEditText.getText().toString().trim().isEmpty())
			saveStok_yeriButton.setEnabled(false);
		else
			saveStok_yeriButton.setEnabled(true);
		
		stok_yeriEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (stok_yeriEditText.getText().toString().trim().isEmpty())
					saveStok_yeriButton.setEnabled(false);
				else
					saveStok_yeriButton.setEnabled(true);

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
		
		saveStok_yeriButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CloudEntity stok_yeriCE = new CloudEntity(Stok_yeri.KIND_STOK_YERI);
				if (stok_yeriEditText != null) { 
					String idkey=DestoUtil.generateId();
					stok_yeriCE.put(Stok_yeri.PROP_IDKEY, idkey);
					String stok_yeristr= stok_yeriEditText.getText()
							.toString();
					stok_yeriCE.put(Stok_yeri.PROP_STOKYERI,stok_yeristr);
				}

				CloudCallbackHandler<CloudEntity> mhandler = new CloudCallbackHandler<CloudEntity>() {
					public void onComplete(CloudEntity result) {

						LinearLayout linearLayout = (LinearLayout) addStok_yeriDialog
								.findViewById(R.id.stok_yeriEditLayout);
						linearLayout.setBackgroundResource(R.color.blue);
						Toast toast = Toast.makeText(getActivity(),
								result.get(Stok_yeri.PROP_STOKYERI).toString()
										+ " kaydedildi",
								DestoConstants.TOAST_MESSAGE_SHOWTIME);
						toast.show();
						Stok_yeri stok_yeriToSave=  new Stok_yeri(result.getKindName(),result);
						DbObjects.getStok_yeriList().add(stok_yeriToSave);
						((ListActivity) getActivity()).getStok_yeriEventHandler().fireStok_yeriChanged();
						addStok_yeriDialog.dismiss();
					}

					@Override
					public void onError(final IOException exception) {
						Log.i(Stok_yeri.KIND_STOK_YERI+ " kayýt hatasý! :",
								exception.toString());
						Toast toast = Toast.makeText(getActivity(),
								Stok_yeri.KIND_STOK_YERI + " kayýt hatasý! ",
								DestoConstants.TOAST_MESSAGE_SHOWTIME);
						toast.show();
						saveStok_yeriButton.setEnabled(true);
						vazgecButton.setEnabled(true);
						stok_yeriEditText.setEnabled(true);
						progressBar.setVisibility(View.GONE);
					}
				};
				saveStok_yeriButton.setEnabled(false);
				vazgecButton.setEnabled(false);
				stok_yeriEditText.setEnabled(false);
				progressBar.setVisibility(View.VISIBLE);
				dbObjects.insert(stok_yeriCE, mhandler);
			}

		});
		addStok_yeriDialog.show();
	}

	private void updateStok_yeri(final Dialog editStok_yeriDialog, final Stok_yeri stok_yeri,
			final TextView stok_yeriEditText) {
		
		stok_yeri.put(Stok_yeri.PROP_IDKEY, stok_yeri.getIdkey());
		stok_yeri.put(Stok_yeri.PROP_STOKYERI, stok_yeriEditText.getText().toString());
		
		final DbObjects dbObjects = new DbObjects(getActivity());
		final ProgressBar progressBar = (ProgressBar) editStok_yeriDialog
				.findViewById(R.id.progressBar);
		CloudCallbackHandler<CloudEntity> mhandler = new CloudCallbackHandler<CloudEntity>() {

			public void onComplete(final CloudEntity result) {
				LinearLayout linearLayout = (LinearLayout) editStok_yeriDialog
						.findViewById(R.id.stok_yeriEditLayout);
				linearLayout.setBackgroundResource(R.color.blue);
				Toast toast = Toast
						.makeText(getActivity(), result.get(Stok_yeri.PROP_STOKYERI)
								.toString() + " kaydedildi",
								DestoConstants.TOAST_MESSAGE_SHOWTIME);
				toast.show();
				DbObjects.getStok_yeriList().setStok_yeri(stok_yeri);
				((ListActivity) getActivity()).getStok_yeriEventHandler().fireStok_yeriChanged();
				editStok_yeriDialog.dismiss();
			}

			@Override
			public void onError(final IOException exception) {
				Log.i(Stok_yeri.KIND_STOK_YERI + " kayýt hatasý! :",
						exception.toString());
				Toast toast = Toast.makeText(getActivity(), Stok_yeri.KIND_STOK_YERI
						+ " kayýt hatasý! ",
						DestoConstants.TOAST_MESSAGE_SHOWTIME);
				toast.show();
				stok_yeriEditText.setEnabled(true);
				progressBar.setVisibility(View.GONE);
			}

		};
		stok_yeriEditText.setEnabled(false);
		progressBar.setVisibility(View.VISIBLE);
		dbObjects.update(stok_yeri, mhandler);
	}

	private void updateStok_yeriList(Stok_yeriList list) {
		if (!list.isEmpty()) {
			suggestion=true;
			stok_yeriListView.setVisibility(View.VISIBLE);
			stok_yeriListView.setAdapter(new Stok_yeriPostAdapter(getActivity(),
					android.R.layout.simple_list_item_1, list));
		} else {
			stok_yeriListView.setVisibility(View.GONE);
		}
	}

	private void updateStok_yeriList() {
		suggestion=false;
		stok_yeriList = DbObjects.getStok_yeriList();
		if (!stok_yeriList.isEmpty()) {
			stok_yeriListView.setVisibility(View.VISIBLE);
			stok_yeriListView.setAdapter(new Stok_yeriPostAdapter(getActivity(),
					android.R.layout.simple_list_item_1, stok_yeriList));
		} else {
			stok_yeriListView.setVisibility(View.GONE);
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
				updateStok_yeriList(suggestionList);
				return true;
			}

			@Override
			public boolean onQueryTextSubmit(String query) {
				return false;

			}

		});

	}

	private void loadSuggestions(String query, SearchView searchView) {
		suggestionList= new Stok_yeriList();
		for (Stok_yeri stok_yeri : stok_yeriList) {
			if (stok_yeri.getStok_yeri().contains(query)) {
				suggestionList.add(stok_yeri);
			}

		}

	}

	@Override
	public <T> void dataChanged(DataChangedEvent<T> e) {
		if (suggestion)
			updateStok_yeriList(suggestionList);
		else updateStok_yeriList();
	}

}
