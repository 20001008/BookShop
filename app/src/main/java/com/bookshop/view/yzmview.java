package com.bookshop.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.bookshop.R;

import java.util.Random;

public class yzmview extends View {
    private int width;
    private int height;
    private int res=R.drawable.bg3;//资源
    private Bitmap srcbitmap;
    private Bitmap kqbitmap;
    private Paint emptyPaint;
    private Paint srcPaint;
    private Paint smallPaint;
    private int small_size=200;
    private int shadowLeft;

    public yzmview(Context context) {
        super(context);
    }

    public yzmview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        srcPaint=new Paint();
        srcPaint.setAntiAlias(true);
        srcPaint.setFilterBitmap(true);
        srcPaint.setColor(Color.WHITE);
        emptyPaint=new Paint();
        emptyPaint.setAntiAlias(true);
        emptyPaint.setColor(Color.parseColor("#AA000000"));

        loadbitmap();
    }




    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        if (srcbitmap!=null)
        {
            int minmunwidth=getMinimumWidth();
            width=getmeasuresize(minmunwidth,widthMeasureSpec);
            float scale=width/(float)srcbitmap.getWidth();
            height=(int)(srcbitmap.getHeight()*scale);
            setMeasuredDimension(width,height);
        }


    }

    @Override
    protected void onDraw(Canvas canvas) {

        RectF rectF = new RectF(0, 0, width, height);
        RectF rectF2 = new RectF(0, 0, 400, 400);
        canvas.drawBitmap(srcbitmap, null, rectF,srcPaint );
        canvas.drawBitmap(kqbitmap,0,0,emptyPaint);
    }
    private int getmeasuresize(int defultsize, int measuresize)
    {
        int mode=MeasureSpec.getMode(measuresize);
        int size=MeasureSpec.getSize(measuresize);
        int result=defultsize;
        switch (mode)
        {
            case MeasureSpec.UNSPECIFIED:
                 result=defultsize;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                result=size;
                break;
        }
        return result;
    }
    private void loadbitmap(){
        if (res>0)
        {
            srcbitmap= BitmapFactory.decodeResource(getResources(), res);
           kqbitmap=createSmallBitmap(srcbitmap);
        }
    }
    private Bitmap clipBitmap(Bitmap bitmap,int newwidth,int newheight){
        int w=bitmap.getWidth();
        int h=bitmap.getHeight();
        float scale_w=(float) newwidth/w;
        float scale_h=(float) newheight/h;
        Matrix matrix=new Matrix();
        matrix.postTranslate(newwidth,newheight);
       return Bitmap.createBitmap(bitmap,0,0,w,h,matrix,true);
    }
    private int dip2px(int size){
        float dec=getResources().getDisplayMetrics().density;
        return (int)(dec*size+0.5f);
    }
    public Bitmap createSmallBitmap(Bitmap var) {
        Bitmap bitmap = Bitmap.createBitmap(small_size, small_size, Bitmap.Config.ARGB_8888);
        Canvas canvas1 = new Canvas(bitmap);
        canvas1.drawCircle(small_size / 2, small_size / 2, small_size / 2, srcPaint);
       // canvas1.drawRect(0,0,small_size,small_size,srcPaint);
        /*设置混合模式*/
        //srcPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));


        /*在指定范围随机生成空缺部分坐标，保证空缺部分出现在View右侧*/
        int min = width / 3;
        int max = width - small_size / 2 - 20;
        Random random = new Random();
        shadowLeft = 0;
        Rect rect = new Rect(shadowLeft, (height - small_size) / 2, small_size + shadowLeft, (height + small_size) / 2);
        RectF rectF = new RectF(0, 0, small_size, small_size);
       // canvas1.drawBitmap(var, rect, rectF, srcPaint);
        //srcPaint.setXfermode(null);
        return bitmap;
    }
}
