package com.mindmade.mcom.fragments;

import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.mindmade.mcom.R;
import com.mindmade.mcom.adapterclasses.SearchAdapter;
import com.mindmade.mcom.utilclasses.Const;
import com.mindmade.mcom.utilclasses.NetworkConnectionManager;
import com.mindmade.mcom.utilclasses.PrefManager;
import com.mindmade.mcom.utilclasses.api.AllApi;
import com.mindmade.mcom.utilclasses.model.Products;
import com.mindmade.mcom.utilclasses.model.SearchModel;
import com.mindmade.mcom.utilclasses.network.ServiceGenerator;
import com.mindmade.mcom.utilclasses.searchview.AnimationUtil;
import com.mindmade.mcom.utilclasses.searchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class Search_Fragment extends Fragment {

    // private MaterialSearchView searchView;
    ImageButton searchButton;
    NetworkConnectionManager connectionManager;
    AllApi apiInitialize;
    PrefManager sessionManger;


    public static final int REQUEST_VOICE = 9999;

    // Avoid using magic numbers.
    //
    private static final int MAX_RESULTS = 1;

    private MenuItem mMenuItem;
    private boolean mIsSearchOpen = false;
    private boolean mClearingFocus;

    //Views
    private View mSearchLayout;
    private View mTintView;
    private ListView mSuggestionsListView;
    private EditText mSearchSrcTextView;
    private ImageButton mBackBtn;
    private ImageButton mVoiceBtn;
    private ImageButton mEmptyBtn;
    private RelativeLayout mSearchTopBar;
    private ProgressBar mSearchProgressBar;

    private CharSequence mOldQueryText;
    private CharSequence mUserQuery;

    private MaterialSearchView.OnQueryTextListener mOnQueryChangeListener;
    private MaterialSearchView.SearchViewListener mSearchViewListener;


    private Context mContext;

    private boolean shouldAnimate;

    SearchAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View search = inflater.inflate(R.layout.search__fragment, container, false);
        //      searchView = (MaterialSearchView) search.findViewById(R.id.search_view);
        searchButton = (ImageButton) search.findViewById(R.id.search_icon);


        mSearchLayout = search.findViewById(R.id.search_whole_layout);

        mSearchTopBar = (RelativeLayout) search.findViewById(R.id.search_top_bar);
        mSuggestionsListView = (ListView) search.findViewById(R.id.suggestion_list);
        mSearchSrcTextView = (EditText) search.findViewById(R.id.searchTextView);
        mBackBtn = (ImageButton) search.findViewById(R.id.action_up_btn);
        mVoiceBtn = (ImageButton) search.findViewById(R.id.action_voice_btn);
        mEmptyBtn = (ImageButton) search.findViewById(R.id.action_empty_btn);
        mTintView = search.findViewById(R.id.transparent_view);
        mSearchProgressBar = (ProgressBar) search.findViewById(R.id.search_progressbar);

        mSearchSrcTextView.setOnClickListener(mOnClickListener);
        mBackBtn.setOnClickListener(mOnClickListener);
        mVoiceBtn.setOnClickListener(mOnClickListener);
        mEmptyBtn.setOnClickListener(mOnClickListener);
        mTintView.setOnClickListener(mOnClickListener);

        connectionManager = new NetworkConnectionManager(getActivity());
        sessionManger = new PrefManager(getActivity());
        apiInitialize = ServiceGenerator.createService(AllApi.class, Const.API_VALUE, Const.PASSWORD_VALUE);


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchButton.setVisibility(View.GONE);
                mSearchLayout.setVisibility(VISIBLE);
                showSearch(true);
            }
        });

        mSearchSrcTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // searchDataFromApi(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
           //     searchDataFromApi(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchDataFromApi(s.toString());
                customOnTextChanged(s.toString());
            }
        });
        /*searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("Success", "Text change::: " + newText);
                searchDataFromApi(newText);
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {

            }
        });

        searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });*/

        return search;
    }

    private void searchDataFromApi(String title) {
        if (connectionManager.isConnectingToInternet()) {
            if (!title.isEmpty()) {
                Call<SearchModel> callSearchApi = apiInitialize.getSearchProducts(title, Const.LIMIT_VALUE, Const.FIELDS_VALUE);
                Log.w("Success", "URL::: " + callSearchApi.request().url().toString());
                callSearchApi.enqueue(new Callback<SearchModel>() {
                    @Override
                    public void onResponse(Call<SearchModel> call, Response<SearchModel> response) {
                        Log.w("Success", "Response::: " + new Gson().toJson(response.body()));
                        try {
                            if (response.isSuccessful()) {

                                SearchModel searchProducts = response.body();
                                if (searchProducts.getSearchList().size() > 0) {
                                    mSearchProgressBar.setVisibility(GONE);
                                    mSuggestionsListView.setVisibility(VISIBLE);
                                    List<SearchModel.Search> data = new ArrayList<>();
                                    data.addAll(searchProducts.getSearchList());
                                    Log.d("Success", "Data Size::: " + data.size());
                                    if (adapter != null) {
                                        adapter.clearAdapter();
                                    }
                                    adapter = new SearchAdapter(getActivity(), data);
                                    mSuggestionsListView.setAdapter(adapter);
                                    //setListViewHeightBasedOnChildren(mSuggestionsListView);
                                }
                            } else {
                                Log.e("Error", "Un successfull Respose");
                            }
                        } catch (Exception e) {
                            Log.e("Exception", "" + e);
                        }
                    }

                    @Override
                    public void onFailure(Call<SearchModel> call, Throwable t) {
                        Log.e("onFailure ", "" + t);
                    }
                });
            } else {
                Log.d("Success", "Title is emplty");
                if (adapter != null) {
                    adapter.clearAdapter();
                }
               /* mSearchProgressBar.setVisibility(VISIBLE);
                mSuggestionsListView.setVisibility(GONE);*/

            }
        } else {
            Log.d("Success", "No internet connection");
        }
    }


    /*Search Filter*/

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {

        public void onClick(View v) {
            if (v == mBackBtn) {
                closeSearch();
            } else if (v == mVoiceBtn) {
                // onVoiceClicked();
            } else if (v == mEmptyBtn) {
                mSearchSrcTextView.setText("");
                //customOnTextChanged("");
                searchDataFromApi("");
            } else if (v == mSearchSrcTextView) {
                showSuggestions();
            } else if (v == mTintView) {
                closeSearch();
            }
        }
    };


    private void customOnTextChanged(CharSequence newText) {
        CharSequence text = mSearchSrcTextView.getText();
        mUserQuery = text;
        boolean hasText = !TextUtils.isEmpty(text);
        if (hasText) {
            mEmptyBtn.setVisibility(VISIBLE);
        } else {
            mEmptyBtn.setVisibility(GONE);
            //showVoice(true);
        }

        if (mOnQueryChangeListener != null && !TextUtils.equals(newText, mOldQueryText)) {
            mOnQueryChangeListener.onQueryTextChange(newText.toString());
        }
        mOldQueryText = newText.toString();
    }

    /**
     * Close search view.
     */
    public void closeSearch() {
        if (!isSearchOpen()) {
            return;
        }

        mSearchSrcTextView.setText(null);
        dismissSuggestions();
        //clearFocus();

        mSearchLayout.setVisibility(GONE);
        if (mSearchViewListener != null) {
            mSearchViewListener.onSearchViewClosed();
        }
        mIsSearchOpen = false;
        searchButton.setVisibility(View.VISIBLE);
        mSearchLayout.setVisibility(GONE);

    }

    /**
     * Call this method to show suggestions list. This shows up when adapter is set. Call {@link #(ListAdapter)} before calling this.
     */
    public void showSuggestions() {
       /* if (mAdapter != null && mAdapter.getCount() > 0 && mSuggestionsListView.getVisibility() == GONE) {
            mSearchProgressBar.setVisibility(GONE);
            mSuggestionsListView.setVisibility(VISIBLE);
        }else {
            mSearchProgressBar.setVisibility(VISIBLE);
            mSuggestionsListView.setVisibility(GONE);
        }*/
        mSearchProgressBar.setVisibility(GONE);
        mSuggestionsListView.setVisibility(VISIBLE);
    }

    /**
     * Call this method and pass the menu item so this class can handle click events for the Menu Item.
     *
     * @param menuItem
     */
    public void setMenuItem(MenuItem menuItem) {
        this.mMenuItem = menuItem;
        mMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                showSearch();
                return true;
            }
        });
    }

    /**
     * Return true if search is open
     *
     * @return
     */
    public boolean isSearchOpen() {
        return mIsSearchOpen;
    }

    /**
     * Open Search View. This will animate the showing of the view.
     */
    public void showSearch() {
        if (shouldAnimate) {
            showSearch(true);
        } else {
            showSearch(false);
        }
    }

    /**
     * Open Search View. if animate is true, Animate the showing of the view.
     *
     * @param animate
     */
    public void showSearch(boolean animate) {
        if (isSearchOpen()) {
            return;
        }

        //Request Focus
        mSearchSrcTextView.setText(null);
        mSearchSrcTextView.requestFocus();

        if (animate) {
            AnimationUtil.fadeInView(mSearchLayout, AnimationUtil.ANIMATION_DURATION_SHORT, new AnimationUtil.AnimationListener() {
                @Override
                public boolean onAnimationStart(View view) {
                    return false;
                }

                @Override
                public boolean onAnimationEnd(View view) {
                    if (mSearchViewListener != null) {
                        mSearchViewListener.onSearchViewShown();
                    }
                    return false;
                }

                @Override
                public boolean onAnimationCancel(View view) {
                    return false;
                }
            });
        } else {
            mSearchLayout.setVisibility(VISIBLE);
            if (mSearchViewListener != null) {
                mSearchViewListener.onSearchViewShown();
            }
        }
        mIsSearchOpen = true;
    }

    /**
     * Dissmiss the suggestions list.
     */
    public void dismissSuggestions() {
        if (mSuggestionsListView.getVisibility() == VISIBLE) {
            mSuggestionsListView.setVisibility(GONE);
        }
    }

    /**
     * @hide
     */
    /*@Override
    public void clearFocus() {
        mClearingFocus = true;
        hideKeyboard(this);
        super.clearFocus();
        mSearchSrcTextView.clearFocus();
        mClearingFocus = false;
    }*/
    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void showKeyboard(View view) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1 && view.hasFocus()) {
            view.clearFocus();
        }
        view.requestFocus();
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, 0);
    }


    //since the whole layout in scrollview, the listview should be static(non scrollable) this method adjusts itself according to its content and make the list non scrollable
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        Log.d("Success", "Comes setListViewHeightBasedOnChildren");
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
