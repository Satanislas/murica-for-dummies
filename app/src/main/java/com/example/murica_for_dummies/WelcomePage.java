    package com.example.murica_for_dummies;

    import android.content.Context;
    import android.content.DialogInterface;
    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.os.Bundle;
    import android.view.Menu;
    import android.view.MenuInflater;
    import android.view.MenuItem;
    import android.view.View;
    import android.widget.Button;
    import android.widget.Toast;

    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AlertDialog;
    import androidx.appcompat.app.AppCompatActivity;
    import com.example.murica_for_dummies.database.entities.Users;
import com.example.murica_for_dummies.Distance.DistanceImperialToMetric;
import com.example.murica_for_dummies.Mass.MassImperialToMetric;
import com.example.murica_for_dummies.Volume.VolumeImperialToMetric;


public class WelcomePage extends AppCompatActivity {

        com.example.murica_for_dummies.databinding.ActivityWelcomePageBinding binding;

        Button MassButton;
        Button VolumeButton;
        Button DistanceButton;
        Button HistoryButton;

        static boolean isAdmin = true;

        Users user;

        private static final String MAIN_ACTIVITY_USER_ID = "com.example.murica_for_dummies.MAIN_ACTIVITY_USER_ID";
        private static final int LOGGED_OUT = -1;
        private static final String SHARED_PREFERENCE_USERID_VALUE = "com.example.murica_for_dummies.SHARED_PREFERENCE_USERID_VALUE";
        int loggedInUserId = -1;

        public final String SHARED_PREFERENCE_USERID_KEY = "com.example.murica_for_dummies.SHARED_PREFERENCE_USERID_KEY";

  
  @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            binding = com.example.murica_for_dummies.databinding.ActivityWelcomePageBinding.inflate(getLayoutInflater());
            View view = binding.getRoot();
            setContentView(view);

            InitAttributes();

            MassButton.setOnClickListener(v -> MassButtonCall());
            VolumeButton.setOnClickListener(v -> VolumeButtonCall());
            DistanceButton.setOnClickListener(v -> DistanceButtonCall());
            HistoryButton.setOnClickListener(v -> startActivity(HistoryActivity.IntentFactory(getApplicationContext())));

            Button logoutButton = findViewById(R.id.logoutButton);

            Button adminButton = findViewById(R.id.adminButton);

            logoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    logout();
                }
            });

            if (isAdmin) {
                adminButton.setVisibility(View.VISIBLE);
                adminButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        return;
                    }
                });
            } else {
                adminButton.setVisibility(View.GONE);
            }
        }

         private void DistanceButtonCall() {
       startActivity(DistanceImperialToMetric.IntentFactory(getApplicationContext()));
    }

    private void VolumeButtonCall() {
        startActivity(VolumeImperialToMetric.IntentFactory(getApplicationContext()));
    }

        private void MassButtonCall() {
            startActivity(MassImperialToMetric.IntentFactory(getApplicationContext()));
        }

        private void InitAttributes() {
            MassButton = binding.MassButton;
            VolumeButton = binding.VolumeButton;
            DistanceButton = binding.DistanceButton;
            HistoryButton = binding.HistoryButton;
        }

        public static Intent IntentFactory(Context context){
            return new Intent(context, WelcomePage.class);
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.logout_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareOptionsMenu(Menu menu) {
            MenuItem item = menu.findItem(R.id.logoutMenuItem);
            item.setVisible(true);
            if(user == null){
                return false;
            }
            item.setTitle(MainActivity.user.getLogin());
            item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(@NonNull MenuItem item) {
                    showLogoutDialog();
                    return false;
                }
            });
            return true;
        }

        private void showLogoutDialog(){
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(WelcomePage.this);
            final AlertDialog alertDialog = alertBuilder.create();

            alertBuilder.setMessage("Do you really want to logout ?");

            alertBuilder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    logout();
                }
            });

            alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    alertDialog.dismiss();
                }
            });

            alertBuilder.create().show();

        }

        private void logout() {
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREFERENCE_USERID_KEY, Context.MODE_PRIVATE);
            SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
            sharedPrefEditor.putInt(SHARED_PREFERENCE_USERID_KEY, LOGGED_OUT);
            sharedPrefEditor.apply();

            getIntent().putExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);
            startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
        }
    }