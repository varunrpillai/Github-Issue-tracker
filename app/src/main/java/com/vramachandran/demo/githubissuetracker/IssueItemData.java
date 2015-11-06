package com.vramachandran.demo.githubissuetracker;

/**
 * The data structure of the issue list item
 */
public final class IssueItemData {
    public String mTitle;
    public String mBody;
    public int mCommentCount;
    public String mCommentUrl;

    public IssueItemData(String title, String body, int noOfComments, String commentUrl) {
        mTitle = title;
        if (body != null) {
            mBody = body.length() > 140 ? body.substring(0, 139) : body;
        }
        mCommentCount = noOfComments;
        mCommentUrl = commentUrl;
    }
}