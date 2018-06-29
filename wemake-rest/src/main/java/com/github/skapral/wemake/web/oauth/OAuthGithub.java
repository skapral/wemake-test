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
package com.github.skapral.wemake.web.oauth;

import com.github.scribejava.apis.GitHubApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.github.skapral.config.ConfigProperty;
import com.github.skapral.config.CpStatic;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import org.json.JSONObject;

/**
 * GitHub OAuth
 * @author skapral
 */
public class OAuthGithub implements OAuth {
    private static final OAuth20ServiceSupplier SERVICE_SUPPLIER = new OAuth20ServiceSupplier(
        //new CpEnvironment("GITHUB_API_KEY"),
        //new CpEnvironment("GITHUB_API_SECRET")
        new CpStatic("2034d02941320dceb0d2"),
        new CpStatic("337aa3171a2c7ee8b559ef41a6689a627e118960")
    );

    @Override
    public final String authUrl() {
        return SERVICE_SUPPLIER.get().getAuthorizationUrl();
    }

    @Override
    public final String authenticateUser(String code) {
        try {
            OAuth20Service service = SERVICE_SUPPLIER.get();
            final OAuth2AccessToken accessToken = service.getAccessToken(code);
            final OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.github.com/user");
            service.signRequest(accessToken, request);
            final com.github.scribejava.core.model.Response resp = service.execute(request);
            final String login = new JSONObject(resp.getBody()).getString("login");
            Objects.requireNonNull(login, "Cannot obtain login - " + resp.getCode() + ": " + resp.getBody());
            return login;
        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    
    /**
     * OAuth 2.0 Scribe service supplier, with memoization
     */
    private static class OAuth20ServiceSupplier implements Supplier<OAuth20Service> {
        private final ConfigProperty apiKey;
        private final ConfigProperty apiSecret;
        private final AtomicReference<OAuth20Service> serviceRef;

        /**
         * Ctor.
         * @param apiKey
         * @param apiSecret
         * @param serviceRef 
         */
        private OAuth20ServiceSupplier(ConfigProperty apiKey, ConfigProperty apiSecret, AtomicReference<OAuth20Service> serviceRef) {
            this.apiKey = apiKey;
            this.apiSecret = apiSecret;
            this.serviceRef = serviceRef;
        }

        /**
         * Ctor.
         * @param apiKey
         * @param apiSecret 
         */
        public OAuth20ServiceSupplier(ConfigProperty apiKey, ConfigProperty apiSecret) {
            this(
                apiKey,
                apiSecret,
                new AtomicReference<OAuth20Service>()
            );
        }
        
        public final OAuth20Service get() {
            OAuth20Service val = serviceRef.get();
            if (val == null) {
                synchronized(serviceRef) {
                    val = serviceRef.get();
                    if (val == null) {
                        val = new ServiceBuilder(apiKey.optionalValue().get())
                            .apiSecret(apiSecret.optionalValue().get())
                            .callback("")
                            .build(GitHubApi.instance());
                        serviceRef.set(val);
                    }
                }
            }
            return val;
        }
        
        
    }
}
