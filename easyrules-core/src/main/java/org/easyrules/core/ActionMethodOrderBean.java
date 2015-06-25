package org.easyrules.core;

import java.lang.reflect.Method;

/**
 * Utility class that associates an action method and its execution order.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
class ActionMethodOrderBean implements Comparable<ActionMethodOrderBean> {

    private Method method;

    private int order;

    ActionMethodOrderBean(final Method method, final int order) {
        this.method = method;
        this.order = order;
    }

    public int getOrder() {
        return order;
    }

    public Method getMethod() {
        return method;
    }

    @Override
    public int compareTo(final ActionMethodOrderBean actionMethodOrderBean) {
        if (order < actionMethodOrderBean.getOrder()) {
            return -1;
        } else if (order > actionMethodOrderBean.getOrder()) {
            return 1;
        } else {
            return method.equals(actionMethodOrderBean.getMethod()) ? 0 : 1;
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof ActionMethodOrderBean)) return false;

        ActionMethodOrderBean that = (ActionMethodOrderBean) o;

        if (order != that.order) return false;
        if (!method.equals(that.method)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = method.hashCode();
        result = 31 * result + order;
        return result;
    }

}