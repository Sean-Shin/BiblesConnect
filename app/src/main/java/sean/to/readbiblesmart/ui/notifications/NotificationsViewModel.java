package sean.to.readbiblesmart.ui.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import sean.to.readbiblesmart.R;

public class NotificationsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NotificationsViewModel() {
        mText = new MutableLiveData<>();

//        mText.setValue(getString(R.string.myself));

    }

    public LiveData<String> getText() {
        return mText;
    }
}