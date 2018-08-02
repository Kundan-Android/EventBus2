package com.caliber.eventbus2;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {


    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        GlobalBus.getsBus().register(this);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setClickListener(view);
    }

    public void setClickListener(final View view) {
        Button btnSubmit = (Button) view.findViewById(R.id.submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etMessage = (EditText) view.findViewById(R.id.editText);

                // We are broadcasting the message here to listen to the subscriber.
                Events.FragmentActivityMessage fragmentActivityMessageEvent =
                        new Events.FragmentActivityMessage(
                                String.valueOf(etMessage.getText()));
                GlobalBus.getsBus().post(fragmentActivityMessageEvent);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMessage(Events.ActivityFragmentMessage activityFragmentMessage){
        TextView messageView = (TextView) getView().findViewById(R.id.message);
        messageView.setText(getString(R.string.message_received) +
                " " + activityFragmentMessage.getMessage());
        Toast.makeText(getActivity(),
                getString(R.string.message_fragment) +
                        " " + activityFragmentMessage.getMessage(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GlobalBus.getsBus().unregister(this);
    }
}
