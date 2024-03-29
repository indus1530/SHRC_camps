package edu.aku.hassannaqvi.shrc_camps.ui.sections;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Validator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.aku.hassannaqvi.shrc_camps.R;
import edu.aku.hassannaqvi.shrc_camps.contracts.MHContract;
import edu.aku.hassannaqvi.shrc_camps.core.MainApp;
import edu.aku.hassannaqvi.shrc_camps.database.DatabaseHelper;
import edu.aku.hassannaqvi.shrc_camps.databinding.ActivityInfoMobileHealthR2Binding;
import edu.aku.hassannaqvi.shrc_camps.models.MobileHealth;
import edu.aku.hassannaqvi.shrc_camps.utils.AppUtilsKt;
import edu.aku.hassannaqvi.shrc_camps.utils.EndSectionActivity;

import static edu.aku.hassannaqvi.shrc_camps.core.MainApp.form;
import static edu.aku.hassannaqvi.shrc_camps.core.MainApp.mobileHealth;

public class InfoSectionMobileHealth extends AppCompatActivity implements EndSectionActivity {

    ActivityInfoMobileHealthR2Binding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_info_mobile_health_r2);
        bi.setCallback(this);
        setupSkips();

    }

    private void setupSkips() {
    }


    private boolean UpdateDB() {
        DatabaseHelper db = MainApp.appInfo.dbHelper;
        long updcount = db.addMH(mobileHealth);
        mobileHealth.setId(String.valueOf(updcount));
        if (updcount > 0) {
            mobileHealth.setUid(mobileHealth.getDeviceId() + mobileHealth.getId());
            long count = db.updatesMHColumn(MHContract.MHTable.COLUMN_UID, mobileHealth.getUid());
            return true;
        } else {
            Toast.makeText(this, "SORRY!! Failed to update DB)", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    private void saveDraft() {

        mobileHealth = new MobileHealth();
        mobileHealth.setSysDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date().getTime()));
       /* mobileHealth.setUuid(MainApp.form.getUid());
        mobileHealth.setUserName(MainApp.user.getUserName());
        mobileHealth.setDcode(MainApp.form.getDcode());
        mobileHealth.setUcode(MainApp.form.getUcode());
        mobileHealth.setCluster(MainApp.form.getCluster());
        mobileHealth.setHhno(MainApp.form.getHhno());
        mobileHealth.setDeviceId(MainApp.appInfo.getDeviceID());
        mobileHealth.setDeviceTag(MainApp.appInfo.getTagName());
        mobileHealth.setAppver(MainApp.appInfo.getAppVersion());*/

        mobileHealth.setSs101(bi.ss101.getText().toString().trim().isEmpty() ? "-1" : bi.ss101.getText().toString());
        mobileHealth.setSs102(bi.ss102.getText().toString().trim().isEmpty() ? "-1" : bi.ss102.getText().toString());
        mobileHealth.setSs103(bi.ss103.getText().toString().trim().isEmpty() ? "-1" : bi.ss103.getText().toString());
        mobileHealth.setSs104(bi.ss104.getText().toString().trim().isEmpty() ? "-1" : bi.ss104.getText().toString());
        //mobileHealth.setSs105(bi.ss105.getText().toString().trim().isEmpty() ? "-1" : bi.ss105.getText().toString());

    }


    public void BtnContinue(View view) {
        if (!formValidation()) return;
        saveDraft();
        if (UpdateDB()) {
            finish();
            startActivity(new Intent(this, InfoSectionMobileHealth.class));
        }
    }


    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.GrpName);

    }

    public void BtnEnd(View view) {
        AppUtilsKt.contextEndActivity(this);
    }

    @Override
    public void endSecActivity(boolean flag) {
        saveDraft();
        form.setHhflag("2");
        if (UpdateDB()) {
            finish();
        }
    }
}