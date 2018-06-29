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
package com.github.skapral.wemake.json;

import com.github.skapral.wemake.web.usr.User;
import io.vavr.collection.List;
import java.util.stream.StreamSupport;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * User info, obtained from GitHub, in JSON format
 * 
 * @author skapral
 */
public class JsonGithubUserInfo implements $<JSONObject> {
    private final User user;

    /**
     * Ctor.
     * @param user User
     */
    public JsonGithubUserInfo(User user) {
        this.user = user;
    }

    @Override
    public final JSONObject $() {
        JSONObject userJson = new HttpJsonObject(() -> new HttpGet(
            "https://api.github.com/users/" + user.userLogin()
        )).$();
        JSONArray reposJson = new HttpJsonArray(() -> new HttpGet(
            userJson.getString("repos_url")
        )).$();
        JSONObject result = new JSONObject();
        result.put("name", userJson.get("name"));
        result.put("avatar_url", userJson.getString("avatar_url"));
        result.put("repos",
            StreamSupport.stream(reposJson.spliterator(), false)
                .map(o -> (JSONObject) o)
                .map(j -> {
                    JSONObject jj = new JSONObject();
                    jj.put("name", j.getString("name"));
                    jj.put("description", j.get("description"));
                    return jj;
                })
                .collect(List.collector())
                .foldLeft(new JSONArray(), (ja, jo) -> ja.put(jo))
        );
        return result;
    }
}
