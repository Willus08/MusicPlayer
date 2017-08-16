package posidenpalace.com.musicplayer.view.activities.mainactivity;


import java.util.List;

import posidenpalace.com.musicplayer.model.MusicList;
import posidenpalace.com.musicplayer.view.BasePresenter;
import posidenpalace.com.musicplayer.view.BaseView;

public interface MainActivityContract {
    interface View extends BaseView{
        void setupAdapter(List<MusicList> stringList);

    }
    interface Presenter extends BasePresenter<View>{
        void getSongs();

    }
}
