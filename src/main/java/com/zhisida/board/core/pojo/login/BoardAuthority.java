
package com.zhisida.board.core.pojo.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 * 用来包装一下角色名称
 *
 * @author Young-Pastor
 */
@Data
@AllArgsConstructor
public class BoardAuthority implements GrantedAuthority {

    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }

}
