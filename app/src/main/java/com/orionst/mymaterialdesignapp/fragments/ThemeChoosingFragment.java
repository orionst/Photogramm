package com.orionst.mymaterialdesignapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.orionst.mymaterialdesignapp.R;
import com.orionst.mymaterialdesignapp.utils.SharedPrefs;

/**
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ThemeChoosingFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public ThemeChoosingFragment() {

    }

    public static ThemeChoosingFragment newInstance() {
        ThemeChoosingFragment fragment = new ThemeChoosingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getContext().setTheme(SharedPrefs.getCurrentTheme(getContext()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_theme_choosing, container, false);

        Button btnSetWarm = layout.findViewById(R.id.btn_theme_warm);
        Button btnSetCold = layout.findViewById(R.id.btn_theme_cold);

        return layout;
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

}
