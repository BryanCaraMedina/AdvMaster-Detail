package es.ulpgc.eite.da.advmasterdetail.seriedetail;

import androidx.fragment.app.FragmentActivity;
import java.lang.ref.WeakReference;
import es.ulpgc.eite.da.advmasterdetail.app.CatalogMediator;
import es.ulpgc.eite.da.advmasterdetail.data.CatalogRepository;
import es.ulpgc.eite.da.advmasterdetail.data.RepositoryContract;

public class SerieDetailScreen {

    public static void configure(SerieDetailContract.View view) {

        WeakReference<FragmentActivity> context =
                new WeakReference<>((FragmentActivity) view);

        CatalogMediator mediator = CatalogMediator.getInstance();
        SerieDetailContract.Presenter presenter = new SerieDetailPresenter(mediator);

        RepositoryContract repository = CatalogRepository.getInstance(context.get());
        SerieDetailContract.Model model = new SerieDetailModel(repository);

        presenter.injectView(new WeakReference<>(view));
        presenter.injectModel(model);
        view.injectPresenter(presenter);
    }
}
