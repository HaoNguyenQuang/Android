package com.example.btl_android.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Trace;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.btl_android.model.TaiKhoan;
import com.example.btl_android.model.Truyen;

public class Database extends SQLiteOpenHelper {

    //Co so du lieu

    //Ten database
    private static String DATABASE_NAME = "doctruyen";
    //Bảng tài khoản
    private static String TABLE_TAIKHOAN = "taikhoan";
    private static String ID_TAI_KHOAN = "idtaikhoan";
    private static String TEN_TAI_KHOAN = "tentaikhoan";
    private static String MAT_KHAU = "matkhau";
    private static String PHAN_QUYEN = "phanquyen";
    private static String EMAIL = "email";
    private static int VERSION = 1;

    //Bảng truyện
    private static String TABLE_TRUYEN = "truyen";
    private static String ID_TRUYEN = "idtruyen";
    private static String TEN_TRUYEN = "tieude";
    private static String NOI_DUNG = "noidung";
    private static String IMAGE = "anh";

    //context
    private Context context;

    //biến lưu câu lệnh tạo bảng tài khoản
    private String SQLQuery = "CREATE TABLE "+ TABLE_TAIKHOAN +" ( "+ID_TAI_KHOAN+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +TEN_TAI_KHOAN+" TEXT UNIQUE, "
            +MAT_KHAU+" TEXT, "
            +EMAIL+" TEXT, "
            + PHAN_QUYEN+" INTEGER) ";

    //biến lưu câu lệnh tạo bảng truyện
    private String SQLQuery1 = "CREATE TABLE "+ TABLE_TRUYEN +" ( "+ID_TRUYEN+" integer primary key AUTOINCREMENT, "
            +TEN_TRUYEN+" TEXT UNIQUE, "
            +NOI_DUNG+" TEXT, "
            +IMAGE+" TEXT, "+ID_TAI_KHOAN+" INTEGER , FOREIGN KEY ( "+ ID_TAI_KHOAN +" ) REFERENCES "+
            TABLE_TAIKHOAN+"("+ID_TAI_KHOAN+"))";

    //Insert dữ liệu vào bằng tài khoản
    //phân quyền 2.admin và 1.người dùng
    private String SQLQuery2 = "INSERT INTO TaiKhoan VAlUES (null,'admin','admin','admin@gmail.com',2)";
    private String SQLQuery3 = "INSERT INTO TaiKhoan VAlUES (null,'khanh','khanh','khanh@gmail.com',1)";

