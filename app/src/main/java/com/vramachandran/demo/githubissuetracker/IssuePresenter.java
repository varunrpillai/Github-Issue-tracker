package com.vramachandran.demo.githubissuetracker;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by vramachandran on 10/1/2015.
 * An adapter between the view and datamodel.
 * It manages all the network operations to keep the data model updated.
 * and performs it asynchronously.
 */
public class IssuePresenter {

    public static final String TAG = "IP";
    public static final String NEW_LINE = "\n";

    public enum RequestType {ISSUE, COMMENT}

    private IssueDataModel mData;
    private IssueView mView;
    private Context mContext;
    private ItemDataArrayAdapter mAdapter;

    public IssuePresenter(Context context, IssueDataModel data, IssueView view) {
        mData = data;
        mContext = context;
        mView = view;
    }

    public void initiateIssueDownload(String url) {
        if (mData.getData().size() == 0) {
            new DownloadTask(mContext, RequestType.ISSUE).execute(url);
        } else {
            mAdapter = new ItemDataArrayAdapter(mContext, 0, mData.getData());
            mView.setIssueListAdapter(mAdapter);
        }
    }

    public void initiateCommentDownload(IssueItemData issue) {
        if (issue.mCommentCount > 0) {
            new DownloadTask(mContext, RequestType.COMMENT).execute(issue.mCommentUrl);
        } else {
            mView.showDoneDialog(mContext.getResources().getString(R.string.comments),
                    mContext.getResources().getString(R.string.no_comments));
        }
    }


    private class DownloadTask extends AsyncTask<String, Void, String> {
        private final Context mContext;
        private final RequestType mType;

        DownloadTask(Context context, RequestType type) {
            mContext = context;
            mType = type;
        }

        @Override
        protected void onPreExecute() {
            mView.showProgressDialog(mContext.getResources()
                    .getString(R.string.comments_progress_message));
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                return downloadUrl(urls[0], mType);
            } catch (IOException e) {
                return "Git hub Issue page retrieval failed. URL may be invalid.";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            mView.closeProgressDialog();
            if (mType == RequestType.ISSUE) {
                ItemDataArrayAdapter adapter =
                        new ItemDataArrayAdapter(mContext, 0, mData.getData());
                mView.setIssueListAdapter(adapter);

                //TODO: Perform notify rather than creating new
                mAdapter = new ItemDataArrayAdapter(mContext, 0, mData.getData());
                mView.setIssueListAdapter(mAdapter);
            } else if (mType == RequestType.COMMENT) {
                mView.showDoneDialog(mContext.getString(R.string.comments), result);
            }
        }
    }

    private String downloadUrl(String url, RequestType type) throws IOException {

        String contentAsString = NetworkUtils.getUrlContent(url);
        JSONArray jArray = null;
        try {
            jArray = new JSONArray(contentAsString);

            switch (type) {
                case ISSUE:
                    for (int i = 0; i < jArray.length(); i++) {
                        // Pulling items from the array
                        JSONObject issueObject = jArray.getJSONObject(i);

                        String title = issueObject.getString(Parameters.TITLE);
                        String body = issueObject.getString(Parameters.BODY);
                        String comments = issueObject.getString(Parameters.COMMENTS);
                        String comments_url = issueObject.getString(Parameters.COMMENTS_URL);
                        int noOfComments = 0;
                        try {
                            noOfComments = Integer.parseInt(comments);
                        } catch (NumberFormatException e) {
                            Log.e(TAG, e.getMessage());
                            //Carry on with the value 0
                        }

                        mData.addIssueItem(new IssueItemData(title, body, noOfComments, comments_url));
                    }
                    break;
                case COMMENT:
                    StringBuilder comments = new StringBuilder(mContext.getResources()
                            .getString(R.string.order_message));
                    comments.append(NEW_LINE).append(mContext.getString(R.string.divider)).append(NEW_LINE);
                    for (int i = 0; i < jArray.length(); i++) {
                        try {
                            JSONObject oneObject = jArray.getJSONObject(i);
                            // Pulling items from the array
                            String body = oneObject.getString(Parameters.BODY);
                            JSONObject user = oneObject.getJSONObject(Parameters.USER);
                            String login = user.getString(Parameters.LOGIN);
                            comments.append(mContext.getResources().getString(R.string.user))
                                    .append(login).append(mContext.getResources()
                                    .getString(R.string.says));
                            comments.append(body);
                            comments.append(NEW_LINE).append(mContext.getResources()
                                    .getString(R.string.divider)).append(NEW_LINE);

                        } catch (JSONException e) {
                            Log.e(TAG, e.getMessage());
                            continue;
                        }
                    }
                    contentAsString = comments.toString();
                    break;

            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
        return contentAsString;
    }
}
