package com.vramachandran.demo.githubissuetracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by vramachandran on 10/2/2015.
 * A dialog fragment which should handle configuration changes
 * TODO:Not completed
 */
public class IssueDialogFragment extends DialogFragment {

    public static final String TAG_DIALOG_FRAGMENT = "DIALOG_FRAGMENT";
    public static final String DIALOG_TITLE = "DIALOG_TITLE";
    public static final String DIALOG_MESSAGE = "DIALOG_MESSAGE";

    AlertDialog.Builder mBuilder;
    String mTitle;
    String mMessage;

    public static DialogFragment newInstance(String title, String message, Activity activity) {

        DialogFragment doneDialogFrag = (DialogFragment) activity.getFragmentManager()
                .findFragmentByTag(TAG_DIALOG_FRAGMENT);
        if (doneDialogFrag == null) {
            doneDialogFrag = new IssueDialogFragment();
        }

        Bundle bundle = new Bundle();
        bundle.putString(IssueDialogFragment.DIALOG_TITLE, title);
        bundle.putString(IssueDialogFragment.DIALOG_MESSAGE, message);
        doneDialogFrag.setArguments(bundle);

        return doneDialogFrag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (mBuilder == null) {
            mBuilder = new AlertDialog.Builder(getActivity())
                    .setNegativeButton(
                            getActivity().getString(R.string.abc_action_mode_done),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
        }
        if (savedInstanceState == null) {
            savedInstanceState = getArguments();
        }
        mTitle = savedInstanceState.getString(DIALOG_TITLE);
        mBuilder.setTitle(mTitle);

        mMessage = savedInstanceState.getString(DIALOG_MESSAGE);
        mBuilder.setMessage(mMessage);
        return mBuilder.create();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(DIALOG_TITLE, mTitle);
        outState.putString(DIALOG_MESSAGE, mMessage);
    }
}
