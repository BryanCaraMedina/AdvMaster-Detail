package es.ulpgc.eite.da.advmasterdetail.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import es.ulpgc.eite.da.advmasterdetail.R;
import es.ulpgc.eite.da.advmasterdetail.movies.MovieListActivity;
import es.ulpgc.eite.da.advmasterdetail.register.RegisterActivity;
import es.ulpgc.eite.da.advmasterdetail.series.SerieListActivity;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    private LoginContract.Presenter presenter;
    private EditText emailEditText, passwordEditText;
    private Button loginButton, registerButton, skipButton;
    private RadioGroup optionGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.login_email);
        passwordEditText = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.go_to_register_button);
        skipButton = findViewById(R.id.skip_login_button);
        optionGroup = findViewById(R.id.login_option_group);

        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            boolean goToMovies = optionGroup.getCheckedRadioButtonId() == R.id.radio_movies;
            presenter.onLoginButtonClicked(email, password, goToMovies);
        });

        registerButton.setOnClickListener(v -> presenter.onRegisterButtonClicked());
        
        skipButton.setOnClickListener(v -> {
            boolean goToMovies = optionGroup.getCheckedRadioButtonId() == R.id.radio_movies;
            presenter.onSkipLoginButtonClicked(goToMovies);
        });

        LoginScreen.configure(this);

        if (savedInstanceState == null) {
            presenter.onCreateCalled();
        } else {
            presenter.onRecreateCalled();
        }
    }

    @Override
    public void onBackPressed() {
        showExitDialog();
    }

    private void showExitDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.dialog_exit_title)
                .setMessage(R.string.dialog_exit_message)
                .setPositiveButton(R.string.dialog_yes, (dialog, which) -> finishAffinity())
                .setNegativeButton(R.string.dialog_no, null)
                .show();
    }

    @Override
    public void displayLoginData(LoginViewModel viewModel) {
    }

    @Override
    public void navigateToMovieListScreen() {
        Intent intent = new Intent(this, MovieListActivity.class);
        startActivity(intent);
    }

    @Override
    public void navigateToSerieListScreen() {
        Intent intent = new Intent(this, SerieListActivity.class);
        startActivity(intent);
    }

    @Override
    public void navigateToRegisterScreen() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void showErrorMessage(String message) {
        runOnUiThread(() -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show());
    }

    @Override
    public void injectPresenter(LoginContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPauseCalled();
    }
}
