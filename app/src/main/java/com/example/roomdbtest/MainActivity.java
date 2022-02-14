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
        Button delete_btn = findViewById(R.id.delete_button);
        Button btn = findViewById(R.id.button);
        AppDatabase database = AppDatabaseSingleton.getInstace(getApplicationContext());

        btn.setOnClickListener(new ButtonClickListenr(this,database,textView,true));
        delete_btn.setOnClickListener(new ButtonClickListenr(this,database,textView,false));

        Log.d(debugTag,"onCreate");
    }

    private class ButtonClickListenr implements View.OnClickListener{
        private Activity activity;
        private AppDatabase database;
        private TextView textView;
        private boolean btn_flag;

        public ButtonClickListenr(Activity activity, AppDatabase database, TextView tv,boolean btn_flag) {
            this.activity = activity;
            this.database = database;
            this.textView = tv;
            this.btn_flag = btn_flag;
        }

        @Override
        public void onClick(View v) {
            Log.d("MainActivity","onClick");
            new DataStoreAsyncTask(database, activity, textView,btn_flag).execute();
        }
    }

    private static class DataStoreAsyncTask extends AsyncTask<Void,Void,Integer>{
        private WeakReference<Activity> weakActivity;
        private AppDatabase database;
        private TextView textView;
        private StringBuilder stringBuilder;
        private boolean btn_flag;

        public DataStoreAsyncTask(AppDatabase database,Activity activity,TextView textView,boolean btn_flag){
            this.database = database;
            weakActivity = new WeakReference<>(activity);
            this.textView = textView;
            this.btn_flag = btn_flag;
        }

        @Override
        protected Integer doInBackground(Void...params){
            Log.d("MainActivity",new Timestamp(System.currentTimeMillis()).toString());
            AccessTimeDao accessTimeDao = (AccessTimeDao) database.accessTimeDao();
            AccessTime accessTime = new AccessTime("");

            if(btn_flag){
                Log.d("MainActivity","add");
                accessTimeDao.insert(new AccessTime(new Timestamp(System.currentTimeMillis()).toString()));

            }else {
                Log.d("MainActivity","delete");

                for(int i = 0;i < 150;i++){
                    accessTime.setId(i);
                    accessTimeDao.delete(accessTime);
                }
            }


            stringBuilder = new StringBuilder();
            List<AccessTime> atList = accessTimeDao.getAll();
            for (AccessTime at: atList) {
                stringBuilder.append(at.getAccessTime()).append("\n");
            }
            Log.d("MainActivity","");

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