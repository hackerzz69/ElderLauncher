/*
 * Copyright (c) 2019, TheStonedTurtle <https://github.com/TheStonedTurtle>
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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OpenOSRSSplashScreen extends JFrame
{
	private static OpenOSRSSplashScreen INSTANCE;
	static final Dimension FRAME_SIZE = new Dimension(600, 350);

	@Getter
	private final MessagePanel messagePanel = new MessagePanel();

	private OpenOSRSSplashScreen(String mode)
	{
		this.setTitle("OpenOSRS");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(FRAME_SIZE);
		this.setLayout(new BorderLayout());
		this.setUndecorated(true);
		this.setIconImage(ImageUtil.getResourceStreamFromClass(OpenOSRSSplashScreen.class, "openosrs.png"));

		final JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setPreferredSize(OpenOSRSSplashScreen.FRAME_SIZE);

		panel.add(new InfoPanel(mode), BorderLayout.EAST);
		panel.add(messagePanel, BorderLayout.WEST);

		this.setContentPane(panel);
		pack();

		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	static void setError(String title, String content)
	{
		if (INSTANCE != null)
		{
			INSTANCE.setErrorInstance(title, content);
		}
	}

	private void setErrorInstance(String title, String content)
	{
		messagePanel.setMessageContent(content);
		messagePanel.setMessageTitle("Error!");

		messagePanel.getBarLabel().setText(title);
		messagePanel.getBar().setBackground(ColorScheme.PROGRESS_ERROR_COLOR.darker());
		messagePanel.getBar().setForeground(ColorScheme.PROGRESS_ERROR_COLOR);

		this.getContentPane().revalidate();
		this.getContentPane().repaint();
	}

	private void setBarText(final String text)
	{
		final JProgressBar bar = messagePanel.getBar();
		bar.setString(text);
		bar.setStringPainted(text != null);
		bar.revalidate();
		bar.repaint();
	}

	private void setMessage(final String msg, final double value)
	{
		messagePanel.getBarLabel().setText(msg);
		messagePanel.getBar().setMaximum(1000);
		messagePanel.getBar().setValue((int) (value * 1000));
		setBarText(null);

		this.getContentPane().revalidate();
		this.getContentPane().repaint();
	}

	static void init(String mode)
	{
		try
		{
			SwingUtilities.invokeAndWait(() ->
			{
				if (INSTANCE != null)
				{
					return;
				}

				try
				{
					INSTANCE = new OpenOSRSSplashScreen(mode);
				}
				catch (Exception e)
				{
					log.warn("Unable to start splash screen", e);
				}
			});
		}
		catch (InterruptedException | InvocationTargetException bs)
		{
			throw new RuntimeException(bs);
		}
	}

	static void close()
	{
		SwingUtilities.invokeLater(() ->
		{
			if (INSTANCE == null)
			{
				return;
			}

			// The CLOSE_ALL_WINDOWS quit strategy on MacOS dispatches WINDOW_CLOSING events to each frame
			// from Window.getWindows. However, getWindows uses weak refs and relies on gc to remove windows
			// from its list, causing events to get dispatched to disposed frames. The frames handle the events
			// regardless of being disposed and will run the configured close operation. Set the close operation
			// to DO_NOTHING_ON_CLOSE prior to disposing to prevent this.
			INSTANCE.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			INSTANCE.setVisible(false);
			INSTANCE.dispose();
			INSTANCE = null;
		});
	}

	public static void stage(double startProgress, double endProgress, String progressText, int done, int total)
	{
		String progress = done + " / " + total;
		stage(startProgress + ((endProgress - startProgress) * done / total), progressText + " " + progress);
	}

	static void stage(double startProgress, double endProgress, String progressText, int done, int total, boolean mib)
	{
		String progress;
		if (mib)
		{
			final double Mb = 1000 * 1000;
			progress = String.format("%.1f / %.1f MB", done / Mb, total / Mb);
		}
		else
		{
			progress = done + " / " + total;
		}
		stage(startProgress + ((endProgress - startProgress) * done / total), progressText + " " + progress);
	}

	static void stage(double overallProgress, String progressText)
	{
		if (INSTANCE != null)
		{
			INSTANCE.setMessage(progressText, overallProgress);
		}
	}

	static void barMessage(String barMessage)
	{
		if (INSTANCE != null)
		{
			INSTANCE.setMessage(barMessage, 0);
		}
	}

	static void message(String message)
	{
		if (INSTANCE != null)
		{
			INSTANCE.messagePanel.setMessageContent(message);
		}
	}

	static List<JButton> addButtons()
	{
		if (INSTANCE != null)
		{
			return INSTANCE.messagePanel.addButtons();
		}

		return null;
	}
}