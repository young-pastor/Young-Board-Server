
package com.zhisida.core.context.group;

/**
 * 保存控制器的方法上的校验group class
 *
 * @author Young-Pastor
 */
public class RequestGroupContext {

    private static final ThreadLocal<Class<?>> GROUP_CLASS_HOLDER = new ThreadLocal<>();

    /**
     * 设置临时的group class
     *
     * @author Young-Pastor
     */
    public static void set(Class<?> groupValue) {
        GROUP_CLASS_HOLDER.set(groupValue);
    }

    /**
     * 获取临时缓存的group class
     *
     * @author Young-Pastor
     */
    public static Class<?> get() {
        return GROUP_CLASS_HOLDER.get();
    }

    /**
     * 清除临时缓存的group class
     *
     * @author Young-Pastor
     */
    public static void clear() {
        GROUP_CLASS_HOLDER.remove();
    }

}
