package com.rong.library.widget.mapsearchbar;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.MenuRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Copyright (c) 2016-2017, j2Rong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class MapSearchBar extends FrameLayout implements
		View.OnClickListener, PopupMenu.OnMenuItemClickListener,
		TextView.OnEditorActionListener, View.OnFocusChangeListener,
		SearchResultAdapter.OnSuggestionItemClickListener {

	public MapSearchBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
	}

	public MapSearchBar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(attrs);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public MapSearchBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init(attrs);
	}

	public interface MapSearchActionListener {
		boolean onSearchBarMenuItemClick(MenuItem item);
		void onNavigationClick(View v);
		void onHistoryClick(View v);
		void onSearchQuerySuggestionClick(SearchResult data);

		void onSearchConfirmedAsync(String query);
		void onSearchQueryChangedAsync(String query);
	}


	public static final int NAV_MAP_AUTO_NAV = 0;
	public static final int NAV_MAP_GOOGLE = 1;


	private CardView cardView = null;
	private AppCompatImageView btnMenu = null;
	private AppCompatImageView btnNavigation = null;
	private AppCompatImageView btnHistory = null;
	private AppCompatEditText searchEdit = null;
	private AppCompatImageView btnClear = null;
	private RelativeLayout containerSuggestion = null;
	private RecyclerView suggestionRecycler = null;
	private PopupMenu popupMenu = null;

	private SearchResultAdapter adapter;
	private MapSearchActionListener mListener = null;
	private SearchQueryThread queryThread = null;

	private int screenHeight = 0;
	private boolean isSearchEditFocused = false;
	private boolean focusCameFromHistory = false;

	private boolean suggestionsVisible;


	private String hint;
	private String emptyHistory;
	private int elevation;
	private int margin;
	private int navType;


	private void init(AttributeSet attrs) {
		inflate(getContext(), R.layout.search_bar, this);

		TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MapSearchBar);
		hint = a.getString(R.styleable.MapSearchBar_ms_searchHint);
		emptyHistory = a.getString(R.styleable.MapSearchBar_ms_defaultHistoryEmptyString);
		elevation = a.getDimensionPixelSize(R.styleable.MapSearchBar_ms_elevation, -1);
		margin = a.getDimensionPixelSize(R.styleable.MapSearchBar_ms_margin, -1);
		navType = a.getInt(R.styleable.MapSearchBar_ms_mapType, -1);

		a.recycle();

		// default settings
		if (hint == null)		hint = getContext().getString(R.string.ms_default_edit_hint);
		if (emptyHistory == null)	emptyHistory = "";
		if (elevation == -1)	elevation = getResources().getDimensionPixelSize(R.dimen.ms_default_search_bar_elevation);
		if (margin == -1)		margin = getResources().getDimensionPixelSize(R.dimen.ms_default_search_bar_margin);
		if (navType == -1)		navType = NAV_MAP_AUTO_NAV;


		findViews();
		setupViews();

		DisplayMetrics displaymetrics = new DisplayMetrics();
		((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		screenHeight = displaymetrics.heightPixels;
	}

	private void findViews() {
		cardView = findViewById(R.id.card);
		btnMenu = findViewById(R.id.btn_menu);
		btnNavigation = findViewById(R.id.btn_nav);
		btnHistory = findViewById(R.id.btn_history);
		searchEdit = findViewById(R.id.edit);
		btnClear = findViewById(R.id.btn_clear);
		containerSuggestion = findViewById(R.id.list_container);
		suggestionRecycler = findViewById(R.id.recycler);
	}

	private void setupViews() {
		cardView.setCardElevation(elevation);

		FrameLayout.LayoutParams lpCard = (FrameLayout.LayoutParams) cardView.getLayoutParams();
		lpCard.setMargins(margin, margin, margin, margin);
		cardView.setLayoutParams(lpCard);

		if (popupMenu == null)
			btnMenu.setVisibility(GONE);

		btnHistory.setOnClickListener(this);

		int navResId = (navType == NAV_MAP_AUTO_NAV) ? R.drawable.a_90 : R.drawable.gmap_24;
		Drawable d = ContextCompat.getDrawable(getContext(), navResId);
		btnNavigation.setImageDrawable(d);
		btnNavigation.setOnClickListener(this);

		searchEdit.setHint(hint);
		searchEdit.setOnEditorActionListener(this);
		searchEdit.setOnFocusChangeListener(this);
		searchEdit.addTextChangedListener(new SearchEditTextWatcher());

		btnClear.setVisibility(GONE);
		btnClear.setOnClickListener(this);

		adapter = new SearchResultAdapter(getContext(), this, emptyHistory);
		suggestionRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
		suggestionRecycler.setAdapter(adapter);
	}


	//
	// animation
	//
	private int getSuggestionHeight() {
		// returns pixels
		int h = (int)(screenHeight * 0.45);
		return adapter.getSuggestedHeight(suggestionRecycler, h);
	}

	private void animateSuggestionList(int from, int to) {
		suggestionsVisible = (to > 0);

		final ViewGroup.LayoutParams lp = containerSuggestion.getLayoutParams();
		if (to == 0 && lp.height == 0)
			return;		// already collapsed

		if (to == 0)
			from = lp.height;

		ValueAnimator animator = ValueAnimator.ofInt(from, to);
		animator.setDuration(200);
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				lp.height = (int) animation.getAnimatedValue();
				containerSuggestion.setLayoutParams(lp);
			}
		});

		// if (adapter.getItemCount() > 0)
		animator.start();
	}



	@Override
	public void onClick(View v) {
		int id = v.getId();	// id == getId()

		if (id == R.id.btn_menu) {
			if (popupMenu != null) {
				popupMenu.show();
				searchEdit.clearFocus();	// clear focus and hide suggestion list
			}
		} else if (id == R.id.btn_clear) {
			searchEdit.setText("");			// auto trigger onTextChanged
			//searchEdit.requestFocus();		// hide
		} else if (id == R.id.btn_nav) {
			if (mListener != null)
				mListener.onNavigationClick(btnNavigation);
		} else if (id == R.id.btn_history) {
			if (mListener != null) {
				mListener.onHistoryClick(btnHistory);
				focusCameFromHistory = true;
				searchEdit.setText("");
				searchEdit.requestFocus();
			}
		}
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		if (actionId == EditorInfo.IME_ACTION_SEARCH) {

			String q = searchEdit.getText().toString();

			// clear and hide suggestions
			searchEdit.setText("");
			searchEdit.clearFocus();

			if (mListener != null) {
				if (!TextUtils.isEmpty(q.trim()))
					mListener.onSearchConfirmedAsync(q);
			}

			return true;
		}

		return false;
	}

	private class SearchEditTextWatcher implements TextWatcher {
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

		@Override
		public void afterTextChanged(Editable s) {}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			if (TextUtils.isEmpty(s.toString())) {
				btnClear.setVisibility(GONE);
			} else {
				btnClear.setVisibility(VISIBLE);
			}

			if (queryThread != null) {
				queryThread.pushQuery(s.toString());
			}
		}
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

		if (hasFocus) {
			if (!focusCameFromHistory && imm != null)
				imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
			focusCameFromHistory = false;

			isSearchEditFocused = true;
			if (adapter.getItemCount() > 0 && !suggestionsVisible)
				animateSuggestionList(0, getSuggestionHeight());

		} else {
			if (imm != null)
				imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

			isSearchEditFocused = false;
			if (suggestionsVisible)
				animateSuggestionList(-1, 0);
		}
	}

	@SuppressWarnings("SimplifiableIfStatement")
	@Override
	public boolean onMenuItemClick(MenuItem item) {
		if (mListener != null)
			return mListener.onSearchBarMenuItemClick(item);
		else
			return false;
	}

	@Override
	public void onSuggestionItemClick(int position, View v, SearchResult data) {
		if (mListener != null)
			mListener.onSearchQuerySuggestionClick(data);

		searchEdit.setText("");
		searchEdit.clearFocus();
	}


	/**
	 * update search result list
	 * @param list		new search result list, null if only content changed
	 * @param theQuery	query of this search
	 *
	 */
	public void updateSearchResult(List<SearchResult> list, String theQuery) {
		int previous, current;

		previous = getSuggestionHeight();

		if (list != null)
			adapter.setList(list, theQuery);

		adapter.notifyDataSetChanged();
		current = getSuggestionHeight();

		if (isSearchEditFocused)
			animateSuggestionList(previous, current);
	}


	public void inflateMenu(@MenuRes int menuRes) {
		btnMenu.setVisibility(VISIBLE);
		btnMenu.setOnClickListener(this);

		popupMenu = new PopupMenu(getContext(), btnMenu);
		popupMenu.setOnMenuItemClickListener(this);
		popupMenu.inflate(menuRes);
		popupMenu.setGravity(Gravity.END);
	}

	public void setMapSearchActionListener(MapSearchActionListener l) {
		mListener = l;
		if (queryThread != null) {
			queryThread.updateListener(mListener);
		}
	}

	public void startQueryThread() {
		if (queryThread == null) {
			queryThread = new SearchQueryThread();
			queryThread.start();
		}
	}

	public void stopQueryThread() {
		if (queryThread != null) {
			queryThread.quit();
			queryThread = null;
		}
	}

	public void setNavigationType(int type) {
		navType = type;
		if (type != NAV_MAP_AUTO_NAV && type != NAV_MAP_GOOGLE)
			navType = NAV_MAP_AUTO_NAV;

		int navResId = (navType == NAV_MAP_AUTO_NAV) ? R.drawable.a_90 : R.drawable.gmap_24;
		Drawable d = ContextCompat.getDrawable(getContext(), navResId);
		btnNavigation.setImageDrawable(d);
	}




}