    //insert truyện
    private String SQLQuery4 = "INSERT INTO truyen VALUES (null,'Rùa và Thỏ','Phần 1:\n" +
            "\n" +
            "Ngày xửa ngày xưa, có một con Rùa và một con Thỏ sống trong một khu rừng xinh đẹp và yên tĩnh. Ngày ngày chúng vui chơi với nhau như hai người bạn thân. Một hôm, Thỏ và Rùa cãi nhau xem ai nhanh hơn. Rồi chúng quyết định giải quyết việc tranh luận bằng một cuộc thi chạy đua. Thỏ và Rùa đồng ý lộ trình và bắt đầu cuộc đua. Thỏ xuất phát nhanh như tên bắn và chạy thục mạng rất nhanh, khi thấy rằng mình đã khá xa Rùa, Thỏ nghĩ nên nghỉ cho đỡ mệt dưới một bóng cây xum xuê lá bên vệ đường. Vì quá tự tin vào khả năng giành chiến thắng của mình, Thỏ ngồi dưới bóng cây và nhanh chóng ngủ thiếp đi. Rùa chạy mãi rồi cũng đến nơi, thấy Thỏ đang ngủ ngon giấc Rùa từ từ vượt qua Thỏ và về đích trước Thỏ. Khi Thỏ thức dậy thì rùa đã đến đích và trở thành người chiến thắng. Lúc này Thỏ biết mình đã thua cuộc vì quá tự tin vào khả năng của mình, còn Rùa chiến thắng vì kiên trì bám đuổi mục tiêu và làm việc hết sức trong khả năng của mình, cộng với một chút may mắn và giành chiến thắng.\n" +
            "\n" +
            "Ý nghĩa câu chuyện phần 1: truyện giáo dục đức tính kiên trì, siêng năng, nhẫn nại. Những người nhanh nhẹn nhưng cẩu thả trong suy nghĩ cuối cùng cũng sẽ bị đánh bại bởi người kiên nhẫn, siêng năng dù họ chậm hơn rất nhiều.\n" +
            "\n" +
            "Phần 2:\n" +
            "\n" +
            "Thỏ vô cùng thất vọng vì để thua Rùa, nó nhận ra rằng nó thua chính vì quá tự tin, bất cẩn và thiếu kỷ luật. Nếu nó không xem mọi thứ quá dễ dàng và chắc thắng thì rùa không thể có cơ hội hạ được nó. Vì thế, Thỏ quyết định thách thức Rùa bằng một cuộc đua mới. Rùa đồng ý. Lần này, Thỏ chạy với tất cả sức lực của nó và chạy một mạch về đích. Nó bỏ xa Rùa đến mấy dặm đường.\n"+
            "Ý nghĩa câu chuyện phần 2: Biết sai và sửa sai là một đức tính tốt, đó chính là lý do giúp anh chàng thỏ giành được chiến thắng ở cuộc đua thứ 2. Mẹ hãy giải thích cho bé hiểu rằng trong công việc hàng ngày giữa một người chậm, cẩn thận và đáng tin cậy với một người nhanh nhẹn, đáng tin cậy, chắc chắn người nhanh nhẹn sẽ được trọng dụng hơn nhiều và họ sẽ tiến xa hơn trong học tập, cũng như trong cuộc sống. Cha mẹ hãy giúp bé hiểu rõ thông điệp chậm và chắc là điều tốt, nhưng nhanh và đáng tin cậy sẽ tốt hơn rất nhiều.','https://toplist.vn/images/800px/rua-va-tho-230179.jpg',1)";

    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public Database(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
        super(context, name, version, openParams);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Thực hiện các câu lệnh truy vấn không trả về kết quả
        db.execSQL(SQLQuery);
        db.execSQL(SQLQuery1);
        db.execSQL(SQLQuery2);
        db.execSQL(SQLQuery3);
        db.execSQL(SQLQuery4);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //Phương thức lấy tất cả tài khoản
    public Cursor getData()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_TAIKHOAN, null);
        return res;
    }

    //Phương thức kiểm tra xem tên tài khoản đã tồn tại trong sqlite chưa
    public boolean isTaiKhoanExists(String taiKhoan) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM TaiKhoan WHERE tentaikhoan = ?";
        Cursor cursor = db.rawQuery(query, new String[]{taiKhoan});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    //Phương thức thêm tài khoản vào database
    public void AddTaiKhoan(TaiKhoan taiKhoan)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        //Thực hiện insert thông qua ConsertValue
        ContentValues values = new ContentValues();
        values.put(TEN_TAI_KHOAN, taiKhoan.getmTenTaiKhoan());
        values.put(MAT_KHAU, taiKhoan.getmMatKhau());
        values.put(EMAIL, taiKhoan.getmEmail());
        values.put(PHAN_QUYEN, taiKhoan.getmPhanQuyen());

        db.insert(TABLE_TAIKHOAN, null, values);
        //Đóng lại khi ko dùng
        db.close();
        Log.e("Add tài khoản", "Thành công");
    }

    //Lay 3 truyen moi nhat
    public Cursor getData1()
    {
        SQLiteDatabase db = this.getReadableDatabase();

//        Cursor res = db.rawQuery("SELECT * FROM "+ TABLE_TRUYEN+ " ORDER BY "+ID_TRUYEN+" DESC LIMIT 9", null);
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_TRUYEN, null);
        return res;
    }

    //Lay tat ca truyen
    public Cursor getData2()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_TRUYEN, null);

        return res;
    }

    //Add truyen
    public void AddTruyen(Truyen truyen)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TEN_TRUYEN, truyen.getTenTruyen());
        values.put(NOI_DUNG, truyen.getNoiDung());
        values.put(IMAGE, truyen.getAnh());
        values.put(ID_TAI_KHOAN, truyen.getID_TK());

        db.insert(TABLE_TRUYEN, null, values);
        db.close();
    }

    //Xoa truyen
    public int Delete(int i){
        SQLiteDatabase db = this.getReadableDatabase();

        int res = db.delete(TABLE_TRUYEN, ID_TRUYEN+" = " +i,null);
        return res;
    }

    //Update truyện
    public int UpdateTruyen(Truyen truyen){
        SQLiteDatabase db = this.getWritableDatabase();

        //đưa dữ liệu vào đối tượng chứa
        ContentValues values = new ContentValues();
        values.put(TEN_TRUYEN,truyen.getTenTruyen());
        values.put(NOI_DUNG,truyen.getNoiDung());
        values.put(IMAGE,truyen.getAnh());

        //Thực thi update
        int res = db.update(TABLE_TRUYEN,values,ID_TRUYEN + " = ?", new String[] {String.valueOf(truyen.getID())});
        return res;
    }

}
