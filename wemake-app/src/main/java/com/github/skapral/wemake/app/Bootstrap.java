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
package com.github.skapral.wemake.app;

import com.github.skapral.jersey.se.SrvGrizzlyWithJersey;
import com.github.skapral.wemake.web.jersey.API;
import com.pragmaticobjects.oo.atom.anno.NotAtom;

/**
 * Application bootstrap class.
 * 
 * @author skapral
 */
@NotAtom
public class Bootstrap {
    /**
     * Entry point
     * 
     * @param args 
     * @throws Exception if something goes wrong
     */
    public static void main(String... args) throws Exception {
        new SrvGrizzlyWithJersey(
            new Cp_PORT(),
            new API()
        ).start();
        while(true) {
            System.in.read();
        }
    }
}
