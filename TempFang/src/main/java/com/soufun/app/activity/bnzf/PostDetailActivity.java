package com.soufun.app.activity.bnzf;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.soufun.app.BaseActivity;
import com.soufun.app.R;
import com.soufun.app.SoufunConstants;
import com.soufun.app.activity.ChatActivity;
import com.soufun.app.entity.db.CallAgentInfo;
import com.soufun.app.utils.IntentUtils;
import com.soufun.app.utils.StringUtils;
import com.soufun.app.utils.UtilsLog;
import com.soufun.app.utils.UtilsVar;
import com.soufun.app.utils.analytics.Analytics;
import com.soufun.voicerecord.VoiceDecoder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Zhang on 2015/7/30 下午 1:54 .
 */
public class PostDetailActivity extends BaseActivity implements AgentListAdapter.OnAgentItemButtonClickListener {

    private static final String TAG = "BasePostDetailActivity";

    /* 应答的置业顾问模块 */
    private LinearLayout llRepliedAgent;

    private TextView txtSearchContent, txtPostTime, txtTimePassed,
            txtTipNoAgent;
    private Button btnPlay, btnResend, btnShift;
    private ListView lsAgent, lsRecommend;
    private AgentListAdapter adapter;

    private CountDownTimer timer5m; // 5min 正计时器
    private CountDownTimer timer60s; // 60s 倒计时器
    private static final long INTERVAL = 5 * 60 * 1000; // 5m
    private static final long COUNT_DOWN_INTERVAL = 60000; // 60s

    private class DataHolder {
        String type = ""; // 新房、二手房或租房

        String searchContent = ""; // 找房意向
        String postTime = ""; // 发布时间：2015-08-04 10:06:16
        String audioUrl = ""; // 音频地址

        public String business_id = "";
    }

    private DataHolder holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setView(R.layout.bnzf_post_detail, LAYOUT_TYPE_HEADER);

        /* 获取Intent数据，存入DataHolder对象 */
        holder = fetchIntent();

        /* 设置Activity标题栏 */
        initHeaderbar(holder);

        /* 实例化布局控件 */
        initView();

        /* 填充数据 */
        fillData(holder);

        /* 注册控件监听器 */
        registerListener(holder);

        /* 初始化控件显示状态 */
        initVisibility(holder);

