package com.finalProject.finalProjectDevOnSpring.web.dto.search;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

@Data
@Accessors(chain = true)
public class BaseSearchCriteria {
    @Schema(description = "Номер страницы с записями. Начинается с 1")
    @NotNull(message = "Номер страницы не может быть пустым")
    @Min(value = 1, message = "Номер страницы не может быть меньше 1")
    private Integer page;
    @Schema(description = "Номер страницы с записями. Начинается с 1")
    @NotNull(message = "Размер страницы не может быть пустым")
    @Min(value = 1, message = "Размер страницы не может быть меньше 1")
    private Integer pageSize;
    @Schema(description = "Наименование поля, по которому будет производится сортировка")
    private String sortField;
    @Schema(description = "Сторона сортировки ASC & DESC")
    private Boolean asc;

    public PageRequest pageRequest() {
        if (StringUtils.hasText(getSortField())) {
            Sort sort = Sort.by(getSortField());
            sort = BooleanUtils.isFalse(getAsc()) ? sort.descending() : sort.ascending();
            return PageRequest.of(getPage() - 1, getPageSize(), sort);
        } else {
            return PageRequest.of(getPage() - 1, getPageSize());
        }
    }
}
