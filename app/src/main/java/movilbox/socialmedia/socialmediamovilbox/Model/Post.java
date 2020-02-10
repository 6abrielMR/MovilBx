package movilbox.socialmedia.socialmediamovilbox.Model;

public class Post {

    private int userId;
    private int id;
    private String title;
    private boolean state;
    private boolean read;

    public Post(int userId, int id, String title, boolean state, boolean read) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.state = state;
        this.read = read;
    }

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isState() {
        return state;
    }

    public boolean isRead() {
        return read;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
