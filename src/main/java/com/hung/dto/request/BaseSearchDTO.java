package com.hung.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author itsol.hungtt
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseSearchDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    private Integer page;

    @NotNull
    @Min(1)
    private Integer size;

    private boolean pagination;
}
