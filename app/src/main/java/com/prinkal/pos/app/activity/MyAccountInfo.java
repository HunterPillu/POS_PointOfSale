package com.prinkal.pos.app.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.prinkal.pos.app.R;
import com.prinkal.pos.app.databinding.ActivityMyAccountInfoBinding;
import com.prinkal.pos.app.db.DataBaseController;
import com.prinkal.pos.app.db.entity.Administrator;
import com.prinkal.pos.app.handlers.MyAccountInfoHandler;
import com.prinkal.pos.app.interfaces.DataBaseCallBack;

import androidx.databinding.DataBindingUtil;

public class MyAccountInfo extends BaseActivity {
    public ActivityMyAccountInfoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_account_info);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DataBaseController.getInstance().getAdminData(this, new DataBaseCallBack() {
            @Override
            public void onSuccess(Object responseData, String successMsg) {
                binding.setData((Administrator) responseData);
                binding.setFirstName(((Administrator) responseData).getFirstName());
                binding.setLastName(((Administrator) responseData).getLastName());
            }

            @Override
            public void onFailure(int errorCode, String errorMsg) {
                Toast.makeText(MyAccountInfo.this, errorMsg + "", Toast.LENGTH_SHORT).show();
            }
        });
        binding.setHandler(new MyAccountInfoHandler(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.findItem(R.id.menu_item_search).setVisible(false);
        final MenuItem barcodeItem = menu.findItem(R.id.menu_item_scan_barcode);
        barcodeItem.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

}
