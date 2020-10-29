package com.adisalagic.test.api;

import com.adisalagic.test.api.json.EditEntry;
import com.adisalagic.test.api.json.Entries;
import com.adisalagic.test.api.json.EntryAdd;
import com.adisalagic.test.api.json.Session;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.EventListener;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Api {

    private static volatile Api mInstance;

    public interface Listener{
        void onFail();
    }

    OkHttpClient client;
    Gson         gson;
    String       token = "uez41ak-wr-P9tXU5b";
    private Session session;


    private static final String BASE_URL       = "https://bnet.i-partner.ru/testAPI/";
    private static final String SESSION        = "new_session";
    private static final String ENTRIES_GET    = "get_entries";
    private static final String ENTRIES_ADD    = "add_entry";
    private static final String ENTRIES_EDIT   = "edit_entry";
    private static final String ENTRIES_REMOVE = "remove_entry";

    private static final String APPLICATION_JSON = "application/json";

    private Listener listener = new Listener() {
        @Override
        public void onFail() {

        }
    };




    private Api() {
        client = new OkHttpClient.Builder()
                .build();

        gson = new Gson();
    }

    private Api(EventListener eventListener){
        client = new OkHttpClient.Builder()
                .eventListener(eventListener)
                .build();
        gson = new Gson();
    }

    public static Api getInstance() {
        if (mInstance == null) {
            synchronized (Api.class) {
                if (mInstance == null) {
                    mInstance = new Api();
                }
            }
        }
        return mInstance;
    }

    public void addFailListener(Listener listener){
        this.listener = listener;
    }

    private RequestBody createBody(String req, Session session, String data, String id) {
        String        and       = "&";
        MediaType     mediaType = MediaType.parse("application/x-www-form-urlencoded");
        if (session != null) {
            if (session.getData() == null) {
                session.setData(this.session.getData());
            }
        }
        StringBuilder builder   = new StringBuilder();
        builder.append("a=").append(req);
        if (session != null) {
            builder.append(and).append("session=").append(session.getData().getSession());
        }
        if (data != null) {
            builder.append(and).append("body=").append(data);
        }
        if (id != null) {
            builder.append(and).append("id=").append(id);
        }
        return RequestBody.create(mediaType, builder.toString());
    }

    private RequestBody createSessionBody() {
        return createBody(SESSION, null, null, null);
    }

    private Request createRequest(RequestBody requestBody){
        return new Request.Builder()
                .url(BASE_URL)
                .post(requestBody)
                .addHeader("token", token)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
    }


    public Session createNewSession() {
        RequestBody requestBody = createSessionBody();
        Request request = createRequest(requestBody);
        Call     call     = client.newCall(request);
        Response response = null;
        Session  session  = new Session();
        session.setStatus(ApiStatus.FAILED);
        try {
            response = call.execute();
            session = gson.fromJson(response.body().string(), Session.class);
        } catch (Exception e) {
            e.printStackTrace();
            listener.onFail();
        }
        this.session = session;
        return session;
    }


    public Entries getEntries(Session session) {
        RequestBody requestBody = createBody(ENTRIES_GET, session, null, null);
        Request request = createRequest(requestBody);
        Call     call     = client.newCall(request);
        Response response = null;
        Entries  entries  = new Entries();
        entries.setStatus(ApiStatus.FAILED);
        try {
            response = call.execute();
            String json = response.body().string();
            entries = gson.fromJson(json, Entries.class);
        } catch (Exception e) {
            e.printStackTrace();
            listener.onFail();
        }
        return entries;
    }

    public EntryAdd addEntry(Session session, String body) {
        RequestBody requestBody = createBody(ENTRIES_ADD, session, body, null);
        Request request = createRequest(requestBody);
        Call     call     = client.newCall(request);
        Response response = null;
        EntryAdd entryAdd = new EntryAdd();
        entryAdd.setStatus(ApiStatus.FAILED);
        try {
            response = call.execute();
            entryAdd = gson.fromJson(response.body().string(), EntryAdd.class);
        } catch (IOException e) {
            e.printStackTrace();
            listener.onFail();
        }
        return entryAdd;
    }

    public EditEntry editEntry(Session session, String id, String body) {
        RequestBody requestBody = createBody(ENTRIES_EDIT, session, body, id);
        Request request = createRequest(requestBody);
        Call      call      = client.newCall(request);
        EditEntry editEntry = new EditEntry();
        Response  response;
        editEntry.setStatus(ApiStatus.FAILED);
        try {
            response = call.execute();
            editEntry = gson.fromJson(response.body().string(), EditEntry.class);
        } catch (Exception e) {
            e.printStackTrace();
            listener.onFail();

        }
        return editEntry;
    }

    public EditEntry removeEntry(Session session, String id) {
        RequestBody requestBody = createBody(ENTRIES_REMOVE, session, null, id);
        Request request = createRequest(requestBody);
        Call call = client.newCall(request);
        Response response;
        EditEntry editEntry = new EditEntry();
        editEntry.setStatus(ApiStatus.FAILED);
        try {
            response = call.execute();
            editEntry = gson.fromJson(response.body().string(), EditEntry.class);
        }catch (Exception e){
            e.printStackTrace();
            listener.onFail();
        }
        return editEntry;
    }
}
