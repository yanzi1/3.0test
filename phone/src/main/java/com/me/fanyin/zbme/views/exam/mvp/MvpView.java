package com.me.fanyin.zbme.views.exam.mvp;

import android.content.Context;

/**
 * 加载数据的接口
 */
public interface MvpView {
  /**
   * 显示进度条
   */
  void showLoading();

  /**
   * 隐藏进度条
   */
  void hideLoading();

  /**
   * 显示重试
   */
  void showRetry();

  /**
   * 隐藏重试
   */
  void hideRetry();

  /**
   * 显示错误的信息
   *
   * @param message 错误的信息.
   */
  void showError(String message);

  /**
   * Get a {@link Context}.
   */
  Context context();
}
