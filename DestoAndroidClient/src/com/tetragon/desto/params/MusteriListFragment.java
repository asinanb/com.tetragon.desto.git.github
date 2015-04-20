package com.tetragon.desto.params;

import java.io.IOException;

import com.google.cloud.backend.core.CloudCallbackHandler;
import com.google.cloud.backend.core.CloudEntity;
import com.google.cloud.backend.core.DbObjects;
import com.tetragon.desto.ListActivity;
import com.tetragon.desto.R;
import com.tetragon.desto.eventHandler.DataChangedEvent;
import com.tetragon.desto.eventHandler.DataListener;
import com.tetragon.desto.model.Musteri;
import com.tetragon.desto.model.MusteriList;
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

public class MusteriListFragment extends Fragment implements
		OnItemClickListener, DataListener {

	private ListView musteriListView;
	private MusteriList musteriList = DbObjects.getMusteriList();

	private MusteriList suggestionList = new MusteriList();
	private boolean suggestion = false;

	private boolean musteriSecici = false;

	private ImageView addImg;

	public MusteriListFragment(String musteriSec) {
		if (musteriSec.equals(DestoConstants.MUSTERI_SEC))
			setMusteriSecici(true);
		else
			setMusteriSecici(false);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = inflater.inflate(R.layout.desto_list, container, false);
		setHasOptionsMenu(true);
		getActivity().setTitle("Musteri Listesi");

		// Musteri deðiþikliklerini dinle
		ListActivity activity = (ListActivity) getActivity();
		activity.getMusteriEventHandler().addListener(this);

		musteriListView = (ListView) view.findViewById(R.id.listView);
		musteriListView.setOnItemClickListener(this);
		updateMusteriList();
		addImg = (ImageView) view.findViewById(R.id.ic_add);
		addImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				addMusteri();
			}

		});

		if (isMusteriSecici()) {
			addImg.setVisibility(View.GONE);
		}

		ProgressBar progressBar = (ProgressBar) view
				.findViewById(R.id.progressBar);
		progressBar.setVisibility(View.GONE);
		return view;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view,
			final int position, long id) {
		final Dialog editMusteriDialog = new Dialog(getActivity());
		
		final Musteri musteri;
		
		if (suggestion)
			musteri = suggestionList.get(position);
		else
			musteri = musteriList.get(position);
		
		if (isMusteriSecici()) {
			DbObjects.getSatisItem().setMusteri(musteri);
			getActivity().finish() ;
		} else {

			editMusteriDialog.setContentView(R.layout.musteri_save);
			editMusteriDialog.setTitle(musteri.getKindName() + " Kayýtlarý");
			final ProgressBar progressBar = (ProgressBar) editMusteriDialog
					.findViewById(R.id.progressBar);
			progressBar.setVisibility(View.GONE);
			editMusteriDialog.show();

			final EditText adEditText = (EditText) editMusteriDialog
					.findViewById(R.id.adEditText);
			adEditText.setText(musteri.getAdi());
			final EditText soyadEditText = (EditText) editMusteriDialog
					.findViewById(R.id.soyadEditText);
			soyadEditText.setText(musteri.getSoyadi());

			final EditText adresEditText = (EditText) editMusteriDialog
					.findViewById(R.id.adresEditText);
			adresEditText.setText(musteri.getAdres());

			final Button vazgecButton = (Button) editMusteriDialog
					.findViewById(R.id.vazgecButton);
			vazgecButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					editMusteriDialog.dismiss();
				}
			});

			final Button saveButton = (Button) editMusteriDialog
					.findViewById(R.id.saveButton);

			adEditText.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					if ((adEditText.getText().toString().trim().isEmpty())
							|| (adEditText.getText().toString().trim()
									.isEmpty()))
						saveButton.setEnabled(false);
					else
						saveButton.setEnabled(true);
				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
					// TODO Auto-generated method stub
				}

				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub

				}
			});

			soyadEditText.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					if ((soyadEditText.getText().toString().trim().isEmpty())
							|| (soyadEditText.getText().toString().trim()
									.isEmpty()))
						saveButton.setEnabled(false);
					else
						saveButton.setEnabled(true);

				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
					// TODO Auto-generated method stub

				}

				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub

				}
			});
			saveButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					saveButton.setEnabled(false);
					vazgecButton.setEnabled(false);
					updateMusteri(editMusteriDialog, musteri, adEditText,
							soyadEditText, adresEditText);
				}
			});
		}
	}

	private void addMusteri() {
		final DbObjects dbObjects = new DbObjects(getActivity());
		final Dialog addMusteriDialog = new Dialog(getActivity());
		addMusteriDialog.setContentView(R.layout.musteri_save);
		addMusteriDialog.setTitle(Musteri.LABEL_MUSTERI + " Giriþi");
		final ProgressBar progressBar = (ProgressBar) addMusteriDialog
				.findViewById(R.id.progressBar);
		progressBar.setVisibility(View.GONE);

		final EditText adEditText = (EditText) addMusteriDialog
				.findViewById(R.id.adEditText);
		final EditText soyadEditText = (EditText) addMusteriDialog
				.findViewById(R.id.soyadEditText);
		final EditText adresEditText = (EditText) addMusteriDialog
				.findViewById(R.id.adresEditText);

		final Button vazgecButton = (Button) addMusteriDialog
				.findViewById(R.id.vazgecButton);

		vazgecButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				addMusteriDialog.dismiss();
			}
		});

		final Button saveButton = (Button) addMusteriDialog
				.findViewById(R.id.saveButton);

		if ((adEditText.getText().toString().trim().isEmpty())
				&& (soyadEditText.getText().toString().trim().isEmpty()))
			saveButton.setEnabled(false);
		else
			saveButton.setEnabled(true);

		adEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if ((adEditText.getText().toString().trim().isEmpty())
						|| (soyadEditText.getText().toString().trim().isEmpty()))
					saveButton.setEnabled(false);
				else
					saveButton.setEnabled(true);

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

		soyadEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if ((adEditText.getText().toString().trim().isEmpty())
						|| (soyadEditText.getText().toString().trim().isEmpty()))
					saveButton.setEnabled(false);
				else
					saveButton.setEnabled(true);

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
		saveButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CloudEntity musteriCE = new CloudEntity(Musteri.KIND_MUSTERI);
				if (adEditText != null) {
					String idkey = DestoUtil.generateId();
					musteriCE.put(Musteri.PROP_IDKEY, idkey);
					String adstr = adEditText.getText().toString();
					musteriCE.put(Musteri.PROP_ADI, adstr);
					String soyadstr = soyadEditText.getText().toString();
					musteriCE.put(Musteri.PROP_SOYADI, soyadstr);
					String adresstr = adresEditText.getText().toString();
					musteriCE.put(Musteri.PROP_ADRES, adresstr);
				}

				CloudCallbackHandler<CloudEntity> mhandler = new CloudCallbackHandler<CloudEntity>() {
					public void onComplete(CloudEntity result) {

						LinearLayout linearLayout = (LinearLayout) addMusteriDialog
								.findViewById(R.id.musteriEditLayout);
						linearLayout.setBackgroundResource(R.color.blue);
						Toast toast = Toast.makeText(getActivity(),
								Musteri.LABEL_MUSTERI + " kaydedildi",
								DestoConstants.TOAST_MESSAGE_SHOWTIME);
						toast.show();
						Musteri musteriToSave = new Musteri(result
								.getKindName(), result);
						DbObjects.getMusteriList().add(musteriToSave);
						((ListActivity) getActivity()).getMusteriEventHandler()
								.fireMusteriChanged();
						addMusteriDialog.dismiss();
					}

					@Override
					public void onError(final IOException exception) {
						Log.i(Musteri.KIND_MUSTERI + " kayýt hatasý! :",
								exception.toString());
						Toast toast = Toast.makeText(getActivity(),
								Musteri.KIND_MUSTERI + " kayýt hatasý! ",
								DestoConstants.TOAST_MESSAGE_SHOWTIME);
						toast.show();
						saveButton.setEnabled(true);
						vazgecButton.setEnabled(true);
						adEditText.setEnabled(true);
						soyadEditText.setEnabled(true);
						adresEditText.setEnabled(true);
						progressBar.setVisibility(View.GONE);
					}
				};
				saveButton.setEnabled(false);
				vazgecButton.setEnabled(false);
				adEditText.setEnabled(false);
				soyadEditText.setEnabled(false);
				adresEditText.setEnabled(false);
				progressBar.setVisibility(View.VISIBLE);
				dbObjects.insert(musteriCE, mhandler);
			}

		});
		addMusteriDialog.show();
	}

	private void updateMusteri(final Dialog editMusteriDialog,
			final Musteri musteri, final TextView adEditText,
			final TextView soyadEditText, final TextView adresEditText) {

		musteri.put(Musteri.PROP_IDKEY, musteri.getIdkey());
		musteri.put(Musteri.PROP_ADI, adEditText.getText().toString());
		musteri.put(Musteri.PROP_SOYADI, soyadEditText.getText().toString());
		musteri.put(Musteri.PROP_ADRES, adresEditText.getText().toString());

		final DbObjects dbObjects = new DbObjects(getActivity());
		final ProgressBar progressBar = (ProgressBar) editMusteriDialog
				.findViewById(R.id.progressBar);
		CloudCallbackHandler<CloudEntity> mhandler = new CloudCallbackHandler<CloudEntity>() {

			public void onComplete(final CloudEntity result) {
				LinearLayout linearLayout = (LinearLayout) editMusteriDialog
						.findViewById(R.id.musteriEditLayout);
				linearLayout.setBackgroundResource(R.color.blue);
				Toast toast = Toast.makeText(getActivity(),
						result.get(Musteri.LABEL_MUSTERI).toString()
								+ " kaydedildi",
						DestoConstants.TOAST_MESSAGE_SHOWTIME);
				toast.show();
				DbObjects.getMusteriList().setMusteri(musteri);
				((ListActivity) getActivity()).getMusteriEventHandler()
						.fireMusteriChanged();
				editMusteriDialog.dismiss();
			}

			@Override
			public void onError(final IOException exception) {
				Log.i(Musteri.KIND_MUSTERI + " kayýt hatasý! :",
						exception.toString());
				Toast toast = Toast.makeText(getActivity(),
						Musteri.KIND_MUSTERI + " kayýt hatasý! ",
						DestoConstants.TOAST_MESSAGE_SHOWTIME);
				toast.show();
				adEditText.setEnabled(true);
				soyadEditText.setEnabled(true);
				adresEditText.setEnabled(true);
				progressBar.setVisibility(View.GONE);
			}

		};
		adEditText.setEnabled(false);
		soyadEditText.setEnabled(false);
		adresEditText.setEnabled(false);
		progressBar.setVisibility(View.VISIBLE);
		dbObjects.update(musteri, mhandler);
	}

	private void updateMusteriList(MusteriList list) {
		if (!list.isEmpty()) {
			suggestion = true;
			musteriListView.setVisibility(View.VISIBLE);
			musteriListView.setAdapter(new MusteriPostAdapter(getActivity(),
					android.R.layout.simple_list_item_1, list,isMusteriSecici()));
		} else {
			musteriListView.setVisibility(View.GONE);
		}
	}

	private void updateMusteriList() {
		suggestion = false;
		musteriList = DbObjects.getMusteriList();
		if ((musteriList != null) && !musteriList.isEmpty()) {
			musteriListView.setVisibility(View.VISIBLE);
			musteriListView.setAdapter(new MusteriPostAdapter(getActivity(),
					android.R.layout.simple_list_item_1, musteriList,isMusteriSecici()));
		} else {
			musteriListView.setVisibility(View.GONE);
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
				updateMusteriList(suggestionList);
				return true;
			}

			@Override
			public boolean onQueryTextSubmit(String query) {
				return false;

			}

		});

	}

	public boolean isMusteriSecici() {
		return musteriSecici;
	}

	public void setMusteriSecici(boolean musteriSecici) {
		this.musteriSecici = musteriSecici;
	}

	private void loadSuggestions(String query, SearchView searchView) {
		suggestionList = new MusteriList();
		for (Musteri musteri : musteriList) {
			if (musteri.toString().contains(query)) {
				suggestionList.add(musteri);
			}

		}

	}

	@Override
	public <T> void dataChanged(DataChangedEvent<T> e) {
		if (suggestion)
			updateMusteriList(suggestionList);
		else
			updateMusteriList();
	}

}
