package temnogrudova.com.retrofittest;

/**
 * Created by 123 on 05.02.2016.
 */
public  class Post {
    public  String userId;
    public  String id;
    public  String title;
    public  String body;

    public Post(String userId, String id, String title, String body) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.body = body;
    }
}
