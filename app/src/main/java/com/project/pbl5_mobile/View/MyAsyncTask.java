package com.project.pbl5_mobile.View;

import android.os.AsyncTask;

public class MyAsyncTask extends AsyncTask<Void, Void, Void> {
    @Override
    protected Void doInBackground(Void... voids) {
        // Thực hiện các hoạt động mạng ở đây
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        // Cập nhật giao diện người dùng (nếu cần) sau khi hoàn thành hoạt động mạng
    }
}
