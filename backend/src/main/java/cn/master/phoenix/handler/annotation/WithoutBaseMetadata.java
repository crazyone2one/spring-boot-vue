package cn.master.phoenix.handler.annotation;

import org.mapstruct.Mapping;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Created by 11's papa on 2025/6/13
 */
@Retention(RetentionPolicy.CLASS)
@Mapping(target = "createDate", ignore = true)
@Mapping(target = "updateDate", ignore = true)
@Mapping(target = "deleted", ignore = true)
public @interface WithoutBaseMetadata {
}
