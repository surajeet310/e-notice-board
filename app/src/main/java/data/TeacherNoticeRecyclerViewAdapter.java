package data;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notice.noticeapp.PDF_View;
import com.example.notice.noticeapp.R;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import models.Notice;



public class TeacherNoticeRecyclerViewAdapter extends RecyclerView.Adapter<TeacherNoticeRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Notice> teacher_notice_list;

    public TeacherNoticeRecyclerViewAdapter(Context context, List<Notice> teacher_notice) {
        this.context = context;
        this.teacher_notice_list = teacher_notice;
    }

    @Override
    public TeacherNoticeRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home,parent,false);


        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(TeacherNoticeRecyclerViewAdapter.ViewHolder holder, int position) {
            Notice teacher_notice = teacher_notice_list.get(position);
            String PDF_link = teacher_notice.getNotice_content();

            holder.teacher_notice_title.setText(teacher_notice.getNotice_title());
    }

    @Override
    public int getItemCount() {
        return teacher_notice_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView teacher_notice_title;
        Button PDF_btn ;
        Button view_PDF_btn;


        public ViewHolder(View itemView, final Context ctx) {
            super(itemView);
            context = ctx;
            teacher_notice_title=(TextView)itemView.findViewById(R.id.title_textView);
            PDF_btn = (Button)itemView.findViewById(R.id.pdf_open_btn);
            view_PDF_btn = (Button)itemView.findViewById(R.id.open_pdf_btn);


            view_PDF_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Notice notice = teacher_notice_list.get(getAdapterPosition());
                    Intent intent = new Intent(context,PDF_View.class);
                    intent.putExtra("notice", notice);
                    ctx.startActivity(intent);


                }
            });

            PDF_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Notice noticeObj = teacher_notice_list.get(getAdapterPosition());
                    final String PDF_download_link = noticeObj.getNotice_content();
                    new DownloadTask(context,PDF_download_link);
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


            downloadFileName = downloadUrl.substring(downloadUrl.lastIndexOf('/'), downloadUrl.length());
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



                        if (new TeacherNoticeRecyclerViewAdapter.CheckForSDCard().isSDCardPresent()) {

                            apkStorage = new File(
                                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/"
                                            + "Notice Files");
                        } else {
                            Toast.makeText(context, "Oops!! There is no SD Card.", Toast.LENGTH_SHORT).show();
                        }


                        if (!apkStorage.getParentFile().exists()) {
                            apkStorage.mkdirs();
                            Log.e(TAG, "Directory Created.");
                        }

                        if (!apkStorage.exists()){
                            apkStorage.createNewFile();
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
