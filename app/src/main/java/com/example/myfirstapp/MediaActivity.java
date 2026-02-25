package com.example.myfirstapp;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MediaActivity extends AppCompatActivity {

    private ListView lvAudio;
    private MediaPlayer mediaPlayer;
    private SwipeRefreshLayout swipeRefreshLayout;
    private VideoView videoView;

    // Массивы ресурсов и названий треков
    int[] audioRes = {R.raw.audio1, R.raw.audio2, R.raw.audio3};
    String[] audioTitles = {"Трек 1", "Трек 2", "Трек 3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);

        lvAudio = findViewById(R.id.lvAudio);
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        videoView = findViewById(R.id.videoView);

        // Список аудио
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                audioTitles
        );
        lvAudio.setAdapter(adapter);

        lvAudio.setOnItemClickListener((parent, view, position, id) -> {
            playAudio(audioRes[position]);
        });

        // Swipe-to-refresh
        swipeRefreshLayout.setOnRefreshListener(() -> {
            stopAudio();
            initVideo(); // перезагрузка видео
            Toast.makeText(this, "Обновлено", Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
        });

        initVideo();
    }

    private void playAudio(int resId) {
        stopAudio(); // остановить предыдущий трек, если он играет
        mediaPlayer = MediaPlayer.create(this, resId);
        mediaPlayer.start();
        Toast.makeText(this, "Воспроизведение...", Toast.LENGTH_SHORT).show();
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
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.sample);
        videoView.setVideoURI(uri);
        videoView.setOnPreparedListener(mp -> {
            // Запускаем видео, когда оно готово
            videoView.start();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopAudio();
    }
}
