package com.mltj.xxks.fragment.card;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mltj.xxks.R;
import com.mltj.xxks.activity.ExaminationCardActivity2;
import com.mltj.xxks.adapter.OptionsListAdapter;
import com.mltj.xxks.bean.Answer;
import com.mltj.xxks.bean.MessageEvent;
import com.mltj.xxks.bean.QuestionBean;
import com.mltj.xxks.bean.QuestionOptionBean;
import com.mltj.xxks.util.Diff_match_patch;
import com.mltj.xxks.widget.NoScrollListview;
import com.mltj.xxks.widget.RichEditor;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class QuestionItemFragment2 extends Fragment {
    QuestionBean questionBean;
    int index;
    private OptionsListAdapter adapter;
    private StringBuffer sb;
    private NoScrollListview lv;
    private Context context;
    String mText = "";
    String mQcontent = "";

    @SuppressLint("ValidFragment")
    public QuestionItemFragment2(Context context, int index, QuestionBean bean) {
        this.context = context;
        this.index = index;
        questionBean = bean;
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.pager_item2, container, false);

        lv = (NoScrollListview) rootView.findViewById(R.id.lv_options);
        RelativeLayout rl_description = rootView.findViewById(R.id.rl_description);
        TextView subjectType = rootView.findViewById(R.id.subject_type);
        RichEditor editor = (RichEditor) rootView.findViewById(R.id.editor);
        LinearLayout lldtjx = rootView.findViewById(R.id.ll_dtjx);
        TextView dtjx = (TextView) rootView.findViewById(R.id.dtjx);
        final TextView as = rootView.findViewById(R.id.answer);
//        if (isShowAnswer) {
            lldtjx.setVisibility(View.VISIBLE);
            dtjx.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    as.setText("解析：" + questionBean.getTopicAnalysis());
                    as.setVisibility(View.VISIBLE);
                }
            });
