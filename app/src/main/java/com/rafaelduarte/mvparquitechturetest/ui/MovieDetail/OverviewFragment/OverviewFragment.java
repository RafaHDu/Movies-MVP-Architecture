package com.rafaelduarte.mvparquitechturetest.ui.MovieDetail.OverviewFragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.rafaelduarte.mvparquitechturetest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OverviewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OverviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OverviewFragment extends Fragment implements OverviewFragmentViewInterface {

    @BindView(R.id.tvReadMore)
    TextView tvReadMore;
    @BindView(R.id.tvStoryline)
    TextView tvStoryline;
    @BindView(R.id.tvRate)
    TextView tvRate;

    private boolean isReadMoreClicked = false;
    private static final String TMDB_ID_ARGS = "tmdb_id_args";
    private int parameterTmdbId;
    private OnFragmentInteractionListener mListener;
    OverviewFragmentPresenter overviewFragmentPresenter;

    public OverviewFragment() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment CommentsFragment.
     */
    public static OverviewFragment newInstance(int tmdbID) {
        OverviewFragment fragment = new OverviewFragment();
        Bundle args = new Bundle();
        args.putInt(TMDB_ID_ARGS, tmdbID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            parameterTmdbId = getArguments().getInt(TMDB_ID_ARGS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overview, container, false);

        ButterKnife.bind(this, view);
        setupMVP();
        getMovirDetails();

        tvReadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isReadMoreClicked) {
                    tvStoryline.setMaxLines(3);
                    isReadMoreClicked = false;
                    tvReadMore.setText("Read More");
                } else {
                    tvStoryline.setMaxLines(Integer.MAX_VALUE);
                    isReadMoreClicked = true;
                    tvReadMore.setText("Read Less");
                }
            }
        });

        return view;
    }

    private void setupMVP() {
        overviewFragmentPresenter = new OverviewFragmentPresenter(this, parameterTmdbId);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void getMovirDetails(){
        overviewFragmentPresenter.getMovieDetails();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void displayOverviewInfo(String rating, String storyline) {
        tvRate.setText(rating);
        tvStoryline.setText(storyline);
        ViewTreeObserver vto = tvStoryline.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Layout l = tvStoryline.getLayout();
                if (l != null) {
                    int lines = l.getLineCount();
                    if (lines > 0)
                        if (l.getEllipsisCount(lines - 1) == 0) {
                            tvReadMore.setVisibility(View.GONE);
                        }
                }
                tvStoryline.getViewTreeObserver().removeOnGlobalLayoutListener(this);  // --> tenho de o matar se nao esta sempre a verificar de X em X
            }

        });
    }

    @Override
    public void displayError(String error) {
        showToast(error);
    }

    @Override
    public void showToast(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     **/
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
