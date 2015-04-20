package com.tetragon.desto.params;

import java.io.IOException;

import com.google.cloud.backend.core.CloudCallbackHandler;
import com.google.cloud.backend.core.CloudEntity;
import com.google.cloud.backend.core.DbObjects;
import com.tetragon.desto.ListActivity;
import com.tetragon.desto.R;
import com.tetragon.desto.eventHandler.DataChangedEvent;
import com.tetragon.desto.eventHandler.DataListener;
import com.tetragon.desto.model.MarkaItem;
import com.tetragon.desto.model.MarkaItemList;
import com.tetragon.desto.model.Model;
import com.tetragon.desto.model.ModelItem;
import com.tetragon.desto.model.ModelItemList;
import com.tetragon.desto.model.Urun_tipItem;
import com.tetragon.desto.model.Urun_tipItemList;
import com.tetragon.desto.model.UrungrubuItem;
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
import android.widget.ImageButton;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ModelListFragment extends Fragment implements OnItemClickListener,
		DataListener {

	private ListView modelListView;
	private ModelItemList modelList = DbObjects.getModelItemList();
	private MarkaItemList markaList = DbObjects.getMarkaItemList();
	private Urun_tipItemList urunTipList = DbObjects.getUrunTipItemList();

	private ModelItemList suggestionList = new ModelItemList();
	private boolean suggestion = false;

	private ImageView addImg;

	private Spinner markaspinner;
	private Spinner urunTipspinner;
	private EditText fiyatEditText;
	
	private int lastMarka=0;
	private int lastUrunTip=0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = inflater.inflate(R.layout.desto_list, container, false);
		setHasOptionsMenu(true);
		getActivity().setTitle("Model Listesi");

		// Model deðiþikliklerini dinle
		ListActivity activity = (ListActivity) getActivity();
		activity.getModelEventHandler().addListener(this);

		modelListView = (ListView) view.findViewById(R.id.listView);
		modelListView.setOnItemClickListener(this);
		updateModelList();
		addImg = (ImageView) view.findViewById(R.id.ic_add);
		addImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				addModel();
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
		final Dialog editModelDialog = new Dialog(getActivity());

		final ModelItem model;
		if (suggestion)
			model = suggestionList.get(position);
		else
			model = modelList.get(position);

		editModelDialog.setContentView(R.layout.model_save);
		editModelDialog.setTitle(Model.LABEL_MODEL + " Kayýtlarý");
		final ProgressBar progressBar = (ProgressBar) editModelDialog
				.findViewById(R.id.progressBar);
		progressBar.setVisibility(View.GONE);
		editModelDialog.show();

		final EditText modelEditText = (EditText) editModelDialog
				.findViewById(R.id.modelEditText);
		modelEditText.setText(model.getModel());

		markaspinner = (Spinner) editModelDialog
				.findViewById(R.id.markaspinner);
		urunTipspinner = (Spinner) editModelDialog
				.findViewById(R.id.urunTipspinner);

		ArrayAdapter<MarkaItem> markaadapter = new ArrayAdapter<MarkaItem>(
				getActivity(), android.R.layout.simple_spinner_item, markaList);
		markaadapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		markaspinner.setAdapter(markaadapter);

		if (model.getMarkaItem() != null)
			markaspinner
					.setSelection(markaadapter.getPosition(model.getMarkaItem()));

		ArrayAdapter<Urun_tipItem> uruntipadapter = new ArrayAdapter<Urun_tipItem>(
				getActivity(), android.R.layout.simple_spinner_item,
				urunTipList);

		uruntipadapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		urunTipspinner.setAdapter(uruntipadapter);

		if (model.getUrun_tipItem() != null)
			urunTipspinner.setSelection(uruntipadapter.getPosition(model
					.getUrun_tipItem()));

		fiyatEditText = (EditText) editModelDialog
				.findViewById(R.id.fiyatEditText);
		
		fiyatEditText.setText(model.getListeFiyati());
		
		final ImageButton vazgecButton = (ImageButton) editModelDialog
				.findViewById(R.id.vazgecButton);
		vazgecButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				editModelDialog.dismiss();
			}
		});
		final ImageButton saveModelButton = (ImageButton) editModelDialog
				.findViewById(R.id.saveButton);
		if (modelEditText.getText().toString().trim().isEmpty())
			saveModelButton.setEnabled(false);
		else
			saveModelButton.setEnabled(true);
		modelEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (modelEditText.getText().toString().trim().isEmpty())
					saveModelButton.setEnabled(false);
				else
					saveModelButton.setEnabled(true);
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

		saveModelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				saveModelButton.setEnabled(false);
				vazgecButton.setEnabled(false);

				int upos = urunTipspinner.getSelectedItemPosition();
				Urun_tipItem purun_tip = (Urun_tipItem) urunTipList.get(upos);

				UrungrubuItem purungrubu = DbObjects.getUrungrubuItemList()
						.findUrungrubuItemByIdkey(
								purun_tip.getUrungrubuItem().getIdkey());

				int mpos = markaspinner.getSelectedItemPosition();
				MarkaItem pmarka = (MarkaItem) markaList.get(mpos);

				model.setUrun_tipItem(purun_tip);
				model.setUrungrubuItem(purungrubu);
				model.setMarkaItem(pmarka);
				model.setListeFiyati(fiyatEditText.getText().toString());
				model.setModel(modelEditText.getText().toString());
				
				updateModel(editModelDialog, model, modelEditText, position);
			}
		});
	}

	private void updateModel(final Dialog editModelDialog, final ModelItem model,
			final TextView modelEditText, final int position) {
		
		final DbObjects dbObjects = new DbObjects(getActivity());
		final ProgressBar progressBar = (ProgressBar) editModelDialog
				.findViewById(R.id.progressBar);
		CloudCallbackHandler<CloudEntity> mhandler = new CloudCallbackHandler<CloudEntity>() {

			public void onComplete(final CloudEntity result) {
				LinearLayout linearLayout = (LinearLayout) editModelDialog
						.findViewById(R.id.modelEditLayout);
				linearLayout.setBackgroundResource(R.color.blue);
				Toast toast = Toast
						.makeText(getActivity(), result.get(Model.PROP_MODEL)
								.toString() + " kaydedildi",
								DestoConstants.TOAST_MESSAGE_SHOWTIME);
				toast.show();
				
				String idkey=(String) result.get(Model.PROP_IDKEY);
				ModelItem item2save = DbObjects.getModelItemList().findModelItemByIdkey(idkey);//
				
				item2save.assign(new ModelItem(result));
				
				DbObjects.getModelItemList().addOrUpdate(item2save);
				((ListActivity) getActivity()).getModelEventHandler()
						.fireModelChanged();
				
				editModelDialog.dismiss();
			}

			@Override
			public void onError(final IOException exception) {
				Log.i(Model.KIND_MODEL + " kayýt hatasý! :",
						exception.toString());
				Toast toast = Toast.makeText(getActivity(), Model.LABEL_MODEL
						+ " kayýt hatasý! ",
						DestoConstants.TOAST_MESSAGE_SHOWTIME);
				toast.show();
				modelEditText.setEnabled(true);
				markaspinner.setEnabled(true);
				urunTipspinner.setEnabled(true);
				progressBar.setVisibility(View.GONE);
			}

		};
		modelEditText.setEnabled(false);
		markaspinner.setEnabled(false);
		urunTipspinner.setEnabled(false);
		progressBar.setVisibility(View.VISIBLE);
		dbObjects.update(model.getModelCE(), mhandler);
	}

	private void addModel() {
		final DbObjects dbObjects = new DbObjects(getActivity());
		final Dialog addModelDialog = new Dialog(getActivity());
		addModelDialog.setContentView(R.layout.model_save);
		addModelDialog.setTitle(Model.LABEL_MODEL + " Giriþi");
		final ProgressBar progressBar = (ProgressBar) addModelDialog
				.findViewById(R.id.progressBar);
		progressBar.setVisibility(View.GONE);
		addModelDialog.show();

		final EditText modelEditText = (EditText) addModelDialog
				.findViewById(R.id.modelEditText);
		final ImageButton vazgecButton = (ImageButton) addModelDialog
				.findViewById(R.id.vazgecButton);

		vazgecButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				addModelDialog.dismiss();
			}
		});
		markaspinner = (Spinner) addModelDialog.findViewById(R.id.markaspinner);

		ArrayAdapter<MarkaItem> markaadapter = new ArrayAdapter<MarkaItem>(
				getActivity(), android.R.layout.simple_spinner_item, markaList);
		markaadapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		markaspinner.setAdapter(markaadapter);
		markaspinner.setSelection(lastMarka);
		
		urunTipspinner = (Spinner) addModelDialog
				.findViewById(R.id.urunTipspinner);

		ArrayAdapter<Urun_tipItem> uruntipadapter = new ArrayAdapter<Urun_tipItem>(
				getActivity(), android.R.layout.simple_spinner_item,
				urunTipList);
		uruntipadapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		urunTipspinner.setAdapter(uruntipadapter);
		urunTipspinner.setSelection(lastUrunTip);
		
		fiyatEditText = (EditText) addModelDialog
				.findViewById(R.id.fiyatEditText);
		
		final ImageButton saveModelButton = (ImageButton) addModelDialog
				.findViewById(R.id.saveButton);

		if (modelEditText.getText().toString().trim().isEmpty())
			saveModelButton.setEnabled(false);
		else
			saveModelButton.setEnabled(true);
		modelEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (modelEditText.getText().toString().trim().isEmpty())
					saveModelButton.setEnabled(false);
				else
					saveModelButton.setEnabled(true);
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
		saveModelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final ModelItem modelItem= new ModelItem();
				if (modelEditText != null) {

					int mpos = markaspinner.getSelectedItemPosition();
					lastMarka=mpos;
					MarkaItem pmarka = markaList.get(mpos);
					
					int upos = urunTipspinner.getSelectedItemPosition();
					lastUrunTip=upos;
					Urun_tipItem purunTip = urunTipList.get(upos);
					
					UrungrubuItem pug = purunTip.getUrungrubuItem();
					
					modelItem.setMarkaItem(pmarka);
					modelItem.setUrun_tipItem(purunTip);
					
					modelItem.setModel(modelEditText.getText()
							.toString());
					modelItem.setListeFiyati(fiyatEditText.getText().toString());
					modelItem.setUrungrubuItem(pug);
					
				}

				CloudCallbackHandler<CloudEntity> mhandler = new CloudCallbackHandler<CloudEntity>() {
					public void onComplete(final CloudEntity result) {

						LinearLayout linearLayout = (LinearLayout) addModelDialog
								.findViewById(R.id.modelEditLayout);
						linearLayout.setBackgroundResource(R.color.blue);
						Toast toast = Toast.makeText(getActivity(),
								result.get(Model.PROP_MODEL).toString()
										+ " kaydedildi",
								DestoConstants.TOAST_MESSAGE_SHOWTIME);
						toast.show();

						modelItem.assign(new ModelItem(result));
						
						DbObjects.getModelItemList().add(modelItem);
						((ListActivity) getActivity()).getModelEventHandler()
								.fireModelChanged();
						
						addModelDialog.dismiss();
					}

					@Override
					public void onError(final IOException exception) {
						Log.i(Model.LABEL_MODEL + " kayýt hatasý! :",
								exception.toString());
						Toast toast = Toast.makeText(getActivity(),
								Model.LABEL_MODEL + " kayýt hatasý! ",
								DestoConstants.TOAST_MESSAGE_SHOWTIME);
						toast.show();
						saveModelButton.setEnabled(true);
						vazgecButton.setEnabled(true);
						modelEditText.setEnabled(true);
						markaspinner.setEnabled(true);
						urunTipspinner.setEnabled(true);
						progressBar.setVisibility(View.GONE);
					}
				};
				saveModelButton.setEnabled(false);
				vazgecButton.setEnabled(false);
				modelEditText.setEnabled(false);
				markaspinner.setEnabled(false);
				urunTipspinner.setEnabled(false);
				progressBar.setVisibility(View.VISIBLE);
				dbObjects.insert(modelItem.getModelCE(), mhandler);
			}
		});

	}

	private void updateModelList(ModelItemList list) {
		if (!list.isEmpty()) {
			suggestion = true;
			modelListView.setVisibility(View.VISIBLE);
			modelListView.setAdapter(new ModelPostAdapter(getActivity(),
					android.R.layout.simple_list_item_1, list));
		} else {
			modelListView.setVisibility(View.GONE);
		}
	}

	private void updateModelList() {
		suggestion = false;
		modelList = DbObjects.getModelItemList();

		if (!modelList.isEmpty()) {
			modelListView.setVisibility(View.VISIBLE);
			modelListView.setAdapter(new ModelPostAdapter(getActivity(),
					android.R.layout.simple_list_item_1, modelList));
		} else {
			modelListView.setVisibility(View.GONE);
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
				updateModelList(suggestionList);
				return true;
			}

			@Override
			public boolean onQueryTextSubmit(String query) {
				return false;
			}
		});
	}

	private void loadSuggestions(String query, SearchView searchView) {
		suggestionList = new ModelItemList();
		for (ModelItem item : modelList) {
			if (item.getModel().contains(query)) {
				suggestionList.add(item);
			}
		}
	}

	@Override
	public <T> void dataChanged(DataChangedEvent<T> e) {
		if (suggestion)
			updateModelList(suggestionList);
		else
			updateModelList();
	}
}
