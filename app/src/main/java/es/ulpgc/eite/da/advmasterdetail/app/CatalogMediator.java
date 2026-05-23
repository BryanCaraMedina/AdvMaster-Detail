package es.ulpgc.eite.da.advmasterdetail.app;

import es.ulpgc.eite.da.advmasterdetail.data.MovieEntity;
import es.ulpgc.eite.da.advmasterdetail.data.SerieEntity;
import es.ulpgc.eite.da.advmasterdetail.data.UserEntity;
import es.ulpgc.eite.da.advmasterdetail.movies.MovieListState;
import es.ulpgc.eite.da.advmasterdetail.moviedetail.MovieDetailState;
import es.ulpgc.eite.da.advmasterdetail.series.SerieListState;
import es.ulpgc.eite.da.advmasterdetail.seriedetail.SerieDetailState;
import es.ulpgc.eite.da.advmasterdetail.login.LoginState;
import es.ulpgc.eite.da.advmasterdetail.register.RegisterState;

public class CatalogMediator {

  private MovieListState movieListState;
  private MovieDetailState movieDetailState;
  private SerieListState serieListState;
  private SerieDetailState serieDetailState;
  private LoginState loginState;
  private RegisterState registerState;

  private MovieEntity movie;
  private SerieEntity serie;
  private UserEntity user;

  private static CatalogMediator INSTANCE;

  private CatalogMediator() {}

  public static void resetInstance() {
    INSTANCE = null;
  }

  public static CatalogMediator getInstance() {
    if(INSTANCE == null){
      INSTANCE = new CatalogMediator();
    }
    return INSTANCE;
  }

  // Movie getters/setters
  public MovieListState getMovieListState() { return movieListState; }
  public void setMovieListState(MovieListState state) { this.movieListState = state; }
  public MovieDetailState getMovieDetailState() { return movieDetailState; }
  public void setMovieDetailState(MovieDetailState state) { this.movieDetailState = state; }
  public MovieEntity getMovie() { return movie; }
  public void setMovie(MovieEntity item) { this.movie = item; }

  // Serie getters/setters
  public SerieListState getSerieListState() { return serieListState; }
  public void setSerieListState(SerieListState state) { this.serieListState = state; }
  public SerieDetailState getSerieDetailState() { return serieDetailState; }
  public void setSerieDetailState(SerieDetailState state) { this.serieDetailState = state; }
  public SerieEntity getSerie() { return serie; }
  public void setSerie(SerieEntity item) { this.serie = item; }

  // User and Auth getters/setters
  public UserEntity getUser() { return user; }
  public void setUser(UserEntity user) { this.user = user; }
  public LoginState getLoginState() { return loginState; }
  public void setLoginState(LoginState state) { this.loginState = state; }
  public RegisterState getRegisterState() { return registerState; }
  public void setRegisterState(RegisterState state) { this.registerState = state; }
}
