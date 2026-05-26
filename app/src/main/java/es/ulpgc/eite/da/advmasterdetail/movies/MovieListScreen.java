package es.ulpgc.eite.da.advmasterdetail.movies;

import androidx.fragment.app.FragmentActivity;
import java.lang.ref.WeakReference;
import es.ulpgc.eite.da.advmasterdetail.app.CatalogMediator;
import es.ulpgc.eite.da.advmasterdetail.app.SessionManager;
import es.ulpgc.eite.da.advmasterdetail.data.CatalogRepository;
import es.ulpgc.eite.da.advmasterdetail.data.RepositoryContract;

public class MovieListScreen {

    public static void configure(MovieListContract.View view) {

        WeakReference<FragmentActivity> context =
                new WeakReference<>((FragmentActivity) view);

        CatalogMediator mediator = CatalogMediator.getInstance();
        SessionManager sessionManager = new SessionManager(context.get());
        
        MovieListContract.Presenter presenter = new MovieListPresenter(mediator, sessionManager);

        RepositoryContract repository = CatalogRepository.getInstance(context.get());
        MovieListContract.Model model = new MovieListModel(repository);

        presenter.injectView(new WeakReference<>(view));
        presenter.injectModel(model);
        view.injectPresenter(presenter);
    }
}
