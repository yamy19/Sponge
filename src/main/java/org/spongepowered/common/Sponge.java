/*
 * This file is part of Sponge, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered <https://www.spongepowered.org>
 * Copyright (c) contributors
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
package org.spongepowered.common;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import com.google.inject.Injector;
import org.apache.logging.log4j.Logger;
import org.spongepowered.api.Game;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Sponge {

    @Nullable
    private static Sponge instance;

    public static Sponge getInstance() {
        checkState(instance != null, "Sponge was not initialized");
        return instance;
    }

    private final Injector injector;
    private final Game game;
    private final Logger logger;
    private final SpongeImpl impl;

    @Inject
    public Sponge(Injector injector, Game game, Logger logger, SpongeImpl impl) {
        checkState(instance == null, "Sponge was already initialized");
        instance = this;

        this.injector = checkNotNull(injector, "injector");
        this.game = checkNotNull(game, "game");
        this.logger = checkNotNull(logger, "logger");
        this.impl = checkNotNull(impl, "impl");
    }

    public static Injector getInjector() {
        return getInstance().injector;
    }

    public static Game getGame() {
        return getInstance().game;
    }

    public static Logger getLogger() {
        return getInstance().logger;
    }

    public static SpongeImpl impl() {
        return getInstance().impl;
    }

}
