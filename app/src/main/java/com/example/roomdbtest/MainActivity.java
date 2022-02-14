package com.example.roomdbtest;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.ref.WeakReference;
import java.sql.Timestamp;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String debugTag = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.index);
        Button btn = findViewById(R.id.button);
        AppDatabase database = AppDatabaseSingleton.getInstace(getApplicationContext());

        btn.setOnClickListener(new ButtonClickListenr(this,database,textView));

    }

    private class ButtonClickListenr implements View.OnClickListener{
        private Activity activity;
        private AppDatabase database;
        private TextView textView;

        public ButtonClickListenr(Activity activity, AppDatabase database, TextView tv) {
            this.activity = activity;
            this.database = database;
            this.textView = tv;
        }

        @Override
        public void onClick(View v) {
            Log.d("debugTag","onClick");
            new DataStoreAsyncTask(database, activity, textView).execute();
        }
    }

    private static class DataStoreAsyncTask extends AsyncTask<Void,Void,Integer>{
        private WeakReference<Activity> weakActivity;
        private AppDatabase database;
        private TextView textView;
        private StringBuilder stringBuilder;

        public DataStoreAsyncTask(AppDatabase database,Activity activity,TextView textView){
            this.database = database;
            weakActivity = new WeakReference<>(activity);
            this.textView = textView;
        }

        @Override
        protected Integer doInBackground(Void...params){
            AccessTimeDao accessTimeDao = (AccessTimeDao) database.accessTimeDao();
            Log.d("new AccessTime",new Timestamp(System.currentTimeMillis()).toString());
            accessTimeDao.insert(new AccessTime(new Timestamp(System.currentTimeMillis()).toString()));

            stringBuilder = new StringBuilder();
            List<AccessTime> atList = accessTimeDao.getAll();
            for (AccessTime at: atList) {
                stringBuilder.append(at.getAccessTime()).append("\n");
            }

            return 0;
        }

        @Override
        protected void onPostExecute(Integer code){
            Log.d("MainActivity","onPostExecute");
            Activity activity = weakActivity.get();
            if(activity == null){
                return;
            }
            textView.setText(stringBuilder.toString());
        }
    }
}