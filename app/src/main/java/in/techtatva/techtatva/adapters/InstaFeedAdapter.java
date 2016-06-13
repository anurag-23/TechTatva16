package in.techtatva.techtatva.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import in.techtatva.techtatva.R;
import in.techtatva.techtatva.models.InstaFeedModel;

/**
 * Created by anurag on 8/6/16.
 */
public class InstaFeedAdapter extends RecyclerView.Adapter<InstaFeedAdapter.InstaFeedViewHolder> {

    List<InstaFeedModel> instaFeedList;
    LayoutInflater inflater;


    public InstaFeedAdapter(Context context, List<InstaFeedModel> instaFeedList) {
        inflater = LayoutInflater.from(context);
        this.instaFeedList = instaFeedList;
    }

    @Override
    public InstaFeedViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.insta_feed_item, viewGroup, false);

        InstaFeedViewHolder holder = new InstaFeedViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(InstaFeedViewHolder holder, int position) {

        InstaFeedModel instaFeedItem = instaFeedList.get(position);
        holder.instaFeedDP.setImageResource(instaFeedItem.getDisplay());
        holder.instaFeedName.setText(instaFeedItem.getName());
        holder.instaFeedImage.setImageResource(instaFeedItem.getImage());

        String description = instaFeedItem.getDescription();
        final SpannableStringBuilder sb = new SpannableStringBuilder(description);
        sb.setSpan(new android.text.style.StyleSpan(Typeface.BOLD), 0, description.indexOf(" "), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        holder.instaFeedDescription.setText(sb);
        holder.instaFeedLikes.setText(Integer.toString(instaFeedItem.getLikes()) + " likes");
        holder.instaFeedComments.setText(Integer.toString(instaFeedItem.getComments()) + " comments");
    }


    @Override
    public int getItemCount() {
        return instaFeedList.size();
    }


    public class InstaFeedViewHolder extends RecyclerView.ViewHolder {

        ImageView instaFeedDP, instaFeedImage;
        TextView instaFeedName, instaFeedDescription, instaFeedLikes, instaFeedComments;

        public InstaFeedViewHolder(View itemView) {
            super(itemView);

            instaFeedDP = (ImageView)itemView.findViewById(R.id.insta_feed_dp_image_view);
            instaFeedName = (TextView)itemView.findViewById(R.id.insta_feed_name_text_view);
            instaFeedImage = (ImageView)itemView.findViewById(R.id.insta_feed_img_image_view);
            instaFeedDescription = (TextView)itemView.findViewById(R.id.insta_feed_description_text_view);
            instaFeedLikes = (TextView)itemView.findViewById(R.id.insta_feed_likes_text_view);
            instaFeedComments = (TextView)itemView.findViewById(R.id.insta_feed_comments_text_view);
        }
    }
}
