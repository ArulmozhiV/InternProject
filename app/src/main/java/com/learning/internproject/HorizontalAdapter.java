package com.learning.internproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SyncStats;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

public class HorizontalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    MediaObject current;
    int currentPos=0;

    public HorizontalAdapter(Context context,MediaObject data){
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        current = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.inner_row_item,parent,false);
        ViewHolderHorizontal holderHorizontal = new ViewHolderHorizontal(view);
        return holderHorizontal;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
         final ViewHolderHorizontal viewHolder = (ViewHolderHorizontal) holder;
       // current = dataObjects.get(position);
        try {

            if(current.getImage()!=null && position<current.getImage().length){

                viewHolder.imageView.setVisibility(View.VISIBLE);

                @SuppressLint("StaticFieldLeak") getMedia getMedia = new getMedia(){
                    @Override
                    protected void onPostExecute(Bitmap bitmap) {
                        super.onPostExecute(bitmap);

                        viewHolder.imageView.setImageBitmap(bitmap);
                    }
                };
                int pos = position;//-current.getVideo().length;
                getMedia.execute(current.image[pos]);



            }else if(current.getVideo()!=null){

                final int pos;
                if(position<current.getVideo().length)
                    pos=position;
                else
                    pos = position-current.getImage().length;

                viewHolder.youTubePlayerView.setVisibility(View.VISIBLE);
                viewHolder.youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                        String videoId = current.video[pos];
                        youTubePlayer.loadVideo(videoId, 0);
                        youTubePlayer.pause();
                    }
                });



            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        //viewHolder.image.

    }

    @Override
    public int getItemCount() {
        //return 1;
        int image=0,video=0;
        if(current.video!=null){
            video = current.getVideo().length;
        }
        if(current.image!=null){
            image = current.getImage().length;
        }
         return (image+video);
    }



}

class ViewHolderHorizontal extends RecyclerView.ViewHolder{

   ImageView imageView;
   //VideoView videoView;
    YouTubePlayerView youTubePlayerView;

    public ViewHolderHorizontal(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageView);
        youTubePlayerView = itemView.findViewById(R.id.youtube_player_view);
        //videoView = itemView.findViewById(R.id.videoView);
    }
}

class getMedia extends AsyncTask<String,Void,Bitmap>{

    @Override
    protected Bitmap doInBackground(String... strings) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(strings[0]);
             bitmap= BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
        
    }
}

