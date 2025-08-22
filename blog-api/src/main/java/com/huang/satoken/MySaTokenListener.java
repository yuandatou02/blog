package com.huang.satoken;

import cn.dev33.satoken.listener.SaTokenListener;
import cn.dev33.satoken.stp.parameter.SaLoginParameter;

/**
 * 自定义侦听器的实现
 *
 * @author Ikaros
 * @since 2025/8/22 13:53 星期五
 */
public class MySaTokenListener implements SaTokenListener {

    @Override
    public void doLogin(String s, Object o, String s1, SaLoginParameter saLoginParameter) {

    }

    @Override
    public void doLogout(String s, Object o, String s1) {

    }

    @Override
    public void doKickout(String s, Object o, String s1) {

    }

    @Override
    public void doReplaced(String s, Object o, String s1) {

    }

    @Override
    public void doDisable(String s, Object o, String s1, int i, long l) {

    }

    @Override
    public void doUntieDisable(String s, Object o, String s1) {

    }

    @Override
    public void doOpenSafe(String s, String s1, String s2, long l) {

    }

    @Override
    public void doCloseSafe(String s, String s1, String s2) {

    }

    @Override
    public void doCreateSession(String s) {

    }

    @Override
    public void doLogoutSession(String s) {

    }

    @Override
    public void doRenewTimeout(String s, Object o, String s1, long l) {

    }
}
