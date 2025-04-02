package com.easycc.common;

import java.io.Serializable;

public class Invocation implements Serializable {

    /**
     * 接口名称。
     */
    private String interFaceName;
    /** 方法名称。 */
    private String methodName;


    /** 方法的参数类型 */
    private Class<?>[] parameterTypes;

    /** 传递给方法的实际参数值数组。 */
    private Object[] parameters;

    public Invocation(String interFaceName, String methodName, Class[] parameterTypes, Object[] parameters) {
        this.methodName = methodName;
        this.interFaceName = interFaceName;
        this.parameterTypes = parameterTypes;
        this.parameters = parameters;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getInterFaceName() {
        return interFaceName;
    }

    public void setInterFaceName(String interFaceName) {
        this.interFaceName = interFaceName;
    }

    public Class[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }
}
