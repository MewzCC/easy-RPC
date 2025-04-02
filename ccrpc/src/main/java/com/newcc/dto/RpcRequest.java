package com.newcc.dto;

import cn.hutool.core.util.StrUtil;
import lombok.*;

import java.io.Serializable;
@Builder
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RpcRequest implements Serializable {
    /** 请求的唯一标识ID。 */
    private String reqId;

    /** 调用的服务接口名称。 */
    private String interfaceName;

    /** 调用的方法名称。 */
    private String methodName;

    /** 方法调用的参数数组。 */
    private Object[] params;

    /** 方法参数的类型数组，用于类型校验或反射调用。 */
    private Class<?>[] paramTypes;

    /** 接口版本号，用于区分不同版本的服务实现。 */
    private String version;

    /** 服务分组名称，用于区分同一接口在不同分组下的不同实现。 */
    private String group;


    public String getRpcServiceName() {
        return getInterfaceName()
               + StrUtil.blankToDefault(getGroup(), StrUtil.EMPTY)
                + StrUtil.blankToDefault(getVersion(), StrUtil.EMPTY);
    }
 }
