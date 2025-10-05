package Project1;

import java.util.Calendar;
import java.util.Date;

public class Post{
    private String data;
    private Date postTimeStamp;

    // constructor to initialize the post with data and postTimeStamp
    public Post(String data, Date postTimeStamp) {
        this.data = data;
        this.postTimeStamp = postTimeStamp;
    }

    // method to get the post itself
    public String getData() {
        return data;
    }

    // method to get the timestamp of the post
    public Date getDate() {
        return postTimeStamp;
    }

    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, 00, 10); // 2024-00-10
        Date postTimeStamp = calendar.getTime();
        System.out.println("Post created on: " + postTimeStamp);
    }
}


// line 216-218 in usermanager.java