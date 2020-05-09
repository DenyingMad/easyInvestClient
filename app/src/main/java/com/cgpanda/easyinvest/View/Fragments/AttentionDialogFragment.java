package com.cgpanda.easyinvest.View.Fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.cgpanda.easyinvest.R;

public class AttentionDialogFragment extends DialogFragment {
    private static final String TAG = "AttentionDialogFragment";

    public static AttentionDialogFragment newInstance(int message){
        AttentionDialogFragment fragment = new AttentionDialogFragment();
        Bundle args = new Bundle();
        args.putInt("message", message);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        int message = getArguments().getInt("message", R.string.no_message);

        return new AlertDialog.Builder(getActivity())
                .setIcon(R.drawable.ic_history_black_24dp)
                .setTitle(R.string.attention)
                .setPositiveButton(R.string.cancel_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "onClick: positive: close dialog");
                    }
                })
                .setMessage(message)
                .create();
    }
}
