package com.me.data.mvp;

import android.app.Activity;

/**
 * 加载数据的接口
 */
public interface MvpView {
  /**
   * 显示加载中页面
   */
  void showLoading();

  /**
   * 隐藏加载中页面
   */
  void hideLoading();

  /**
   * 显示无数据页面
   */
  void showEmptyData();

  /**
   * 显示数据错误页面
   */
  void showErrorData();

  /**
   * 显示无网络页面
   */
  void showNetworkError();

  /**
   * 显示无权限
   */
  void showNoPermission();

  /**
   * 显示Dialog进度条
   */
  void showDialogLoading();

  /**
   * 隐藏Dialog进度条
   */
  void hideDialogLoading();

  /**
   * 显示错误的信息
   *
   * @param message 错误的信息.
   */
  void showError(String message);

  /**
   * Get a {@link Activity}.
   */
  Activity context();
}
