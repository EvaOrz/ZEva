package cn.com.zwwl.bayuwen.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.com.zwwl.bayuwen.MyApplication;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.GiftAdapter;
import cn.com.zwwl.bayuwen.adapter.JiangZhuangAdapter;
import cn.com.zwwl.bayuwen.api.ChildInfoApi;
import cn.com.zwwl.bayuwen.api.HonorActionApi;
import cn.com.zwwl.bayuwen.api.HonorListApi;
import cn.com.zwwl.bayuwen.api.UploadPicApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.ChildModel;
import cn.com.zwwl.bayuwen.model.CommonModel;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.GiftAndJiangModel;
import cn.com.zwwl.bayuwen.model.UserModel;
import cn.com.zwwl.bayuwen.util.AppValue;
import cn.com.zwwl.bayuwen.util.CalendarTools;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.view.CalendarOptionPopWindow;
import cn.com.zwwl.bayuwen.view.DatePopWindow;
import cn.com.zwwl.bayuwen.view.GenderPopWindow;
import cn.com.zwwl.bayuwen.view.NianjiPopWindow;
import cn.com.zwwl.bayuwen.widget.FetchPhotoManager;
import cn.com.zwwl.bayuwen.widget.MostGridView;

/**
 * 编辑学员信息页面
 */
public class ChildInfoActivity extends BaseActivity {
    private String picturePath;// 头像
    private static final String AVATAR_PIC = "avatar.jpg";
    private File photoFile;
    private ImageView aImg;
    private EditText nameEv, phoneEv, schoolEv;
    private TextView genderTv, birthTv, ruxueTv, nianjiTv, noTv, titleTv;
    private boolean isNeedChangePic = false;
    private boolean isModify = false;// 是否是修改信息

    private TextView deAll, deCancle;
    private ImageView deDo;

    private MostGridView giftGridView;
    private JiangZhuangAdapter adapter;
    private List<GiftAndJiangModel> datas = new ArrayList<>();

