package com.vramachandran.demo.githubissuetracker;

import java.util.ArrayList;

/**
 * An interface for handling the Issue DataModel
 * Created by vramachandran on 10/1/2015.
 */
public interface IssueDataModel {
    /**
     * Returns the list of issues
     *
     * @return issue list
     */
    ArrayList<IssueItemData> getData();

    /**
     * Add an issue to list
     *
     * @param issueItemData item
     */
    void addIssueItem(IssueItemData issueItemData);

    //TODO:Support Remove
    //TODO:Support Update
}