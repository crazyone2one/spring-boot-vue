package cn.master.phoenix.handler.annotation;

import org.mapstruct.Mapping;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Created by 11's papa on 2025/6/13
 */
@Retention(RetentionPolicy.CLASS)
@Mapping(target = "lastMineCode", ignore = true)
@Mapping(target = "roles", ignore = true)
@Mapping(target = "password", ignore = true)
@Mapping(target = "enabled", ignore = true)
@WithoutBaseMetadata
public @interface WithoutMetadata {
}
