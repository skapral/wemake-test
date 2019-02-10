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

import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author skapral
 */
public class JsonWemakeUserInfo implements Json {
    private final UserInfo userInfo;
    private final Multiple<Repo> userRepos;

    /**
     * Ctor.
     * 
     * @param userInfo User info
     * @param userRepos User repositories
     */
    public JsonWemakeUserInfo(UserInfo userInfo, Multiple<Repo> userRepos) {
        this.userInfo = userInfo;
        this.userRepos = userRepos;
    }

    @Override
    public final JSONObject json() {
        JSONObject result = new JSONObject();
        result.put("name", userInfo.userName());
        result.put("avatar_url", userInfo.userAvatar().toString());
        JSONArray repos = new JSONArray();
        for(Repo repo : userRepos.items()) {
            JSONObject repoJson = new JSONObject();
            repoJson.put("name", repo.repoName());
            repoJson.put("description", repo.repoDescription());
            repos.put(repoJson);
        }
        result.put("repos", repos);
        return result;
    }
}
