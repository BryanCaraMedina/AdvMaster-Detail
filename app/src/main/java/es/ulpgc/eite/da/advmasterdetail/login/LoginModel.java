package es.ulpgc.eite.da.advmasterdetail.login;

import es.ulpgc.eite.da.advmasterdetail.data.RepositoryContract;

public class LoginModel implements LoginContract.Model {

    private final RepositoryContract repository;

    public LoginModel(RepositoryContract repository) {
        this.repository = repository;
    }

    @Override
    public void login(String username, String password, RepositoryContract.LoginCallback callback) {
        repository.login(username, password, callback);
    }
}
