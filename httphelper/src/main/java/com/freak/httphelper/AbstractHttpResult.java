package com.freak.httphelper;


/**
 * 此方法是根据接口返回的数据去定义的，抽取出返回json数据的对象进行去解析
 *
 * @author freak
 * @date 2019/01/25
 * @param <T> result是接口数据的一个对象，bean类中的数据书写也是书写这个json数据的对象的字段即可
 */
public abstract class AbstractHttpResult<T> {
    /**
     * 获取接口请求成功数据
     *
     * @return
     */
    protected abstract T getResultData();

    /**
     * 设置int类型请求成功成功结果码
     *
     * @return
     */
    protected abstract int getIntSuccessCode();

    /**
     * 设置string类型请求成功成功结果码
     *
     * @return
     */
    protected abstract String getStringSuccessCode();

    /**
     * 设置错误提示信息
     *
     * @return
     */
    protected abstract String getResultErrorMsg();

    /**
     * 设置接口返回的int类型状态码
     *
     * @return
     */
    protected abstract int getIntResultCode();

    /**
     * 设置接口返回string类型的状态码
     *
     * @return
     */
    protected abstract String getStringResultCode();

    /**
     * 设置int类型的其他状态码
     *
     * @return
     */
    protected abstract int[] getIntOtherCode();

    /**
     * 设置string类型的其他状态码
     *
     * @return
     */
    protected abstract String[] getStringOtherCode();

}
