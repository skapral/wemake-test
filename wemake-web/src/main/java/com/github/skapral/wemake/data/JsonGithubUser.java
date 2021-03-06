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
package com.github.skapral.wemake.data;

import com.github.skapral.wemake.web.usr.User;
import com.pragmaticobjects.oo.memoized.core.Memory;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;

/**
 *
 * @author skapral
 */
public class JsonGithubUser extends HttpJsonObject implements JsonGithubApiResponse {
    /**
     * Ctor.
     * @param memory memory
     * @param user user
     */
    public JsonGithubUser(Memory memory, User user) {
        super(
            memory,
            new HttpCall(user)
        );
    }
    
    /**
     * 
     */
    private static class HttpCall implements com.github.skapral.wemake.data.HttpCall {
        private final User user;

        /**
         * 
         * @param user 
         */
        public HttpCall(User user) {
            this.user = user;
        }

        @Override
        public final HttpRequestBase httpCall() {
            return new HttpGet(
                "https://api.github.com/users/" + user.userLogin()
            );
        }
    }
}
