package com.adisalagic.test;

import android.content.Context;
import android.os.AsyncTask;

import com.adisalagic.test.api.Api;
import com.adisalagic.test.api.json.ApiClass;
import com.adisalagic.test.api.json.DataSession;
import com.adisalagic.test.api.json.Session;

import okhttp3.EventListener;

public class ApiHandler {
    private Context context;

    public interface OnDoneListener {
        void onDone(ApiClass apiClass);
    }

    private EventListener eventListener;


    ApiHandler() {
    }

    public void addEventListener(Api.Listener listener) {
        Api.getInstance().addFailListener(listener);
    }


    public void createNewSession(OnDoneListener doneListener) {
        ApiNewSession session = new ApiNewSession();
        session.addListener(doneListener);
        session.execute();
    }

    public void getEntries(Session session, OnDoneListener doneListener) {
        ApiGetEntries getEntries = new ApiGetEntries();
        getEntries.addListener(doneListener);
        getEntries.execute(session.getData().getSession());
    }

    public void addNewEntry(Session session, String body, OnDoneListener doneListener) {
        ApiAddEntry apiAddEntry = new ApiAddEntry();
        apiAddEntry.addListener(doneListener);
        apiAddEntry.execute(session.getData().getSession(), body);
    }

    private static class ApiAddEntry extends ApiRequest {

        @Override
        protected ApiClass doInBackground(String... strings) {
            Api     api     = Api.getInstance();
            Session session = createNew(strings[0]);
            return api.addEntry(session, strings[1]);
        }

        @Override
        protected void onPostExecute(ApiClass apiClass) {
            super.onPostExecute(apiClass);
            doneListener.onDone(apiClass);
        }
    }

    private static class ApiGetEntries extends ApiRequest {

        @Override
        protected ApiClass doInBackground(String... strings) {
            Api     api     = Api.getInstance();
            Session session = createNew(strings[0]);
            return api.getEntries(session);
        }

        @Override
        protected void onPostExecute(ApiClass apiClass) {
            super.onPostExecute(apiClass);
            doneListener.onDone(apiClass);
        }
    }

    private static class ApiNewSession extends ApiRequest {
        @Override
        protected ApiClass doInBackground(String... strings) {
            Api api = Api.getInstance();
            return api.createNewSession();
        }

        @Override
        protected void onPostExecute(ApiClass apiClass) {
            super.onPostExecute(apiClass);
            doneListener.onDone(apiClass);
        }
    }

    private static abstract class ApiRequest extends AsyncTask<String, Void, ApiClass> {
        OnDoneListener doneListener;

        protected Session createNew(String s) {
            Session     session     = new Session();
            DataSession dataSession = new DataSession();
            dataSession.setSession(s);
            return session;
        }

        public void addListener(OnDoneListener listener) {
            doneListener = listener;
            if (doneListener == null) {
                doneListener = new OnDoneListener() {
                    @Override
                    public void onDone(ApiClass apiClass) {

                    }
                };
            }
        }
    }
}
