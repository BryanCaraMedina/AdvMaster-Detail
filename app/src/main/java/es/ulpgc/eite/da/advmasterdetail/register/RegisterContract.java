package es.ulpgc.eite.da.advmasterdetail.register;

import java.lang.ref.WeakReference;
import es.ulpgc.eite.da.advmasterdetail.data.RepositoryContract;

/**
 * Interface defining the contract between the View, Presenter and Model for the Register screen.
 */
public interface RegisterContract {

    /**
     * Interface for the View layer of the Register screen.
     */
    interface View {
        /**
         * Injects the presenter into the view.
         * @param presenter The presenter instance.
         */
        void injectPresenter(Presenter presenter);

        /**
         * Updates the UI with register-related data.
         * @param viewModel The view model.
         */
        void displayRegisterData(RegisterViewModel viewModel);

        /**
         * Navigates back to the Login screen after successful registration.
         */
        void navigateToLoginScreen();

        /**
         * Shows an error message to the user.
         * @param message The message to display.
         */
        void showErrorMessage(String message);

        /**
         * Shows a success message to the user.
         * @param message The message to display.
         */
        void showSuccessMessage(String message);
    }

    /**
     * Interface for the Presenter layer of the Register screen.
     */
    interface Presenter {
        void injectView(WeakReference<View> view);
        void injectModel(Model model);

        /**
         * Handles the click on the register button.
         * @param username The entered username.
         * @param password The entered password.
         */
        void onRegisterButtonClicked(String username, String password);

        void onCreateCalled();
        void onRecreateCalled();
        void onPauseCalled();
    }

    /**
     * Interface for the Model layer of the Register screen.
     */
    interface Model {
        /**
         * Registers a new user in the repository.
         * @param username The username.
         * @param password The password.
         * @param callback Result callback.
         */
        void register(String username, String password, RepositoryContract.RegisterCallback callback);
    }
}
