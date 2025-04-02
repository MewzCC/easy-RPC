package com.newcc.config;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.AnnotatedType;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RpcServiceConfig {

    private Object service;
    private String version="";
    private String group="";

    public RpcServiceConfig(Object service) {
        this.service = service;
    }

    /**
     * 全类名 + version + group   com.easycc.api.UserService:1.0:user
     * **/
    public List<String> getRpcServiceName() {
        List<String> interFaceName = getInterFaceName();
        List<String> collect = interFaceName.stream()
                .map(interfaceName -> interfaceName+ version+ group) // 拼接版本号与组别
                .collect(Collectors.toList());

        return collect;
    }

    private List<String> getInterFaceName(){
        return Arrays.stream(service.getClass().getInterfaces())
                .map(Class::getCanonicalName)
                .collect(Collectors.toList());
    }
}
