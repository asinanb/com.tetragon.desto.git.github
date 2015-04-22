package com.tetragon.desto.satis;

import com.google.cloud.backend.core.DbObjects;
import com.tetragon.desto.DestoApplication;
import com.tetragon.desto.ListActivity;
import com.tetragon.desto.R;
import com.tetragon.desto.SubMenuActivity;
import com.tetragon.desto.eventHandler.DataChangedEvent;
import com.tetragon.desto.eventHandler.DataListener;
import com.tetragon.desto.model.SatisItem;
import com.tetragon.desto.model.StokItem;
import com.tetragon.desto.model.StokItemList;
import com.tetragon.desto.util.DestoConstants;

import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;

public class SatisListFragment extends Fragment implements OnItemClickListener,
		DataListener {

	private ListView stokListView;
	private StokItemList stokItemList;
	private SatisItem satisItem;

	private StokItemList suggestionList = new StokItemList();
	private boolean suggestion = false;
	private ImageView satisImg;

	private ProgressBar progressBar;

	@Override
	public void onResume() {
		super.onResume();
		boolean satisTamam=((DestoApplication) getActivity().getApplication()).isSatisTamamlandi();
		if (satisTamam){
			getActivity().onBackPressed();
		}else{
			// Set title
			getActivity().getActionBar().setTitle(R.string.satisTitle);
			getActivity().getActionBar().setIcon(R.drawable.ic_satis);
		}
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		stokItemList = DbObjects.getStokItemList();
		stokItemList.setSelectedAll(false);
		DbObjects.getSelectedStokList().clear();
		// Set view
		View view = inflater.inflate(R.layout.desto_list, container, false);
		progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
		setHasOptionsMenu(true);

		// Stok deðiþikliklerini dinle
		addStokListener();

		// Create visual items
		addVisualItems(view);

		// Update List view
		updateStokList();
		progressBar.setVisibility(View.GONE);

		return view;
	}

	

	private void openSatisFragment() {
		// ArrayList<Stok> satislistesi) {
		Intent intent = new Intent(getActivity(), SubMenuActivity.class);
		intent.putExtra("TAG", DestoConstants.SATISISLEMI);
		startActivity(intent);
	}

	private void updateStokList() {
		suggestion = false;
		stokItemList = DbObjects.getStokItemList();
		if (!stokItemList.isEmpty()) {
			stokListView.setVisibility(View.VISIBLE);
			stokListView.setAdapter(new SatisPostAdapter(getActivity(),
					android.R.layout.simple_list_item_1, stokItemList));
		} else {
			stokListView.setVisibility(View.GONE);
		}
	}

	private void updateStokList(StokItemList list) {
		if (!list.isEmpty()) {
			suggestion = false;
			stokListView.setVisibility(View.VISIBLE);
			stokListView.setAdapter(new SatisPostAdapter(getActivity(),
					android.R.layout.simple_list_item_1, list));
		} else {
			stokListView.setVisibility(View.GONE);
		}
	}

	private void addVisualItems(View view) {

		stokListView = (ListView) view.findViewById(R.id.listView);
		stokListView.setOnItemClickListener(this);
		satisImg = (ImageView) view.findViewById(R.id.ic_add);
		satisImg.setImageResource(R.drawable.ic_satis);
		satisImg.setEnabled(false);
		satisImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				openSatisFragment();
			}

		});
	}

	private void addStokListener() {
		// Stok deðiþikliklerini dinle
		ListActivity activity = (ListActivity) getActivity(); 
		activity.getStokEventHandler().addListener(this);
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
				updateStokList(suggestionList);
				return true;
			}

			@Override
			public boolean onQueryTextSubmit(String query) {
				return false;

			}

		});

	}

	private void loadSuggestions(String query, SearchView searchView) {
		suggestionList = new StokItemList();
		for (StokItem stok : stokItemList) {
			if (stok.getModelItem().getModel().contains(query)) {
				suggestionList.add(stok);
			}

		}

	}

	@Override
	public <T> void dataChanged(DataChangedEvent<T> e) {
		if (suggestion)
			updateStokList(suggestionList);
		else
			updateStokList();
		StokItemList list= DbObjects.getSelectedStokList();
		satisItem = DbObjects.createSatisItem(list);
		
		if (satisItem.getStokItemList().isEmpty()){
			if(satisImg!=null)
				satisImg.setEnabled(false);
		}else if(satisImg!=null)
			satisImg.setEnabled(true);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		openSatisFragment();
		updateStokList();
		
	}

}
