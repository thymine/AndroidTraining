package com.soufun.app.activity.bnzf;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.soufun.app.R;
import com.soufun.app.SoufunApp;
import com.soufun.app.SoufunConstants;
import com.soufun.app.chatManager.tools.Chat;
import com.soufun.app.entity.db.CallAgentInfo;
import com.soufun.app.utils.LoaderImageExpandUtil;
import com.soufun.app.utils.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Created by Zhang on 2015/8/4 下午 2:08 .
 */
public class AgentListAdapter extends BaseAdapter {

    public static final int PHONE_CALL = 0; // 拨打电话
    public static final int MSG_SEND = 1; // 发送消息
    private SoufunApp app;
    /* Set用来记录已经添加到itemList集合中的CallAgentInfo的agentId字段，去重用 */
    private Set<String> set = new HashSet<>();
    private ArrayList<CallAgentInfo> itemList = new ArrayList<>();

    private OnAgentItemButtonClickListener listener;

    interface OnAgentItemButtonClickListener {
        void onAgentItemButtonClick(int what, CallAgentInfo info);
    }

    public AgentListAdapter(SoufunApp context, OnAgentItemButtonClickListener listener) {
        this.listener = listener;
        this.app = context;
    }

    @Override
    public int getCount() {
        return set.size();
    }

    public void update(List<CallAgentInfo> infos) {
        for (CallAgentInfo info : infos) {
            try {
                /* 已记录的数据不再添加 */
                if (!set.contains(info.agentId)) {
                    set.add(info.agentId);
                    /* 添加没有的新数据 */
                    itemList.add(info);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        /* 按时间倒序 */
        Collections.sort(itemList, new TimeComparator());
        notifyDataSetChanged();
    }

    @Override
    public CallAgentInfo getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(app).inflate(R.layout.bnzf_agent_item, null);
            holder.iv_head = (ImageView) view.findViewById(R.id.iv_head);
            holder.tv_name = (TextView) view.findViewById(R.id.tv_name);
            holder.tv_chatcount = (TextView) view.findViewById(R.id.tv_chatcount);
            holder.tv_time = (TextView) view.findViewById(R.id.tv_time);
            holder.tv_company = (TextView) view.findViewById(R.id.tv_company);
            holder.tv_chatcontent = (TextView) view.findViewById(R.id.tv_chatcontent);
            holder.btn_call = (Button) view.findViewById(R.id.btn_call);
            holder.btn_chat = (Button) view.findViewById(R.id.btn_chat);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        /* 应用数据到控件 */
        setData(holder, getItem(position));
        /* 注册控件监听事件 */
        registerListener(holder, getItem(position));

        /* 设置控件可见性 */
        setVisibility(holder, getItem(position));
        return view;
    }

    private void setVisibility(ViewHolder holder, final CallAgentInfo info) {
        /* 拨号按钮可见性 */
        if (StringUtils.isNullOrEmpty(info.phone))
            holder.btn_call.setVisibility(View.GONE);
    }

    private void registerListener(ViewHolder holder, final CallAgentInfo info) {
        /* 拨打电话 */
        holder.btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onAgentItemButtonClick(PHONE_CALL, info);
            }
        });
        /* 发送消息 */
        holder.btn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onAgentItemButtonClick(MSG_SEND, info);
            }
        });
    }

    private void setData(ViewHolder holder, CallAgentInfo info) {
        /* 置业顾问名字 */
        if (StringUtils.isNullOrEmpty(info.agentName)) {
            holder.tv_name.setVisibility(View.INVISIBLE);
        } else {
            holder.tv_name.setVisibility(View.VISIBLE);
            holder.tv_name.setText(info.agentName + "");
        }

        /* 消息时间 */
        holder.tv_time.setText(getTimeShort(info.messageTime) + "");

        /* 消息内容 */
        holder.tv_chatcontent.setText(getChatContent(info) + "");

        /* 置业顾问头像地址 */
        if (!StringUtils.isNullOrEmpty(info.picUrl)) {
            LoaderImageExpandUtil.displayImage(
                    StringUtils.getImgPath(info.picUrl, 75, 100, false), holder.iv_head);
        }

        /* 公司 */
        if (StringUtils.isNullOrEmpty(info.agentCompany)) {
            holder.tv_company.setVisibility(View.GONE);
        } else {
            holder.tv_company.setVisibility(View.VISIBLE);
            holder.tv_company.setText(info.agentCompany + "");
        }

        /* 消息数目 */
        int chatcout = (int) app.getDb().getCount("chat",
                "agentname='" + info.agentName + "' and state='1'");
        if (chatcout == 0) {
            holder.tv_chatcount.setVisibility(View.GONE);
        } else {
            holder.tv_chatcount.setVisibility(View.VISIBLE);
            holder.tv_chatcount.setText(chatcout + "");
        }

    }

    private String getChatContent(CallAgentInfo info) {
        if (info == null) {
            return "";
        }
        Chat chat = app.getDb().queryRecentlyContactChatByName(info.agentName);
        if (chat == null) {
            return "";
        }
        String msg;
        if (SoufunConstants.COMMONT_IMG.equals(chat.command)) {
            msg = "[图片]";
        } else if (SoufunConstants.COMMONT_VIDEO.equals(chat.command)) {
            msg = "[视频]";
        } else if (SoufunConstants.COMMONT_REPVIDEO.equals(chat.command)) {
            msg = "[直播看房]";
        } else if (SoufunConstants.COMMONT_VOICE.equals(chat.command)) {
            msg = "[语音]";
        } else {
            msg = chat.message;
        }
        return msg;
    }

    public String getTimeShort(String time) {
        if (StringUtils.isNullOrEmpty(time)) {
            time = String.valueOf(System.currentTimeMillis());
        }
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm", Locale.CHINA);
            Date date = new Date(Long.parseLong(time));
            return formatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private class TimeComparator implements Comparator<CallAgentInfo> {

        @Override
        public int compare(CallAgentInfo lhs, CallAgentInfo rhs) {
            long lhs_time = Long.parseLong(lhs.messageTime);
            long rhs_time = Long.parseLong(rhs.messageTime);
            return (int) (rhs_time - lhs_time);
        }

    }

    class ViewHolder {
        /* 头像 */
        ImageView iv_head;
        /* 名字 */
        TextView tv_name;
        /* 消息数量 */
        TextView tv_chatcount;
        /* 公司 */
        TextView tv_company;
        /* 消息时间 */
        TextView tv_time;
        /* 消息内容 */
        TextView tv_chatcontent;

        /* 电话 */
        Button btn_call;
        /* 聊天 */
        Button btn_chat;
    }

}
