package com.example.lucky.alarmsystemfull.Activities.Options;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.lucky.alarmsystemfull.Adapters.PhonesAdapter;
import com.example.lucky.alarmsystemfull.App.Constants;
import com.example.lucky.alarmsystemfull.Presenters.OptionPhoneListPresenter;
import com.example.lucky.alarmsystemfull.R;
import com.example.lucky.alarmsystemfull.Views.OptionPhoneListView;

public class OptionPhonesListActivity extends MvpAppCompatActivity implements OptionPhoneListView, PhonesAdapter.onClick {

    @InjectPresenter
    OptionPhoneListPresenter optionPhoneListPresenter;

    private EditText newPhone;
    private Button btnNext;
    private RecyclerView recyclerView;
    private PhonesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phones_list);
        initViews();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recycler_view);
        newPhone = findViewById(R.id.new_phone);
        btnNext = findViewById(R.id.btn_next);

        btnNext.setVisibility(getIntent()
                .getBooleanExtra(Constants.FIRST_START, false) ?
                View.VISIBLE : View.GONE);

        initRecyclerView();
        setListeners();
    }

    private void initRecyclerView() {
        adapter = new PhonesAdapter(optionPhoneListPresenter.getPhones(), this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

//        adapter.notifyItemInserted(optionPhoneListPresenter.getPhonesLastPosition());
    }

    private void setListeners() {
        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optionPhoneListPresenter.addPhone(newPhone.getText().toString());
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optionPhoneListPresenter.next();
                finish();
            }
        });
    }

    @Override
    public void notifyItemInserted(int position) {
        adapter.notifyItemInserted(position);
        newPhone.setText("");
    }

    @Override
    public void click(int position) {
        optionPhoneListPresenter.removeItemFromAdapter(position);
        adapter.notifyDataSetChanged();
    }
}