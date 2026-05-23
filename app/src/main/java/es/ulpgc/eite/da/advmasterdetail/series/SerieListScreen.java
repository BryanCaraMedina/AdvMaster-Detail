package es.ulpgc.eite.da.advmasterdetail.series;

import androidx.fragment.app.FragmentActivity;
import java.lang.ref.WeakReference;
import es.ulpgc.eite.da.advmasterdetail.app.CatalogMediator;
import es.ulpgc.eite.da.advmasterdetail.data.CatalogRepository;
import es.ulpgc.eite.da.advmasterdetail.data.RepositoryContract;

public class SerieListScreen {

    public static void configure(SerieListContract.View view) {

        WeakReference<FragmentActivity> context =
                new WeakReference<>((FragmentActivity) view);

        CatalogMediator mediator = CatalogMediator.getInstance();
        SerieListContract.Presenter presenter = new SerieListPresenter(mediator);

        RepositoryContract repository = CatalogRepository.getInstance(context.get());
        SerieListContract.Model model = new SerieListModel(repository);

        presenter.injectView(new WeakReference<>(view));
        presenter.injectModel(model);
        view.injectPresenter(presenter);
    }
}
