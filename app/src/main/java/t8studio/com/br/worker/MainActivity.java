package t8studio.com.br.worker;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startEvaluation(View view) {
        Intent workIntent = new Intent(this, WorkerActivity.class);
        startActivity(workIntent);
    }
}