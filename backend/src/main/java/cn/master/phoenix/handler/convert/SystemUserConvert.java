package cn.master.phoenix.handler.convert;

import cn.master.phoenix.entity.SystemUser;
import cn.master.phoenix.handler.annotation.WithoutMetadata;
import cn.master.phoenix.payload.request.SaveUserRequest;
import org.mapstruct.Mapper;

/**
 * @author Created by 11's papa on 2025/6/13
 */
@Mapper(componentModel = "spring")
public interface SystemUserConvert {
    @WithoutMetadata
    SystemUser convert(SaveUserRequest request);
}
