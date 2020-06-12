package com.lemonfish.dto;

import com.lemonfish.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 用户排行榜DTO
 *
 * @author Chao Zhang
 * @version V1.0
 * @Package com.lemonfish.dto
 * @date 2020/5/20 12:00
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserListDTO {
    /**
     * 用户信息
     */
    private User user;

    /**
     * 数据量
     */
    private Integer count;
}
