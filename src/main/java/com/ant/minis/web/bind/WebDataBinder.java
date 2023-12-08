package com.ant.minis.web.bind;

import com.ant.minis.beans.PropertyEditor;
import com.ant.minis.beans.PropertyValues;
import com.ant.minis.util.WebUtils;
import com.ant.minis.web.BeanWrapperImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * web 请求参数 绑定为 java 对象
 * </p>
 *
 * @author Ant
 * @since 2023/11/30 17:47
 */
public class WebDataBinder {
    private Object target;

    private Class<?> clz;

    private String objectName;

    public WebDataBinder(Object target) {
        this(target, "");
    }

    public WebDataBinder(Object target, String targetName) {
        this.target = target;
        this.objectName = targetName;
        this.clz = this.target.getClass();
    }

    /***
     * <p>
     * 核心绑定方法，将request里面的参数绑定到目标对象属性上
     * </p>
     *
     * @param request
     * @return void
     */
    public void bind(HttpServletRequest request) {
        PropertyValues mpvs = assignParameters(request);

        addBindValues(mpvs, request);

        doBind(mpvs);
    }

    private void doBind(PropertyValues mpvs) {
        applyPropertyValues(mpvs);
    }

    /***
     * <p>
     * 实际将参数值与对象属性进行绑定的方法
     * </p>
     *
     * @param mpvs
     * @return void
     */
    protected void applyPropertyValues(PropertyValues mpvs) {
        getPropertyAccessor().setPropertyValues(mpvs);
    }

    /**
     * <p>
     * 设置属性值的工具
     * </p>
     *
     * @return BeanWrapperImpl
     */
    protected BeanWrapperImpl getPropertyAccessor() {
        return new BeanWrapperImpl(this.target);
    }

    /***
     * <p>
     * 将Request参数解析成PropertyValues
     * </p>
     *
     * @param request
     * @return com.ant.minis.beans.PropertyValues
     */
    private PropertyValues assignParameters(HttpServletRequest request) {
        Map<String, Object> map = WebUtils.getParametersStartingWith(request, "");
        return new PropertyValues(map);
    }

    protected void addBindValues(PropertyValues mpvs, HttpServletRequest request) {

    }

    public void registerCustomEditor(Class<?> requiredType, PropertyEditor propertyEditor) {
        getPropertyAccessor().registerCustomEditor(requiredType, propertyEditor);
    }
}
