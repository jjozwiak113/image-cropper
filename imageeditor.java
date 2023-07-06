package Cropper;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.*;
import javax.swing.*;
import javax.imageio.*;

public class imageeditor extends JFrame
{
	JLabel image = new JLabel();
	public imageeditor()
	{
		;
	}
	
	public imageeditor(Image im)
	{
		setSize(500, 500);
		setTitle("Image editor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout());
		setExtendedState(MAXIMIZED_BOTH);
		setUndecorated(true);
		image = new JLabel(new ImageIcon(im));
		add(image);
		setVisible(true);
	}	
}
