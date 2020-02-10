package movilbox.socialmedia.socialmediamovilbox.Presenter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import movilbox.socialmedia.socialmediamovilbox.Model.Post;
import movilbox.socialmedia.socialmediamovilbox.R;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostsViewHolder> {

    //constants
    private static final String TAG = "PostAdapter";
    private static final String CLS = "";

    //vars
    private Context context;
    private List<Post> posts;
    private OnItemClickListener mListener;
    private boolean state;

    public interface OnItemClickListener {
        void onPost(int position, ImageView foc);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post, parent, false);
        return new PostsViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final PostsViewHolder vh, final int i) {
        vh.title.setText(CLS);
        Log.d(TAG, "onBindViewHolder: UserId => " + posts.get(i).getUserId());
        Log.d(TAG, "onBindViewHolder: IdPost => " + posts.get(i).getId());
        vh.title.setText(posts.get(i).getTitle());
        if (posts.get(i).isRead()) vh.focus.setVisibility(View.VISIBLE);
        else vh.focus.setVisibility(View.GONE);
        if (!posts.get(i).isState())
            vh.fav.setColorFilter(context.getResources().getColor(R.color.no_fav));
        state = posts.get(i).isState();
        vh.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state) {
                    vh.fav.setColorFilter(context.getResources().getColor(R.color.no_fav));
                    posts.get(i).setState(false);
                    state = false;
                }
                else {
                    vh.fav.setColorFilter(context.getResources().getColor(R.color.fav));
                    posts.get(i).setState(true);
                    state = true;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class PostsViewHolder extends RecyclerView.ViewHolder {
        LinearLayout post;
        TextView title;
        ImageView fav, focus;

        public PostsViewHolder(View v, final OnItemClickListener listener) {
            super(v);
            post = v.findViewById(R.id.post_post);
            title = v.findViewById(R.id.post_title);
            fav = v.findViewById(R.id.post_fav);
            focus = v.findViewById(R.id.post_focus);

            post.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onPost(position, focus);
                        }
                    }
                }
            });
        }
    }

}
