package edu.aku.hassannaqvi.shrc_camps.ui.sections;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;
import com.validatorcrawler.aliazaz.Clear;
import com.validatorcrawler.aliazaz.Validator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.aku.hassannaqvi.shrc_camps.R;
import edu.aku.hassannaqvi.shrc_camps.contracts.MHContract;
import edu.aku.hassannaqvi.shrc_camps.core.MainApp;
import edu.aku.hassannaqvi.shrc_camps.database.DatabaseHelper;
import edu.aku.hassannaqvi.shrc_camps.databinding.ActivityMobileHealthR2Binding;
import edu.aku.hassannaqvi.shrc_camps.models.Camps;
import edu.aku.hassannaqvi.shrc_camps.models.MobileHealth;
import edu.aku.hassannaqvi.shrc_camps.ui.MainActivity;
import edu.aku.hassannaqvi.shrc_camps.utils.AppUtilsKt;
import edu.aku.hassannaqvi.shrc_camps.utils.EndSectionActivity;
import edu.aku.hassannaqvi.shrc_camps.utils.shared.SharedStorage;

import static edu.aku.hassannaqvi.shrc_camps.core.MainApp.mobileHealth;
import static edu.aku.hassannaqvi.shrc_camps.utils.DateUtilsKt.convertDateFormat;
import static edu.aku.hassannaqvi.shrc_camps.utils.extension.ActivityExtKt.gotoActivity;
import static edu.aku.hassannaqvi.shrc_camps.utils.extension.ActivityExtKt.gotoActivityWithPutExtra;

public class SectionMobileHealthR2 extends AppCompatActivity implements EndSectionActivity {

    private final boolean AllVaccinationsViewed = false;
    private String patientType;
    private List<String> campNo;
    private DatabaseHelper db;
    ActivityMobileHealthR2Binding bi;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_mobile_health_r2);
        bi.setCallback(this);
        setSupportActionBar(bi.toolbar);

        /*
         * Get camp data and set it to xml
         * */
        Camps camp = new Gson().fromJson(SharedStorage.INSTANCE.getSelectedCampData(this), Camps.class);
        bi.setCamps(camp);
        db = MainApp.appInfo.dbHelper;
        //populateSpinner(camp.getIdCamp());  // Populate Doctors' Name
        bi.ss101.setMinDate(convertDateFormat(camp.getPlan_date()));
        setupSkips();
    }


    private void setupSkips() {

/*        bi.ss108.setOnCheckedChangeListener((radioGroup, i) -> {
            Clear.clearAllFields(bi.fldGrpCVss109);
            Clear.clearAllFields(bi.fldGrpCVvs301);

            if (i == bi.ss108a.getId()) {
                Clear.clearAllFields(bi.fldGrpCVss109);
                bi.fldGrpCVss109.setVisibility(View.GONE);
                Clear.clearAllFields(bi.fldGrpCVvs301);
                bi.fldGrpCVvs301.setVisibility(View.GONE);
            } else {
                if (TextUtils.isEmpty(bi.ss107y.getText().toString())) {
                    return;
                } else if (Integer.parseInt(bi.ss107y.getText().toString()) >= 14) {
                    Clear.clearAllFields(bi.fldGrpCVss109);
                    bi.fldGrpCVss109.setVisibility(View.VISIBLE);
                    Clear.clearAllFields(bi.fldGrpCVvs301);
                    bi.fldGrpCVvs301.setVisibility(View.VISIBLE);
                }
            }
        });*/

        bi.ss108.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == bi.ss108a.getId()) {
                Clear.clearAllFields(bi.fldGrpCVss109);
                bi.fldGrpCVss109.setVisibility(View.GONE);
                Clear.clearAllFields(bi.fldGrpCVvs301);
                bi.fldGrpCVvs301.setVisibility(View.GONE);
            } else {
                if (TextUtils.isEmpty(bi.ss107y.getText().toString())) {
                    return;
                } else if (Integer.parseInt(bi.ss107y.getText().toString()) >= 14) {
                    bi.fldGrpCVss109.setVisibility(View.VISIBLE);
                    bi.fldGrpCVvs301.setVisibility(View.VISIBLE);
                }
            }
        });


        bi.vs307.setOnCheckedChangeListener((radioGroup, i) -> Clear.clearAllFields(bi.fldGrpCVvs308));

        bi.vs30699.setOnCheckedChangeListener((compoundButton, b) -> Clear.clearAllFields(bi.vs306check, !b));
        bi.ss11199.setOnCheckedChangeListener((compoundButton, b) -> Clear.clearAllFields(bi.ss111check, !b));
        bi.pc20199.setOnCheckedChangeListener((compoundButton, b) -> Clear.clearAllFields(bi.pc201check, !b));
        bi.di20299.setOnCheckedChangeListener((compoundButton, b) -> Clear.clearAllFields(bi.di202check, !b));
        bi.me20399.setOnCheckedChangeListener((compoundButton, b) -> Clear.clearAllFields(bi.me203check, !b));

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            bi.llscrollviewmh26.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View view, int i, int i1, int i2, int i3) {

                    bi.llscrollviewmh26.getChildAt(bi.llscrollviewmh26.getChildCount() - 1);
                    int diff = (bi.llscrollviewmh26.getRight() - (bi.llscrollviewmh26.getWidth() + bi.llscrollviewmh26.getScrollX()));
                    if (diff == 0) {
                        AllVaccinationsViewed = true;
                    }
                }
            });
        }*/

