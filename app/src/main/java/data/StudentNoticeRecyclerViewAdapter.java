package data;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notice.noticeapp.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import models.Notice;
import com.example.notice.noticeapp.home_activity;

import static com.example.notice.noticeapp.R.style.Theme_AlertDialog;



public class StudentNoticeRecyclerViewAdapter extends RecyclerView.Adapter<StudentNoticeRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Notice> studentNotice;

    public StudentNoticeRecyclerViewAdapter(Context context, List<Notice> studentNotice) {
        this.context = context;
        this.studentNotice = studentNotice;
    }

    @Override
    public StudentNoticeRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home,parent,false);

        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(StudentNoticeRecyclerViewAdapter.ViewHolder holder, int position) {

        Notice student_notice = studentNotice.get(position);
        String PDF_download_link = student_notice.getNotice_content();

        holder.notice_student_title.setText(student_notice.getNotice_title());


    }

    @Override
    public int getItemCount() {
        return studentNotice.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView notice_student_title;
        Button PDF_download_btn;

        public ViewHolder(View itemView, final Context ctx) {
            super(itemView);

            context = ctx;
            notice_student_title = (TextView)itemView.findViewById(R.id.title_textView);
            PDF_download_btn = (Button)itemView.findViewById(R.id.pdf_open_btn);

            PDF_download_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (ctx instanceof home_activity){
//                        ((home_activity)ctx).disp();
//                    }
                    Notice noticeObj = studentNotice.get(getAdapterPosition());
                    //Log.d("Notice Link", noticeObj.getNotice_content());
                    final String PDF_link = noticeObj.getNotice_content();
                    new DownloadTask(context, PDF_link);

                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }
    public class CheckForSDCard {

        public boolean isSDCardPresent() {
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                return true;
            }
            return false;
        }
    }

    public class DownloadTask {

        private static final String TAG = "Download Task";
        private Context context;

        private String downloadUrl = "", downloadFileName = "";
        private ProgressDialog progressDialog;

        public DownloadTask(Context context, String downloadUrl) {
            this.context = context;

            this.downloadUrl = downloadUrl;


            downloadFileName = downloadUrl.substring(downloadUrl.lastIndexOf('/'), downloadUrl.length());//Create file name by picking download file name from URL
            Log.e(TAG, downloadFileName);

            //Start Downloading Task
            new DownloadingTask().execute();
        }

        private class DownloadingTask extends AsyncTask<Void, Void, Void> {

            File apkStorage = null;
            File outputFile = null;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Downloading...");
                progressDialog.show();
            }

            @Override
            protected void onPostExecute(Void result) {
                try {
                    if (outputFile != null) {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Downloaded Successfully", Toast.LENGTH_SHORT).show();
                    } else {

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                            }
                        }, 3000);

                        Log.e(TAG, "Download Failed");

                    }
                } catch (Exception e) {
                    e.printStackTrace();



                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                        }
                    }, 3000);
                    Log.e(TAG, "Download Failed with Exception - " + e.getLocalizedMessage());

                }


                super.onPostExecute(result);
            }

            @Override
            protected Void doInBackground(Void... arg0) {
                int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
                try {
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED){

                        ActivityCompat.requestPermissions((Activity) context,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                   }
                    else{
                    URL url = new URL(downloadUrl);
                    HttpURLConnection c = (HttpURLConnection) url.openConnection();
                    c.setRequestMethod("GET");
                    c.connect();


                    if (c.getResponseCode() != HttpURLConnection.HTTP_OK) {
                        Log.e(TAG, "Server returned HTTP " + c.getResponseCode()
                                + " " + c.getResponseMessage());

                    }



                    if (new CheckForSDCard().isSDCardPresent()) {

                        apkStorage = new File(
                                Environment.getExternalStorageDirectory() + "/"
                                        + "Notice Files");
                    }
                    else {
                        Toast.makeText(context, "Oops!! There is no SD Card.", Toast.LENGTH_SHORT).show();
                    }


                    if (!apkStorage.exists()) {
                        apkStorage.mkdir();
                        Log.e(TAG, "Directory Created.");
                    }


                    outputFile = new File(apkStorage, downloadFileName);


                    if (!outputFile.exists()) {
                        outputFile.createNewFile();
                        Log.e(TAG, "File Created");
                    }

                    FileOutputStream fos = new FileOutputStream(outputFile);

                    InputStream is = c.getInputStream();

                    byte[] buffer = new byte[1024];
                    int len1 = 0;
                    while ((len1 = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, len1);
                    }


                    fos.close();
                    is.close();

                } }catch (Exception e) {


                    e.printStackTrace();
                    outputFile = null;
                    Log.e(TAG, "Download Error Exception " + e.getMessage());
                }

                return null;
            }

        }
    }


}
