package lai.ina.strech;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import lai.ina.strech.db.*;

public class MyActivity extends Activity {
    SQLiteDatabase db;
    DBDataSQL helper = new DBDataSQL(MyActivity.this,"strech");


    private LinearLayout scheduleLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        setContentView(R.layout.activity_my);
        scheduleLayout = (LinearLayout) findViewById(R.id.sportLayout);
        db = helper.getReadableDatabase();
        final Cursor sportList = db.rawQuery("select id,name,picture from Sport",null);
        int count = 0;
        sportList.moveToFirst();
        for(int i=0;i<sportList.getCount();i++)
        {
            LinearLayout picLayout = new LinearLayout(MyActivity.this);
            picLayout.setOrientation(LinearLayout.HORIZONTAL);


            ImageView imageButton = new ImageButton(MyActivity.this);
            int resID = getResources().getIdentifier(sportList.getString(2), "drawable", getPackageName());
            final String sportId = sportList.getString(0);
            imageButton.setImageResource(resID);
            imageButton.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Intent intent = new Intent();
                       intent.setClass(MyActivity.this,SportMainActivity.class);
                       intent.putExtra("sport_id",sportId);
                       startActivity(intent);
                   }
               }
            );
            picLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setClass(MyActivity.this,SportMainActivity.class);
                    intent.putExtra("sport_id",sportId);
                    startActivity(intent);
                }
            });
            picLayout.addView(imageButton);

            TextView sportName = new TextView(this);
            sportName.setText(sportList.getString(1));
            picLayout.addView(sportName);

            sportList.moveToNext();

            scheduleLayout.addView(picLayout);

        }
        sportList.close();
        db.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
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
