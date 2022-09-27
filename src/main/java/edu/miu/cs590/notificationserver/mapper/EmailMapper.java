package edu.miu.cs590.notificationserver.mapper;

import edu.miu.cs590.notificationserver.dto.EmailDto;
import edu.miu.cs590.notificationserver.entity.Email;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface  EmailMapper {

    Email dtoToEmail(EmailDto emailDto);
}
