package com.prinkal.pos.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.prinkal.pos.app.R;
import com.prinkal.pos.app.databinding.ActivityOtherBinding;
import com.prinkal.pos.app.handlers.OtherActivityHandler;

import androidx.databinding.DataBindingUtil;

public class OtherActivity extends BaseActivity {
    ActivityOtherBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_other);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.setHandler(new OtherActivityHandler(this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        switch (requestCode) {
            case 7:
                if (resultCode == RESULT_OK) {
                    if (data.getData() != null) {
                        String PathHolder = data.getData().getEncodedPath();
                        binding.getHandler().onActivityResultCustom(PathHolder);
                    }
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.findItem(R.id.menu_item_search).setVisible(false);
        menu.findItem(R.id.menu_item_scan_barcode).setVisible(false);
        return true;
    }

}