        /* 启动正计时器：发布页点击“提交”按钮后5min内 */
        startCountUpTimer(holder);

    }

    private DataHolder fetchIntent() {
        DataHolder holder = new DataHolder();
        // TODO 将获取的数据存入DataHolder
        Intent intent = getIntent();

        return holder;
    }

    private void initHeaderbar(DataHolder holder) {
        String title = "";

        if ("xf".equals(holder.type)) {
            title = "我的买新房详情";
        } else if ("esf".equals(holder.type)) {
            title = "我的买二手房详情";
        } else if ("zf".equals(holder.type)) {
            title = "我的租房详情";
        }

        setHeaderBar(title, "查看地图");
    }

    private void initView() {
        /* 头部内容模块：搜索内容、发布时间和播放音频按钮 */
        txtSearchContent = (TextView) findViewById(R.id.tv_search_content);
        txtPostTime = (TextView) findViewById(R.id.tv_post_time);
        btnPlay = (Button) findViewById(R.id.btn_play);

        /* 应答置业顾问模块：标题、倒计时、重新发送按钮、提示文字和置业顾问列表 */
        llRepliedAgent = (LinearLayout) findViewById(R.id.ll_replied_agent);
        txtTimePassed = (TextView) findViewById(R.id.tv_time_passed);
        btnResend = (Button) findViewById(R.id.btn_resend);
        txtTipNoAgent = (TextView) findViewById(R.id.tv_tip_no_answered_agent);
        lsAgent = (ListView) findViewById(R.id.lv_agent_list);

        /* 推荐房源模块：换一换按钮和房源列表 */
        btnShift = (Button) findViewById(R.id.btn_shift);
        lsRecommend = (ListView) findViewById(R.id.lv_recommend_list);
    }

    private void fillData(DataHolder holder) {
        // TODO 将DataHolder的数据塞入布局控件
        /* 找房意向模块 */
        txtSearchContent.setText(holder.searchContent);
        txtPostTime.setText("发布时间：" + holder.postTime);

        /* 应答的置业顾问模块 */
        // 置业顾问列表
        adapter = new AgentListAdapter(mApp, this);
        lsAgent.setAdapter(adapter);
    }

    private void registerListener(final DataHolder holder) {
        /* 播放录音 */
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new VoiceDecoder(holder.audioUrl).startPlay();
                toast("播放语音中...");
            }
        });

        /* 再次发送按钮 */
        btnResend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 重新发送按钮不可点击，60s后可用
                btnResend.setClickable(false);
                /* 60s后按钮可以继续使用 */
                timer60s = new CountDownTimer(COUNT_DOWN_INTERVAL, 1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {
                        UtilsLog.i(TAG, "MillisUntiFinished: " + millisUntilFinished);
                    }

                    @Override
                    public void onFinish() {
                        // 设置按钮可点击
                        btnResend.setClickable(true);
                    }

                }.start();

                // TODO 处理再次呼叫置业顾问请求
                // TODO 异步任务结束后，弹toast：您的需求最多只能发送两次哦！

                tipsOnFirstClickResend();
            }

        });

        /* 置业顾问列表item点击 */
        lsAgent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sendMsg(adapter.getItem(position));
            }
        });

        /* 推荐房源，换一换按钮 */
        btnShift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO 换一换推荐房源
            }
        });
    }

    /**
     * 第一次点击重新发送按钮提示信息
     */
    private void tipsOnFirstClickResend() {
        toast("已将您的需求发送给10位置业顾问，先看看房天下为您推荐的房源吧！");
        txtTipNoAgent.setText("还没有置业顾问应答，看看房天下推荐的房源吧！");
    }

    private void initVisibility(DataHolder holder) {
        /* 是否显示播放按钮 */
        if (StringUtils.isNullOrEmpty(holder.audioUrl))
            btnPlay.setVisibility(View.GONE);

        /* 默认（刚进详情页）隐藏重新发送按钮 */
        btnResend.setVisibility(View.GONE);
    }

    private void startCountUpTimer(DataHolder holder) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CHINA);
        long publishTime = 0;
        try {
            Date date = format.parse(holder.postTime);
            publishTime = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        /* 当前时间距离发布时间 */
        final long passedTime = System.currentTimeMillis() - publishTime;
        /* 5分钟内的剩余时间 */
        final long remains = INTERVAL - passedTime;

        if (remains < 0) {
            /* 当前时间距离发布时间超过5分钟 */
            // 显示重新发送按钮
            btnResend.setVisibility(View.VISIBLE);
            return;
        }

        timer5m = new CountDownTimer(remains, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                diplay(millisUntilFinished);
            }

            private void diplay(long millisUntilFinished) {
                long passed = remains - millisUntilFinished;
                long displayed = passed + passedTime;
                /* 显示正计时时间， */
                txtTimePassed.setText(
                        String.format(
                                "等待 %02d : %02d",
                                displayed / 1000 / 60,
                                displayed / 1000 % 60
                        )
                );
            }

            @Override
            public void onFinish() {
                tipsAfter5Mins();
            }

        }.start();
    }

    /**
     * 5分钟后，应答的置业顾问模块控件变动
     */
    private void tipsAfter5Mins() {
        // 隐藏正计时提示文本
        txtTimePassed.setVisibility(View.GONE);
        // 设置提示文本
        txtTipNoAgent.setText("没有置业顾问应答，再发送一次您的需求吧。");
        // 显示重新发送按钮
        btnResend.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter("xxx"); // TODO 过滤置业顾问回复
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    /**
     * 响应置业顾问（经纪人）广播接收器
     */
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("com.soufun.agent".equals(intent.getAction())) {// 呼叫推送广播
                removeTimerRelatedViews();
                refreshAgentList();
            }
        }
    };

    /**
     * 移除计时、提示文字和重新发送按钮
     */
    private void removeTimerRelatedViews() {
        txtTimePassed.setVisibility(View.GONE);
        btnResend.setVisibility(View.GONE);
        txtTipNoAgent.setVisibility(View.GONE);
    }

    private void refreshAgentList() {
        String queryType;
        if ("xf".equals(holder.type)) {
            queryType = "xf";
        } else {
            queryType = "agent";
        }
        List<CallAgentInfo> list = mApp.getDb().queryAll(CallAgentInfo.class, " type='" + queryType
                + "' and business_id='" + holder.business_id + "'");
        if (list != null) {
            /* 刷新应答的置业顾问列表数据集 */
            adapter.update(list);
        }
    }

    /**
     * 聊天发起
     *
     * @param info 应答的职业顾问
     */
    private void sendMsg(CallAgentInfo info) {
        analyticBylabel("联系应答置业顾问");

        Intent intent = new Intent();
        intent.setClass(mContext, ChatActivity.class);
        intent.putExtra("to", info.form);
        intent.putExtra("agentname", info.agentName);
        intent.putExtra("agentId", info.agentId);
        intent.putExtra("agentcity", UtilsVar.CITY);
        intent.putExtra("CallAgent", true);
        intent.putExtra("send", false);
        if ("xf".equals(info.type)) {
            intent.putExtra("chatClass", 1);
        }
        startActivityForAnima(intent);
    }

    private void analyticBylabel(String label) {
        if (StringUtils.isNullOrEmpty(label)) {
            return;
        }
        String typeString;
        if (SoufunConstants.XF.equals(holder.type)) {
            typeString = "-新房";
        } else if (SoufunConstants.ESF.equals(holder.type)) {
            typeString = "-二手房";
        } else {
            typeString = "-租房";
        }
        Analytics.trackEvent("搜房-7.9.2-找房详情页", "点击", label + typeString);
    }

    /**
     * 处理应答的置业顾问列表item按钮点击
     */
    @Override
    public void onAgentItemButtonClick(int what, CallAgentInfo info) {
        switch (what) {
            case AgentListAdapter.MSG_SEND:
                sendMsg(info);
                break;
            case AgentListAdapter.PHONE_CALL:
                IntentUtils.dialPhone(mApp, info.phone, false);
                break;
        }
    }
}
