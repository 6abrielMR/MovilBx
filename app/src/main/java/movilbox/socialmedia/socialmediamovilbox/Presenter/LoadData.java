package movilbox.socialmedia.socialmediamovilbox.Presenter;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import movilbox.socialmedia.socialmediamovilbox.Model.ContentM;
import movilbox.socialmedia.socialmediamovilbox.Model.Post;
import movilbox.socialmedia.socialmediamovilbox.Model.User;
import movilbox.socialmedia.socialmediamovilbox.View.Activity.Posts;
import movilbox.socialmedia.socialmediamovilbox.View.Fragment.Content;

public class LoadData {

    //constants
    private static final String TAG = "LoadData";
    private static final String POSTS_SIMPLE = "https://jsonplaceholder.typicode.com/todos";
    private static final String POSTS_CONTENT = "https://jsonplaceholder.typicode.com/posts";
    private static final String USER = "https://jsonplaceholder.typicode.com/users";
    private static final String USERID = "userId";
    private static final String ID = "id";
    private static final String TITTLE = "title";
    private static final String SUBTITTLE = "title";
    private static final String BODY = "body";
    private static final String STATE = "completed";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String ADDRESS = "address";
    private static final String STREET = "street";
    private static final String SUITE = "suite";
    private static final String CITY = "city";

    public static class LoadPostSimples extends AsyncTask<RequestQueue, Void, Void> {

        @SuppressLint("StaticFieldLeak")
        private Posts context;

        public LoadPostSimples(Posts context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(RequestQueue... request) {
            JsonArrayRequest response = new JsonArrayRequest(Request.Method.GET, POSTS_SIMPLE, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray resp) {
                            List<Post> data = new ArrayList<>();
                            int i = 0;
                            int s = 0;
                            try {
                                while (i < resp.length()) {
                                    JSONObject jsonObject = resp.getJSONObject(i);
                                    if (s >= 20)
                                        data.add(new Post(jsonObject.getInt(USERID), jsonObject.getInt(ID),
                                                jsonObject.getString(TITTLE), jsonObject.getBoolean(STATE), false));
                                    else {
                                        data.add(new Post(jsonObject.getInt(USERID), jsonObject.getInt(ID),
                                                jsonObject.getString(TITTLE), jsonObject.getBoolean(STATE), true));
                                        s++;
                                    }

                                    Log.d(TAG, "onResponse: " + s);
                                    i++;
                                }
                                context.showData(data);
                            } catch (JSONException je) {
                                je.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });

            request[0].add(response);

            return null;
        }
    }

    public static class LoadContent extends AsyncTask<RequestQueue, Void, Void> {

        @SuppressLint("StaticFieldLeak")
        private Content context;
        private int userId;

        public LoadContent(Content context, int userId) {
            this.context = context;
            this.userId = userId;
        }

        @Override
        protected Void doInBackground(RequestQueue... request) {
            JsonArrayRequest response = new JsonArrayRequest(Request.Method.GET, POSTS_CONTENT, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray resp) {
                            int i = 0;
                            try {
                                while (i < resp.length()) {
                                    JSONObject jsonObject = resp.getJSONObject(i);
                                    if (jsonObject.getInt(USERID) == userId) {
                                        context.showContent(new ContentM(jsonObject.getInt(USERID),
                                                jsonObject.getInt(ID),
                                                jsonObject.getString(SUBTITTLE),
                                                jsonObject.getString(BODY)));
                                        break;
                                    }
                                    i++;
                                }
                            } catch (JSONException je) {
                                je.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });

            request[0].add(response);

            return null;
        }
    }

    public static class LoadUser extends AsyncTask<RequestQueue, Void, Void> {

        @SuppressLint("StaticFieldLeak")
        private Content context;
        private int userId;

        public LoadUser(Content context, int userId) {
            this.context = context;
            this.userId = userId;
        }

        @Override
        protected Void doInBackground(RequestQueue... request) {
            JsonArrayRequest response = new JsonArrayRequest(Request.Method.GET, USER, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray resp) {
                            int i = 0;
                            try {
                                while (i < resp.length()) {
                                    JSONObject jsonObject = resp.getJSONObject(i);
                                    if (jsonObject.getInt(ID) == userId) {
                                        JSONArray jsonArray = new JSONArray();
                                        jsonArray.put(jsonObject.get(ADDRESS));
                                        context.showUserData(new User(jsonObject.getInt(ID),
                                                jsonObject.getString(NAME),
                                                jsonObject.getString(EMAIL),
                                                jsonArray.getJSONObject(0).getString(STREET) + ", " +
                                                jsonArray.getJSONObject(0).getString(SUITE) + " - " +
                                                jsonArray.getJSONObject(0).getString(CITY)));
                                        break;
                                    }
                                    i++;
                                }
                            } catch (JSONException je) {
                                je.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });

            request[0].add(response);

            return null;
        }
    }

}
