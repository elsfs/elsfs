/*
 * Copyright 2020-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.elsfs.security.core.federated;

import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * {@link Consumer} 要执行 {@link OAuth2User}.
 * <p>
 * 用户存储库OAuth2用户处理程序
 *
 * @author Steve Riesenberg
 * @since 0.2.3
 */
public final class UserRepositoryOAuth2UserHandler implements Consumer<OAuth2User> {

    private final UserRepository userRepository = new UserRepository();

    @Override
    public void accept(OAuth2User user) {
        // 首次身份验证时捕获本地数据存储中的用户
        if (this.userRepository.findByName(user.getName()) == null) {
            System.out.println("保存首次用户：name=" + user.getName() + ", claims=" + user.getAttributes() + ", authorities="
                    + user.getAuthorities());
            this.userRepository.save(user);
        }
    }

    static class UserRepository {

        private final Map<String, OAuth2User> userCache = new ConcurrentHashMap<>();

        public OAuth2User findByName(String name) {
            return this.userCache.get(name);
        }

        public void save(OAuth2User oauth2User) {
            this.userCache.put(oauth2User.getName(), oauth2User);
        }

    }
    /**
     * gitee 保存首次用户：name=zengmingyong, claims={login=zengmingyong, id=42780443,
     * node_id=MDQ6VXNlcjQyNzgwNDQz,
     * avatar_url=https://avatars.githubusercontent.com/u/42780443?v=4, gravatar_id=,
     * url=https://api.github.com/users/zengmingyong,
     * html_url=https://github.com/zengmingyong,
     * followers_url=https://api.github.com/users/zengmingyong/followers,
     * following_url=https://api.github.com/users/zengmingyong/following{/other_user},
     * gists_url=https://api.github.com/users/zengmingyong/gists{/gist_id},
     * starred_url=https://api.github.com/users/zengmingyong/starred{/owner}{/repo},
     * subscriptions_url=https://api.github.com/users/zengmingyong/subscriptions,
     * organizations_url=https://api.github.com/users/zengmingyong/orgs,
     * repos_url=https://api.github.com/users/zengmingyong/repos,
     * events_url=https://api.github.com/users/zengmingyong/events{/privacy},
     * received_events_url=https://api.github.com/users/zengmingyong/received_events,
     * type=User, site_admin=false, name=null, company=null, blog=, location=null,
     * email=null, hireable=null, bio=null, twitter_username=null, public_repos=2,
     * public_gists=0, followers=0, following=0, created_at=2018-08-28T17:03:20Z,
     * updated_at=2023-03-08T09:25:22Z, private_gists=0, total_private_repos=0,
     * owned_private_repos=0, disk_usage=1513, collaborators=0,
     * two_factor_authentication=false, plan={name=free, space=976562499, collaborators=0,
     * private_repos=10000}}, authorities=[OAUTH2_USER, SCOPE_read:user,user:email]
     */

}
