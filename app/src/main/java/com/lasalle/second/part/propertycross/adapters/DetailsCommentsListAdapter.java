package com.lasalle.second.part.propertycross.adapters;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lasalle.second.part.propertycross.R;
import com.lasalle.second.part.propertycross.model.Comment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Eduard on 05/02/2016.
 */
public class DetailsCommentsListAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<Comment> commentList;
    private Activity ownerActivity;

    public DetailsCommentsListAdapter(Activity ownerActivity, List<Comment> commentList) {
        layoutInflater = ownerActivity.getLayoutInflater();
        this.commentList = commentList;
        this.ownerActivity = ownerActivity;
    }

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.details_comment_item, parent, false);
        }

        Comment comment = this.commentList.get(position);


        setViewTitle(convertView, comment);
        setViewDate(convertView, comment);
        setViewImage(convertView, comment);
        setViewText(convertView, comment);

        return convertView;
    }

    private void setViewText(View convertView, Comment comment) {
        TextView textView = (TextView) convertView.findViewById(R.id.details_comment_text);
        textView.setText(comment.getText());
    }

    private void setViewDate(View convertView, Comment comment) {
        TextView commentDate = (TextView) convertView.findViewById(R.id.details_comment_date);
        String format = "dd.MM.yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
        commentDate.setText(sdf.format(comment.getDate()));
    }

    private void setViewImage(View convertView, Comment comment) {
        ImageView imageView = (ImageView) convertView.findViewById(R.id.details_comment_image);
        if (comment.getPhoto() == null) {
            switch ((int) (Math.random() * 10) % 4) {
                case 0:
                    imageView.setImageResource(R.drawable.flat_sample_image);
                    break;
                case 1:
                    imageView.setImageResource(R.drawable.flat_sample_image_2);
                    break;
                case 2:
                    imageView.setImageResource(R.drawable.flat_sample_image_3);
                case 3:
                    imageView.setImageResource(R.drawable.flat_sample_image_4);
                    break;
            }
        }
        else {
            imageView.setImageURI(comment.getPhoto());
        }
    }

    private void setViewTitle(View convertView, Comment comment) {
        TextView title = (TextView) convertView.findViewById(R.id.details_comment_title);
        title.setText(comment.getAuthor());
    }

    public void addComments(String text, Uri photo) {
        Comment comment = new Comment();

        comment.setAuthor(ownerActivity.getString(R.string.comment_default_author));
        comment.setText(text);
        comment.setDate(Calendar.getInstance().getTime());
        comment.setPhoto(photo);

        commentList.add(0, comment);
    }
}
