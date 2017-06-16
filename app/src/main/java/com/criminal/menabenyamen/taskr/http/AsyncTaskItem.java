package com.criminal.menabenyamen.taskr.http;

import android.os.AsyncTask;

/**
 * Created by menabenyamen on 2017-06-08.
 */

public final class AsyncTaskItem extends AsyncTask<Void, Void, HttpResponse>{

    private final ApiCommand apiCommand;

    public AsyncTaskItem(ApiCommand apiCommand) {
        this.apiCommand = apiCommand;
    }

    @Override
    protected HttpResponse doInBackground(Void... params) {
        return apiCommand.execute();
    }
}
