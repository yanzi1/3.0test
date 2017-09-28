package com.me.data.mvp;


import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BasePersenter<T extends MvpView> implements Presenter<T> {

    public BasePersenter(){
    }


    private T mMvpView;

    public CompositeDisposable mDisposable;

    @Override
    public void attachView(T mvpView) {
        mMvpView = mvpView;
        mDisposable = new CompositeDisposable();
    }

    @Override
    public void detachView() {
        mMvpView = null;
        cancelAllSubscription();
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public T getMvpView() {
        return mMvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }

    protected void addSubscription(Disposable disposable) {
        mDisposable.add(disposable);
    }

    public void cancelSubscription(Disposable disposable) {
        if (mDisposable != null && disposable != null && !disposable.isDisposed()) {
            mDisposable.remove(disposable);
        }
    }

    public void cancelAllSubscription() {
        if (mDisposable != null) {
            mDisposable.clear();
        }
    }


    public abstract void getData();
}
