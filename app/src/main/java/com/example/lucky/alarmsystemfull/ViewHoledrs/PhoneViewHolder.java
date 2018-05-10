package com.example.lucky.alarmsystemfull.ViewHoledrs;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Lucky on 10.04.2018.
 */

public class PhoneViewHolder extends RecyclerView.ViewHolder {

    public PhoneViewHolder(View itemView) {
        super(itemView);
    }

    public interface onClick {
        void click(int position);
    }
}
