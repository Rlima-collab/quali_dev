package org.ormi.priv.tfa.orderflow.productregistry.infra.web.dto;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.ormi.priv.tfa.orderflow.productregistry.application.ProductCommand.RegisterProductCommand;
import org.ormi.priv.tfa.orderflow.contracts.productregistry.v1.write.RegisterProductCommandDto;
import org.ormi.priv.tfa.orderflow.kernel.product.SkuIdMapper;

/**
 * MapStruct mapper for command DTOs.
 * <p>
 * Converts between REST API command DTOs and internal command objects.
 * Handles SKU ID mapping via SkuIdMapper.
 * </p>
 * 
 * @author Order Flow Team
 * @version 1.0
 */
@Mapper(
    componentModel = "cdi",
    builder = @Builder(disableBuilder = true),
    uses = { SkuIdMapper.class },
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CommandDtoMapper {
    public RegisterProductCommand toCommand(RegisterProductCommandDto dto);
    public RegisterProductCommandDto toDto(RegisterProductCommand command);
}
