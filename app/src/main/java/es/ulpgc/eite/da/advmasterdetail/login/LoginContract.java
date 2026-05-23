package es.ulpgc.eite.da.advmasterdetail.login;

import java.lang.ref.WeakReference;
import es.ulpgc.eite.da.advmasterdetail.data.RepositoryContract;

public interface LoginContract {

    interface View {
        void injectPresenter(Presenter presenter);
        void displayLoginData(LoginViewModel viewModel);
        void navigateToMovieListScreen();
        void navigateToRegisterScreen();
        void showErrorMessage(String message);
    }

    interface Presenter {
        void injectView(WeakReference<View> view);
        void injectModel(Model model);
        void onLoginButtonClicked(String username, String password);
        void onRegisterButtonClicked();
        void onSkipLoginButtonClicked();
        void onCreateCalled();
        void onRecreateCalled();
        void onPauseCalled();
    }

    interface Model {
        void login(String username, String password, RepositoryContract.LoginCallback callback);
    }
}
