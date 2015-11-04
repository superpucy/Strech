package lai.ina.strech;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Calendar;

import lai.ina.strech.R;
import lai.ina.strech.db.DBDataSQL;

public class StrechingActivity extends Activity {
    SQLiteDatabase db;
    DBDataSQL helper = new DBDataSQL(StrechingActivity.this,"strech");
    private Handler handler = new Handler();
    int countSecond = 0;
    Long realStartTime = System.currentTimeMillis();
    private RelativeLayout strechingLayout;
    String sport_id ;
    private ImageView imageView;
    private ImageButton pause;

    private AnimationDrawable animation ;
    private AnimationDrawable sphereAnimation;
    private AnimationDrawable sphereResume;
    private Drawable currentFrame;
    private Drawable checkFrame;
    private int frameIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_streching);
        strechingLayout = (RelativeLayout) findViewById(R.id.strechingLayout);
        imageView = (ImageView) findViewById(R.id.back);
        Bundle bundle = getIntent().getExtras();
        sport_id = bundle.getString("sport_id");
        pause = (ImageButton) findViewById(R.id.pause);
        aaaa();
    }

    private void aaaa()
    {
        db = helper.getReadableDatabase();
        final AnimationSet animationSet1 = new AnimationSet(true);
        final Cursor sportList = db.rawQuery("select a.cycle, a.seconds, b.picture, b.is2Side from SportStrech a inner join Strech b on a.strech_id = b.id where a.sport_id = ? order by a.rank", new String[]{sport_id});

        Resources res = this.getResources();
        sportList.moveToFirst();
        animation = new AnimationDrawable();
        for(int i=0;i<sportList.getCount();i++) {
            countSecond = 0;

            final int cycle = sportList.getInt(0);
            int seconds = sportList.getInt(1);
            final int resStrechID = getResources().getIdentifier(sportList.getString(2), "drawable", getPackageName());


            animation.addFrame(CountSecond(resStrechID,"3"), 1000);
            animation.addFrame(CountSecond(resStrechID,"2"), 1000);
            animation.addFrame(CountSecond(resStrechID,"1"), 1000);

            int is2Side = sportList.getInt(3);
            for(int c=0;c<cycle;c++)
            {
                if(is2Side == 1)
                {
                    for(int s=seconds;s>0;s--)
                    {
                        String ss = String.valueOf(s);
                        //res.getDrawable(resStrechID).draw(DoingSecondCanvas(resStrechID,c,ss));
                        animation.addFrame(DoingSecond(resStrechID, c, ss), 1000);
                    }
                    // animation.addFrame(res.getDrawable(resStrechID),seconds*1000);
                    final int resStrechIDL = getResources().getIdentifier(sportList.getString(2)+"_l", "drawable", getPackageName());
                    animation.addFrame(ChangeSide(),1000);
                    animation.addFrame(CountSecond(resStrechIDL,"3"), 1000);
                    animation.addFrame(CountSecond(resStrechIDL,"2"), 1000);
                    animation.addFrame(CountSecond(resStrechIDL,"1"), 1000);

                    for(int s=seconds;s>0;s--)
                    {

                        String ss = String.valueOf(s);
                        //res.getDrawable(resStrechID).draw(DoingSecondCanvas(resStrechID,c,ss));
                        animation.addFrame(DoingSecond(resStrechIDL, c, ss), 1000);
                    }
                    // animation.addFrame(res.getDrawable(resStrechID),seconds*1000);
                }else {
                    for (int s = seconds; s > 0; s--) {
                        String ss = String.valueOf(s);
                        //res.getDrawable(resStrechID).draw(DoingSecondCanvas(resStrechID,c,ss));

                        animation.addFrame(DoingSecond(resStrechID, c, ss), 1000);
                    }
                    // animation.addFrame(res.getDrawable(resStrechID),seconds*1000);
                }
            }
            sportList.moveToNext();
        }
        sportList.close();
        db.close();
        animation.addFrame(Finish(), 1000);
        imageView.setBackgroundDrawable(animation);


        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(animation.isRunning())
                {

                    animation.stop();
                    int fi = animation.getNumberOfFrames();

                    sphereResume = new AnimationDrawable();

                    Drawable stopNow = animation.getCurrent();
                    for (int i = 0; i < fi; i++) {
                        Drawable preDraw = animation.getFrame(i);

                        if (preDraw == stopNow) {
                            frameIndex = i;
                            for (int k = frameIndex; k < fi; k++) {
                                Drawable frame = animation.getFrame(k);
                                sphereResume.addFrame(frame, 1000);
                            }

                            break ;
                        }
                    }

                    animation = sphereResume;
                    imageView.setBackgroundDrawable(animation);
                    imageView.invalidate();
                    pause.setImageResource(R.drawable.play);
                }else{
                    animation.start();
                    pause.setImageResource(R.drawable.pause);
                }
            }
        });
        animation.start();
        System.gc();
    }

    /**
     * 換下一個動作的倒數
     * @param seconde
     * @return
     */
    public Drawable CountSecond( final String seconde){

        Drawable d = new Drawable() {
            @Override
            public void draw(Canvas canvas) {
                int width = canvas.getWidth();
                int height = canvas.getHeight();
                int radius = width > height ? height/2 : width/2;
                int center_x = width/2;
                int center_y = height/2;

//                String sCycle = "下一動";
//                Paint cyPaint = new Paint();
//                cyPaint.setColor(Color.BLACK);
//                cyPaint.setTextSize(100);
//                canvas.drawText(sCycle,center_x,100,cyPaint);

                Path circle = new Path();
                Paint cPaint = new Paint();
                cPaint.setColor(Color.BLACK);
                Paint tPaint = new Paint();
                tPaint.setColor(Color.WHITE);
                tPaint.setTextSize(300);
                circle.addCircle(center_x, center_y, radius, Path.Direction.CW);
                canvas.drawPath(circle, cPaint);
                canvas.drawText(seconde,center_x,center_y,tPaint);
            }

            @Override
            public void setAlpha(int i) {

            }

            @Override
            public void setColorFilter(ColorFilter colorFilter) {

            }

            @Override
            public int getOpacity() {
                return 0;
            }
        };
        return d;
    }

    public Drawable CountSecond(final int resID, final String seconde){

        Drawable d = new Drawable() {
            @Override
            public void draw(Canvas canvas) {
                Resources res = getResources();
                Bitmap bitmap = BitmapFactory.decodeResource(res, resID);
                final Rect rect = new Rect(0, 0, canvas.getWidth(), canvas.getHeight());
                canvas.drawBitmap(bitmap,rect,rect,new Paint());

//                String sCycle = "第" + cycle + "次：倒數";
//                Paint cyPaint = new Paint();
//                cyPaint.setColor(Color.BLACK);
//                canvas.drawText(sCycle,0,0,cyPaint);
                int width = 80;
                int height = 80;
                int radius = width > height ? height/2 : width/2;
                int center_x = width/2;
                int center_y = height/2;

                Path circle = new Path();
                Paint cPaint = new Paint();
                cPaint.setColor(Color.BLACK);
                Paint tPaint = new Paint();
                tPaint.setColor(Color.WHITE);
                tPaint.setTextSize(40);
                circle.addCircle(center_x, center_y, radius, Path.Direction.CW);
                canvas.drawPath(circle, cPaint);
                canvas.drawText(seconde,center_x-10,center_y+10,tPaint);
            }

            @Override
            public void setAlpha(int i) {

            }

            @Override
            public void setColorFilter(ColorFilter colorFilter) {

            }

            @Override
            public int getOpacity() {
                return 0;
            }
        };
        return d;
    }

    /**
     *
     * @return
     */
    public Drawable ChangeSide(){

        Drawable d = new Drawable() {
            @Override
            public void draw(Canvas canvas) {
                int width = canvas.getWidth();
                int height = canvas.getHeight();
                int radius = width > height ? height/2 : width/2;
                int center_x = width/2;
                int center_y = height/2;

                Path circle = new Path();
                Paint cPaint = new Paint();
                cPaint.setColor(Color.RED);
                Paint tPaint = new Paint();
                tPaint.setColor(Color.YELLOW);
                tPaint.setTextSize(200);

                circle.addCircle(center_x, center_y, radius, Path.Direction.CW);
                canvas.drawPath(circle, cPaint);
                canvas.drawText("換邊",center_x-200,center_y+50,tPaint);
            }

            @Override
            public void setAlpha(int i) {

            }

            @Override
            public void setColorFilter(ColorFilter colorFilter) {

            }

            @Override
            public int getOpacity() {
                return 0;
            }
        };
        return d;
    }

    /**
     * 伸展結束
     * @return
     */
    public Drawable Finish(){

        Drawable d = new Drawable() {
            @Override
            public void draw(Canvas canvas) {
                int width = canvas.getWidth();
                int height = canvas.getHeight();
                int radius = width > height ? height/2 : width/2;
                int center_x = width/2;
                int center_y = height/2;
                Path circle = new Path();
                Paint cPaint = new Paint();
                cPaint.setColor(Color.RED);
                Paint tPaint = new Paint();
                tPaint.setColor(Color.WHITE);
                tPaint.setTextSize(100);
                circle.addCircle(center_x, center_y, radius, Path.Direction.CW);
                canvas.drawPath(circle, cPaint);
                canvas.drawText("伸展結束",center_x-100,center_y,tPaint);
            }

            @Override
            public void setAlpha(int i) {

            }

            @Override
            public void setColorFilter(ColorFilter colorFilter) {

            }

            @Override
            public int getOpacity() {
                return 0;
            }
        };
        return d;
    }

    /**
     *
     * @param resID
     * @param cycle
     * @param seconde
     * @return
     */
    public Drawable DoingSecond(final int resID, final int cycle, final String seconde){

        Drawable d = new Drawable() {
            @Override
            public void draw(Canvas canvas) {

                Resources res = getResources();
                Bitmap bitmap = BitmapFactory.decodeResource(res, resID);
                final Rect rect = new Rect(0, 0, canvas.getWidth(), canvas.getHeight());
                canvas.drawBitmap(bitmap,rect,rect,new Paint());

//                String sCycle = "第" + cycle + "次：倒數";
//                Paint cyPaint = new Paint();
//                cyPaint.setColor(Color.BLACK);
//                canvas.drawText(sCycle,0,0,cyPaint);
                int width = 80;
                int height = 80;
                int radius = width > height ? height/2 : width/2;
                int center_x = width/2;
                int center_y = height/2;

                Path circle = new Path();
                Paint cPaint = new Paint();
                cPaint.setColor(Color.RED);
                Paint tPaint = new Paint();
                tPaint.setColor(Color.WHITE);
                tPaint.setTextSize(40);
                circle.addCircle(center_x, center_y, radius, Path.Direction.CW);
                canvas.drawPath(circle, cPaint);
                canvas.drawText(seconde,center_x-10,center_y+10,tPaint);
            }

            @Override
            public void setAlpha(int i) {

            }

            @Override
            public void setColorFilter(ColorFilter colorFilter) {

            }

            @Override
            public int getOpacity() {
                return 0;
            }
        };
        return d;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.streching, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