    private ChildModel childModel = new ChildModel();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_child);
        picturePath = Environment.getExternalStorageDirectory().getPath() + "/" + AVATAR_PIC;
        initView();
        if (getIntent().getSerializableExtra("ChildInfoActivity_data") != null && getIntent()
                .getSerializableExtra("ChildInfoActivity_data") instanceof ChildModel) {
            childModel = (ChildModel) getIntent().getSerializableExtra("ChildInfoActivity_data");
            initData();
            isModify = true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getHonorList();
    }


    private void getHonorList() {
        new HonorListApi(mContext, 1, childModel.getNo(), new FetchEntryListListener() {
            @Override
            public void setData(List list) {
                datas.clear();
                if (Tools.listNotNull(list)) {
                    datas.addAll(list);
                }
                addLast();
                handler.sendEmptyMessage(6);
            }

            @Override
            public void setError(ErrorMsg error) {

            }
        });

    }

    private void initView() {
        aImg = findViewById(R.id.info_c_aimg);
        nameEv = findViewById(R.id.info_c_nametv);
        genderTv = findViewById(R.id.info_c_sextv);
        phoneEv = findViewById(R.id.info_c_phoneev);
        birthTv = findViewById(R.id.info_p_birthtv);
        ruxueTv = findViewById(R.id.info_p_ruxuetv);
        nianjiTv = findViewById(R.id.info_c_nianjitv);
        schoolEv = findViewById(R.id.info_c_schoolev);
        deAll = findViewById(R.id.delete_all);
        deCancle = findViewById(R.id.delete_cancle);
        deDo = findViewById(R.id.delete_do);
        noTv = findViewById(R.id.info_c_student_no);
        titleTv = findViewById(R.id.info_c_title);

        giftGridView = findViewById(R.id.gift_grid);
        adapter = new JiangZhuangAdapter(mContext);
        giftGridView.setAdapter(adapter);
        giftGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (datas.get(position).isDeleteStatus()) {
                    doDelete(datas.get(position).getId() + "");
                } else {
                    Intent i = new Intent(mContext, JiangZhuangActivity.class);
                    i.putExtra("JiangZhuangActivity_data", datas.get(position));
                    startActivity(i);
                }
            }
        });
        findViewById(R.id.info_c_back).setOnClickListener(this);
        findViewById(R.id.info_c_avatar).setOnClickListener(this);
        findViewById(R.id.info_c_sex).setOnClickListener(this);
        findViewById(R.id.info_c_ruxue).setOnClickListener(this);
        findViewById(R.id.info_c_birth).setOnClickListener(this);
        findViewById(R.id.info_c_nianji).setOnClickListener(this);
        findViewById(R.id.info_c_save).setOnClickListener(this);
        deAll.setOnClickListener(this);
        deCancle.setOnClickListener(this);
        deDo.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.info_c_back:
                finish();
                break;
            case R.id.info_c_avatar:
                doFecthPicture();
                break;
            case R.id.info_c_sex:
                hideJianpan();
                new GenderPopWindow(this, new GenderPopWindow.ChooseGenderListener() {
                    @Override
                    public void choose(int gender) {// 1男2女0保密
                        if (gender == 0) {
                            childModel.setGender(2);
                        } else if (gender == 2) {
                            childModel.setGender(0);
                        } else
                            childModel.setGender(1);
                        handler.sendEmptyMessage(5);
                    }
                });
                break;
            case R.id.info_c_nianji:// 年级
                hideJianpan();
                new NianjiPopWindow(mContext, new NianjiPopWindow.MyNianjiPickListener() {
                    @Override
                    public void onNianjiPick(String string) {
                        childModel.setGrade(string);
                        handler.sendEmptyMessage(4);
                    }
                });
                break;
            case R.id.info_c_birth:// 出生年月
                hideJianpan();
                int y = CalendarTools.getCurrentYear(), m = CalendarTools.getCurrentMonth(), d =
                        CalendarTools.getCurrentDay();
                if (!TextUtils.isEmpty(childModel.getBirthday())) {
                    Calendar c = CalendarTools.fromStringToca(childModel.getBirthday());
                    if (c != null) {
                        y = c.get(Calendar.YEAR);
                        m = c.get(Calendar.MONTH) + 1;
                        d = c.get(Calendar.DATE);
                    }
                }
                new DatePopWindow(mContext, true, y, m, d, new DatePopWindow
                        .MyDatePickListener() {
                    @Override
                    public void onDatePick(int year, int month, int day) {
                        childModel.setBirthday(year + "-" + month + "-" + day);
                        handler.sendEmptyMessage(0);
                    }
                });

                break;

            case R.id.info_c_ruxue:// 入学年月
                hideJianpan();
                int y1 = CalendarTools.getCurrentYear(), m1 = CalendarTools.getCurrentMonth(), d1 =
                        CalendarTools.getCurrentDay();
                if (!TextUtils.isEmpty(childModel.getAdmission_time())) {
                    Calendar c = CalendarTools.fromStringToca(childModel.getAdmission_time());
                    if (c != null) {
                        y1 = c.get(Calendar.YEAR);
                        m1 = c.get(Calendar.MONTH) + 1;
                        d1 = c.get(Calendar.DATE);
                    }
                }
                new DatePopWindow(mContext, false, y1, m1, d1, new
                        DatePopWindow.MyDatePickListener() {
                            @Override
                            public void onDatePick(int year, int month, int day) {
                                childModel.setAdmission_time(year + "-" + month);
                                handler.sendEmptyMessage(2);
                            }
                        });

                break;
            case R.id.info_c_save:
                String na = nameEv.getText().toString();
                String phone = phoneEv.getText().toString();
                String grade = nianjiTv.getText().toString();
                String school = schoolEv.getText().toString();
                if (TextUtils.isEmpty(na)) {
                    showToast("姓名不能为空");
                } else if (TextUtils.isEmpty(phone)) {
                    showToast("紧急联系人不能为空");
                } else if (TextUtils.isEmpty(grade)) {
                    showToast("年级不能为空");
                } else {
                    if (AppValue.checkIsPhone(mContext, phone)) {
                        childModel.setName(na);
                        childModel.setSchool(school);
                        childModel.setTel(phone);

                        showLoadingDialog(true);
                        if (isNeedChangePic) {
                            uploadPic(photoFile);
                        } else
                            commit();
                    }
                }
                break;

            case R.id.delete_all:
                String deleteIds = "";
                for (GiftAndJiangModel g : datas) {
                    if (g.getId() != -1)
                        deleteIds += g.getId() + ",";
                }
                doDelete(deleteIds);
                deDo.setVisibility(View.GONE);
                deCancle.setVisibility(View.GONE);
                deAll.setVisibility(View.GONE);
                datas.clear();
                addLast();
                handler.sendEmptyMessage(7);
                break;
            case R.id.delete_do:
                deDo.setVisibility(View.GONE);
                deCancle.setVisibility(View.VISIBLE);
                deAll.setVisibility(View.VISIBLE);
                setCheckStatus(true);

                break;
            case R.id.delete_cancle://
                deDo.setVisibility(View.VISIBLE);
                deCancle.setVisibility(View.GONE);
                deAll.setVisibility(View.GONE);
                setCheckStatus(false);
                break;

        }
    }

    private void addLast() {
        GiftAndJiangModel last = new GiftAndJiangModel();
        last.setId(-1);
        last.setStudent_no(childModel.getNo());
        datas.add(last);
    }

    /**
     * 上传头像
     *
     * @param file
     */
    private void uploadPic(File file) {
        new UploadPicApi(this, file, new FetchEntryListener() {
            @Override
            public void setData(Entry entry) {
                if (entry != null && entry instanceof CommonModel) {
                    childModel.setPic(((CommonModel) entry).getUrl());
                    commit();

                }
            }

            @Override
            public void setError(ErrorMsg error) {
                if (error != null)
                    showToast(TextUtils.isEmpty(error.getDesc()) ? mContext.getResources()
                            .getString(R.string.upload_faild) : error
                            .getDesc());
            }
        });
    }

    /**
     * 提交
     */
    private void commit() {
        new ChildInfoApi(this, childModel, isModify, new FetchEntryListener() {
            @Override
            public void setData(Entry entry) {
                showLoadingDialog(false);
                if (entry != null && entry instanceof ChildModel) {
                    MyApplication.loginStatusChange = true;
                    finish();
                }
            }

            @Override
            public void setError(ErrorMsg error) {
                showLoadingDialog(false);
                if (error != null)
                    showToast(error.getDesc());
            }
        });
    }

    protected void doFecthPicture() {
        FetchPhotoManager fetchPhotoManager = new FetchPhotoManager(this, picturePath);
        fetchPhotoManager.doFecthPicture();
    }

    @Override
    protected void initData() {
        if (!TextUtils.isEmpty(childModel.getPic()))
            Glide.with(this).load(childModel.getPic()).into(aImg);
        nameEv.setText(childModel.getName());
        genderTv.setText(childModel.getSexTxt(childModel.getGender()));
        phoneEv.setText(childModel.getTel());
        birthTv.setText(childModel.getBirthday());
        ruxueTv.setText(childModel.getAdmission_time());
        nianjiTv.setText(childModel.getGrade());
        noTv.setText(childModel.getNo());
        titleTv.setText(childModel.getName());
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:// 出生年月日
                    birthTv.setText(childModel.getBirthday());
                    break;
                case 1:// 重新加载用户信息
                    initData();
                    break;
                case 2:// 入学年月日
                    ruxueTv.setText(childModel.getAdmission_time());
                    break;
                case 3:
                    isNeedChangePic = true;
                    Glide.with(mContext).load(photoFile).into(aImg);
                    break;
                case 4:// 年级选择
                    nianjiTv.setText(childModel.getGrade());
                    break;
                case 5:// 性别选择
                    genderTv.setText(childModel.getSexTxt(childModel.getGender()));
                    break;
                case 6:// 初始化荣誉数据
                    if (datas.size() > 1) {
                        deDo.setVisibility(View.VISIBLE);
                    } else {
                        deDo.setVisibility(View.GONE);
                    }
                    deCancle.setVisibility(View.GONE);
                    deAll.setVisibility(View.GONE);
                    adapter.setData(datas);
                    break;
                case 7:// 重置荣誉数据状态
                    adapter.setData(datas);
                    break;

            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == FetchPhotoManager.REQUEST_CAMERA) {
//                avatarBit = data.getParcelableExtra("data");
                photoFile = new File(picturePath);
            } else if (requestCode == FetchPhotoManager.REQUEST_GALLERY) {
                if (data != null) {
                    String[] projection = {MediaStore.Video.Media.DATA};
                    Cursor cursor = managedQuery(data.getData(), projection, null, null, null);
                    int column_index = cursor
                            .getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
                    cursor.moveToFirst();
                    photoFile = new File(cursor.getString(column_index));

                }
            }
            handler.sendEmptyMessage(3);

        } else if (resultCode == RESULT_CANCELED) {
            showToast(R.string.cancle);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void setCheckStatus(boolean isCheck) {
        for (int i = 0; i < datas.size(); i++) {
            datas.get(i).setDeleteStatus(isCheck);
        }
        handler.sendEmptyMessage(7);
    }

    private void doDelete(String ids) {
        showLoadingDialog(true);
        new HonorActionApi(mContext, ids, new FetchEntryListener() {
            @Override
            public void setData(Entry entry) {

            }

            @Override
            public void setError(ErrorMsg error) {
                showLoadingDialog(false);
                if (error == null) {
                    getHonorList();
                    showToast("删除成功");
                } else {
                    showToast(error.getDesc());
                }
            }
        });
    }
}
