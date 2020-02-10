package movilbox.socialmedia.socialmediamovilbox.Model;

public class ContentM {

    private int userId;
    private int id;
    private String subtitle;
    private String body;

    public ContentM(int userId, int id, String subtitle, String body) {
        this.userId = userId;
        this.id = id;
        this.subtitle = subtitle;
        this.body = body;
    }

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getBody() {
        return body;
    }
}
