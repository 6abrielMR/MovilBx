package movilbox.socialmedia.socialmediamovilbox.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import movilbox.socialmedia.socialmediamovilbox.Interface.PostsImpl;
import movilbox.socialmedia.socialmediamovilbox.Model.Post;
import movilbox.socialmedia.socialmediamovilbox.Presenter.LoadData;
import movilbox.socialmedia.socialmediamovilbox.Presenter.PostsAdapter;
import movilbox.socialmedia.socialmediamovilbox.R;
import movilbox.socialmedia.socialmediamovilbox.View.Fragment.Content;

public class Posts extends AppCompatActivity implements PostsImpl {

    //constants
    private static final String TAG = "Post";

    //vars
    RequestQueue request;
    private PostsAdapter mAdapter;
    private List<Post> posts;

    //widgets
    private BottomAppBar bar;
    private RecyclerView recycler;
    private FloatingActionButton btnRemove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.posts);

        init();
    }

    private void init() {
        bar = findViewById(R.id.bar);
        setSupportActionBar(bar);
        bar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.filter) {
                    Log.d(TAG, "onMenuItemClick: " + posts.size());
                    for (int i = 0; i < posts.size(); i++) {
                        if (!posts.get(i).isState()){
                            posts.remove(i);
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                }

                return true;
            }
        });
        request = Volley.newRequestQueue(this);
        recycler = findViewById(R.id.posts_recycler);
        btnRemove = findViewById(R.id.posts_remove);
        LoadData.LoadPostSimples ld = new LoadData.LoadPostSimples(this);
        ld.execute(request);
    }

    private void initRecycler() {
        //recycler.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recycler.setLayoutManager(manager);
        recycler.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new PostsAdapter.OnItemClickListener() {
            @Override
            public void onPost(int position, ImageView foc) {
                Content content = new Content(Posts.this, posts.get(position).getTitle(),
                        posts.get(position).isState(), posts.get(position).getUserId(),
                        request);
                content.show(getSupportFragmentManager(), Posts.this.getLocalClassName());
                posts.get(position).setRead(false);
                foc.setVisibility(View.GONE);
            }
        });
    }

    public void removeAllPosts(View view) {
        posts.clear();
        Toast.makeText(this, "Se borraron todos los posts", Toast.LENGTH_SHORT).show();
        mAdapter.notifyDataSetChanged();
    }
    
    @Override
    public void showData(List<Post> data) {
        posts = data;
        mAdapter = new PostsAdapter(this, posts);
        initRecycler();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
