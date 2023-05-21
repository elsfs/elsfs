package org.elsfs.security.core.userdetails;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * @author zeng
 */

public class SecurityUser implements UserDetails, UserExtension, CredentialsContainer {

    private SecurityUser() {
    }

    private String userId;

    private String phone;

    private String email;

    /**
     * 是否父账号
     */
    private Boolean subAccount;

    // 用户昵称
    private String nickname;

    private Collection<? extends GrantedAuthority> authorities = new ArrayList<>();

    private String password;

    private String username;

    private String avatar;

    /**
     * 父帐户
     */
    private String parentAccount;

    public SecurityUser(String userId, String phone, String email, Boolean subAccount, String nickname,
            Collection<? extends GrantedAuthority> authorities, String password, String username,
            String parentAccount) {
        this.userId = userId;
        this.phone = phone;
        this.email = email;
        this.subAccount = subAccount;
        this.nickname = nickname;
        this.authorities = authorities;
        this.password = password;
        this.username = username;
        this.parentAccount = parentAccount;
    }

    @Override
    public Boolean isSubAccount() {
        return subAccount;
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

    /**
     *
     */
    @Override
    public void eraseCredentials() {
        this.password = null;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getSubAccount() {
        return subAccount;
    }

    public void setSubAccount(Boolean subAccount) {
        this.subAccount = subAccount;
    }

    @Override
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String getParentAccount() {
        return parentAccount;
    }

    public void setParentAccount(String parentAccount) {
        this.parentAccount = parentAccount;
    }

    @Override
    public String toString() {
        return "SecurityUser{" + "userId='" + userId + '\'' + ", phone='" + phone + '\'' + ", email='" + email + '\''
                + ", subAccount=" + subAccount + ", nickname='" + nickname + '\'' + ", authorities=" + authorities
                + ", password='" + password + '\'' + ", username='" + username + '\'' + ", avatar='" + avatar + '\''
                + ", parentAccount='" + parentAccount + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        SecurityUser that = (SecurityUser) o;
        return Objects.equals(userId, that.userId) && Objects.equals(phone, that.phone)
                && Objects.equals(email, that.email) && Objects.equals(subAccount, that.subAccount)
                && Objects.equals(nickname, that.nickname) && Objects.equals(authorities, that.authorities)
                && Objects.equals(password, that.password) && Objects.equals(username, that.username)
                && Objects.equals(avatar, that.avatar) && Objects.equals(parentAccount, that.parentAccount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, phone, email, subAccount, nickname, authorities, password, username, avatar,
                parentAccount);
    }

}
