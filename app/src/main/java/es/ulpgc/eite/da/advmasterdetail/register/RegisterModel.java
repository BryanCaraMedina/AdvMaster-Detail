package es.ulpgc.eite.da.advmasterdetail.register;

import es.ulpgc.eite.da.advmasterdetail.data.RepositoryContract;
import es.ulpgc.eite.da.advmasterdetail.data.UserEntity;

public class RegisterModel implements RegisterContract.Model {

    private final RepositoryContract repository;

    public RegisterModel(RepositoryContract repository) {
        this.repository = repository;
    }

    @Override
    public void register(String username, String password, RepositoryContract.RegisterCallback callback) {
        UserEntity user = new UserEntity(username, password);
        repository.register(user, callback);
    }
}
