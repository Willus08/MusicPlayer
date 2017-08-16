package posidenpalace.com.musicplayer.view;


public interface BasePresenter<V extends BaseView> {
    void addView(V view);
    void removeView();
}
