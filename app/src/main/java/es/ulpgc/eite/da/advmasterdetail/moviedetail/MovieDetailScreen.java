package es.ulpgc.eite.da.advmasterdetail.moviedetail;

import androidx.fragment.app.FragmentActivity;
import java.lang.ref.WeakReference;
import es.ulpgc.eite.da.advmasterdetail.app.CatalogMediator;
import es.ulpgc.eite.da.advmasterdetail.data.CatalogRepository;
import es.ulpgc.eite.da.advmasterdetail.data.RepositoryContract;

public class MovieDetailScreen {

    public static void configure(MovieDetailContract.View view) {

        WeakReference<FragmentActivity> context =
                new WeakReference<>((FragmentActivity) view);

        CatalogMediator mediator = CatalogMediator.getInstance();
        MovieDetailContract.Presenter presenter = new MovieDetailPresenter(mediator);

        RepositoryContract repository = CatalogRepository.getInstance(context.get());
        MovieDetailContract.Model model = new MovieDetailModel(repository);

        presenter.injectView(new WeakReference<>(view));
        presenter.injectModel(model);
        view.injectPresenter(presenter);
    }
}
