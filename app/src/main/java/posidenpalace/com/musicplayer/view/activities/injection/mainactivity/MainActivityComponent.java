package posidenpalace.com.musicplayer.view.activities.injection.mainactivity;

import dagger.Component;
import posidenpalace.com.musicplayer.view.activities.mainactivity.MainActivity;

@Component(modules = MainActivityModule.class)
public interface MainActivityComponent {
    void inject(MainActivity mainActivity);
}
