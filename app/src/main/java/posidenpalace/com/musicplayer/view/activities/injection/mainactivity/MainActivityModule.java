package posidenpalace.com.musicplayer.view.activities.injection.mainactivity;


import dagger.Module;
import dagger.Provides;
import posidenpalace.com.musicplayer.view.activities.mainactivity.MainActivityPresenter;

@Module
public class MainActivityModule {
    @Provides
    public MainActivityPresenter provideMainActivityPresenter(){
        return new MainActivityPresenter();
    }
}
