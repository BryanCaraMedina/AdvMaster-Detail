package es.ulpgc.eite.da.advmasterdetail.register;

import java.lang.ref.WeakReference;
import es.ulpgc.eite.da.advmasterdetail.data.RepositoryContract;

public interface RegisterContract {

    interface View {
        void injectPresenter(Presenter presenter);
        void displayRegisterData(RegisterViewModel viewModel);
        void navigateToLoginScreen();
        void showErrorMessage(String message);
        void showSuccessMessage(String message);
    }

    interface Presenter {
        void injectView(WeakReference<View> view);
        void injectModel(Model model);
        void onRegisterButtonClicked(String username, String password);
        void onCreateCalled();
        void onRecreateCalled();
        void onPauseCalled();
    }

    interface Model {
        void register(String username, String password, RepositoryContract.RegisterCallback callback);
    }
}
