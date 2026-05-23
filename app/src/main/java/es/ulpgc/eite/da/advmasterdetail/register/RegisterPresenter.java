package es.ulpgc.eite.da.advmasterdetail.register;

import java.lang.ref.WeakReference;
import es.ulpgc.eite.da.advmasterdetail.app.CatalogMediator;
import es.ulpgc.eite.da.advmasterdetail.data.RepositoryContract;

public class RegisterPresenter implements RegisterContract.Presenter {

    private WeakReference<RegisterContract.View> view;
    private RegisterState state;
    private RegisterContract.Model model;
    private CatalogMediator mediator;

    public RegisterPresenter(CatalogMediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public void onCreateCalled() {
        state = new RegisterState();
    }

    @Override
    public void onRecreateCalled() {
        state = mediator.getRegisterState();
    }

    @Override
    public void onPauseCalled() {
        mediator.setRegisterState(state);
    }

    @Override
    public void onRegisterButtonClicked(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            view.get().showErrorMessage("Please fill all fields");
            return;
        }

        model.register(username, password, new RepositoryContract.RegisterCallback() {
            @Override
            public void onRegisterResult(boolean success) {
                if (success) {
                    view.get().showSuccessMessage("Registration successful");
                    view.get().navigateToLoginScreen();
                } else {
                    view.get().showErrorMessage("Registration failed");
                }
            }
        });
    }

    @Override
    public void injectView(WeakReference<RegisterContract.View> view) {
        this.view = view;
    }

    @Override
    public void injectModel(RegisterContract.Model model) {
        this.model = model;
    }
}
