package movilbox.socialmedia.socialmediamovilbox.View.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.android.volley.RequestQueue;
import com.codesgood.views.JustifiedTextView;

import org.json.JSONException;
import org.json.JSONObject;

import movilbox.socialmedia.socialmediamovilbox.Interface.ContentImpl;
import movilbox.socialmedia.socialmediamovilbox.Model.ContentM;
import movilbox.socialmedia.socialmediamovilbox.Model.User;
import movilbox.socialmedia.socialmediamovilbox.Presenter.LoadData;
import movilbox.socialmedia.socialmediamovilbox.R;

public class Content extends DialogFragment implements ContentImpl {

    //constants
    private static final String STREET = "street";
    private static final String SUITE = "suite";
    private static final String CITY = "city";

    //vars
    private Context context;
    private String mainTitle;
    private boolean stateFav;
    private int userId;
    private RequestQueue request;

    //widgets
    private TextView title;
    private TextView subtitle;
    private JustifiedTextView text;
    private TextView name;
    private TextView email;
    private TextView address;

    public Content(Context context, String mainTitle, boolean stateFav, int userId, RequestQueue request) {
        this.context = context;
        this.mainTitle = mainTitle;
        this.stateFav = stateFav;
        this.userId = userId;
        this.request = request;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        View v = getActivity().getLayoutInflater().inflate(R.layout.post_content, null);

        title = v.findViewById(R.id.pc_title);
        subtitle = v.findViewById(R.id.pc_subtitle);
        text = v.findViewById(R.id.pc_text);
        ImageView fav = v.findViewById(R.id.pc_fav);
        name = v.findViewById(R.id.pc_name);
        email = v.findViewById(R.id.pc_email);
        address = v.findViewById(R.id.pc_address);

        if (stateFav) fav.setColorFilter(context.getResources().getColor(R.color.fav));
        else fav.setColorFilter(context.getResources().getColor(R.color.no_fav));

        LoadData.LoadContent lc = new LoadData.LoadContent(this, userId);
        lc.execute(request);
        LoadData.LoadUser lu = new LoadData.LoadUser(this, userId);
        lu.execute(request);

        builder.setView(v)
                .setPositiveButton("Volver", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        return builder.create();
    }

    @Override
    public void showContent(ContentM content) {
        title.setText(mainTitle);
        subtitle.setText(content.getSubtitle());
        text.setText(content.getBody());
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void showUserData(User user) {
        name.setText(user.getName());
        email.setText(user.getEmail());
        address.setText(user.getAddress());
    }
}
