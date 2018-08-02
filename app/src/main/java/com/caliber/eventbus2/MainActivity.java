package com.caliber.eventbus2;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addFragment();
    }
    @Override
    protected void onStart() {
        super.onStart();
        // Register this fragment to listen to event.
        GlobalBus.getsBus().register(this);
    }
    public void sendMessageToFragment(View view) {
        EditText etMessage = (EditText) findViewById(R.id.activityData);
        Events.ActivityFragmentMessage activityFragmentMessageEvent =
                new Events.ActivityFragmentMessage(String.valueOf(etMessage.getText()));

        GlobalBus.getsBus().post(activityFragmentMessageEvent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMessage(Events.FragmentActivityMessage fragmentActivityMessage, Events.ActivityActivityMessage activityActivityMessage) {
        TextView messageView = (TextView) findViewById(R.id.message);
        if (fragmentActivityMessage != null) {
            messageView.setText(getString(R.string.message_received) + " " + fragmentActivityMessage.getMessage());

            Toast.makeText(getApplicationContext(),
                    getString(R.string.message_main_activity) + " " + fragmentActivityMessage.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
        else if (activityActivityMessage != null){
            messageView.setText((getString(R.string.message_received) + " " + activityActivityMessage.getMessage()));
        }
    }


    private void addFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, new UserFragment())
                .commit();

    }
    @Override
    protected void onStop() {
        super.onStop();
        GlobalBus.getsBus().unregister(this);
    }

    public void sendMessageActivityToActivity(View view) {
        EditText et = findViewById(R.id.secondEdittext);
        Events.ActivityActivityMessage activityActivityMessage = new Events.ActivityActivityMessage(String.valueOf(et.getText()));
        GlobalBus.getsBus().post(activityActivityMessage);
    }
}
