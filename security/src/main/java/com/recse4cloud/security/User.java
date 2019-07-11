package com.recse4cloud.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.security.auth.Subject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author author
 */
@Setter
@Getter
public class User extends AbstractAuthenticationToken implements UserDetails {

    private static final long serialVersionUID = 1546075317884L;

    /**
     * 用户编号
     * isNullAble:0
     */
    private String userNo;

    /**
     * 用户名
     * isNullAble:1
     */
    private String userName;

    /**
     * 密码
     * isNullAble:1
     */
    private String password;

    /**
     * 手机号码
     * isNullAble:1
     */
    private String phone;

    /**
     * 角色编号
     * isNullAble:1
     */
    private String roleNo;

    /**
     * 微信或支付宝open_id
     * isNullAble:1
     */
    private String openId;

    /**
     * 易护系统认证状态
     * isNullAble:1
     */
    private Integer ecState;

    /**
     * 权限
     */
    List<String> authList;
    /**
     * 认证用户信息
     */
    Object principal;
    /**
     * 认证密码信息
     */
    Object credentials;

    public User(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        super.setAuthenticated(true);
    }

    public User() {
        super(null);
    }

    public static List<SimpleGrantedAuthority> getAuthList(List<String> authNoList) {
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        if (authNoList != null && !authNoList.isEmpty()) {
            authNoList.forEach(a -> simpleGrantedAuthorities.add(new SimpleGrantedAuthority(a)));
        }
        return simpleGrantedAuthorities;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    /**
     * Returns true if the specified subject is implied by this principal.
     *
     * <p>The default implementation of this method returns true if
     * {@code subject} is non-null and contains at least one principal that
     * is equal to this principal.
     *
     * <p>Subclasses may override this with a different implementation, if
     * necessary.
     *
     * @param subject the {@code Subject}
     * @return true if {@code subject} is non-null and is
     * implied by this principal, or false otherwise.
     * @since 1.8
     */
    @Override
    public boolean implies(Subject subject) {
        return false;
    }
}
