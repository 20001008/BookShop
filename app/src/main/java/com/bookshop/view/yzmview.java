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
import android.util.Log;
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
    private int random_x;
    private int random_y;
    private int leftsrc=0;

    public yzmview(Context context) {
        super(context);
    }

    public yzmview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        srcPaint=new Paint();
        srcPaint.setAntiAlias(true);
        srcPaint.setFilterBitmap(true);
        srcPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        //srcPaint.setColor(Color.WHITE);
        emptyPaint=new Paint();
        emptyPaint.setAntiAlias(true);
        emptyPaint.setStyle(Paint.Style.STROKE);
        emptyPaint.setColor(Color.RED);
        emptyPaint.setStrokeWidth(20);

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
            //给随机的xy
            Random random=new Random();
            random_x=random.nextInt(width/2-width/4)+width/2;
            random_y=random.nextInt(height/2-height/4)+height/2;
        }


    }

    @Override
    protected void onDraw(Canvas canvas) {

        RectF rectF = new RectF(0, 0, width, height);
        RectF rectF1=new RectF(leftsrc,0,leftsrc+small_size,small_size);
        Log.d("TAG", "onDraw: "+leftsrc);
      canvas.drawBitmap(srcbitmap, null, rectF,srcPaint );
        if (random_x>0&&random_y>0)
        {
           // canvas.drawBitmap(kqbitmap,null,small_rectF,srcPaint);
           canvas.drawBitmap(kqbitmap,null,rectF1,srcPaint);


        }

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
        //canvas1.drawCircle(small_size / 2, small_size / 2, small_size / 2, srcPaint);
        canvas1.drawRect(0,0,small_size,small_size,srcPaint);
        /*设置混合模式*/
        srcPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        Rect rect=new Rect(random_x,(random_y-small_size)/2,small_size,(random_y+small_size)/2);
        RectF rectF=new RectF(0,0,small_size,small_size);
       canvas1.drawBitmap(var,rect,rectF, srcPaint);
       // canvas1.drawBitmap(var,random_x,random_y,srcPaint);

        srcPaint.setXfermode(null);
        return bitmap;
    }

    public void setLeftsrc(int leftsrc) {
        this.leftsrc = leftsrc;
        invalidate();
    }
}
