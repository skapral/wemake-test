/*
 * The MIT License
 *
 * Copyright 2019 skapral.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.github.skapral.wemake.web.jersey;

import com.github.skapral.wemake.data.JsonGithubUser;
import com.github.skapral.wemake.data.JsonGithubUserRepositories;
import com.github.skapral.wemake.data.JsonWemakeUserInfo;
import com.github.skapral.wemake.data.MultipleRepos;
import com.github.skapral.wemake.data.UserAvatarFromJson;
import com.github.skapral.wemake.data.UserNameFromJson;
import com.github.skapral.wemake.web.usr.UserAuthenticated;
import com.github.skapral.wemake.data.Json;
import com.github.skapral.wemake.data.UserInfoComposite;
import com.pragmaticobjects.oo.atom.anno.NotAtom;
import com.pragmaticobjects.oo.memoized.core.Memory;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

/**
 * Index endpoint
 * 
 * @author skapral
 */
@NotAtom
@Path("/user")
@Produces("application/json")
public class UserEndpoint {
    @Inject private Memory memory;
    
    /**
     * @param request Request
     * @return user info in json format
     */
    @GET
    public Json getUserInfo(@Context HttpServletRequest request) {
        return new JsonWemakeUserInfo(
            new UserInfoComposite(
                new UserNameFromJson(
                    new JsonGithubUser(
                        memory,
                        new UserAuthenticated(request)
                    )
                ),
                new UserAvatarFromJson(
                    new JsonGithubUser(
                        memory,
                        new UserAuthenticated(request)
                    )
                )
            ),
            new MultipleRepos(
                new JsonGithubUserRepositories(
                    memory,
                    new JsonGithubUser(
                        memory,
                        new UserAuthenticated(request)
                    )
                )
            )
        );
    }
}
