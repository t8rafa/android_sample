package t8studio.com.br.worker.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import t8studio.com.br.worker.model.Candidate;
import t8studio.com.br.worker.model.CandidateResponse;

/**
 * Created by rafael on 19/03/15.
 */
public class Api {
    private final String URL = "http://workerapi.azurewebsites.net/api/values/";

    private static Api instance;
    private ConnectionManager connectionManager;

    private Api(Context context) {
        connectionManager = ConnectionManager.getInstance(context);
    }

    public static synchronized Api getInstance(Context context) {
        if (instance == null) {
            instance = new Api(context);
        }

        return instance;
    }

    public void Send(final Candidate candidate, final Listener listener) {
        final Gson parser = new Gson();

        JsonRequest request = new JsonRequest(
                Request.Method.POST,
                URL,
                parser.toJson(candidate),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        CandidateResponse item = parser.fromJson(response.toString(), CandidateResponse.class);
                        if (item.getStatus() == "error") {
                            listener.onError(TypeErrors.SERVER);
                        } else {
                            listener.onSuccess();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError(TypeErrors.NETWORK);
                    }
                }
        );
        request.setTag("CANDIDATE_TAG");

        connectionManager.addToRequestQueue(request);
    }
}