//        } else {
//            lldtjx.setVisibility(View.GONE);
//        }
        Answer answer = ((ExaminationCardActivity2) context).getAnswerMap().get(index);
        if (answer != null) {
            initedit(getActivity(), editor, answer.getQuestion(), questionBean.getQuestionContent());
        } else {
            String content = questionBean.getQuestionContent();
            String rc = content.replace("【】", "【<h contenteditable=\"true\"></h>】");
            initedit(getActivity(), editor, rc, rc);
        }
        final int questionType = Integer.valueOf(questionBean.getQuestionType());
        sb = new StringBuffer();
        if (questionType == 1) {
            subjectType.setText("单选题");
            adapter = new OptionsListAdapter(getActivity(), questionBean.getQuestionOptions(), lv, index);
            lv.setAdapter(adapter);
            lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            lv.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    adapter.notifyDataSetChanged();
                    Answer answer = new Answer();
                    answer.setPosition(index);
                    answer.setQuestion(questionBean.getQuestionContent());
                    answer.setType(questionType);
                    answer.setSelectoption(lv.getCheckedItemIds());
                    answer.setAnswers(getAnsOption(lv.getCheckedItemIds(), (ArrayList<QuestionOptionBean>) questionBean.getQuestionOptions()));
                    ((ExaminationCardActivity2) context).getAnswerMap().put(index, answer);
                }
            });
            if (answer != null) {
                long[] select = answer.getSelectoption();
                if (select != null) {
                    for (int i = 0; i < select.length; i++) {
                        lv.setItemChecked((int) select[i], true);
                    }
                }

            }

        } else if (questionType == 4) {
            subjectType.setText("多选题");
            adapter = new OptionsListAdapter(getActivity(), questionBean.getQuestionOptions(), lv, index);
            lv.setAdapter(adapter);
            lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

            lv.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    adapter.notifyDataSetChanged();
                    Answer answer = new Answer();
                    answer.setPosition(index);
                    answer.setQuestion(questionBean.getQuestionContent());
                    answer.setType(questionType);
                    answer.setSelectoption(lv.getCheckedItemIds());
                    answer.setAnswers(getAnsOption(lv.getCheckedItemIds(), (ArrayList<QuestionOptionBean>) questionBean.getQuestionOptions()));
                    ((ExaminationCardActivity2) context).getAnswerMap().put(index, answer);
                }
            });
            if (answer != null) {
                long[] select = answer.getSelectoption();
                if (select != null) {
                    for (int i = 0; i < select.length; i++) {
                        lv.setItemChecked((int) select[i], true);
                    }
                }

            }

        } else if (questionType == 2) {
            subjectType.setText("填空题");
        } else {
            subjectType.setText("判断题");
            adapter = new OptionsListAdapter(getActivity(), questionBean.getQuestionOptions(), lv, index);
            lv.setAdapter(adapter);
            lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            lv.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    adapter.notifyDataSetChanged();
                    Answer answer = new Answer();
                    answer.setPosition(index);
                    answer.setQuestion(questionBean.getQuestionContent());
                    answer.setType(questionType);
                    answer.setSelectoption(lv.getCheckedItemIds());
                    answer.setAnswers(getAnsOption(lv.getCheckedItemIds(), (ArrayList<QuestionOptionBean>) questionBean.getQuestionOptions()));
                    ((ExaminationCardActivity2) context).getAnswerMap().put(index, answer);
                }
            });
            if (answer != null) {
                long[] select = answer.getSelectoption();
                if (select != null) {
                    for (int i = 0; i < select.length; i++) {
                        lv.setItemChecked((int) select[i], true);
                    }
                }
            }
        }

        return rootView;
    }

    private void initedit(Activity activity, RichEditor mEditor, String html, final String qcontent) {
        mEditor.setEditorFontSize(50);
        mEditor.setPadding(50, 50, 50, 50);
        WebSettings webSettings = mEditor.getSettings();
        //设置webview推荐使用的窗口
        webSettings.setUseWideViewPort(true);
        //设置webview加载的页面的模式
        webSettings.setLoadWithOverviewMode(true);
        //隐藏webview缩放按钮
        webSettings.setDisplayZoomControls(false);
        // 设置支持javascript脚本
        webSettings.setJavaScriptEnabled(true);
        // 允许访问文件
        webSettings.setAllowFileAccess(true);
        // 设置显示缩放按钮
        webSettings.setBuiltInZoomControls(true);
        // 支持缩放
        webSettings.setSupportZoom(true);
        //主要用于平板，针对特定屏幕代码调整分辨率
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int mDensity = metrics.densityDpi;
        int dens = 240;
        int dens1 = 160;
        int dens2 = 120;
        if (mDensity == dens) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (mDensity == dens1) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        } else if (mDensity == dens2) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
        } else if (mDensity == DisplayMetrics.DENSITY_XHIGH) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (mDensity == DisplayMetrics.DENSITY_TV) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        }
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        mEditor.focusEditor();
        mEditor.setEdit(html);
        mEditor.setEditorAble(false);
//        mQcontent = qcontent;
        mEditor.setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override
            public void onTextChange(String text) {
//                mText = text;
                Answer answer = new Answer();
                answer.setPosition(index);
                answer.setQuestion(text);
                answer.setType(3);
                answer.setAnswers(getAnsOption(text,qcontent));
                ((ExaminationCardActivity2)context).getAnswerMap().put(index, answer);
            }
        });
    }

    private ArrayList<String> getAnsOption(long[] select, ArrayList<QuestionOptionBean> options) {
        ArrayList<String> temp = new ArrayList<>();
        for (int i = 0; i < select.length; i++) {
            QuestionOptionBean optionBean = options.get((int) select[i]);
            if (optionBean != null) {
                temp.add(optionBean.getOptionCode());
            }
        }
        return temp;
    }

    private ArrayList<String> getAnsOption(String text, String qcontent) {
        return Diff_match_patch.getAns(text, qcontent);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        String message = messageEvent.getMessage();
        if (message.equals("next_tm")) {
            int questionType = Integer.valueOf(questionBean.getQuestionType());
            if (questionType == 2) {
                Answer answer = new Answer();
                answer.setPosition(index);
                answer.setQuestion(mText);
                ArrayList<String> as=getAnsOption(mText,mQcontent);
                answer.setAnswers(as);
                ((ExaminationCardActivity2) context).getAnswerMap().put(index, answer);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

}