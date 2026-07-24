package com.fintech.omnipe.Util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.print.pdf.PrintedPdfDocument;
import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.fintech.omnipe.R;

import java.io.FileOutputStream;
import java.io.IOException;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class MyPrintDocumentAdapter extends PrintDocumentAdapter {

    public PdfDocument myPdfDocument;
    public int totalpages = 1;
    Activity context;
    int n = 0;
    int fontSize;
    double relation;
    String Rechargedetail;
    private int pageHeight;
    private int pageWidth;
    Bitmap bitmap;

    public MyPrintDocumentAdapter(Activity context, String Rechargedetail) {
        this.context = context;
        this.Rechargedetail = Rechargedetail;
    }

    public void printDocument(String detail) {
        //   n = tv_item.getAdapter().getCount();
        PrintManager printManager = (PrintManager) context.getSystemService(Context.PRINT_SERVICE);
        Rechargedetail = detail;
        String jobNameD = "Reciept Preview";
        printManager.print(jobNameD, new MyPrintDocumentAdapter(context, Rechargedetail),
                null);
    }

    @Override
    public void onLayout(PrintAttributes oldAttributes,
                         PrintAttributes newAttributes,
                         CancellationSignal cancellationSignal,
                         LayoutResultCallback callback,
                         Bundle metadata) {

        myPdfDocument = new PrintedPdfDocument(context, newAttributes);

        pageHeight = newAttributes.getMediaSize().getHeightMils() / 1000 * 72;
        pageWidth = newAttributes.getMediaSize().getWidthMils() / 1000 * 72;

        if (cancellationSignal.isCanceled()) {
            callback.onLayoutCancelled();
            return;
        }

        if (totalpages > 0) {
            PrintDocumentInfo.Builder builder = new PrintDocumentInfo
                    .Builder("print_Preview.pdf")
                    .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                    .setPageCount(totalpages);

            PrintDocumentInfo info = builder.build();
            callback.onLayoutFinished(info, true);
        } else {
            callback.onLayoutFailed("Page count is zero.");
        }
    }

    @Override
    public void onWrite(final PageRange[] pageRanges,
                        final ParcelFileDescriptor destination,
                        final CancellationSignal cancellationSignal,
                        final WriteResultCallback callback) {

        for (int i = 0; i < totalpages; i++) {
            if (pageInRange(pageRanges, i)) {
                // page builder
                PdfDocument.PageInfo newPage = new PdfDocument.PageInfo.Builder(pageWidth,
                        pageHeight, i).create();

                PdfDocument.Page page =
                        myPdfDocument.startPage(newPage);

                if (cancellationSignal.isCanceled()) {
                    callback.onWriteCancelled();
                    myPdfDocument.close();
                    myPdfDocument = null;
                    return;
                }
                drawPage(page, i, pageWidth, pageHeight);
                myPdfDocument.finishPage(page);
            }
        }

        try {
            myPdfDocument.writeTo(new FileOutputStream(
                    destination.getFileDescriptor()));
        } catch (IOException e) {
            callback.onWriteFailed(e.toString());
            return;
        } finally {
            myPdfDocument.close();
            myPdfDocument = null;
        }
        callback.onWriteFinished(pageRanges);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private boolean pageInRange(PageRange[] pageRanges, int page) {
        for (int i = 0; i < pageRanges.length; i++) {
            if ((page >= pageRanges[i].getStart()) &&
                    (page <= pageRanges[i].getEnd()))
                return true;
        }
        return false;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public PdfDocument.Page drawPage(PdfDocument.Page page, int pagenumber, int pageWidth1, int pageHeight1) {

        pageWidth = pageWidth1;
        pageHeight = pageHeight1;
        String jobNameD = "Reciept Preview";
        Canvas canvas = page.getCanvas();
        pagenumber++;
        Paint paint = new Paint();
        paint.setTextScaleX(1.0f);
        relation = Math.sqrt(canvas.getWidth() * canvas.getHeight());
        relation = relation / 250;
        fontSize = context.getResources().getDimensionPixelSize(R.dimen.scoreFontSize);
        paint.setTextSize((float) (fontSize * relation));
        drawHeader(canvas, paint);
        return page;
    }

    private void drawHeader(Canvas canvas, Paint paint) {

        Glide.with(context).asBitmap().load(UtilMethods.INSTANCE.getAppLogoUrl(context)).into(new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resourse, Transition<? super Bitmap> transition) {
              bitmap=resourse;
            }
            @Override
            public void onLoadCleared(Drawable placeholder) {

            }
        });
        paint.setTextAlign(Paint.Align.LEFT);
        int y = 15;
        /*Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.rnd_logo);*/
        Bitmap b = Bitmap.createScaledBitmap(bitmap, (10 * pageWidth) / 100, (7 * pageWidth) / 100, false);
        canvas.drawBitmap(b, (7 * pageWidth) / 100, (y * pageHeight) / 100, null);

        String[] str = Rechargedetail.split("\n");
        String company = "MIEUX INFRACON LTD\n" +
                "B-720, Gopal Palace, 7th floor \n" +
                "Nr Nehrunagar BRTS Stand \n" +
                "S M Road, Nehrunagar, Ahmedabad - 380015 \n" +
                "Ahmedabad,Gujarat ";

        String[] str2 = company.split("\n");
        for (int i = 0; i < str2.length; i++) {
            canvas.drawText(
                    str2[i] + "",
                    (19 * pageWidth) / 100,
                    (y * pageHeight) / 100,
                    paint);
            y = y + 5;
        }
        canvas.drawLine(10, ((y - 3) * pageHeight) / 100, pageWidth - 10, ((y - 3) * pageHeight) / 100, paint);
        for (int i = 0; i < str.length; i++) {
            canvas.drawText(
                    str[i] + "",
                    (19 * pageWidth) / 100,
                    (y * pageHeight) / 100,
                    paint);
            y = y + 5;
        }

    }
}




