/*
 * Copyright (c) 2018, Psikoi <https://github.com/psikoi>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.launcher;

import java.awt.Color;

/**
 * This class serves to hold commonly used UI colors.
 */
class ColorScheme
{
	/* Brand accent color (modern gold) */
	static final Color BRAND_BLUE = new Color(212, 175, 55); // re-themed to gold

	/* Brand accent with lowered opacity */
	static final Color BRAND_BLUE_TRANSPARENT = new Color(212, 175, 55, 120); // re-themed to gold (transparent)

	/* Dark UI neutrals (modernized black/graphite) */
	static final Color DARK_GRAY_COLOR = new Color(18, 18, 20);    // ~#121214
	static final Color DARKER_GRAY_COLOR = new Color(10, 10, 12);  // ~#0A0A0C
	static final Color MEDIUM_GRAY_COLOR = new Color(42, 42, 46);  // ~#2A2A2E

	/* The background color of the scrollbar's track */
	static final Color SCROLL_TRACK_COLOR = new Color(20, 20, 22); // ~#141416

	/* The color for the red progress bar (used in ge offers, farming tracker, etc) */
	static final Color PROGRESS_ERROR_COLOR = new Color(230, 30, 30);

	/* Additional themed colors (optional use) */
	static final Color BRAND_GOLD = new Color(212, 175, 55);
	static final Color BRAND_GOLD_DARK = new Color(168, 138, 44);
	static final Color TEXT_PRIMARY = new Color(235, 235, 235);
	static final Color TEXT_MUTED = new Color(170, 170, 175);
	static final Color BORDER_COLOR = new Color(36, 36, 40);
	static final Color HOVER_BG = new Color(28, 28, 32);
	static final Color SUCCESS_COLOR = new Color(48, 168, 92);
	static final Color WARNING_COLOR = new Color(235, 180, 52);
	static final Color PROGRESS_OK_COLOR = new Color(212, 175, 55); // gold progress/accent
}