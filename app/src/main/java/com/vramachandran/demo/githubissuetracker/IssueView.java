package com.vramachandran.demo.githubissuetracker;

import android.widget.ArrayAdapter;

/**
 * Created by vramachandran on 10/1/2015.
 */
public interface IssueView {

    void showProgressDialog(String message);

    void closeProgressDialog();

    void setIssueListAdapter(ArrayAdapter adapter);

    void showDoneDialog(String comments, String result);
}
