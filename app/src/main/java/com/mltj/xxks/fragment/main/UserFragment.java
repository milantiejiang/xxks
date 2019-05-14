package com.mltj.xxks.fragment.main;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mltj.xxks.R;
import com.mltj.xxks.activity.ExaminationCardActivity;
import com.mltj.xxks.activity.IntegralRuleActivity;
import com.mltj.xxks.activity.RankingActivity;
import com.mltj.xxks.activity.SettingsActivity;
import com.mltj.xxks.activity.WrongActivity;
import com.mltj.xxks.fragment.BasiceFragment;
import com.mltj.xxks.util.Contents;
import com.mltj.xxks.util.UserSPUtil;

import butterknife.OnClick;

public class UserFragment extends BasiceFragment implements View.OnClickListener {
    TextView userid;
    TextView usserName;
    TextView userCompany;
    TextView userDepment;
    ImageView imgmryl;
    ImageView imgmzyk;
    ImageView imgKs;
    ImageView setting;
    ImageView imgCt;
    ImageView imgPh;
    ImageView imgjfgz;
    ImageView imgjfph;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_user;
    }

    @Override
    protected void initView(View view) {
        userCompany=view.findViewById(R.id.user_company);
        userDepment=view.findViewById(R.id.user_dep);
        imgKs = view.findViewById(R.id.img_ks);
        setting = view.findViewById(R.id.setting);
        userid=view.findViewById(R.id.user_id);
        usserName=view.findViewById(R.id.user_name);
        imgCt=view.findViewById(R.id.img_ct);
        imgPh=view.findViewById(R.id.img_ph);
        imgmryl=view.findViewById(R.id.img_mryl);
        imgmzyk=view.findViewById(R.id.img_mzyk);
        imgjfgz=view.findViewById(R.id.img_jfgz);
        imgjfph=view.findViewById(R.id.img_jfph);
        imgjfph.setOnClickListener(this);
        imgjfgz.setOnClickListener(this);
        imgKs.setOnClickListener(this);
        setting.setOnClickListener(this);
        imgCt.setOnClickListener(this);
        imgPh.setOnClickListener(this);
        imgmryl.setOnClickListener(this);
        imgmzyk.setOnClickListener(this);
        usserName.setText(UserSPUtil.getInstance(getActivity()).getString(Contents.KEY_USER_NAME));
        userid.setText(UserSPUtil.getInstance(getActivity()).getInt(Contents.KEY_USER_ID)+"");
        userDepment.setText(UserSPUtil.getInstance(getActivity()).getString(Contents.KEY_USER_DEPMENT));
        userCompany.setText(UserSPUtil.getInstance(getActivity()).getString(Contents.KEY_USER_COMPANY));
    }

    @OnClick({R.id.img_ks,R.id.img_mryl,R.id.img_mzyk, R.id.setting,R.id.img_ct,R.id.img_ph,R.id.img_jfgz,R.id.img_jfph})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_mryl:
                ExaminationCardActivity.start(getActivity(),14);
                break;
            case R.id.img_mzyk:
                ExaminationCardActivity.start(getActivity(),15);
                break;
            case R.id.img_ks:
                ExaminationCardActivity.start(getActivity(),17);
                break;
            case R.id.setting:
                Intent intent1 = new Intent(getActivity(), SettingsActivity.class);
                getActivity().startActivity(intent1);
                break;
            case R.id.img_ct:
                Intent intent2=new Intent(getActivity(), WrongActivity.class);
                getActivity().startActivity(intent2);
                break;
            case R.id.img_ph:
                Intent intent3=new Intent(getActivity(), RankingActivity.class);
                getActivity().startActivity(intent3);
                break;
            case R.id.img_jfgz:
                Intent intent4=new Intent(getActivity(), IntegralRuleActivity.class);
                getActivity().startActivity(intent4);
                break;
            case R.id.img_jfph:
                break;
            default:
                break;
        }
    }
}
