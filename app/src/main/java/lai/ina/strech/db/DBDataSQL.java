package lai.ina.strech.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by apple on 2015/6/9.
 */
public class DBDataSQL extends SQLiteOpenHelper {

    private static final int VERSION = 1;

    public DBDataSQL(Context context,String name) {
        this(context, name, null, VERSION);
    }

    public DBDataSQL(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBDataSQL(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_STORT_TABLE = "create table Sport(" +
                "id int NOT NULL PRIMARY KEY, " +
                "name nvarchar(50), " +
                "desc nvarchar(500), " +
                "picture nvarchar(50) " +
                ")";

        String CREATE_STRECH_TABLE = "create table Strech(" +
                "id int NOT NULL PRIMARY KEY, " +
                "name nvarchar(50), " +
                "desc nvarchar(500), " +
                "part int," +
                "is2Side bit," +
                "picture nvarchar(50) " +
                ")";

        String CREATE_MAP_TABLE = "create table SportStrech(" +
                "id integer primary key autoincrement not null, " +
                "sport_id int, " +
                "strech_id int, " +
                "cycle int, " +
                "seconds int, " +
                "rank int " +
                ")";
//        sqLiteDatabase.execSQL("drop table Sport");
//        sqLiteDatabase.execSQL("drop table Strech");
//        sqLiteDatabase.execSQL("drop table SportStrech");

        sqLiteDatabase.execSQL(CREATE_STORT_TABLE);
        sqLiteDatabase.execSQL(CREATE_STRECH_TABLE);
        sqLiteDatabase.execSQL(CREATE_MAP_TABLE);

        sqLiteDatabase.execSQL("insert into Sport(id,name,picture,desc) values(1,'跑步前','sport_01','運動前做約4分鐘，伸展前先慢跑3~5分鐘當熱身')");
        sqLiteDatabase.execSQL("insert into Sport(id,name,picture,desc) values(2,'跑步後','sport_01','運動後做約3分鐘')");
        sqLiteDatabase.execSQL("insert into Sport(id,name,picture,desc) values(3,'跑步前(迷你版)','sport_01','運動前做約4分鐘，伸展前先慢跑3~5分鐘當熱身')");
        sqLiteDatabase.execSQL("insert into Sport(id,name,picture,desc) values(4,'跑步後(迷你版)','sport_01','運動後做約3分鐘')");

        sqLiteDatabase.execSQL("insert into Strech(id,picture,is2Side) values(1,'strech_0101',0)");
        sqLiteDatabase.execSQL("insert into Strech(id,picture,is2Side) values(2,'strech_0102',1)");
        sqLiteDatabase.execSQL("insert into Strech(id,picture,is2Side) values(3,'strech_0103',0)");
        sqLiteDatabase.execSQL("insert into Strech(id,picture,is2Side) values(4,'strech_0104',1)");
        sqLiteDatabase.execSQL("insert into Strech(id,picture,is2Side) values(5,'strech_0105',1)");
        sqLiteDatabase.execSQL("insert into Strech(id,picture,is2Side) values(6,'strech_0106',0)");
        sqLiteDatabase.execSQL("insert into Strech(id,picture,is2Side) values(7,'strech_0107',0)");
        sqLiteDatabase.execSQL("insert into Strech(id,picture,is2Side) values(8,'strech_0108',1)");
        sqLiteDatabase.execSQL("insert into Strech(id,picture,is2Side) values(9,'strech_0201',1)");
        sqLiteDatabase.execSQL("insert into Strech(id,picture,is2Side) values(10,'strech_0202',0)");
        sqLiteDatabase.execSQL("insert into Strech(id,picture,is2Side) values(11,'strech_0203',1)");
        sqLiteDatabase.execSQL("insert into Strech(id,picture,is2Side) values(12,'strech_0204',1)");
        sqLiteDatabase.execSQL("insert into Strech(id,picture,is2Side) values(13,'strech_0205',1)");
        sqLiteDatabase.execSQL("insert into Strech(id,picture,is2Side) values(14,'strech_0206',0)");
        sqLiteDatabase.execSQL("insert into Strech(id,picture,is2Side) values(15,'strech_0207',1)");
        sqLiteDatabase.execSQL("insert into Strech(id,picture,is2Side) values(16,'strech_0208',0)");

        sqLiteDatabase.execSQL("insert into SportStrech(sport_id,strech_id,cycle,seconds,rank) values(1,1,2,5,1)");
        sqLiteDatabase.execSQL("insert into SportStrech(sport_id,strech_id,cycle,seconds,rank) values(1,2,1,10,2)");
        sqLiteDatabase.execSQL("insert into SportStrech(sport_id,strech_id,cycle,seconds,rank) values(1,3,1,15,3)");
        sqLiteDatabase.execSQL("insert into SportStrech(sport_id,strech_id,cycle,seconds,rank) values(1,4,1,30,4)");
        sqLiteDatabase.execSQL("insert into SportStrech(sport_id,strech_id,cycle,seconds,rank) values(1,5,1,15,5)");
        sqLiteDatabase.execSQL("insert into SportStrech(sport_id,strech_id,cycle,seconds,rank) values(1,6,1,30,6)");
        sqLiteDatabase.execSQL("insert into SportStrech(sport_id,strech_id,cycle,seconds,rank) values(1,7,1,15,7)");
        sqLiteDatabase.execSQL("insert into SportStrech(sport_id,strech_id,cycle,seconds,rank) values(1,8,1,15,8)");

        sqLiteDatabase.execSQL("insert into SportStrech(sport_id,strech_id,cycle,seconds,rank) values(2,9,1,10,1)");
        sqLiteDatabase.execSQL("insert into SportStrech(sport_id,strech_id,cycle,seconds,rank) values(2,10,1,15,2)");
        sqLiteDatabase.execSQL("insert into SportStrech(sport_id,strech_id,cycle,seconds,rank) values(2,11,1,15,3)");
        sqLiteDatabase.execSQL("insert into SportStrech(sport_id,strech_id,cycle,seconds,rank) values(2,12,1,10,4)");
        sqLiteDatabase.execSQL("insert into SportStrech(sport_id,strech_id,cycle,seconds,rank) values(2,13,1,15,5)");
        sqLiteDatabase.execSQL("insert into SportStrech(sport_id,strech_id,cycle,seconds,rank) values(2,14,2,5,6)");
        sqLiteDatabase.execSQL("insert into SportStrech(sport_id,strech_id,cycle,seconds,rank) values(2,15,1,15,7)");
        sqLiteDatabase.execSQL("insert into SportStrech(sport_id,strech_id,cycle,seconds,rank) values(2,16,2,5,8)");

        sqLiteDatabase.execSQL("insert into SportStrech(sport_id,strech_id,cycle,seconds,rank) values(3,3,1,15,3)");
        sqLiteDatabase.execSQL("insert into SportStrech(sport_id,strech_id,cycle,seconds,rank) values(3,4,1,30,4)");
        sqLiteDatabase.execSQL("insert into SportStrech(sport_id,strech_id,cycle,seconds,rank) values(3,5,1,15,5)");
        sqLiteDatabase.execSQL("insert into SportStrech(sport_id,strech_id,cycle,seconds,rank) values(3,8,1,15,8)");


        sqLiteDatabase.execSQL("insert into SportStrech(sport_id,strech_id,cycle,seconds,rank) values(4,9,1,10,1)");
        sqLiteDatabase.execSQL("insert into SportStrech(sport_id,strech_id,cycle,seconds,rank) values(4,13,1,15,5)");
        sqLiteDatabase.execSQL("insert into SportStrech(sport_id,strech_id,cycle,seconds,rank) values(4,14,2,5,6)");
        sqLiteDatabase.execSQL("insert into SportStrech(sport_id,strech_id,cycle,seconds,rank) values(4,16,2,5,8)");


//        sqLiteDatabase.execSQL("insert into Sport(name,picture) values('游泳','swimmer9')");
//        sqLiteDatabase.execSQL("insert into Sport(name,picture) values('騎腳踏車','bicycle24')");
//        sqLiteDatabase.execSQL("insert into Sport(name,picture) values('籃球','basketball51')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Sport");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Strech");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS SportStrech");
        onCreate(sqLiteDatabase);

    }
}
