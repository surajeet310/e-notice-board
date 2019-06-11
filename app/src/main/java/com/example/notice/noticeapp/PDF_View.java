package com.example.notice.noticeapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.listener.OnPageScrollListener;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.github.barteksc.pdfviewer.listener.OnTapListener;

import models.Notice;

public class PDF_View extends AppCompatActivity {

    String PDF_link;
    PDFView pdfView;
    private OnDrawListener onDrawListener;
    private OnLoadCompleteListener onLoadCompleteListener;
    private OnPageChangeListener onPageChangeListener;
    private OnPageScrollListener onPageScrollListener;
    private OnErrorListener onErrorListener;
    private OnPageErrorListener onPageErrorListener;
    private OnRenderListener onRenderListener;
    private OnTapListener onTapListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf__view);


        Intent i = getIntent();

        Notice notice_recieved = (Notice) i.getSerializableExtra("notice");
        PDF_link = notice_recieved.getNotice_content();
        pdfView = (PDFView)findViewById(R.id.pdfView);

        pdfView.fromUri(Uri.parse(PDF_link))
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(0)
                .onDraw(onDrawListener)
                .onDrawAll(onDrawListener)
                .onLoad(onLoadCompleteListener)
                .onPageChange(onPageChangeListener)
                .onPageScroll(onPageScrollListener)
                .onError(onErrorListener)
                .onPageError(onPageErrorListener)
                .onRender(onRenderListener)
                .onTap(onTapListener)
                .enableAnnotationRendering(false)
                .password(null)
                .scrollHandle(null)
                .enableAntialiasing(true)
                .spacing(0)
//                .autoSpacing(false)
//                .linkHandler(DefaultLinkHandler)
//                .pageFitPolicy(FitPolicy.WIDTH)
//                .pageSnap(true)
//                .pageFling(false)
//                .nightMode(false)
                .load();

    }
}
