package com.mustafacqn.xoxgame.achievements;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.jos.JosApps;
import com.huawei.hms.jos.JosAppsClient;
import com.huawei.hms.jos.games.AchievementsClient;
import com.huawei.hms.jos.games.Games;
import com.huawei.hms.jos.games.achievement.Achievement;
import com.huawei.hms.support.hwid.result.AuthHuaweiId;
import com.mustafacqn.xoxgame.R;

import java.util.ArrayList;
import java.util.List;

public class Achievements extends AppCompatActivity {

    private static final String TAG = "Achievements";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private Achievements context;
    private AchievementsClient achievementsClient;
    private List<Achievement> listItems;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: ilk önce AppGallery içerisinde kullanıcılara nasıl achievement oluşturacaklarını anlat
        // TODO: achievements layoutunu oluştur.
        // TODO: RecyclerView'ı gradle dosyasına ekle
        // TODO: Opening classında ve layoutunda buton oluştur ve signin intentleri ile AuthHuaweiId yi yolla
        // TODO: Task<List<Achievement>> değişkenini oluştururken insanlara yarattığımız Achievements den farklı olduğunu söyle
        // TODO: 1 ACHIEVEMENTSLERI TELEFONDAN KONTROL ET OLMUYORSA AGC YI AC VE RELEASED OLDULAR MI DIYE KONTROL ET. EĞER 2 SIDE OLMAZ ISE KODLARI GOZDEN GECIR
        setContentView(R.layout.achievements);

        context = this;
        listItems = new ArrayList<>();
        init();
        requestData();

    }

    public void requestData() {
        AuthHuaweiId huaweiId = getIntent().getParcelableExtra("hw_account");

        JosAppsClient appsClient = JosApps.getJosAppsClient(this, huaweiId);
        appsClient.init();

        achievementsClient = Games.getAchievementsClient(this, huaweiId);

        Task<List<Achievement>> achievementsList = achievementsClient.getAchievementList(false);
        achievementsList.addOnSuccessListener(new OnSuccessListener<List<Achievement>>() {
            @Override
            public void onSuccess(List<Achievement> achievements) {

                if (achievements == null) {
                    return;
                }

                listItems.clear();
                for (Achievement achievement : achievements) {
                    listItems.add(achievement);
                }
                recyclerView.setAdapter(adapter);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {

                if (e instanceof ApiException){
                    Toast.makeText(context, "Failed while retrieving Achievements, Error Code: " + ((ApiException)e).getStatusCode(), Toast.LENGTH_SHORT).show();
                }
                finish();

            }
        });

    }

    public void init() {
        recyclerView = findViewById(R.id.achievements_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new AchievementsAdapter(listItems, context);
        recyclerView.setAdapter(adapter);
    }
}
