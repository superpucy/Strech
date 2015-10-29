package lai.ina.strech;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import lai.ina.strech.R;
import lai.ina.strech.db.DBDataSQL;

public class SportMainActivity extends Activity {
    SQLiteDatabase db;
    DBDataSQL helper = new DBDataSQL(SportMainActivity.this,"strech");


    private LinearLayout sterchListLayout;
    private ImageView sportPicture;
    private TextView sportName;
    private TextView sportDesc;
    private Button btnStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_main);
        sportPicture = (ImageView) findViewById(R.id.sportPicture);
        sportName = (TextView) findViewById(R.id.sprotName);
        sportDesc = (TextView) findViewById(R.id.sprotDesc);
        btnStart = (Button) findViewById(R.id.btnStart);


        Bundle bundle = getIntent().getExtras();
        final String sport_id = bundle.getString("sport_id");
        db = helper.getReadableDatabase();
        final Cursor sport = db.rawQuery("select name,desc,picture from Sport where id = ? ", new String[]{sport_id});
        sport.moveToFirst();
        String sport_name = sport.getString(0);
        String sport_desc = sport.getString(1);
        String sport_picture = sport.getString(2);
        sportName.setText(sport_name);
        sportDesc.setText(sport_desc);
        int resID = getResources().getIdentifier(sport_picture, "drawable", getPackageName());
        sportPicture.setImageResource(resID);

        sterchListLayout = (LinearLayout)findViewById(R.id.strechListLayout);


        final Cursor sportList = db.rawQuery("select b.picture, a.cycle, a.seconds from SportStrech a inner join Strech b on a.strech_id = b.id where a.sport_id = ? order by a.rank", new String[]{sport_id});
        int count = 0;
        sportList.moveToFirst();

        for(int i=0;i<sportList.getCount();i++)
        {
            LinearLayout linearLayout = new LinearLayout(SportMainActivity.this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            ImageView imageButton = new ImageView(SportMainActivity.this);
            int resStrechID = getResources().getIdentifier(sportList.getString(0), "drawable", getPackageName());
            imageButton.setImageResource(resStrechID);
            imageButton.setScaleType(ImageView.ScaleType.CENTER_CROP);

            TextView desc = new TextView(this);
            int cycle = sportList.getInt(1);
            int seconds = sportList.getInt(2);
            desc.setText(cycle+"次,每次"+seconds+"秒");

            linearLayout.addView(imageButton);
            linearLayout.addView(desc);
            sportList.moveToNext();


            sterchListLayout.addView(linearLayout);
        }
        sportList.close();
        db.close();

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(SportMainActivity.this, StrechingActivity.class);
                intent.putExtra("sport_id", sport_id);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sport_main, menu);
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
