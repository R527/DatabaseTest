//package com.example.roomdbtest;
//
//import android.app.Activity;
//import android.os.AsyncTask;
//import android.widget.TextView;
//
//import java.lang.ref.WeakReference;
//
//private class DataStoreAsyncTask extends AsyncTask<Void, Void, Integer> {
//    private WeakReference<Activity> weakActivity;
//    private AppDatabase db;
//    private TextView textView;
//    private StringBuilder sb;
//
//    public DataStoreAsyncTask(AppDatabase db, Activity activity, TextView textView) {
//        this.db = db;
//        weakActivity = new WeakReference<>(activity);
//        this.textView = textView;
//    }
//
//    @Override
//    protected Integer doInBackground(Void... params) {
//        AccessTimeDao accessTimeDao = db.accessTimeDao();
//        accessTimeDao.insert(new AccessTime(new Timestamp(System.currentTimeMillis()).toString()));
//
//        sb = new StringBuilder();
//        List<AccessTime> atList = accessTimeDao.getAll();
//        for (AccessTime at: atList) {
//            sb.append(at.getAccessTime()).append("\n");
//        }
//
//        return 0;
//    }
//
//    @Override
//    protected void onPostExecute(Integer code) {
//        Activity activity = weakActivity.get();
//        if(activity == null) {
//            return;
//        }
//
//        textView.setText(sb.toString());
//
//    }
//}