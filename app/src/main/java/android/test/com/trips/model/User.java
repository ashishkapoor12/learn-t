package android.test.com.trips.model;

public class User {

    public static final String TABLE_NAME = "user";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ID = "user_id";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_COMMENT = "comment";


    public static final String COLUMN_PRIM_ID = "id";


    private String name;
    private String user_id;
    private String email;
    private String gender;
    private int id;

    private String comment;


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //create table
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_PRIM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+COLUMN_ID + " TEXT," +
            COLUMN_NAME + " TEXT,"  + COLUMN_EMAIL + " TEXT," + COLUMN_GENDER + " TEXT," + COLUMN_COMMENT + " TEXT" + ")";


    public User() {
    }


    public int getId() {
        return id;
    }

    public User(String name, String user_id, String email, String gender, int id, String comment) {
        this.name = name;
        this.user_id = user_id;
        this.email = email;
        this.gender = gender;
        this.id = id;
        this.comment = comment;
    }

    public User(String name, String user_id, String email, String gender, String comment) {

        this.name = name;
        this.user_id = user_id;
        this.email = email;
        this.gender = gender;
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "User{" + "name='" + name + '\'' + ", id='" + id + '\'' + ", email='" + email + '\'' + ", gender='" + gender + '\'' + ", comment='" + comment + '\'' + '}';
    }
}
