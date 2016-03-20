package com.brightlight.padzmj.myfootballscores.Fixtures.UI;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brightlight.padzmj.myfootballscores.R;

/**
 * Created by PadzMJ on 19/03/2016.
 */
public class ShareFixtureDialog extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.share_fixture_fragment, container, false);
        getDialog().setTitle("Share");

        return rootView;
    }
}
