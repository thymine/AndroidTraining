package me.zhang.workbench.listvideo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import me.zhang.workbench.R;

public class VideoActivity extends Activity {

    RecyclerView rlVideoList;
    List<String> videos = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        rlVideoList = (RecyclerView) findViewById(R.id.rv_vieo_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(VideoActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rlVideoList.setLayoutManager(layoutManager);
        rlVideoList.setHasFixedSize(true);

        videos.add("http://az29176.vo.msecnd.net/videocontent/LakePowell_Thunderstorms_nimiaRM_4471864_062_768_ZH-CN.mp4");
        videos.add("http://az29176.vo.msecnd.net/videocontent/VernalFall_Yosemite_FP_229-991-600_768_v2_ZH-CN.mp4");
        videos.add("http://az29176.vo.msecnd.net/videocontent/LakePowell_Thunderstorms_nimiaRM_4471864_062_768_ZH-CN.mp4");
        videos.add("http://az29176.vo.msecnd.net/videocontent/VernalFall_Yosemite_FP_229-991-600_768_v2_ZH-CN.mp4");
        videos.add("http://az29176.vo.msecnd.net/videocontent/LakePowell_Thunderstorms_nimiaRM_4471864_062_768_ZH-CN.mp4");
        videos.add("http://az29176.vo.msecnd.net/videocontent/VernalFall_Yosemite_FP_229-991-600_768_v2_ZH-CN.mp4");
        videos.add("http://az29176.vo.msecnd.net/videocontent/LakePowell_Thunderstorms_nimiaRM_4471864_062_768_ZH-CN.mp4");
        videos.add("http://az29176.vo.msecnd.net/videocontent/VernalFall_Yosemite_FP_229-991-600_768_v2_ZH-CN.mp4");
        videos.add("http://az29176.vo.msecnd.net/videocontent/LakePowell_Thunderstorms_nimiaRM_4471864_062_768_ZH-CN.mp4");

        VideoAdapter adapter = new VideoAdapter(VideoActivity.this, videos);
        rlVideoList.setAdapter(adapter);
    }
}
