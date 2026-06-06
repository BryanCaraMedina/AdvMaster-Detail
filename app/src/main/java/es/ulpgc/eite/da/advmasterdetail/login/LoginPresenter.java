package es.ulpgc.eite.da.advmasterdetail.login;

import java.lang.ref.WeakReference;
import es.ulpgc.eite.da.advmasterdetail.app.CatalogMediator;
import es.ulpgc.eite.da.advmasterdetail.app.SessionManager;
import es.ulpgc.eite.da.advmasterdetail.data.RepositoryContract;
import es.ulpgc.eite.da.advmasterdetail.data.UserEntity;

public class LoginPresenter implements LoginContract.Presenter {

    private WeakReference<LoginContract.View> view;
    private LoginState state;
    private LoginContract.Model model;
    private CatalogMediator mediator;
    private SessionManager sessionManager;

    public LoginPresenter(CatalogMediator mediator, SessionManager sessionManager) {
        this.mediator = mediator;
        this.sessionManager = sessionManager;
    }

    @Override
    public void onCreateCalled() {
        state = new LoginState();
        
        if (sessionManager.isLoggedIn()) {
            UserEntity user = new UserEntity(sessionManager.getUsername(), "");
            user.id = sessionManager.getUserId();
            mediator.setUser(user);
            view.get().navigateToMovieListScreen();
        }
    }

    @Override
    public void onRecreateCalled() {
        state = mediator.getLoginState();
        if (state == null) {
            state = new LoginState();
        }
    }

    @Override
    public void onPauseCalled() {
        mediator.setLoginState(state);
    }

    @Override
    public void onLoginButtonClicked(String username, String password, boolean goToMovies) {
        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            view.get().showErrorMessage("Por favor, introduce usuario y contraseña");
            return;
        }

        model.login(username, password, new RepositoryContract.LoginCallback() {
            @Override
            public void onLoginResult(boolean success, UserEntity user) {
                if (success) {
                    sessionManager.createSession(user.id, user.username);
                    mediator.setUser(user);
                    if(goToMovies){
                        view.get().navigateToMovieListScreen();
                    } else {
                        view.get().navigateToSerieListScreen();
                    }
                } else {
                    view.get().showErrorMessage("Error al iniciar sesión. Revisa tus credenciales.");
                }
            }
        });
    }

    @Override
    public void onRegisterButtonClicked() {
        view.get().navigateToRegisterScreen();
    }

    @Override
    public void onSkipLoginButtonClicked(boolean goToMovies) {
        mediator.setUser(null);
        sessionManager.logout();
        if(goToMovies){
            view.get().navigateToMovieListScreen();
        } else {
            view.get().navigateToSerieListScreen();
        }
    }

    @Override
    public void injectView(WeakReference<LoginContract.View> view) {
        this.view = view;
    }

    @Override
    public void injectModel(LoginContract.Model model) {
        this.model = model;
    }
}
