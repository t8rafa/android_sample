package t8studio.com.br.worker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import t8studio.com.br.worker.api.Api;
import t8studio.com.br.worker.api.Listener;
import t8studio.com.br.worker.api.TypeErrors;
import t8studio.com.br.worker.model.Candidate;


public class WorkerActivity extends ActionBarActivity implements Listener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker);

        Integer[] ranges = new Integer[] {0,1,2,3,4,5,6,7,8,9,10};

        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this,
                android.R.layout.simple_spinner_item, ranges);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item );

        Spinner spinnerHtml = (Spinner)findViewById(R.id.spinner_skill_html);
        spinnerHtml.setAdapter(arrayAdapter);

        Spinner spinnerCss = (Spinner)findViewById(R.id.spinner_skill_css);
        spinnerCss.setAdapter(arrayAdapter);

        Spinner spinnerJs = (Spinner)findViewById(R.id.spinner_skill_js);
        spinnerJs.setAdapter(arrayAdapter);

        Spinner spinnerPython = (Spinner)findViewById(R.id.spinner_skill_python);
        spinnerPython.setAdapter(arrayAdapter);

        Spinner spinnerDjango = (Spinner)findViewById(R.id.spinner_skill_django);
        spinnerDjango.setAdapter(arrayAdapter);

        Spinner spinnerIos = (Spinner)findViewById(R.id.spinner_skill_ios);
        spinnerIos.setAdapter(arrayAdapter);

        Spinner spinnerAndroid = (Spinner)findViewById(R.id.spinner_skill_android);
        spinnerAndroid.setAdapter(arrayAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_worker, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            validateBeforeSend();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private Map<Integer, Integer> validateFields() {
        Map<Integer, Integer> result = new HashMap<Integer, Integer>();

        EditText textName = (EditText)findViewById(R.id.editText_candidate_name);
        EditText textEmail = (EditText)findViewById(R.id.editText_candidate_email);

        if (TextUtils.isEmpty(textName.getText().toString())) {
            result.put(R.id.editText_candidate_name, R.id.textView_candidate_name_error);
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(textEmail.getText().toString()).matches()) {
            result.put(R.id.editText_candidate_email, R.id.textView_candidate_email_error);
        }

        return result;
    }

    private void validateBeforeSend() {
        Map<Integer, Integer> errors = validateFields();

        if (errors.size() == 0) {
            confirmBeforeSend();
        } else {
            boolean isFirst = true;
            for (Map.Entry<Integer, Integer> entry : errors.entrySet()) {
                TextView textError = (TextView)findViewById(entry.getValue());
                textError.setVisibility(View.VISIBLE);

                if (isFirst) {
                    EditText editError = (EditText)findViewById(entry.getKey());
                    editError.requestFocus();
                    isFirst = false;
                }
            }
        }
    }

    private void confirmBeforeSend() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Confirma o envio dos dados?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        saveForm();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        Dialog alert = builder.create();
        alert.show();
    }

    private void saveForm() {
        EditText textName = (EditText)findViewById(R.id.editText_candidate_name);
        EditText textEmail = (EditText)findViewById(R.id.editText_candidate_email);
        Spinner spinnerHtml = (Spinner)findViewById(R.id.spinner_skill_html);
        Spinner spinnerCss = (Spinner)findViewById(R.id.spinner_skill_css);
        Spinner spinnerJs = (Spinner)findViewById(R.id.spinner_skill_js);
        Spinner spinnerPython = (Spinner)findViewById(R.id.spinner_skill_python);
        Spinner spinnerDjango = (Spinner)findViewById(R.id.spinner_skill_django);
        Spinner spinnerIos = (Spinner)findViewById(R.id.spinner_skill_ios);
        Spinner spinnerAndroid = (Spinner)findViewById(R.id.spinner_skill_android);

        Candidate candidate = new Candidate();
        candidate.setName(textName.getText().toString());
        candidate.setEmail(textEmail.getText().toString());
        candidate.setHtml((int)spinnerHtml.getSelectedItem());
        candidate.setCss((int) spinnerCss.getSelectedItem());
        candidate.setJavaScript((int) spinnerJs.getSelectedItem());
        candidate.setPython((int) spinnerPython.getSelectedItem());
        candidate.setDjango((int) spinnerDjango.getSelectedItem());
        candidate.setIos((int) spinnerIos.getSelectedItem());
        candidate.setAndroid((int) spinnerAndroid.getSelectedItem());

        Api api = Api.getInstance(getApplicationContext());
        api.Send(candidate, this);
    }

    @Override
    public void onSuccess() {
        String message = getResources().getString(R.string.success_registration);

        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onError(TypeErrors type) {
        String message = getResources().getString(R.string.error_connection);
        if (type == TypeErrors.SERVER) {
            message = getResources().getString(R.string.error_server);
        }

        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
        toast.show();
    }
}