package com.vramachandran.demo.githubissuetracker;

/**
 * Created by vramachandran on 10/1/2015.
 */

import android.app.Fragment;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * This Fragment is a retention fragment to keep the issue list on configuration changes.
 */
public class IssueDataFragment extends Fragment implements IssueDataModel {

    ArrayList<IssueItemData> mItemDataList = new ArrayList<IssueItemData>();

    /**
     * This method will only be called once when the retained
     * Fragment is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retain this fragment across configuration changes.
        setRetainInstance(true);

    }

    @Override
    public ArrayList<IssueItemData> getData() {
        return mItemDataList;
    }

    @Override
    public void addIssueItem(IssueItemData issueItemData) {
        mItemDataList.add(issueItemData);
    }
}