/*
        bi.chkWeight.setOnCheckedChangeListener((compoundButton, b) -> Clear.clearAllFields(bi.mh012, !b));
        bi.chkHeight.setOnCheckedChangeListener((compoundButton, b) -> Clear.clearAllFields(bi.mh015, !b));
        bi.chkMUAC.setOnCheckedChangeListener((compoundButton, b) -> Clear.clearAllFields(bi.mh016, !b));
        bi.mh01704.setOnCheckedChangeListener((compoundButton, b) -> Clear.clearAllFields(bi.llmh020, !b));
        bi.mh017097.setOnCheckedChangeListener((compoundButton, b) -> Clear.clearAllFields(bi.mh017check, !b));
        bi.mh018097.setOnCheckedChangeListener((compoundButton, b) -> Clear.clearAllFields(bi.mh018check, !b));
        bi.mh019097.setOnCheckedChangeListener((compoundButton, b) -> Clear.clearAllFields(bi.mh019check, !b));


        //TODO:
        bi.mh025.setOnCheckedChangeListener((radioGroup, i) -> {
            //Log.d("TAG", "setupSkips:1 "+bi.mh02202.isChecked()+"|"+bi.mh02501.isChecked());
            if (bi.mh02202.isChecked() && bi.mh02501.isChecked()) {
                // Log.d("TAG", "setupSkips:2 ");
                openWarningDialog(this, "Error", "Answer conflicts with Q. MH022", bi.mh025);
                //bi.mh025.clearCheck();
            }
        });
        bi.mh022.setOnCheckedChangeListener((radioGroup, i) -> {
            if (bi.mh02202.isChecked() && bi.mh02501.isChecked()) {
                openWarningDialog(this, "Error", "Answer conflicts with Q. MH025", bi.mh022);
            }
        });

        bi.mh024.setOnCheckedChangeListener((radioGroup, i) -> {
            if (bi.mh02302.isChecked() && bi.mh02401.isChecked()) {
                openWarningDialog(this, "Error", "Answer conflicts with Q. MH023", bi.mh024);
            }
        });

        bi.mh023.setOnCheckedChangeListener((radioGroup, i) -> {
            if (bi.mh02302.isChecked() && bi.mh02401.isChecked()) {
                openWarningDialog(this, "Error", "Answer conflicts with Q. MH024", bi.mh023);
            }
        });
        //bi.mh023.setOnCheckedChangeListener((radioGroup, i) -> Clear.clearRadioGroup(bi.mh024, i != bi.mh02302.getId()));


        bi.mh027b.setOnCheckedChangeListener((radioGroup, i) -> {
            bi.mh02601.setTag(null);
            bi.rgmh02601.setTag(null);
            bi.rgmh02602.setTag(null);
            bi.rgmh02603.setTag(null);
            bi.rgmh02604.setTag(null);
            bi.rgmh02605.setTag(null);
            bi.mh026019.setTag(null);
            bi.mh026021.setTag(null);
            bi.mh026022.setTag(null);
            Clear.clearAllFields(bi.fldGrpCVmh026);
            Clear.clearAllFields(bi.fldGrpCVmh027);
            Clear.clearAllFields(bi.fldGrpCVmh027a);
            bi.mh02601.setChecked(false);
            bi.mh026019.setChecked(false);
            bi.mh026021.setChecked(false);
            bi.mh026022.setChecked(false);
            bi.fldGrpCVmh026.setVisibility(View.GONE);
            bi.fldGrpCVmh027.setVisibility(View.GONE);
            bi.fldGrpCVmh027a.setVisibility(View.VISIBLE);
            if (i == bi.mh027b02.getId()) {
                bi.fldGrpCVmh026.setVisibility(View.VISIBLE);
                bi.fldGrpCVmh027.setVisibility(View.VISIBLE);
                //bi.fldGrpCVmh027a.setVisibility(View.VISIBLE);
            }*//* else if (i == bi.mh027b01.getId()) {
                bi.fldGrpCVmh027a.setVisibility(View.VISIBLE);
            }*//*
        });

        setTags(bi.mh02601, new View[]{bi.rgmh02601, bi.rgmh02602, bi.rgmh02603, bi.rgmh02604, bi.rgmh02605, bi.rgmh02606, bi.mh026021, bi.mh026022});
        setTags(bi.rgmh02601, new View[]{bi.mh02601, bi.rgmh02602, bi.rgmh02603, bi.rgmh02604, bi.rgmh02605, bi.rgmh02606, bi.mh026021, bi.mh026022});
        setTags(bi.rgmh02602, new View[]{bi.mh02601, bi.rgmh02601, bi.rgmh02603, bi.rgmh02604, bi.rgmh02605, bi.rgmh02606, bi.mh026021, bi.mh026022});
        setTags(bi.rgmh02603, new View[]{bi.mh02601, bi.rgmh02601, bi.rgmh02602, bi.rgmh02604, bi.rgmh02605, bi.rgmh02606, bi.mh026021, bi.mh026022});
        setTags(bi.rgmh02604, new View[]{bi.mh02601, bi.rgmh02601, bi.rgmh02602, bi.rgmh02603, bi.rgmh02605, bi.rgmh02606, bi.mh026021, bi.mh026022});
        setTags(bi.rgmh02605, new View[]{bi.mh02601, bi.rgmh02601, bi.rgmh02602, bi.rgmh02603, bi.rgmh02604, bi.rgmh02606, bi.mh026021, bi.mh026022});
        setTags(bi.rgmh02606, new View[]{bi.mh02601, bi.rgmh02601, bi.rgmh02602, bi.rgmh02603, bi.rgmh02604, bi.rgmh02605, bi.mh026021, bi.mh026022});
        setTags(bi.mh026021, new View[]{bi.mh02601, bi.rgmh02601, bi.rgmh02602, bi.rgmh02603, bi.rgmh02604, bi.rgmh02605, bi.rgmh02606, bi.mh026022});
        setTags(bi.mh026022, new View[]{bi.mh02601, bi.rgmh02601, bi.rgmh02602, bi.rgmh02603, bi.rgmh02604, bi.rgmh02605, bi.rgmh02606, bi.mh026021});

        bi.rgmh02603.setOnCheckedChangeListener((radioGroup, i) -> {
            Clear.clearAllFields(bi.fldGrpCVmh027a);
            bi.fldGrpCVmh027a.setVisibility(View.GONE);
        });*/


    }

    public void ss107yOnTextChanged(CharSequence s, int start, int before, int count) {
        if (TextUtils.isEmpty(bi.ss107y.getText().toString()))
            return;

        Clear.clearAllFields(bi.fldGrpCVss108);

        int age = Integer.parseInt(bi.ss107y.getText().toString());

        if (age >= 5) {
            Clear.clearAllFields(bi.fldGrpCVdi202);
            bi.fldGrpCVdi202.setVisibility(View.GONE);
            Clear.clearAllFields(bi.fldGrpU5);
            bi.fldGrpU5.setVisibility(View.GONE);
        } else {
            bi.fldGrpCVdi202.setVisibility(View.VISIBLE);
            bi.fldGrpU5.setVisibility(View.VISIBLE);
        }

        if (age < 14) {
            Clear.clearAllFields(bi.fldGrpCVss109);
            bi.fldGrpCVss109.setVisibility(View.GONE);
            Clear.clearAllFields(bi.fldGrpCVvs301);
            bi.fldGrpCVvs301.setVisibility(View.GONE);
        } else {
            bi.fldGrpCVss109.setVisibility(View.VISIBLE);
            bi.fldGrpCVvs301.setVisibility(View.VISIBLE);
        }
    }

    public void setTags(RadioGroup rg, View[] views) {
        rg.setOnCheckedChangeListener((radioGroup, i) -> {
            for (View view : views) view.setTag("-1");
        });
    }

    public void setTags(RadioButton rb, View[] views) {
        rb.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) for (View view : views) view.setTag("-1");
        });
    }


    /*public void ageZeroCheck(CharSequence s, int i, int i1, int i2) {
        if (TextUtils.isEmpty(bi.mh09d.getText()) || TextUtils.isEmpty(bi.mh09m.getText()) || TextUtils.isEmpty(bi.mh09y.getText()))
            return;
        int check = Integer.parseInt(bi.mh09d.getText().toString()) + Integer.parseInt(bi.mh09m.getText().toString()) + Integer.parseInt(bi.mh09y.getText().toString());
        if (check == 0) openWarningDialog(this, "Error", "All Fields Can't be ZERO", new EditTextPicker[]{bi.mh09y, bi.mh09m, bi.mh09d});
        segregate();
    }*/


    public void segregateByAge(CharSequence s, int i, int i1, int i2) {
        segregate();
    }


    public void segregateByGender(RadioGroup radioGroup, int i) {
        segregate();
    }


    public void segregate() {
       /* if (TextUtils.isEmpty(bi.mh09d.getText()) || TextUtils.isEmpty(bi.mh09m.getText()) || TextUtils.isEmpty(bi.mh09y.getText()) || bi.mh010.getCheckedRadioButtonId() == -1)
            return;
        int age = Integer.parseInt(bi.mh09d.getText().toString()) + (Integer.parseInt(bi.mh09m.getText().toString()) * 29) + (Integer.parseInt(bi.mh09y.getText().toString()) * 365);

       *//* bi.mh012.setMinvalue(15f);
        bi.mh012.setMaxvalue(250f);*//*
        bi.mh012.setMask("###.#");
        bi.mh012.setHint("###.#");
        Clear.clearAllFields(bi.fldGrpCVmh010a);
        Clear.clearAllFields(bi.fldGrpCVmh017);
        Clear.clearAllFields(bi.llmh020);
        Clear.clearAllFields(bi.fldGrpCVmh015);
        Clear.clearAllFields(bi.fldGrpCVmh016);
        Clear.clearAllFields(bi.fldGrpCVmh018);
        Clear.clearAllFields(bi.llchild);
        bi.mh02601.setChecked(false);
        bi.mh026019.setChecked(false);
        bi.fldGrpCVmh010a.setVisibility(View.GONE);
        bi.fldGrpCVmh017.setVisibility(View.GONE);
        bi.llmh020.setVisibility(View.GONE);
        bi.fldGrpCVmh015.setVisibility(View.GONE);
        bi.fldGrpCVmh016.setVisibility(View.GONE);
        bi.fldGrpCVmh018.setVisibility(View.GONE);
        bi.llchild.setVisibility(View.GONE);
        patientType = "General";

        if (age >= 5110 && age < 18250 && bi.mh01002.isChecked()) {
            bi.fldGrpCVmh017.setVisibility(View.VISIBLE);
            bi.llmh020.setVisibility(View.VISIBLE);
            patientType = "MWRA";
        }
        if (age >= 5110) {
            bi.fldGrpCVmh010a.setVisibility(View.VISIBLE);
        }
        if (age <= 1825) {
            bi.fldGrpCVmh015.setVisibility(View.VISIBLE);
            bi.fldGrpCVmh016.setVisibility(View.VISIBLE);
            bi.fldGrpCVmh018.setVisibility(View.VISIBLE);
            bi.llchild.setVisibility(View.VISIBLE);
            bi.mh012.setMinvalue(0.9f);
            bi.mh012.setMaxvalue(58f);
            bi.mh012.setMask("###.#");
            bi.mh012.setHint("###.#");
            patientType = "Child";
        }*/
    }


    private boolean UpdateDB() {
        long updcount = db.addMH(mobileHealth);
        mobileHealth.setId(String.valueOf(updcount));
        if (updcount > 0) {
            mobileHealth.setUid(mobileHealth.getDeviceId() + mobileHealth.getId());
            long count = db.updatesMHColumn(MHContract.MHTable.COLUMN_UID, mobileHealth.getUid());
            if (count > 0)
                count = db.updatesMHColumn(MHContract.MHTable.COLUMN_SA, mobileHealth.sAtoString());
            if (count > 0) return true;
            else {
                Toast.makeText(this, "SORRY! Failed to update DB", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(this, "SORRY! Failed to update DB", Toast.LENGTH_SHORT).show();
            return false;
        }

    }


    private void saveDraft() {

        mobileHealth = new MobileHealth();
        mobileHealth.setSysDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date().getTime()));
        mobileHealth.setUserName(MainApp.user.getUserName());
        mobileHealth.setDeviceId(MainApp.appInfo.getDeviceID());
        mobileHealth.setDeviceTag(MainApp.appInfo.getTagName());
        mobileHealth.setAppver(MainApp.appInfo.getAppVersion());


        mobileHealth.setSs101(bi.ss101.getText().toString());
        mobileHealth.setSs102(bi.ss102.getText().toString());
        mobileHealth.setSs103(bi.ss103.getText().toString());
        mobileHealth.setSs104(bi.ss104.getText().toString());
        mobileHealth.setSs105(bi.ss105.getText().toString());
        mobileHealth.setSs106(bi.ss106.getText().toString());
        mobileHealth.setSs107(bi.ss107y.getText().toString() + "-" + bi.ss107m.getText().toString() + "-" + bi.ss107d.getText().toString());
        mobileHealth.setSs107y(bi.ss107y.getText().toString());
        mobileHealth.setSs107m(bi.ss107m.getText().toString());
        mobileHealth.setSs107d(bi.ss107d.getText().toString());
        mobileHealth.setSs108(bi.ss108a.isChecked() ? "1"
                : bi.ss108b.isChecked() ? "2"
                : "-1");
        mobileHealth.setSs109(bi.ss109a.isChecked() ? "1"
                : bi.ss109b.isChecked() ? "2"
                : bi.ss10999.isChecked() ? "99"
                : "-1");
        mobileHealth.setSs110(bi.ss110.getText().toString());
        mobileHealth.setSs111a(bi.ss111a.isChecked() ? "1" : "-1");
        mobileHealth.setSs111b(bi.ss111b.isChecked() ? "2" : "-1");
        mobileHealth.setSs111c(bi.ss111c.isChecked() ? "3" : "-1");
        mobileHealth.setSs111d(bi.ss111d.isChecked() ? "4" : "-1");
        mobileHealth.setSs11199(bi.ss11199.isChecked() ? "99" : "-1");
        mobileHealth.setPc20101(bi.pc20101.isChecked() ? "1" : "-1");
        mobileHealth.setPc20102(bi.pc20102.isChecked() ? "2" : "-1");
        mobileHealth.setPc20103(bi.pc20103.isChecked() ? "3" : "-1");
        mobileHealth.setPc20104(bi.pc20104.isChecked() ? "4" : "-1");
        mobileHealth.setPc20105(bi.pc20105.isChecked() ? "5" : "-1");
        mobileHealth.setPc20106(bi.pc20106.isChecked() ? "6" : "-1");
        mobileHealth.setPc20107(bi.pc20107.isChecked() ? "7" : "-1");
        mobileHealth.setPc20108(bi.pc20108.isChecked() ? "8" : "-1");
        mobileHealth.setPc20109(bi.pc20109.isChecked() ? "9" : "-1");
        mobileHealth.setPc20110(bi.pc20110.isChecked() ? "10" : "-1");
        mobileHealth.setPc20111(bi.pc20111.isChecked() ? "11" : "-1");
        mobileHealth.setPc20112(bi.pc20112.isChecked() ? "12" : "-1");
        mobileHealth.setPc20113(bi.pc20113.isChecked() ? "13" : "-1");
        mobileHealth.setPc20114(bi.pc20114.isChecked() ? "14" : "-1");
        mobileHealth.setPc20115(bi.pc20115.isChecked() ? "15" : "-1");
        mobileHealth.setPc20116(bi.pc20116.isChecked() ? "16" : "-1");
        mobileHealth.setPc20117(bi.pc20117.isChecked() ? "17" : "-1");
        mobileHealth.setPc20118(bi.pc20118.isChecked() ? "18" : "-1");
        mobileHealth.setPc20119(bi.pc20119.isChecked() ? "19" : "-1");
        mobileHealth.setPc20196(bi.pc20196.isChecked() ? "20" : "-1");
        mobileHealth.setPc20196x(bi.pc20196x.getText().toString());
        mobileHealth.setPc20199(bi.pc20199.isChecked() ? "99" : "-1");
        mobileHealth.setDi20201(bi.di20201.isChecked() ? "1" : "-1");
        mobileHealth.setDi20202(bi.di20202.isChecked() ? "2" : "-1");
        mobileHealth.setDi20203(bi.di20203.isChecked() ? "3" : "-1");
        mobileHealth.setDi20204(bi.di20204.isChecked() ? "4" : "-1");
        mobileHealth.setDi20205(bi.di20205.isChecked() ? "5" : "-1");
        mobileHealth.setDi20206(bi.di20206.isChecked() ? "6" : "-1");
        mobileHealth.setDi20207(bi.di20207.isChecked() ? "7" : "-1");
        mobileHealth.setDi20208(bi.di20208.isChecked() ? "8" : "-1");
        mobileHealth.setDi20209(bi.di20209.isChecked() ? "9" : "-1");
        mobileHealth.setDi20210(bi.di20210.isChecked() ? "10" : "-1");
        mobileHealth.setDi20211(bi.di20211.isChecked() ? "11" : "-1");
        mobileHealth.setDi20212(bi.di20212.isChecked() ? "12" : "-1");
        mobileHealth.setDi20213(bi.di20213.isChecked() ? "13" : "-1");
        mobileHealth.setDi20214(bi.di20214.isChecked() ? "14" : "-1");
        mobileHealth.setDi20215(bi.di20215.isChecked() ? "15" : "-1");
        mobileHealth.setDi20216(bi.di20216.isChecked() ? "16" : "-1");
        mobileHealth.setDi20217(bi.di20217.isChecked() ? "17" : "-1");
        mobileHealth.setDi20218(bi.di20218.isChecked() ? "18" : "-1");
        mobileHealth.setDi20219(bi.di20219.isChecked() ? "19" : "-1");
        mobileHealth.setDi20296(bi.di20296.isChecked() ? "96" : "-1");
        mobileHealth.setDi20296x(bi.di20296x.getText().toString());
        mobileHealth.setDi20299(bi.di20299.isChecked() ? "99" : "-1");
        mobileHealth.setMe20301(bi.me20301.isChecked() ? "1" : "-1");
        mobileHealth.setMe20302(bi.me20302.isChecked() ? "2" : "-1");
        mobileHealth.setMe20303(bi.me20303.isChecked() ? "3" : "-1");
        mobileHealth.setMe20304(bi.me20304.isChecked() ? "4" : "-1");
        mobileHealth.setMe20305(bi.me20305.isChecked() ? "5" : "-1");
        mobileHealth.setMe20306(bi.me20306.isChecked() ? "6" : "-1");
        mobileHealth.setMe20307(bi.me20307.isChecked() ? "7" : "-1");
        mobileHealth.setMe20308(bi.me20308.isChecked() ? "8" : "-1");
        mobileHealth.setMe20309(bi.me20309.isChecked() ? "9" : "-1");
        mobileHealth.setMe20310(bi.me20310.isChecked() ? "10" : "-1");
        mobileHealth.setMe20311(bi.me20311.isChecked() ? "11" : "-1");
        mobileHealth.setMe20312(bi.me20312.isChecked() ? "12" : "-1");
        mobileHealth.setMe20313(bi.me20313.isChecked() ? "13" : "-1");
        mobileHealth.setMe20314(bi.me20314.isChecked() ? "14" : "-1");
        mobileHealth.setMe20315(bi.me20315.isChecked() ? "15" : "-1");
        mobileHealth.setMe20316(bi.me20316.isChecked() ? "16" : "-1");
        mobileHealth.setMe20317(bi.me20317.isChecked() ? "17" : "-1");
        mobileHealth.setMe20318(bi.me20318.isChecked() ? "18" : "-1");
        mobileHealth.setMe20319(bi.me20319.isChecked() ? "19" : "-1");
        mobileHealth.setMe20320(bi.me20320.isChecked() ? "20" : "-1");
        mobileHealth.setMe20321(bi.me20321.isChecked() ? "21" : "-1");
        mobileHealth.setMe20322(bi.me20322.isChecked() ? "22" : "-1");
        mobileHealth.setMe20323(bi.me20323.isChecked() ? "23" : "-1");
        mobileHealth.setMe20324(bi.me20324.isChecked() ? "24" : "-1");
        mobileHealth.setMe20396(bi.me20396.isChecked() ? "96" : "-1");
        mobileHealth.setMe20396x(bi.me20396x.getText().toString());
        mobileHealth.setMe20399(bi.me20399.isChecked() ? "99" : "-1");
        mobileHealth.setVs301(bi.vs301a.isChecked() ? "1"
                : bi.vs301b.isChecked() ? "2"
                : bi.vs30199.isChecked() ? "99"
                : "-1");
        mobileHealth.setVs302(bi.vs302a.isChecked() ? "1"
                : bi.vs302b.isChecked() ? "2"
                : bi.vs30299.isChecked() ? "99"
                : "-1");
        mobileHealth.setVs303(bi.vs303a.isChecked() ? "1"
                : bi.vs303b.isChecked() ? "2"
                : bi.vs30399.isChecked() ? "99"
                : "-1");
        mobileHealth.setVs304(bi.vs304a.isChecked() ? "1"
                : bi.vs304b.isChecked() ? "2"
                : bi.vs304c.isChecked() ? "3"
                : bi.vs304d.isChecked() ? "4"
                : bi.vs30499.isChecked() ? "99"
                : "-1");
        mobileHealth.setVs305(bi.vs305a.isChecked() ? "1"
                : bi.vs305b.isChecked() ? "2"
                : bi.vs305c.isChecked() ? "3"
                : bi.vs305d.isChecked() ? "4"
                : bi.vs30599.isChecked() ? "99"
                : "-1");
        mobileHealth.setVs306a(bi.vs306a.isChecked() ? "1" : "-1");
        mobileHealth.setVs306b(bi.vs306b.isChecked() ? "2" : "-1");
        mobileHealth.setVs306c(bi.vs306c.isChecked() ? "3" : "-1");
        mobileHealth.setVs306d(bi.vs306d.isChecked() ? "4" : "-1");
        mobileHealth.setVs306e(bi.vs306e.isChecked() ? "5" : "-1");
        mobileHealth.setVs306f(bi.vs306f.isChecked() ? "6" : "-1");
        mobileHealth.setVs306g(bi.vs306g.isChecked() ? "7" : "-1");
        mobileHealth.setVs306i(bi.vs306i.isChecked() ? "8" : "-1");
        mobileHealth.setVs30699(bi.vs30699.isChecked() ? "99" : "-1");
        mobileHealth.setVs307(bi.vs307a.isChecked() ? "1"
                : bi.vs307b.isChecked() ? "2"
                : bi.vs30799.isChecked() ? "99"
                : "-1");
        mobileHealth.setVs308(bi.vs308a.isChecked() ? "1"
                : bi.vs308b.isChecked() ? "2"
                : "-1");

    }


    public void BtnContinue(View view) {
        if (!formValidation()) return;
        saveDraft();
        if (UpdateDB()) {
            Toast.makeText(this, "Patient Added", Toast.LENGTH_SHORT).show();
            finish();
            gotoActivityWithPutExtra(this, SectionMobileHealthR2.class, "complete", true);
        }
    }


    private boolean formValidation() {

/*

        if (!TextUtils.isEmpty(bi.mh09d.getText()) || !TextUtils.isEmpty(bi.mh09m.getText()) || !TextUtils.isEmpty(bi.mh09y.getText())) {
            int check = Integer.parseInt(bi.mh09d.getText().toString()) + Integer.parseInt(bi.mh09m.getText().toString()) + Integer.parseInt(bi.mh09y.getText().toString());
            if (check == 0)
                return Validator.emptyCustomTextBox(this, bi.mh09d, "All Fields can't be zero");
        }

        if(bi.mh027b02.isChecked()
                && bi.rgmh02601.getCheckedRadioButtonId() == -1
                && bi.rgmh02602.getCheckedRadioButtonId() == -1
                && bi.rgmh02603.getCheckedRadioButtonId() == -1
                && bi.rgmh02604.getCheckedRadioButtonId() == -1
                && bi.rgmh02605.getCheckedRadioButtonId() == -1
                && bi.rgmh02606.getCheckedRadioButtonId() == -1
                && !bi.mh02601.isChecked()
                && !bi.mh026019.isChecked()
                && !bi.mh026021.isChecked()
                && !bi.mh026022.isChecked()

        ) {
            return Validator.emptyCustomTextBox(this, bi.mh026020, "Please select at least one vaccine.");
        }
*/

       /* if (!AllVaccinationsViewed && Integer.valueOf(bi.mh09y.getText().toString()) <= 5 && bi.mh027b02.isChecked()) {

            Toast.makeText(
                    this,
                    "ERROR(Vaccinations) Probe all vaccinations ",
                    Toast.LENGTH_SHORT
            ).show();

            bi.llscrollviewmh26.requestFocus();
            return false;
        }*/

        return Validator.emptyCheckingContainer(this, bi.GrpName);

    }


    public void BtnEnd(View view) {
        AppUtilsKt.contextEndActivity(this);
    }


    @Override
    public void endSecActivity(boolean flag) {
        //if (!Validator.emptyTextBox(this, bi.mh01)) return;

        finish();
        gotoActivity(this, MainActivity.class);

    }


  /*  private void populateSpinner(String camp_id) {
        // Spinner Drop down elements
        campNo = new ArrayList<String>() {
            {
                add("....");
            }
        };
        List<String> campDoc = new ArrayList<String>() {
            {
                add("....");
            }
        };
        ArrayList<Doctor> dc = db.getDoctorByCamp(camp_id);
        for (Doctor d : dc) {
            campNo.add(d.getIddoctor());
            campDoc.add(d.getStaff_name());
        }

        bi.mh06.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, campDoc));

    }*/

}