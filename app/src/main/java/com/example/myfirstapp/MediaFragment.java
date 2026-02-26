package com.example.myfirstapp;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.VideoView;
import androidx.fragment.app.Fragment;  // ИЗМЕНЕНО
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MediaFragment extends Fragment {  // ИЗМЕНЕНО: теперь extends Fragment

    private ListView lvAudio;
    private MediaPlayer mediaPlayer;
    private SwipeRefreshLayout swipeRefreshLayout;
    private VideoView videoView;

    int[] audioRes = {R.raw.audio1, R.raw.audio2, R.raw.audio3};
    String[] audioTitles = {"Трек 1", "Трек 2", "Трек 3"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {  // ИЗМЕНЕНО: onCreateView вместо onCreate
        View view = inflater.inflate(R.layout.activity_media, container, false);  // ИЗМЕНЕНО

        lvAudio = view.findViewById(R.id.lvAudio);  // ИЗМЕНЕНО
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);  // ИЗМЕНЕНО
        videoView = view.findViewById(R.id.videoView);  // ИЗМЕНЕНО

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(),  // ИЗМЕНЕНО: getContext()
                android.R.layout.simple_list_item_1,
                audioTitles
        );
        lvAudio.setAdapter(adapter);

        lvAudio.setOnItemClickListener((parent, v, position, id) -> {
            playAudio(audioRes[position]);
        });

        swipeRefreshLayout.setOnRefreshListener(() -> {
            stopAudio();
            initVideo();
            Toast.makeText(getContext(), "Обновлено", Toast.LENGTH_SHORT).show();  // ИЗМЕНЕНО
            swipeRefreshLayout.setRefreshing(false);
        });

        initVideo();

        return view;  // ДОБАВЛЕНО
    }

    private void playAudio(int resId) {
        stopAudio();
        mediaPlayer = MediaPlayer.create(getContext(), resId);  // ИЗМЕНЕНО: getContext()
        mediaPlayer.start();
        Toast.makeText(getContext(), "Воспроизведение...", Toast.LENGTH_SHORT).show();  // ИЗМЕНЕНО
    }

    private void stopAudio() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void initVideo() {
        // ИЗМЕНЕНО: getActivity().getPackageName()
        Uri uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.sample);
        videoView.setVideoURI(uri);
        videoView.setOnPreparedListener(mp -> {
            videoView.start();
        });
    }

    @Override
    public void onDestroyView() {  // ИЗМЕНЕНО: onDestroyView вместо onDestroy
        super.onDestroyView();
        stopAudio();
    }
}