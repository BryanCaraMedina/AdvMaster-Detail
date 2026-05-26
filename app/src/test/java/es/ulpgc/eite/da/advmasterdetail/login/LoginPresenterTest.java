package es.ulpgc.eite.da.advmasterdetail.login;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.lang.ref.WeakReference;

import es.ulpgc.eite.da.advmasterdetail.app.CatalogMediator;
import es.ulpgc.eite.da.advmasterdetail.app.SessionManager;
import es.ulpgc.eite.da.advmasterdetail.data.RepositoryContract;
import es.ulpgc.eite.da.advmasterdetail.data.UserEntity;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28)
public class LoginPresenterTest {

    @Mock
    private LoginContract.View view;
    @Mock
    private LoginContract.Model model;
    @Mock
    private CatalogMediator mediator;
    @Mock
    private SessionManager sessionManager;

    private LoginPresenter presenter;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        presenter = new LoginPresenter(mediator, sessionManager);
        presenter.injectView(new WeakReference<>(view));
        presenter.injectModel(model);
    }

    @Test
    public void onLoginButtonClicked_withEmptyFields_showsError() {
        presenter.onLoginButtonClicked("", "");
        verify(view).showErrorMessage("Please enter username and password");
    }

    @Test
    public void onSkipLoginButtonClicked_clearsUserAndNavigates() {
        presenter.onSkipLoginButtonClicked();
        verify(mediator).setUser(null);
        verify(sessionManager).logout();
        verify(view).navigateToMovieListScreen();
    }
}
