package com.lemonfish.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author Chao Zhang
 * @version V1.0
 * @Package com.lemonfish.dto
 * @date 2020/5/10 10:54
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ArticleRelatedDTO implements Serializable {

    private static final long serialVersionUID = -6753681098068335252L;
    private Long id;
    private String title;
    private Integer likeCount;
    private Integer commentCount;
}
