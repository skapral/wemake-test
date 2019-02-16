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

import com.pragmaticobjects.oo.memoized.core.Memory;
import java.nio.charset.Charset;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;

/**
 *
 * @author skapral
 */
public class HttpJsonArray implements JsonArray {
    private final static CloseableHttpClient HTTP_CLIENT = HttpClients.createDefault();
    private final Memory memory;
    private final HttpCall call;

    /**
     * Ctor.
     * @param memory Memory
     * @param call Http call
     */
    public HttpJsonArray(Memory memory, HttpCall call) {
        this.memory = memory;
        this.call = call;
    }

    @Override
    public final JSONArray jsonArray() {
        return memory.memoizedCalculation(
            this, HttpJsonArray::jsonArray,
            () -> {
                try (CloseableHttpResponse response = HTTP_CLIENT.execute(call.httpCall())) {
                    return new JSONArray(
                        IOUtils.toString(response.getEntity().getContent(), Charset.defaultCharset())
                    );
                } catch(Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        ).calculate();
    }
}
