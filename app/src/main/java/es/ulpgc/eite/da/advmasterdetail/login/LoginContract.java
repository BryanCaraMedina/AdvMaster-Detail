package es.ulpgc.eite.da.advmasterdetail.login;

import java.lang.ref.WeakReference;
import es.ulpgc.eite.da.advmasterdetail.data.RepositoryContract;

/**
 * Interface defining the contract between the View, Presenter and Model for the Login screen.
 */
public interface LoginContract {

    /**
     * Interface for the View layer of the Login screen.
     */
    interface View {
        /**
         * Injects the presenter into the view.
         * @param presenter The presenter instance.
         */
        void injectPresenter(Presenter presenter);

        /**
         * Updates the UI with login-related data.
         * @param viewModel The view model.
         */
        void displayLoginData(LoginViewModel viewModel);

        /**
         * Navigates to the Movie List screen after successful login.
         */
        void navigateToMovieListScreen();

        /**
         * Navigates to the Register screen.
         */
        void navigateToRegisterScreen();

        /**
         * Shows an error message to the user.
         * @param message The message to display.
         */
        void showErrorMessage(String message);
    }

    /**
     * Interface for the Presenter layer of the Login screen.
     */
    interface Presenter {
        void injectView(WeakReference<View> view);
        void injectModel(Model model);

        /**
         * Handles the click on the login button.
         * @param username The entered username.
         * @param password The entered password.
         */
        void onLoginButtonClicked(String username, String password);

        /**
         * Handles the click on the register button.
         */
        void onRegisterButtonClicked();

        /**
         * Handles the click on the "Skip Login" button.
         */
        void onSkipLoginButtonClicked();

        void onCreateCalled();
        void onRecreateCalled();
        void onPauseCalled();
    }

    /**
     * Interface for the Model layer of the Login screen.
     */
    interface Model {
        /**
         * Authenticates the user against the repository.
         * @param username The username.
         * @param password The password.
         * @param callback Result callback.
         */
        void login(String username, String password, RepositoryContract.LoginCallback callback);
    }
}
