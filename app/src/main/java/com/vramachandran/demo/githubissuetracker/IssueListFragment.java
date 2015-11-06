package com.vramachandran.demo.githubissuetracker;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * A placeholder fragment containing a simple view.
 */
public class IssueListFragment extends ListFragment implements IssueView {

    private static final String TAG_ISSUE_FRAGMENT = "issue_data_fragment";
    //TODO:Make it as a user input
    public static final String GIT_HUB_ISSUES_URL = "https://api.github.com/repos/rails/rails/issues";

    Context mContext;
    private IssueDataFragment mIssueFragment;
    private IssuePresenter mIssuePresenter;
    private ProgressDialog mProgressDialog;
    private AlertDialog.Builder mBuilder;
    private IssueDialogFragment mDialogFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = (Context) getActivity();
        FragmentManager fm = getFragmentManager();
        mIssueFragment = (IssueDataFragment) fm.findFragmentByTag(TAG_ISSUE_FRAGMENT);
        // If the Fragment is non-null, then it is currently being
        // retained across a configuration change.
        if (mIssueFragment == null) {
            mIssueFragment = new IssueDataFragment();
            fm.beginTransaction().add(mIssueFragment, TAG_ISSUE_FRAGMENT).commit();
        }

        //TODO: if (mIssueFragment instanceof DataModel)
        mIssuePresenter = new IssuePresenter(mContext, mIssueFragment, this);

        if (!NetworkUtils.hasConnection(mContext)) {
            Toast.makeText(getActivity(), mContext.getString(R.string.no_network),
                    Toast.LENGTH_LONG).show();
        } else {
            mIssuePresenter.initiateIssueDownload(GIT_HUB_ISSUES_URL);
        }

        return inflater.inflate(R.layout.fragment_issue_tracker, container, false);
    }

    @Override
    public void showProgressDialog(String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    @Override
    public void closeProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void setIssueListAdapter(ArrayAdapter adapter) {
        setListAdapter(adapter);
    }

    @Override
    public void showDoneDialog(String title, String message) {
        DialogFragment doneDialogFrag = IssueDialogFragment.newInstance(title, message, getActivity());
        if (!doneDialogFrag.isAdded()) {
            doneDialogFrag.show(getFragmentManager(), IssueDialogFragment.TAG_DIALOG_FRAGMENT);
        }
    }


    @Override
    public void onListItemClick(ListView list, View view, int position, long id) {
        super.onListItemClick(list, view, position, id);
        IssueItemData item = (IssueItemData) getListView().getItemAtPosition(position);
        if (!NetworkUtils.hasConnection(mContext)) {
            Toast.makeText(getActivity(), mContext.getString(R.string.no_network),
                    Toast.LENGTH_LONG).show();
        } else {
            mIssuePresenter.initiateCommentDownload(item);
        }
    }
}
