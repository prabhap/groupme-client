package com.groupify.prabhapattabiraman.groupme;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.groupify.prabhapattabiraman.groupme.retrofit.impl.GroupmeServerService;
import com.groupify.prabhapattabiraman.groupme.util.DBConstants;
import com.groupify.prabhapattabiraman.groupme.util.LocationProber;
import com.groupify.prabhapattabiraman.groupme.util.Session;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.groupify.prabhapattabiraman.groupme.util.LocationProber.getLocationInstance;



public class CreateNewGroupActivity extends AppCompatActivity implements Validator.ValidationListener {

    @NotEmpty
    private EditText groupNameEditText;
    @NotEmpty
    private EditText rangeEditText;
    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_group);
        validator = new Validator(this);
        validator.setValidationListener(this);

    }

    public void onCreateGroup(View view) {
        groupNameEditText = (EditText) findViewById(R.id.groupName);
        rangeEditText = (EditText) findViewById(R.id.groupRange);
        validator.validate();
    }

    @Override
    public void onValidationSucceeded() {
        String groupName = groupNameEditText.getText().toString();
        int range = Integer.valueOf(rangeEditText.getText().toString());
        int checkedRadioButtonId = ((RadioGroup) findViewById(R.id.open)).getCheckedRadioButtonId();
        RadioButton isOpen = (RadioButton) findViewById(checkedRadioButtonId);
        boolean open = isOpen.getText() == getResources().getString(R.string.openGroup) ? true : false;
        LocationProber locationProber = getLocationInstance();
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        String currentLocation = locationProber.getCurrentLocation(locationManager, this);


        Call<ResponseBody> response = GroupmeServerService.getServiceInstance().getService().createGroup(Session.getCurrentUser(this),
                groupName, currentLocation, range, open);
        final Context currentActivity = this;

        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Intent intent = new Intent(currentActivity, UserDashboard.class);
                currentActivity.startActivity(intent);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }


    }
}
